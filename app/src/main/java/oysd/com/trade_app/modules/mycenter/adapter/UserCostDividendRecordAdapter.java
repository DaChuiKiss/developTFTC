package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;

public class UserCostDividendRecordAdapter extends BaseAdapter<UserCostDividendRecord> {

    public UserCostDividendRecordAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    private UserCostDividendRecordAdapter.OnTradeRevokeListener onTradeRevokeListener;

    public interface OnTradeRevokeListener {
        void onRevoke(UserCostDividendRecord ucdr, int position);
    }


    public UserCostDividendRecordAdapter(@NonNull Context context, int layoutId,
                                         @Nullable List<UserCostDividendRecord> dataSet) {
        super(context, layoutId, dataSet);
    }


    public void setOnTradeRevokeListener(UserCostDividendRecordAdapter.OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, final UserCostDividendRecord ucdr, int position) {

        holder.setText(R.id.tv_mycenter_mining_extractDate, DateTimeUtils.correctSvrTime(ucdr.getExtractDate()));
        holder.setText(R.id.tv_mycenter_mining_costCoinName, ucdr.getCostCoinName());
        holder.setText(R.id.tv_mycenter_mining_bourseCoinNumber, EmptyUtils.getBigDecimalValue(ucdr.getBourseCoinNumber()));
    }
}