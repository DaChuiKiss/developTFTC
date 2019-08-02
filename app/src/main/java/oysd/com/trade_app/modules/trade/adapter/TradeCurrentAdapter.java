package oysd.com.trade_app.modules.trade.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class TradeCurrentAdapter extends BaseAdapter<CurrentBean> {

    private OnTradeRevokeListener onTradeRevokeListener;

    public interface OnTradeRevokeListener {
        void onRevoke(CurrentBean currentBean, int position);
    }

    public TradeCurrentAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public TradeCurrentAdapter(@NonNull Context context, int layoutId,
                        @Nullable List<CurrentBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    public void setOnTradeRevokeListener(OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, final CurrentBean currentBean, final int position) {
        if(currentBean.getType()==1) {
            holder.setTextColor(R.id.tv_trade_current_name,context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_trade_current_name,context.getResources().getString(R.string.buy)+" "+currentBean.getBillPair());
        }else{
            holder.setTextColor(R.id.tv_trade_current_name,context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_trade_current_name,context.getResources().getString(R.string.sale)+" "+currentBean.getBillPair());
        }

        holder.setText(R.id.tv_trade_current_time,context.getResources().getString(R.string.create_time)+ DateTimeUtils.correctSvrTime(currentBean.getCreateDate()));

        holder.setText(R.id.tv_trade_current_entrustCount, Utils.myAccountFormat(currentBean.getEntrustCount()));
        holder.setText(R.id.tv_trade_current_entrustPrice, EmptyUtils.getBigDecimalValue(currentBean.getEntrustPrice()));
        holder.setText(R.id.tv_trade_current_reachedCount, Utils.myAccountFormat(currentBean.getReachedCount()));

        if(currentBean.getEntrustStatus()==1){
            holder.setTextColor(R.id.tv_trade_current_revoke,context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_trade_current_revoke,context.getResources().getString(R.string.revoke));
        }else if(currentBean.getEntrustStatus()==2){
            holder.setTextColor(R.id.tv_trade_current_revoke,context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_trade_current_revoke,context.getResources().getString(R.string.revoke));
        }else if(currentBean.getEntrustStatus()==3){
            holder.setTextColor(R.id.tv_trade_current_revoke,context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_trade_current_revoke,context.getResources().getString(R.string.entrustStatus_3));
        }else if(currentBean.getEntrustStatus()==4){
            holder.setTextColor(R.id.tv_trade_current_revoke,context.getResources().getColor(R.color.text_light));
            holder.setText(R.id.tv_trade_current_revoke,context.getResources().getString(R.string.entrustStatus_4));
        }

        holder.setOnClickListener(R.id.tv_trade_current_revoke, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTradeRevokeListener != null) {
                    onTradeRevokeListener.onRevoke(currentBean, position);
                }
            }
        });
    }

}