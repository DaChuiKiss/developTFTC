package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.modules.mycenter.bean.VipCardInfoBean;
import oysd.com.trade_app.util.DateTimeUtils;

/**
 * “我的会员” 页面中的 view pager adapter.
 * Created by Liam on 2018/9/15
 */
public class VipCardAdapter extends PagerAdapter {

    private Context context;
    private List<VipCardInfoBean> dataSet;
    private LayoutInflater inflater;


    public VipCardAdapter(@NonNull Context context, @Nullable List<VipCardInfoBean> dataSet) {
        this.context = context;
        this.dataSet = dataSet == null ? new ArrayList<>() : dataSet;
        inflater = LayoutInflater.from(context);
    }

    public void setDataSet(List<VipCardInfoBean> dataSet) {
        if (dataSet == null) return;
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.layout_vip_cardview, container, false);
        setView(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void setView(View view, int position) {
        VipCardInfoBean cardInfoBean = dataSet.get(position);

        LinearLayout llCard = view.findViewById(R.id.ll_vip_card);
        TextView tvType = view.findViewById(R.id.tv_vip_card_type);
        TextView tvPeriodLimit = view.findViewById(R.id.tv_vip_card_period_limit);
        TextView tvPeriod = view.findViewById(R.id.tv_vip_card_period);
        TextView tvPrice = view.findViewById(R.id.tv_vip_card_price);
        TextView tvPriceUnit = view.findViewById(R.id.tv_vip_card_price_unit);

        String currentDate = DateTimeUtils.getCurrentDateWithDot() + " — ";
        String period = null;
        int cardType = cardInfoBean.getCardId();
        switch (cardType) {
            case 1: // month
                llCard.setBackgroundResource(R.drawable.bg_vip_month);
                tvType.setText(R.string.vip_card_month);
                period = currentDate + DateTimeUtils.getNextMonthDate();
                tvPriceUnit.setText(R.string.vip_card_month_price_unit);
                break;

            case 2: // season
                llCard.setBackgroundResource(R.drawable.bg_vip_season);
                tvType.setText(R.string.vip_card_season);
                period = currentDate + DateTimeUtils.getNextSeasonDate();
                tvPriceUnit.setText(R.string.vip_card_season_price_unit);
                break;

            case 3: // year
                llCard.setBackgroundResource(R.drawable.bg_vip_year);
                tvType.setText(R.string.vip_card_year);
                period = currentDate + DateTimeUtils.getNextYearDate();
                tvPriceUnit.setText(R.string.vip_card_year_price_unit);
                break;

            case 4: // lifeLong
                llCard.setBackgroundResource(R.drawable.bg_vip_lifelong);
                tvType.setText(R.string.vip_card_lifelong);
                period = currentDate + Constants.LIFELONG_CARD_END_TIME_DOT;
                tvPriceUnit.setText("USDT");
                break;

            default:
                break;
        }

        tvPeriodLimit.setText(R.string.vip_card_period);
        tvPeriod.setText(period);
        tvPrice.setText(cardInfoBean.getPrice());
    }

}
