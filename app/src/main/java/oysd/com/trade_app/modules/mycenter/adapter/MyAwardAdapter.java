package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.AwardBean;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.util.FormatUtils;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的奖励-适配器
 */
public class MyAwardAdapter extends BaseAdapter<AwardBean> {

    public MyAwardAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public MyAwardAdapter(@NonNull Context context, @NonNull int layoutId, @Nullable List<AwardBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, AwardBean awardBean, int position) {
        if (!TextUtils.isEmpty(awardBean.getInvitedAccountNumber())) {
            holder.setText(R.id.tv_invitedAccountNumber, awardBean.getInvitedAccountNumber());
        }
        if (!TextUtils.isEmpty(awardBean.getDealTime())) {
            holder.setText(R.id.tv_antiCommission, awardBean.getDealTime());
        }
        if (!TextUtils.isEmpty(awardBean.getCoinName())) {
            holder.setText(R.id.tv_coinName, awardBean.getCoinName());
        }
        holder.setText(R.id.tv_commission, FormatUtils.to8NoComma(awardBean.getCommission()));


    }
}