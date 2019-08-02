package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class MyAccountAdapter extends BaseAdapter<LegalTenderBean> {

    public MyAccountAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public MyAccountAdapter(@NonNull Context context, @NonNull int layoutId, @Nullable List<LegalTenderBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, LegalTenderBean legalTenderBean, int position) {
        if (!TextUtils.isEmpty(legalTenderBean.getCoinName())) {
            holder.setText(R.id.mycount_currency_name, legalTenderBean.getCoinName());
        }
        holder.setText(R.id.mycount_currency_blance,context.getResources().getString(R.string.available)+ Utils.myAccountFormat(legalTenderBean.getBalance()));
        holder.setText(R.id.mycount_currency_freeze, context.getResources().getString(R.string.locked)+ Utils.myAccountFormat(legalTenderBean.getFreeze()));
    }
}