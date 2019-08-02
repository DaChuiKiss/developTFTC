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
import oysd.com.trade_app.modules.trade.bean.DealBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class TradeDealAdapter extends BaseAdapter<DealBean> {


    public TradeDealAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public TradeDealAdapter(@NonNull Context context, int layoutId,
                            @Nullable List<DealBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, DealBean db, int position) {

        if(db.getType()==1){
            holder.setTextColor(R.id.tv_list_price,context.getResources().getColor(R.color.text_pink));
            holder.setTextColor(R.id.tv_list_type,context.getResources().getColor(R.color.text_pink));
            if(db.getPrice().compareTo(BigDecimal.ZERO)==0){
                holder.setText(R.id.tv_list_type,"--");
            }else {
                holder.setText(R.id.tv_list_type, context.getResources().getString(R.string.buy));
            }
        }else{
            holder.setTextColor(R.id.tv_list_type,context.getResources().getColor(R.color.text_red));
            if(db.getPrice().compareTo(BigDecimal.ZERO)==0){
                holder.setText(R.id.tv_list_type,"--");
            }else {
                holder.setText(R.id.tv_list_type, context.getResources().getString(R.string.sale));
            }
            holder.setTextColor(R.id.tv_list_price,context.getResources().getColor(R.color.text_red));
        }
        if(db.getPrice().compareTo(BigDecimal.ZERO)==0){
            holder.setText(R.id.tv_list_price, "--");
            holder.setText(R.id.tv_list_count, "--");
            holder.setText(R.id.tv_list_time, "--");
        }else {
            holder.setText(R.id.tv_list_price, EmptyUtils.getBigDecimalValue(db.getPrice()));
            holder.setText(R.id.tv_list_count, Utils.myAccountFormat(db.getCount()));
            holder.setText(R.id.tv_list_time, db.getTime());
        }
    }


}