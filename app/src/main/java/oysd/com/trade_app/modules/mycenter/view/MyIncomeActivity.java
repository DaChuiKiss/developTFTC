package oysd.com.trade_app.modules.mycenter.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;

import java.util.ArrayList;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.util.StatusBarUtils;
import oysd.com.trade_app.util.Utils;

public class MyIncomeActivity extends BaseToolActivity {



    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    public static  String[] TITLES;

    @Override
    protected int setView() {
        return R.layout.myincome_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        TITLES =new String[3];
        TITLES[0] =getString(R.string.compartment_refund);
        TITLES[1]=getString(R.string.mining);
        TITLES[2]=getString(R.string.trade_dividends);
        setTitleText(getString(R.string.my_income));
        setTitleBarColor(R.color.text_white);
        StatusBarUtils.setStatusBarLightMode(MyIncomeActivity.this, true);
        setBackImg(R.mipmap.back_black);
        setTitleBarTextColor(R.color.text_normal);

        // 给 TitleBar 加上阴影。
        ViewCompat.setElevation(getTitleBar(), Utils.dip2px(this, 1.0f));
        ViewCompat.setTranslationZ(getTitleBar(), Utils.dip2px(this, 2.0f));
    }



    protected  void initData(){
        super.initData();
        ArrayList<Fragment> fragment = new ArrayList<>();
        fragment.add(new CompartmentFragment());
        fragment.add(new MiningFragment());
        fragment.add(new TradingDividendsFragment() );

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }
}
