package oysd.com.trade_app.modules.trade.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.BigDecimal;
import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.SocketOrder;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class TradeOrderSaleAdapter extends BaseAdapter<SocketOrder.SellEntrustListBean> {


    public TradeOrderSaleAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public TradeOrderSaleAdapter(@NonNull Context context, int layoutId,
                                 @Nullable List<SocketOrder.SellEntrustListBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, SocketOrder.SellEntrustListBean bb, int position) {
            holder.setText(R.id.tv_order_trade_index,(++position)+"");
            holder.setTextColor(R.id.tv_list_price,context.getResources().getColor(R.color.text_red));
            if(bb.getPrice().compareTo(BigDecimal.ZERO)==0) {
                holder.setText(R.id.tv_list_price,  "--");
                holder.setText(R.id.tv_list_count,  "--");
            }else{
                holder.setText(R.id.tv_list_price, EmptyUtils.getBigDecimalValue(bb.getPrice()));
                holder.setText(R.id.tv_list_count, Utils.getKtotal(bb.getVolume()));
            }
    }

}