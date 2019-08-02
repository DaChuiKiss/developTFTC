package oysd.com.trade_app.modules.mycenter.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;

public class OrderManageActivity extends BaseToolActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    public static String[] TITLES;

    @Override
    protected int setView() {
        return R.layout.order_manage_activity_buy;
    }

    @Override
    protected void initView() {
        super.initView();
        TITLES = new String[2];
        TITLES[0] = getResources().getString(R.string.order_coin);
        TITLES[1] = getResources().getString(R.string.order_otc);
        setTitleText(R.string.type3);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<Fragment> fragment = new ArrayList<>();
        fragment.add(new OrderForCoinFragment());
        fragment.add(new OrderFor0TCFragment());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
