package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.SelectTheCurrencyBean;

public class SelectTheCurrencyAdapter extends BaseAdapter<SelectTheCurrencyBean> {

    private OnTradeRevokeListener onTradeRevokeListener;

    public SelectTheCurrencyAdapter(@NonNull Context context, int layoutId) {
        super(context, layoutId);
    }

    public interface OnTradeRevokeListener {
        void onRevoke(SelectTheCurrencyBean selectTheCurrencyBean, int position);
    }

    public void setOnTradeRevokeListener(OnTradeRevokeListener onTradeRevokeListener) {
        this.onTradeRevokeListener = onTradeRevokeListener;
    }

    @Override
    protected void onBind(BaseHolder holder, SelectTheCurrencyBean selectTheCurrencyBean, int position) {
        // iv_image
        // tv_text
        holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTradeRevokeListener != null) {
                    onTradeRevokeListener.onRevoke(selectTheCurrencyBean, position);
                }
            }
        });

    }

}
