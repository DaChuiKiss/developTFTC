package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

public class MyOrderOTCAdapter extends BaseAdapter<OtcOrderBean> {

    private OnItemSelectedListener onItemSelectedListener;

    public MyOrderOTCAdapter(@NonNull Context context, int layoutId) {
        super(context, layoutId);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(OtcOrderBean otcOrderBean, int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    protected void onBind(BaseHolder holder, OtcOrderBean otcOrderBean, int position) {
        if (otcOrderBean.getTransactionType() == 2) {
            holder.setTextColor(R.id.tv_mycenter_order_name, context.getResources().getColor(R.color.text_pink));
            holder.setText(R.id.tv_mycenter_order_name, context.getResources().getString(R.string.buy) + " " + otcOrderBean.getCoinName());
        } else {
            holder.setTextColor(R.id.tv_mycenter_order_name, context.getResources().getColor(R.color.text_red));
            holder.setText(R.id.tv_mycenter_order_name, context.getResources().getString(R.string.sale) + " " + otcOrderBean.getCoinName());
        }
        holder.setText(R.id.tv_mycenter_order_time, DateTimeUtils.correctSvrTime(otcOrderBean.getCreateDate()));

        if (otcOrderBean.getStatus() == 1) {
            holder.setText(R.id.tv_mycenter_order_sataus, R.string.otc_order_type2);
        } else if (otcOrderBean.getStatus() == 2) {
            holder.setText(R.id.tv_mycenter_order_sataus, R.string.otc_order_type3);
        } else if (otcOrderBean.getStatus() == 3) {
            holder.setText(R.id.tv_mycenter_order_sataus, R.string.otc_order_type4);
        } else if (otcOrderBean.getStatus() == 4) {
            holder.setText(R.id.tv_mycenter_order_sataus, R.string.otc_order_type5);
        } else if (otcOrderBean.getStatus() == 5) {
            holder.setText(R.id.tv_mycenter_order_sataus, R.string.otc_order_type6);
        }

        holder.setText(R.id.mycenter_order_otc_count, Utils.myAccountFormat(otcOrderBean.getTransationQuantity()));

        holder.setText(R.id.mycenter_order_otc_price, EmptyUtils.getBigDecimalValue(otcOrderBean.getTransactionPrice()));

        holder.setText(R.id.mycenter_order_otc_account, EmptyUtils.getBigDecimalValue(otcOrderBean.getTransationAmount()));
        holder.setText(R.id.tv_mycenter_order_otc_userName, otcOrderBean.getUserAccountName());
        holder.setText(R.id.tv_mycenter_order_otc_number, otcOrderBean.getOrderNo());


        holder.setOnClickListener(R.id.tv_mycenter_order_sataus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(otcOrderBean, position);
                }
            }
        });
    }

}
