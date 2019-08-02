package oysd.com.trade_app.modules.otc.view.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.otc.bean.DetailPaymentInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.PaymentMethodBean;
import oysd.com.trade_app.modules.otc.contract.UnpaidContract;
import oysd.com.trade_app.modules.otc.presenter.UnpaidPresenter;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.GlideUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PickerUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.ViewHelper;
import oysd.com.trade_app.widget.dialog.IosConfirmDialog;
import oysd.com.trade_app.widget.dialog.IosNormalDialog;
import oysd.com.trade_app.widget.dialog.ShowImageDialog;

/**
 * 未付款页面。
 * Created by Liam on 2018/7/18
 */
public class UnpaidActivity
        extends BaseToolActivity implements UnpaidContract.View {

    public static final String EXTRA_ORDER_ID = "order_id";


    @BindView(R.id.tv_unpaid_notice)
    TextView tvNotice;

    @BindView(R.id.tv_otc_order_price)
    TextView tvPrice;

    @BindView(R.id.tv_otc_order_quantity)
    TextView tvQuantity;

    @BindView(R.id.tv_otc_order_amount)
    TextView tvMoney;

    @BindView(R.id.ll_unpaid_container_seller)
    LinearLayout llSellContainer;

    @BindView(R.id.ll_unpaid_container_buyer)
    LinearLayout llBuyerContainer;

    @BindView(R.id.ll_unpaid_container_bank)
    LinearLayout llBankContainer;

    @BindView(R.id.ll_unpaid_container_net)
    LinearLayout llNetContainer;


    // seller info
    @BindView(R.id.tv_otc_order_payer)
    TextView tvPayer;

    @BindView(R.id.iv_otc_order_payer_copy)
    ImageView ivCopyPayer;

    @BindView(R.id.tv_otc_order_number_4seller)
    TextView tvOrderNumSeller;

    @BindView(R.id.iv_otc_order_number_4seller_copy)
    ImageView ivCopyOrderNumSeller;

    @BindView(R.id.tv_otc_order_refer_4seller)
    TextView tvOrderReferSeller;

    @BindView(R.id.iv_otc_order_refer_4seller_copy)
    ImageView ivCopyOrderReferSeller;


    // buyer info
    @BindView(R.id.tv_unpaid_payment_method)
    TextView tvPaymentMethod;

    @BindView(R.id.tv_otc_order_bank)
    TextView tvBank;

    @BindView(R.id.tv_otc_order_card_num)
    TextView tvCardNo;

    @BindView(R.id.iv_otc_order_card_num_copy)
    ImageView ivCopyCardNo;

    @BindView(R.id.tv_otc_order_account)
    TextView tvNetAccount;

    @BindView(R.id.iv_otc_order_account_copy)
    ImageView ivCopyNetAccount;

    @BindView(R.id.iv_otc_order_QR_code)
    ImageView ivQRCode;

    @BindView(R.id.tv_otc_order_receiver)
    TextView tvReceiver;

    @BindView(R.id.iv_otc_order_receiver_copy)
    ImageView ivCopyReceiver;

    @BindView(R.id.tv_otc_order_number)
    TextView tvOrderNumber;

    @BindView(R.id.iv_otc_order_number_copy)
    ImageView ivCopyOrderNumber;

    @BindView(R.id.tv_otc_order_refer)
    TextView tvRefer;

    @BindView(R.id.iv_otc_order_refer_copy)
    ImageView ivCopyRefer;


    @BindView(R.id.btn_unpaid_cancel)
    Button btnCancel;

    @BindView(R.id.btn_unpaid_pay)
    Button btnPay;

    UnpaidContract.Presenter presenter = new UnpaidPresenter(this);

    private int orderId;
    // 当前进入的用户是买家还是卖家. 1. 买家 2.卖家
    private int buyerOrSeller = 0;
    private String strAmount;
    private String strQuantity;
    private OtcOrderDetailBean orderDetailBean = null;
    private DetailPaymentInfoBean detailPaymentInfoBean = null;

    private List<PaymentMethodBean> paymentMethodBeans;
    private List<String> paymentMethodNames = new ArrayList<>();
    private int selectedPaymentMethodCode = 0;

    private CountDownTimer countDownTimer;
    // 是否订单超过 15 分钟。
    private boolean isTimeout = false;

    // 当前选择的支付方式是 bank 支付还是 网络账户 支付：1. net Account ， 2：bank account.
    private int isBankOrNet = 0;


    @Override
    protected int setView() {
        return R.layout.activity_unpaid;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        setEnableRefresh(false);
        setTitleText(R.string.title_unpaid);

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
        btnPay.setOnClickListener(this);
        ivQRCode.setOnClickListener(this);

        // set copy
        ViewHelper.setCopyListener(context, ivCopyPayer, tvPayer);
        ViewHelper.setCopyListener(context, ivCopyOrderNumSeller, tvOrderNumSeller);
        ViewHelper.setCopyListener(context, ivCopyOrderReferSeller, tvOrderReferSeller);
        ViewHelper.setCopyListener(context, ivCopyCardNo, tvCardNo);
        ViewHelper.setCopyListener(context, ivCopyNetAccount, tvNetAccount);
        ViewHelper.setCopyListener(context, ivCopyReceiver, tvReceiver);
        ViewHelper.setCopyListener(context, ivCopyOrderNumber, tvOrderNumber);
        ViewHelper.setCopyListener(context, ivCopyRefer, tvRefer);
    }

    @Override
    protected void initData() {
        presenter.getOrderDetail(orderId);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_unpaid_payment_method:
                PickerUtils.showOneOptionPicker(context, paymentMethodNames, (opt1, opt2, opt3, view) -> {
                    tvPaymentMethod.setText(paymentMethodNames.get(opt1));
                    selectedPaymentMethodCode = paymentMethodBeans.get(opt1).getPaymentMethod();
                    selectPaymentMethod();
                });
                break;

            case R.id.btn_unpaid_cancel:
                if (isTimeout) {
                    ToastUtils.showShort(context, R.string.toast_order_timeout);
                    return;
                }
                cancelOrder();
                break;

            case R.id.btn_unpaid_pay:
                if (isTimeout) {
                    ToastUtils.showShort(context, R.string.toast_order_timeout);
                    return;
                }
                submitAlreadyPaid();
                break;

            case R.id.iv_otc_order_QR_code:
                // 点击放大显示 二维码 图片.
                new ShowImageDialog(context, detailPaymentInfoBean.getReceivablesCodeUrl());
                break;

            default:
                break;
        }
    }

    private void submitAlreadyPaid() {
        // 如果 orderDetailBean 为 null，表示数据加载失败。
        if (orderDetailBean == null) return;

        String content = String.format("确认以 %1$s 购买 %2$s?", strAmount, strQuantity);

        new IosConfirmDialog(context)
                .setContent(content)
                .setOnClickListener(new IosConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirmClick(IosConfirmDialog dialog) {
                        presenter.userAlreadyPaid(orderId, selectedPaymentMethodCode);
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
                        UserInfoBean userInfoBean = InfoProvider.getInstance().getUserInfo();
                        if (userInfoBean == null) {
                            Logger.e("can not get UserInfo.");
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

    private void updateView(OtcOrderDetailBean bean) {
        this.buyerOrSeller = bean.getCurrentLoginUser();

        if (buyerOrSeller == 1) {
            llBuyerContainer.setVisibility(View.VISIBLE);
            llSellContainer.setVisibility(View.GONE);
            // 默认展示银行卡支付
            llBankContainer.setVisibility(View.VISIBLE);
            llNetContainer.setVisibility(View.GONE);

            paymentMethodBeans = bean.getSellerUserPaymentMethod();
            for (PaymentMethodBean paymentMethodBean : paymentMethodBeans) {
                paymentMethodNames.add(paymentMethodBean.getPaymentMethodName());
            }

            // 在此时对 paymentMethod 设置监听器。
            tvPaymentMethod.setOnClickListener(this);
            tvPaymentMethod.setText(paymentMethodNames.get(0));
            selectedPaymentMethodCode = paymentMethodBeans.get(0).getPaymentMethod();
            selectPaymentMethod();

            tvNotice.setText(R.string.notice_have_ordered_pls_pay);
            tvReceiver.setText(bean.getDrawee());
            tvOrderNumber.setText(bean.getOrderNo());
            tvRefer.setText(bean.getReferenceNumber());

            setCountDownTimer(bean);

        } else if (buyerOrSeller == 2) {
            llSellContainer.setVisibility(View.VISIBLE);
            llBuyerContainer.setVisibility(View.GONE);

            tvNotice.setText(R.string.notice_wait_buyer_pay);
            tvPayer.setText(bean.getPayee());
            tvOrderNumSeller.setText(bean.getOrderNo());
            tvOrderReferSeller.setText(bean.getReferenceNumber());
        }

        String currencySymbol = ViewHelper.getCurrencySymbolByName(bean.getSettlementCurrency());
        String strPrice = currencySymbol + FormatUtils.to2(bean.getTransactionPrice());
        tvPrice.setText(strPrice);

        this.strQuantity = FormatUtils.to8(bean.getTransationQuantity()) + " " + bean.getCoinName();
        tvQuantity.setText(this.strQuantity);

        this.strAmount = currencySymbol + FormatUtils.to2(bean.getTransationAmount());
        tvMoney.setText(this.strAmount);
    }

    // 设置倒计时
    private void setCountDownTimer(OtcOrderDetailBean bean) {
        // 使用 createTimestamp 来替代，将来会改成时间戳
        long diffMills = System.currentTimeMillis() - bean.getCreateDateTimeStamp();

        long quarter = 15 * DateTimeUtils.MINUTE;
        int minute = 0;
        int second = 0;

        // 大于 15 mins
        if (diffMills >= quarter) {
            String strLeftTime = getString(R.string.left_time, minute, second);
            btnPay.setText(strLeftTime);
            setTimeout();
            return;
        }

        // 小于 15 mins ，启动 CountDownTimer.
        long leftMills = quarter - diffMills;
        countDownTimer = new CountDownTimer(leftMills, DateTimeUtils.SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                int leftSecond = (int) millisUntilFinished / 1000;
                int minute = leftSecond / 60;
                int second = leftSecond % 60;

                String strLeftTime = getString(R.string.left_time, minute, second);
                btnPay.setText(strLeftTime);
            }

            @Override
            public void onFinish() {
                // 刷新数据。
                // presenter.getOrderDetail(orderId);

                setTimeout();
            }
        }.start();

    }

    /**
     * 设置当前为 timeout 状态 。
     * 当为 timeout 状态后，会将按钮置灰，同时按钮功能将被禁止。
     */
    private void setTimeout() {
        isTimeout = true;
        btnPay.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_grey));
    }

    private void selectPaymentMethod() {
        switch (selectedPaymentMethodCode) {
            case 1:
            case 2:
            case 3:
            case 4:
                // net account
                llNetContainer.setVisibility(View.VISIBLE);
                llBankContainer.setVisibility(View.GONE);
                isBankOrNet = 1;
                break;

            case 5:
            case 6:
                // bank
                llBankContainer.setVisibility(View.VISIBLE);
                llNetContainer.setVisibility(View.GONE);
                isBankOrNet = 2;
                break;
            default:
                break;
        }

        // Request detail payment info.
        presenter.getDetailPaymentInfo(orderId, selectedPaymentMethodCode);
    }


    @Override
    public void getOrderDetailSuccess(OtcOrderDetailBean response) {
        this.orderDetailBean = response;
        updateView(response);
    }

    @Override
    public void getOrderDetailFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void getDetailPaymentInfoSuccess(DetailPaymentInfoBean response) {
        this.detailPaymentInfoBean = response;

        if (isBankOrNet == 1) {
            tvNetAccount.setText(response.getAccountNumber());
            GlideUtils.loadImage(context, ivQRCode, response.getReceivablesCodeUrl());

        } else if (isBankOrNet == 2) {
            tvBank.setText(response.getBankName());
            tvCardNo.setText(response.getBankCardNumber());
        }
    }

    @Override
    public void getDetailPaymentInfoFailed(int code, String msg) {
        Logger.e("code = " + code + " , " + msg);
    }

    @Override
    public void cancelOrderSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_cancel_order_suc);
        finish();
    }

    @Override
    public void cancelOrderFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void userAlreadyPaidSuccess() {
        Bundle bundle = new Bundle();
        bundle.putInt(PaidActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(PaidActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(UnpaidActivity.class);
    }

    @Override
    public void userAlreadyPaidFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }

}
