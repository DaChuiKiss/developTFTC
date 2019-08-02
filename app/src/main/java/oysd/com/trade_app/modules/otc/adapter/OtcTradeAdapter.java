package oysd.com.trade_app.modules.otc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.PaymentMethodBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ViewHelper;

/**
 * Otc trade adapter.
 * Created by Liam on 2018/8/14
 */
public class OtcTradeAdapter extends BaseAdapter<OtcAdBean> {

    private String type;
    private OnItemClickListener onItemClickListener;

    public OtcTradeAdapter(@NonNull Context context, @NonNull String type) {
        this(context, type, null);
    }

    public OtcTradeAdapter(@NonNull Context context, @NonNull String type,
                           @Nullable List<OtcAdBean> dataSet) {
        super(context, R.layout.item_otc_trade, dataSet);
        this.type = type;
    }

    // 设置被点击时回调的 listener.
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBind(BaseHolder holder, OtcAdBean otcAdBean, int position) {
        String currencySymbol = ViewHelper.getCurrencySymbolById(otcAdBean.getSettlementCurrency());

        holder.setText(R.id.tv_otc_trade_name, otcAdBean.getNickName());

        String quantity = context.getString(R.string.quantity_with_num_value,
                FormatUtils.to8(otcAdBean.getBalance())) + " " + otcAdBean.getCoinName();
        holder.setText(R.id.tv_otc_trade_quantity, quantity);

        holder.setText(R.id.tv_otc_trade_price, currencySymbol + FormatUtils.to2(otcAdBean.getOnlineOrderPrice()));
        holder.setText(R.id.tv_otc_trade_limit_text, context.getString(R.string.limit_amount, currencySymbol));

        holder.setText(R.id.tv_otc_trade_limit,
                FormatUtils.to6NoComma(otcAdBean.getMinAmountTransation().multiply(otcAdBean.getOnlineOrderPrice()))
                        + "~"
                        + FormatUtils.to8NoComma(otcAdBean.getMaxAmountTranscation().multiply(otcAdBean.getOnlineOrderPrice())));
        holder.setText(R.id.tv_otc_trade_type, type);

        List<PaymentMethodBean> list = otcAdBean.getUserPaymentMethod();
        if (EmptyUtils.isEmpty(list)) {
            Logger.e("User payment method is empty.");
        }

        // 预先将所有的 image 都隐藏，防止 holder 保持有之前的状态，出现紊乱。
        if (true) {
            holder.setGone(R.id.iv_otc_trade_icon_zfb, true)
                    .setGone(R.id.iv_otc_trade_icon_wechat, true)
                    .setGone(R.id.iv_otc_trade_icon_paypal, true)
                    .setGone(R.id.iv_otc_trade_icon_epay, true)
                    .setGone(R.id.iv_otc_trade_icon_bank, true)
                    .setGone(R.id.iv_otc_trade_icon_usd, true);
        }

        for (PaymentMethodBean bean : list) {
            switch (bean.getPaymentMethod()) {
                case 1:
                    holder.setVisible(R.id.iv_otc_trade_icon_zfb, true);
                    break;
                case 2:
                    holder.setVisible(R.id.iv_otc_trade_icon_wechat, true);
                    break;
                case 3:
                    holder.setVisible(R.id.iv_otc_trade_icon_paypal, true);
                    break;
                case 4:
                    holder.setVisible(R.id.iv_otc_trade_icon_epay, true);
                    break;
                case 5:
                    holder.setVisible(R.id.iv_otc_trade_icon_bank, true);
                    break;
                case 6:
                    holder.setVisible(R.id.iv_otc_trade_icon_usd, true);
                    break;
                default:
                    break;
            }
        }

        holder.setOnClickListener(R.id.tv_otc_trade_type, v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(otcAdBean);
            }
        });

    }

    /**
     * RecyclerView 中 Item 被点击时的回调。
     */
    public interface OnItemClickListener {
        void onClick(OtcAdBean otcAdBean);
    }

}
