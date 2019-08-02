package oysd.com.trade_app.modules.trade.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.Api;

import java.math.BigDecimal;
import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.SpannableEditor;
import oysd.com.trade_app.util.Utils;

/**
 * Home adapter for display market ranking list.
 * Created by Liam on 2018/8/2
 */
public class HomeAdapter extends BaseAdapter<MarketListBean> {

    public HomeAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public HomeAdapter(@NonNull Context context, int layoutId,
                       @Nullable List<MarketListBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, MarketListBean marketListBean, int position) {
        String marketName = marketListBean.getMarketName();
        String[] strings = marketName.split("[/]");

        SpannableEditor spannableEditor = new SpannableEditor(context, marketName);
        spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_normal), strings[0]);
        spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_grey), strings[1]);
        spannableEditor.setTextSize(Utils.dip2px(context, 15), strings[0]);
        spannableEditor.setTextSize(Utils.dip2px(context, 13), strings[1]);

        holder.setText(R.id.coinName, spannableEditor.build());
        holder.setText(R.id.coinUSDTprice, EmptyUtils.getBigDecimalValue(marketListBean.getPrice()) + marketListBean.getSymbol());

        if (marketListBean.getPriceChangeRatio().compareTo(BigDecimal.ZERO) >= 0) {
            holder.setTextColor(R.id.coin_chg, context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.coin_chg, "+" + FormatUtils.to2(marketListBean.getPriceChangeRatio()) + "%");
        } else {
            holder.setTextColor(R.id.coin_chg, context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.coin_chg, FormatUtils.to2(marketListBean.getPriceChangeRatio()) + "%");
        }
    }

}
