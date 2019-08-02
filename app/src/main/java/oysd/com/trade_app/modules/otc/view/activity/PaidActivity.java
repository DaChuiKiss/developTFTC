package oysd.com.trade_app.modules.otc.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.PaidContract;
import oysd.com.trade_app.modules.otc.presenter.PaidPresenter;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;
import oysd.com.trade_app.widget.dialog.IosNormalDialog;

/**
 * 已付款页面。
 * Created by Liam on 2018/7/18
 */
public class PaidActivity
        extends BaseToolActivity implements PaidContract.View {

    public static final String EXTRA_ORDER_ID = "order_id";


    @BindView(R.id.tv_otc_order_price)
    TextView tvPrice;

    @BindView(R.id.tv_otc_order_quantity)
    TextView tvQuantity;

    @BindView(R.id.tv_otc_order_amount)
    TextView tvMoney;

    @BindView(R.id.tv_otc_order_seller_buyer_text)
    TextView tvBuyerOrSellerText;

    @BindView(R.id.tv_otc_order_seller_buyer)
    TextView tvBuyerOrSeller;

    @BindView(R.id.iv_otc_order_seller_buyer_copy)
    ImageView ivCopyBuyerOrSeller;

    @BindView(R.id.tv_otc_order_time)
    TextView tvOrderTime;

    @BindView(R.id.otc_order_payment_method)
    TextView tvPaymentMethod;

    @BindView(R.id.tv_otc_order_number)
    TextView tvOrderNumber;

    @BindView(R.id.iv_otc_order_number_copy)
    ImageView ivCopyOrderNumber;

    @BindView(R.id.tv_otc_order_refer)
    TextView tvRefer;

    @BindView(R.id.iv_otc_order_refer_copy)
    ImageView ivCopyRefer;

    @BindView(R.id.tv_paid_notice)
    TextView tvNotice;

    @BindView(R.id.btn_paid_cancel)
    Button btnCancel;

    @BindView(R.id.btn_paid_appeal)
    Button btnAppeal;

    PaidContract.Presenter presenter = new PaidPresenter(this);

    private int orderId;
    private int buyerOrSeller;

    private OtcOrderDetailBean detailBean = null;
    private OtcFinishedOrderDetailBean orderDetailBean = null;


    @Override
    protected int setView() {
        return R.layout.activity_paid;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.title_paid);

        if (extras == null) {
            throw new IllegalArgumentException("must pass orderId value.");
        }
        orderId = extras.getInt(EXTRA_ORDER_ID);
        if (orderId == 0) {
            throw new IllegalArgumentException("must pass orderId value.");
        }
    }

    @Override
    protected void initClicks() {
        btnCancel.setOnClickListener(this);
        btnAppeal.setOnClickListener(this);

        ViewHelper.setCopyListener(context, ivCopyBuyerOrSeller, tvBuyerOrSeller);
        ViewHelper.setCopyListener(context, ivCopyOrderNumber, tvOrderNumber);
        ViewHelper.setCopyListener(context, ivCopyRefer, tvRefer);
    }

    @Override
    protected void initData() {
        // 获取订单详情。
        // presenter.getOrderDetail(orderId);
        presenter.getFinishedOrderDetail(orderId);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.btn_paid_cancel:
                    leftButtonClick();
                    break;

                case R.id.btn_paid_appeal:
                    rightButtonClick();
                    break;

                default:
                    break;
            }
        }
    }

    private void leftButtonClick() {
        if (buyerOrSeller == 1) {
            cancelOrder();

        } else if (buyerOrSeller == 2) {
            Bundle bundle = new Bundle();
            bundle.putInt(SubmitAppealActivity.EXTRA_ORDER_ID, orderId);
            quickStartActivity(SubmitAppealActivity.class, bundle);
        }
    }

    private void rightButtonClick() {
        if (buyerOrSeller == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt(SubmitAppealActivity.EXTRA_ORDER_ID, orderId);
            quickStartActivity(SubmitAppealActivity.class, bundle);

        } else if (buyerOrSeller == 2) {
            releaseCoins();
        }
    }

    private void releaseCoins() {
        new IosNormalDialog(context)
                .setTitleText(context.getString(R.string.release) + orderDetailBean.getCoinName())
                .hasEdit(true)
                .isPassword(true)
                .setEditHint("password")
                .setOnDialogClickListener(new IosNormalDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirmClick(IosNormalDialog dialog) {
                        String password = dialog.getInputString();

                        Map<String, Object> params = new HashMap<>();
                        params.put("id", orderId);
                        params.put("password", Utils.MD5(password));
                        presenter.releaseCoins(params);
                    }

                    @Override
                    public void onCancelClick(IosNormalDialog dialog) {
                    }
                })
                .init();
    }

    private void cancelOrder() {
        new IosNormalDialog(context)
                .setTitleText(R.string.cancel_order)
                .setContent(R.string.cancel_order_pls_confirm)
                .hasEdit(true)
                .isPassword(false)
                .setOnDialogClickListener(new IosNormalDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirmClick(IosNormalDialog dialog) {
                        UserInfoBean userInfoBean = InfoProvider.getInstance().getUserInfo();
                        if (userInfoBean == null) {
                            Logger.e("can not get UserInfo from InfoProvider.");
                            return;
                        }

                        String input = dialog.getInputString();
                        if (input.equals(userInfoBean.getNickName())) {
                            presenter.cancelOrder(orderId);
                        } else {
                            ToastUtils.showLong(context, R.string.toast_wrong_username);
                        }
                    }

                    @Override
                    public void onCancelClick(IosNormalDialog dialog) {
                    }
                })
                .init();
    }


    private void updateView(OtcFinishedOrderDetailBean bean) {
        this.buyerOrSeller = bean.getBuyerOrSeller();

        String coinName = bean.getCoinName();

        if (buyerOrSeller == 1) {
            String strNotice = getString(R.string.notice_have_paid_wait_release, coinName);
            tvNotice.setText(strNotice);
            tvBuyerOrSellerText.setText(R.string.seller);
            tvBuyerOrSeller.setText(bean.getSellerName());
            btnCancel.setText(R.string.cancel_exchange);
            btnAppeal.setText(R.string.submit_appeal);

        } else if (buyerOrSeller == 2) {
            tvNotice.setText(R.string.notice_have_paid_pls_release);
            tvBuyerOrSellerText.setText(R.string.buyer);
            tvBuyerOrSeller.setText(bean.getBuyerName());
            btnCancel.setText(R.string.appeal_immediately);
            btnAppeal.setText(context.getString(R.string.release) + coinName);
        }

        String currencySymbol = ViewHelper.getCurrencySymbolByName(bean.getSettlementCurrency());
        String strPrice = currencySymbol + FormatUtils.to2(bean.getTransactionPrice());
        tvPrice.setText(strPrice);

        String strQuantity = FormatUtils.to8(bean.getTransationQuantity()) + coinName;
        tvQuantity.setText(strQuantity);

        String strAmount = currencySymbol + FormatUtils.to2(bean.getTransationAmount());
        tvMoney.setText(strAmount);

        tvOrderTime.setText(bean.getCreateDate());
        tvPaymentMethod.setText(bean.getPaymentMethod());
        tvOrderNumber.setText(bean.getOnlineOrderNo());
        tvRefer.setText(bean.getRefOrderNo());
    }

    @Override
    public void getOrderDetailSuccess(OtcOrderDetailBean response) {
        this.detailBean = response;

        // 因为 OtcOrderDetailBean 中缺少 付款方式 这个字段，所以需要使用另外一个接口
        // "订单交易完成买卖双方查看订单信息" 来获取数据.
        // updateView(response);
    }

    @Override
    public void getOrderDetailFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response) {
        this.orderDetailBean = response;
        updateView(response);
    }

    @Override
    public void getFinishedOrderDetailFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void cancelOrderSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_cancel_order_suc);

        // goto cancel activity.
        Bundle bundle = new Bundle();
        bundle.putInt(TradeCanceledActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(TradeCanceledActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(PaidActivity.class);
    }

    @Override
    public void cancelOrderFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void releaseCoinsSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_release_coin_suc);

        // goto complete activity
        Bundle bundle = new Bundle();
        bundle.putInt(TradeCompletedActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(TradeCompletedActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(PaidActivity.class);
    }

    @Override
    public void releaseCoinsFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
