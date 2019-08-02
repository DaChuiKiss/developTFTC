package oysd.com.trade_app.modules.otc.view.activity;

import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.TradeCompletedContract;
import oysd.com.trade_app.modules.otc.presenter.TradeCompletedPresenter;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.ViewHelper;


/**
 * 交易已完成页面.
 * Created by Liam on 2018/8/25
 */
public class TradeCompletedActivity
        extends BaseToolActivity implements TradeCompletedContract.View {

    public static final String EXTRA_ORDER_ID = "order_id";


    @BindView(R.id.tv_otc_order_price)
    TextView tvPrice;

    @BindView(R.id.tv_otc_order_quantity)
    TextView tvQuantity;

    @BindView(R.id.tv_otc_order_amount)
    TextView tvMoney;

    @BindView(R.id.tv_otc_order_seller_buyer_text)
    TextView tvBuyerOrPayerText;

    @BindView(R.id.tv_otc_order_seller_buyer)
    TextView tvBuyerOrPayer;

    @BindView(R.id.iv_otc_order_seller_buyer_copy)
    ImageView ivCopyBuyerOrPayer;

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


    TradeCompletedContract.Presenter presenter = new TradeCompletedPresenter(this);

    private int orderId;


    @Override
    protected int setView() {
        return R.layout.activity_trade_completed;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.title_completed);

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
        ViewHelper.setCopyListener(context, ivCopyBuyerOrPayer, tvBuyerOrPayer);
        ViewHelper.setCopyListener(context, ivCopyOrderNumber, tvOrderNumber);
        ViewHelper.setCopyListener(context, ivCopyRefer, tvRefer);
    }

    @Override
    protected void initData() {
        presenter.getFinishedOrderDetail(orderId);
    }

    // 获取网络数据后,更新页面显示.
    private void updateView(OtcFinishedOrderDetailBean bean) {
        String currencySymbol = ViewHelper.getCurrencySymbolByName(bean.getSettlementCurrency());

        String strPrice = currencySymbol + FormatUtils.to2(bean.getTransactionPrice());
        tvPrice.setText(strPrice);

        String strQuantity = FormatUtils.to8(bean.getTransationQuantity()) + " " + bean.getCoinName();
        tvQuantity.setText(strQuantity);

        String strAmount = currencySymbol + FormatUtils.to2(bean.getTransationAmount());
        tvMoney.setText(strAmount);

        int buyerOrSeller = bean.getBuyerOrSeller();
        if (buyerOrSeller == 1) {
            tvBuyerOrPayerText.setText(R.string.seller);
            tvBuyerOrPayer.setText(bean.getSellerName());

        } else if (buyerOrSeller == 2) {
            tvBuyerOrPayerText.setText(R.string.buyer);
            tvBuyerOrPayer.setText(bean.getBuyerName());
        }

        tvOrderTime.setText(bean.getCreateDate());
        tvPaymentMethod.setText(bean.getPaymentMethod());
        tvOrderNumber.setText(bean.getOnlineOrderNo());
        tvRefer.setText(bean.getRefOrderNo());
    }

    @Override
    public void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response) {
        updateView(response);
    }

    @Override
    public void getFinishedOrderDetailFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
