package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Utils;

public class MyOrderCoinAdapter extends BaseAdapter<CoinOrderBean> {

    public MyOrderCoinAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    private MyOrderCoinAdapter.OnTradeRevokeListener onTradeRevokeListener;

    public interface OnTradeRevokeListener {
        void onRevoke(CoinOrderBean coinOrderBean, int position);
    }


    public MyOrderCoinAdapter(@NonNull Context context, int layoutId,
                              @Nullable List<CoinOrderBean> dataSet) {
        super(context, layoutId, dataSet);
    }


    public void setOnTradeRevokeListener(MyOrderCoinAdapter.OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, final CoinOrderBean cob, int position) {
        if (cob.getType() == 1) {
            holder.setTextColor(R.id.tv_mycenter_order_name, context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_mycenter_order_name, context.getResources().getString(R.string.buy) + " " + cob.getBillPair());
        } else {
            holder.setTextColor(R.id.tv_mycenter_order_name, context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_mycenter_order_name, context.getResources().getString(R.string.sale) + " " + cob.getBillPair());
        }
        holder.setText(R.id.tv_mycenter_order_time, DateTimeUtils.correctSvrTime(cob.getCreateDate()));
        holder.setText(R.id.tv_mycenter_order_coin_price, EmptyUtils.getBigDecimalValue(cob.getEntrustPrice()));
        holder.setText(R.id.tv_mycenter_order_coin_count, Utils.myAccountFormat(cob.getEntrustCount()));
        holder.setText(R.id.tv_mycenter_order_coin_reachedCount, Utils.myAccountFormat(cob.getReachedCount()));

        if (cob.getEntrustStatus() == 1) {
            holder.setTextColor(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getColor(R.color.text_red));
            holder.setBackgroundRes(R.id.tv_mycenter_order_coin_entrustStatus, R.drawable.rectangle_red);
            holder.setText(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getString(R.string.revoke));
        } else if (cob.getEntrustStatus() == 2) {
            holder.setBackgroundRes(R.id.tv_mycenter_order_coin_entrustStatus, R.drawable.rectangle_red);
            holder.setTextColor(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getString(R.string.revoke));
        } else if (cob.getEntrustStatus() == 3) {
            holder.setBackgroundRes(R.id.tv_mycenter_order_coin_entrustStatus, R.color.bg_white);
            holder.setTextColor(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getString(R.string.entrustStatus_3));
        } else if (cob.getEntrustStatus() == 4) {
            holder.setBackgroundRes(R.id.tv_mycenter_order_coin_entrustStatus, R.color.bg_white);
            holder.setTextColor(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getColor(R.color.text_light));
            holder.setText(R.id.tv_mycenter_order_coin_entrustStatus, context.getResources().getString(R.string.entrustStatus_4));
        }
        if (cob.getEntrustStatus() == 1 || cob.getEntrustStatus() == 2) {
            holder.setOnClickListener(R.id.tv_mycenter_order_coin_entrustStatus, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTradeRevokeListener != null) {
                        onTradeRevokeListener.onRevoke(cob, position);
                    }
                }
            });
        }

    }
}