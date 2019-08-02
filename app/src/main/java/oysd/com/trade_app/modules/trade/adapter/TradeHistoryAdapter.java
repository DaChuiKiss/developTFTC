package oysd.com.trade_app.modules.trade.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.trade.bean.HistoryBean;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Utils;

public class TradeHistoryAdapter extends BaseAdapter<HistoryBean> {

    private TradeHistoryAdapter.OnTradeRevokeListener onTradeRevokeListener;

    public interface OnTradeRevokeListener {
        void onRevoke(HistoryBean historyBean, int position);
    }

    public void setOnTradeRevokeListener(TradeHistoryAdapter.OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    public TradeHistoryAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public TradeHistoryAdapter(@NonNull Context context, int layoutId,
                       @Nullable List<HistoryBean> dataSet) {
        super(context, layoutId, dataSet);
    }


    @Override
    protected void onBind(BaseHolder holder, final HistoryBean historyBean, int position) {

        if(historyBean.getType()==1){
            holder.setTextColor(R.id.tv_trade_history_name,context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_trade_history_name,context.getResources().getString(R.string.buy)+" "+historyBean.getBillPair());
        }else{
            holder.setTextColor(R.id.tv_trade_history_name,context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_trade_history_name,context.getResources().getString(R.string.sale)+" "+historyBean.getBillPair());
        }

        holder.setText(R.id.tv_trade_history_time,context.getResources().getString(R.string.create_time)+ DateTimeUtils.correctSvrTime(historyBean.getCreateDate()));
        holder.setText(R.id.tv_trade_history_entrustCount, Utils.myAccountFormat(historyBean.getEntrustCount()));
        holder.setText(R.id.tv_trade_history_entrustPrice, EmptyUtils.getBigDecimalValue(historyBean.getEntrustPrice()));
        holder.setText(R.id.tv_trade_history_reachedCount, Utils.myAccountFormat(historyBean.getReachedCount()));

        holder.setText(R.id.tv_history_avgReachedPrice, Utils.myAccountFormat(historyBean.getAvgReachedPrice()));
        holder.setText(R.id.tv_history_reachedCount, Utils.myAccountFormat(historyBean.getReachedCount()));
        if(historyBean.getReachedCount()!=null&&historyBean.getAvgReachedPrice()!=null) {
            holder.setText(R.id.tv_history_turnover, Utils.myAccountFormat(historyBean.getReachedCount().multiply(historyBean.getAvgReachedPrice())));
        }else{
            holder.setText(R.id.tv_history_turnover,"0");
        }

        holder.setOnClickListener(R.id.tv_show_history_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (onTradeRevokeListener != null) {
//                    onTradeRevokeListener.onRevoke(historyBean, position);
//                }
                if(holder.isVisable(R.id.ll_history_detail).getVisibility()==View.GONE) {
                    holder.isVisable(R.id.ll_history_detail).setVisibility(View.VISIBLE);
                    holder.setdrawRightImg(R.id.tv_show_history_detail, context.getResources().getDrawable(R.mipmap.hide));
                }else{
                    holder.isVisable(R.id.ll_history_detail).setVisibility(View.GONE);
                    holder.setdrawRightImg(R.id.tv_show_history_detail, context.getResources().getDrawable(R.mipmap.show));
                }

            }
        });

    }

    public final class ViewHolder {
        public TextView tv_trade_history_name;
        public TextView tv_trade_history_time;
        public TextView tv_trade_history_entrustCount;
        public TextView tv_trade_history_entrustPrice;
        public TextView tv_trade_history_reachedCount;
    }
}