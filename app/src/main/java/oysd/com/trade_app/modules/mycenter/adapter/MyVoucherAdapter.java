package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseHolder;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户-凭证账户-适配器
 */
public class MyVoucherAdapter extends BaseAdapter<VoucherBean> {

    public MyVoucherAdapter(@NonNull Context context, @NonNull int layoutId) {
        super(context, layoutId);
    }

    public MyVoucherAdapter(@NonNull Context context, @NonNull int layoutId, @Nullable List<VoucherBean> dataSet) {
        super(context, layoutId, dataSet);
    }

    @Override
    protected void onBind(BaseHolder holder, VoucherBean voucherBean, int position) {
        holder.setText(R.id.tv_name, voucherBean.getCertificateName());
        holder.setText(R.id.tv_usable, FormatUtils.to8NoComma(voucherBean.getUsableAmount()));
        holder.setText(R.id.tv_freeze, FormatUtils.to8NoComma(voucherBean.getFreezeAmount()));
        holder.setText(R.id.tv_total, FormatUtils.to8NoComma(Double.parseDouble(voucherBean.getUsableAmount()) + Double.parseDouble(voucherBean.getFreezeAmount())));
        holder.setText(R.id.tv_wallet_address, voucherBean.getAddress());

    }
}