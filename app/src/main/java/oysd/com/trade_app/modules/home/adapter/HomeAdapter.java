package oysd.com.trade_app.modules.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import java.math.BigDecimal;
import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.home.bean.UpListBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.SpannableEditor;
import oysd.com.trade_app.util.Utils;

/**
 * Home adapter for display market ranking list.
 * Created by Liam on 2018/8/2
 */
public class HomeAdapter extends BaseAdapter<UpListBean> {

    public HomeAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public HomeAdapter(@NonNull Context context, int layoutId,
                       @Nullable List<UpListBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, UpListBean upListBean, int position) {
        String marketName = upListBean.getMarketName();
        String[] strings = marketName.split("[/]");

        SpannableEditor spannableEditor = new SpannableEditor(context, marketName);
        spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_normal), strings[0]);
        spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_grey), strings[1]);
        spannableEditor.setTextSize(Utils.sp2px(context, 15), strings[0]);
        spannableEditor.setTextSize(Utils.sp2px(context, 13), strings[1]);

        if(position==0){
            holder.setBackgroundRes(R.id.tv_home_rank,R.mipmap.paixu1);
        }else if(position==1){
            holder.setBackgroundRes(R.id.tv_home_rank,R.mipmap.paixu2);
        }else if(position==2){
            holder.setBackgroundRes(R.id.tv_home_rank,R.mipmap.paixu3);
        }else{
            holder.setBackgroundRes(R.id.tv_home_rank,R.mipmap.paixu4);
        }
        holder.setText(R.id.tv_home_rank,(position+1)+"");
        holder.setText(R.id.coinName, spannableEditor.build());
        holder.setText(R.id.coinUSDTprice, EmptyUtils.getBigDecimalValue(upListBean.getPrice()));

        holder.setText(R.id.coinRMBprice, upListBean.getSymbol() +FormatUtils.to2(EmptyUtils.getBigDecimalValue(upListBean.getLegarTenderVal())));

        if(upListBean.getBtcVolume()!=null) {
            holder.setText(R.id.volum_24h, "24H:" + Utils.getKtotal(upListBean.getBtcVolume()));
        }

        if (upListBean.getPriceChangeRatio().compareTo(BigDecimal.ZERO)>=0) {
            holder.setTextColor(R.id.coin_chg,context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.coin_chg, "+"+FormatUtils.to2(upListBean.getPriceChangeRatio())+ "%");
        } else {
            holder.setTextColor(R.id.coin_chg,context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.coin_chg, FormatUtils.to2(upListBean.getPriceChangeRatio())+ "%");
        }
    }

}
