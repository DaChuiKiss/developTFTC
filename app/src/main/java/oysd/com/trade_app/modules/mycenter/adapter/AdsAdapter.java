package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class AdsAdapter extends BaseAdapter<OtcAdBean> {

    private OnTradeRevokeListener onTradeRevokeListener;

    public AdsAdapter(@NonNull Context context, int layoutId) {
        super(context, layoutId);
    }

    public interface OnTradeRevokeListener {
        void onRevoke(OtcAdBean otcAdBean, int position);
    }

    public void setOnTradeRevokeListener(OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, OtcAdBean otcAdBean, int position) {
        if (otcAdBean.getTransactionType() == 1) {
            holder.setTextColor(R.id.tv_mycenter_ads_type, context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_mycenter_ads_type, context.getResources().getString(R.string.buy));
        } else {
            holder.setTextColor(R.id.tv_mycenter_ads_type, context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_mycenter_ads_type, context.getResources().getString(R.string.sale));
        }
        if (!TextUtils.isEmpty(otcAdBean.getCoinName())) {
            holder.setText(R.id.tv_mycenter_ads_coinType, otcAdBean.getCoinName());
        }
        holder.setText(R.id.tv_mycenter_ads_coinPrice, FormatUtils.to2(otcAdBean.getOnlineOrderPrice()));
        holder.setText(R.id.tv_mycenter_ads_coinCount, Utils.myAccountFormat(otcAdBean.getOnlineOrderQuantity()));

        holder.setOnClickListener(R.id.bt_mycenter_ads_action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTradeRevokeListener != null) {
                    onTradeRevokeListener.onRevoke(otcAdBean, position);
                }
            }
        });

    }

}
