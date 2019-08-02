package oysd.com.trade_app.modules.otc.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.util.Logger;

/**
 * 支付设置 - USD 设置 fragment.
 * Created by Liam on 2018/8/8
 */
public class UsdFragment extends LazyLoadBaseFragment {

    @BindView(R.id.tl_usd_tabs)
    TabLayout tabLayout;

    @BindView(R.id.vp_usd_pager)
    ViewPager viewPager;

    private List<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;

    private int[] bgs = new int[]{
            R.drawable.selector_payment_tab_usd,
            R.drawable.selector_payment_tab_paypal,
            R.drawable.selector_payment_tab_epay};


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_usd;
    }

    @Override
    protected void initView(View rootView) {
        fragments = new ArrayList<>();
        fragments.add(new SettingUsdFragment());
        fragments.add(SettingUsdPeFragment.newInstance(getString(R.string.paypal)));
        fragments.add(SettingUsdPeFragment.newInstance(getString(R.string.epay)));

        fragmentAdapter = new FragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        setTabViews();
        setTabListener();
    }

    private void setTabViews() {
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) {
                Logger.e("wrong index of tab in payment.");
                continue;
            }

            View view = LayoutInflater.from(context).inflate(R.layout.layout_payment_setting_tabs, null);
            ImageView imageView = view.findViewById(R.id.iv_payment_setting_tab);
            imageView.setImageResource(bgs[i]);
            tab.setCustomView(imageView);

            if (i == 0) {
                imageView.setFocusable(true);
            }
        }
    }

    private void setTabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    customView.findViewById(R.id.iv_payment_setting_tab).setFocusable(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    customView.findViewById(R.id.iv_payment_setting_tab).setFocusable(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
