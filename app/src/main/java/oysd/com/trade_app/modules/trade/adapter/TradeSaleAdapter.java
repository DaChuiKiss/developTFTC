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
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.SocketOrder;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class TradeSaleAdapter extends BaseAdapter<SocketOrder.SellEntrustListBean> {


    public TradeSaleAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public TradeSaleAdapter(@NonNull Context context, int layoutId,
                            @Nullable List<SocketOrder.SellEntrustListBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, SocketOrder.SellEntrustListBean bb, int position) {
            String index=null;
            if(position==4){
                index="1";
            }else if(position==3){
                index="2";
            }else   if(position==2){
                index="3";
            }else   if(position==1){
                index="4";
            }else   if(position==0){
                index="5";
            }
            holder.setTextColor(R.id.tv_list_price,context.getResources().getColor(R.color.text_red));
        if(bb.getPrice().compareTo(BigDecimal.ZERO)==0){
            holder.setText(R.id.tv_list_price, "--");
            holder.setText(R.id.tv_list_count,"--");
        }else {
            holder.setText(R.id.tv_list_price, EmptyUtils.getBigDecimalValue(bb.getPrice()));
            holder.setText(R.id.tv_list_count, Utils.getKtotal(bb.getVolume()));
        }
            holder.setText(R.id.tv_list_index,index+"");
    }

}