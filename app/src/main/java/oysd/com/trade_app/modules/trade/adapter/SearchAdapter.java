package oysd.com.trade_app.modules.trade.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;

public class SearchAdapter extends BaseAdapter<MarketListBean> {
    private SearchAdapter.OnTradeRevokeListener onTradeRevokeListener;

    public interface OnTradeRevokeListener {
        void onRevoke(MarketListBean searchBean, int position);
    }

    public SearchAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public SearchAdapter(@NonNull Context context, int layoutId,
                               @Nullable List<MarketListBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    public void setOnTradeRevokeListener(SearchAdapter.OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, final MarketListBean mb, final int position) {

        holder.setText(R.id.tv_trade_search_coinName,mb.getMarketName());

        if(mb.isSelect()) {
            holder.setImageResource(R.id.tv_trade_search_select, R.mipmap.is_select_ok);
        }else{
            holder.setImageResource(R.id.tv_trade_search_select, R.mipmap.is_select_no);
        }

        holder.setOnClickListener(R.id.tv_trade_search_select, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTradeRevokeListener != null) {
                    onTradeRevokeListener.onRevoke(mb, position);
                }
            }
        });
    }
}
