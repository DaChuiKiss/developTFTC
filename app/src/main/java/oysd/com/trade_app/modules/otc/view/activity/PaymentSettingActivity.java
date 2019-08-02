package oysd.com.trade_app.modules.otc.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.otc.view.fragment.CnyFragment;
import oysd.com.trade_app.modules.otc.view.fragment.UsdFragment;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;


/**
 * 设置支付方式 - 页面。
 * Created by Liam on 2018/8/8
 */
public class PaymentSettingActivity extends BaseToolActivity {

    @BindView(R.id.tl_payment_setting_tabs)
    TabLayout tabLayout;

    @BindView(R.id.vp_payment_setting_pager)
    ViewPager viewPager;

    private String[] titles;
    private List<Fragment> fragments;

    private FragmentAdapter fragmentAdapter;


    @Override
    protected int setView() {
        return R.layout.activity_payment_setting;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        // 禁止刷新
        setEnableRefresh(false);
        setTitleText(R.string.title_payment_method);

        fragments = new ArrayList<>();
        fragments.add(new CnyFragment());
        fragments.add(new UsdFragment());

        titles = new String[]{
                getString(R.string.cny_setting),
                getString(R.string.usd_setting)};

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.setTabTextColors(
                getResources().getColor(R.color.text_normal),
                getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setupWithViewPager(viewPager);
    }

}
