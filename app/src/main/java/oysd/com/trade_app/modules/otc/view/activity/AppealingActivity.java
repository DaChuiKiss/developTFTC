package oysd.com.trade_app.modules.otc.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.otc.bean.AppealInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.AppealingContract;
import oysd.com.trade_app.modules.otc.presenter.AppealingPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.GlideUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;
import oysd.com.trade_app.widget.dialog.IosConfirmDialog;
import oysd.com.trade_app.widget.dialog.IosNormalDialog;
import oysd.com.trade_app.widget.dialog.ShowImageDialog;

/**
 * 申诉中页面。
 * Created by Liam on 2018/7/18
 */
public class AppealingActivity
        extends BaseToolActivity implements AppealingContract.View {

    public static final String EXTRA_ORDER_ID = "order_id";


    @BindView(R.id.tv_appealing_notice)
    TextView tvNotice;

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
    TextView tvAppealTime;

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

    @BindView(R.id.tv_otc_order_appeal_reason)
    TextView tvAppealReason;

    @BindView(R.id.iv_appealing_img1)
    ImageView ivAppealImg1;

    @BindView(R.id.iv_appealing_img2)
    ImageView ivAppealImg2;

    @BindView(R.id.iv_appealing_img3)
    ImageView ivAppealImg3;

    @BindView(R.id.btn_appealing_cancel_trade)
    Button btnCancelDeal;

    @BindView(R.id.btn_appealing_cancel_appeal)
    Button btnCancelAppeal;

    AppealingContract.Presenter presenter = new AppealingPresenter(this);

    private int orderId;
    private int buyerOrSeller = 0;
    private int complainant = 0;
    private OtcFinishedOrderDetailBean orderDetailBean = null;
    private AppealInfoBean appealInfoBean = null;


    @Override
    protected int setView() {
        return R.layout.activity_appealing;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.title_appealing);

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
        btnCancelDeal.setOnClickListener(this);
        btnCancelAppeal.setOnClickListener(this);

        ivAppealImg1.setOnClickListener(this);
        ivAppealImg2.setOnClickListener(this);
        ivAppealImg3.setOnClickListener(this);

        ViewHelper.setCopyListener(context, ivCopyBuyerOrSeller, tvBuyerOrSeller);
        ViewHelper.setCopyListener(context, ivCopyOrderNumber, tvOrderNumber);
        ViewHelper.setCopyListener(context, ivCopyRefer, tvRefer);
    }

    @Override
    protected void initData() {
        presenter.getFinishedOrderDetail(orderId);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (Utils.isFastClick()) return;

        List<String> imageUrls = appealInfoBean.getAppealImageUrls();
        switch (v.getId()) {
            case R.id.btn_appealing_cancel_trade:
                clickLeftButton();
                break;

            case R.id.btn_appealing_cancel_appeal:
                clickRightButton();
                break;

            case R.id.iv_appealing_img1:
                if (imageUrls.size() >= 1) {
                    showImage(imageUrls.get(0));
                }
                break;

            case R.id.iv_appealing_img2:
                if (imageUrls.size() >= 2) {
                    showImage(imageUrls.get(1));
                }
                break;

            case R.id.iv_appealing_img3:
                if (imageUrls.size() >= 3) {
                    showImage(imageUrls.get(2));
                }
                break;

            default:
                break;
        }
    }

    // 大图展示图片。
    private void showImage(String imageUrl) {
        new ShowImageDialog(context, imageUrl);
    }

    private void clickLeftButton() {
        if (buyerOrSeller == 1) {
            cancelOrder();

        } else if (buyerOrSeller == 2) {
            if (complainant == 2) {
                cancelAppeal();
            }
        }
    }

    private void clickRightButton() {
        if (buyerOrSeller == 1) {
            if (complainant == 1) {
                cancelAppeal();
            }

        } else if (buyerOrSeller == 2) {
            releaseCoins();
        }
    }

    // 放行 coin
    private void releaseCoins() {
        new IosNormalDialog(context)
                .setTitleText(context.getString(R.string.release) + orderDetailBean.getCoinName())
                .hasEdit(true)
                .isPassword(true)
                .setEditHint(R.string.trade_pwd_hit)
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

    private void cancelAppeal() {
        new IosConfirmDialog(context)
                .setContent(context.getString(R.string.cancel_appeal_ensure))
                .setOnClickListener(new IosConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirmClick(IosConfirmDialog dialog) {
                        if (appealInfoBean == null) return;
                        presenter.cancelAppeal(appealInfoBean.getId());
                    }

                    @Override
                    public void onCancelClick(IosConfirmDialog dialog) {
                    }
                });
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
                        presenter.cancelOrder(orderId);
                    }

                    @Override
                    public void onCancelClick(IosNormalDialog dialog) {
                    }
                })
                .init();
    }

    private void updateView(OtcFinishedOrderDetailBean bean) {
        this.buyerOrSeller = bean.getBuyerOrSeller();

        if (buyerOrSeller == 1) {
            tvBuyerOrSellerText.setText(R.string.seller);
            tvBuyerOrSeller.setText(bean.getSellerName());

        } else if (buyerOrSeller == 2) {
            tvBuyerOrSellerText.setText(R.string.buyer);
            tvBuyerOrSeller.setText(bean.getBuyerName());
        }

        String currencySymbol = ViewHelper.getCurrencySymbolByName(bean.getSettlementCurrency());
        String strPrice = currencySymbol + FormatUtils.to2(bean.getTransactionPrice());
        tvPrice.setText(strPrice);

        String strQuantity = Utils.myAccountFormat(BigDecimal.valueOf(bean.getTransationQuantity())) + bean.getCoinName();
        tvQuantity.setText(strQuantity);

        String strAmount = currencySymbol + FormatUtils.to2(bean.getTransationAmount());
        tvMoney.setText(strAmount);

        tvPaymentMethod.setText(bean.getPaymentMethod());
        tvOrderNumber.setText(bean.getOnlineOrderNo());
        tvRefer.setText(bean.getRefOrderNo());
    }

    private void updateView(AppealInfoBean bean) {
        this.complainant = bean.getComplainant();

        String name;
        String strNotice = "";

        if (buyerOrSeller == 1) {
            // 买家
            name = orderDetailBean.getSellerName();
            if (complainant == 1) {
                strNotice = getString(R.string.notice_have_appealed_wait, name);
                btnCancelDeal.setText(R.string.cancel_exchange);
                btnCancelAppeal.setText(R.string.cancel_appeal);

            } else if (complainant == 2) {
                strNotice = getString(R.string.notice_have_been_appealed, name);
                btnCancelDeal.setText(R.string.cancel_exchange);
                btnCancelAppeal.setVisibility(View.GONE);
            }

        } else if (buyerOrSeller == 2) {
            // 卖家
            name = orderDetailBean.getBuyerName();
            if (complainant == 2) {
                strNotice = getString(R.string.notice_have_appealed_wait, name);
                btnCancelDeal.setText(R.string.cancel_appeal);
                btnCancelAppeal.setText(context.getString(R.string.release) + orderDetailBean.getCoinName());

            } else if (complainant == 1) {
                strNotice = getString(R.string.notice_have_been_appealed, name);
                btnCancelDeal.setVisibility(View.GONE);
                btnCancelAppeal.setText(context.getString(R.string.release) + orderDetailBean.getCoinName());
            }
        }

        tvNotice.setText(strNotice);
        tvAppealTime.setText(bean.getCreateDate());
        tvAppealReason.setText(bean.getAppealGrounds());

        List<String> appealImages = bean.getAppealImageUrls();
        if (EmptyUtils.isNotEmpty(appealImages)) {
            for (int i = 0; i < appealImages.size(); i++) {
                if (i == 0) {
                    GlideUtils.loadImage(context, ivAppealImg1, appealImages.get(i));
                } else if (i == 1) {
                    GlideUtils.loadImage(context, ivAppealImg2, appealImages.get(i));
                } else if (i == 2) {
                    GlideUtils.loadImage(context, ivAppealImg3, appealImages.get(i));
                }
            }
        }
    }

    @Override
    public void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response) {
        // 因为设置 AppealInfo 时需要 OrderDetailBean 中的某些值，所以需要在它返回后
        // 再请求 AppealInfo。如果同时请求，会因为返回的先后不确定性，造成某些数据不正常。
        presenter.getAppealInfo(orderId);

        this.orderDetailBean = response;
        updateView(response);
    }

    @Override
    public void getFinishedOrderDetailFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void getAppealInfoSuccess(AppealInfoBean response) {
        this.appealInfoBean = response;
        updateView(response);
    }

    @Override
    public void getAppealInfoFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void cancelOrderSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_cancel_order_suc);

        // goto cancel activity.
        Bundle bundle = new Bundle();
        bundle.putInt(TradeCanceledActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(TradeCanceledActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(AppealingActivity.class);
    }

    @Override
    public void cancelOrderFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void cancelAppealSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_cancel_appeal_suc);

        // goto Paid activity
        Bundle bundle = new Bundle();
        bundle.putInt(PaidActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(PaidActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(AppealingActivity.class);
    }

    @Override
    public void cancelAppealFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void releaseCoinsSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_release_coin_suc);

        // goto complete activity
        Bundle bundle = new Bundle();
        bundle.putInt(TradeCompletedActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(TradeCompletedActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(AppealingActivity.class);
    }

    @Override
    public void releaseCoinsFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
