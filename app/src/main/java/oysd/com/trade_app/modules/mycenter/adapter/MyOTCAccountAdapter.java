package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class MyOTCAccountAdapter extends BaseAdapter<LegalOTCBean> {

    public MyOTCAccountAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public MyOTCAccountAdapter(@NonNull Context context, @NonNull int layoutId, @Nullable List<LegalOTCBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, LegalOTCBean legalTenderBean, int position) {
        holder.setText(R.id.mycount_currency_name, legalTenderBean.getCoinName());
        holder.setText(R.id.mycount_currency_blance, context.getResources().getString(R.string.available) + Utils.myAccountFormat(legalTenderBean.getAvailableAmount()));
        holder.setText(R.id.mycount_currency_freeze, context.getResources().getString(R.string.locked) + Utils.myAccountFormat(legalTenderBean.getFrozenAmount()));
    }
}