package oysd.com.trade_app.main;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.home.HomeFragment;
import oysd.com.trade_app.base.BaseFragment;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.otc.OtcFragment;
import oysd.com.trade_app.main.view.NoScrollViewPager;

import oysd.com.trade_app.modules.mycenter.MycenterFragment;
import oysd.com.trade_app.modules.trade.TradeFragment;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.TabItem;
import oysd.com.trade_app.widget.TabLayout;

public class MainActivity extends BaseToolActivity
        implements TabLayout.OnTabClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    private ArrayList<TabItem> tabs;
    BaseFragment fragment;
    int onclickIndex = 0;//保存用户进入的button

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }
//
//    private static Tracker mTracker;

    @Override
    protected void initView() {
        super.initView();
//        mTracker = App.getInstance().getDefaultTracker();
        // 申请授权。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void initData() {
        setTitle("公告");
        tabs = new ArrayList<>();
        tabs.add(new TabItem(R.drawable.selector_tab_ad, R.string.ad_name, HomeFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_trade, R.string.trade_name, TradeFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_c2c, R.string.c2c_name, OtcFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_mycenter, R.string.mycenter_name, MycenterFragment.class));


        mTabLayout.initData(tabs, this);
        mTabLayout.setCurrentTab(0);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        mViewPager.setNoScroll(true);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected boolean setTitleBarShowStatus() {
        return false;
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        int item = tabs.indexOf(tabItem);
        Log.i(TAG, "onTabClick: item=" + item);
        onclickIndex = item;
        mViewPager.setCurrentItem(item);
    }

    public void reloadMyCenterFragment() {
        // FragmentManager fm = this.getSupportFragmentManager();
        // 整个重新刷新。
        initData();
    }

    public class FragAdapter extends FragmentPagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            try {
                return tabs.get(arg0).tagFragmentClz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onclickIndex == 3) {
            if (InfoProvider.getInstance().getLogin()) {
                if (InfoProvider.getInstance().getGesture()) {
                    if (Global.isLogin) {
                        mTabLayout.setCurrentTab(3);
                        mViewPager.setCurrentItem(3);
                    } else {
                        onclickIndex = 0;
                        mTabLayout.setCurrentTab(0);
                        mViewPager.setCurrentItem(0);
                    }
                } else {
                    mTabLayout.setCurrentTab(3);
                    mViewPager.setCurrentItem(3);
                }
            } else {
                onclickIndex = 0;
                mTabLayout.setCurrentTab(0);
                mViewPager.setCurrentItem(0);
            }
        } else {
            mTabLayout.setCurrentTab(onclickIndex);
            mViewPager.setCurrentItem(onclickIndex);
        }
        Log.d("TGA", "onResume");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

//        mTracker.setScreenName("Image~" + "MainActivity");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override

    protected void clickBack() {
        Global.isLogin = false;
        super.clickBack();
    }

    long firstTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.showShort(App.getContext(), getResources().getString(R.string.click_again));
                firstTime = secondTime;
                return true;
            } else {
                Global.isLogin = false;
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
