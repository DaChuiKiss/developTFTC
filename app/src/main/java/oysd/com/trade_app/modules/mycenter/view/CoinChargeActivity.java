package oysd.com.trade_app.modules.mycenter.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;

public class CoinChargeActivity extends BaseToolActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    public static String[] TITLES;

    public LegalTenderBean getLtb() {
        return ltb;
    }

    public void setLtb(LegalTenderBean ltb) {
        this.ltb = ltb;
    }

    private LegalTenderBean ltb;

    @Override
    protected int setView() {
        return R.layout.myaccount_coin_charge;
    }

    @Override
    protected void initView() {
        super.initView();
        ltb = this.getIntent().getParcelableExtra("LegalTenderBean");
        if (ltb.getCoinName().equals("ACN")) {
            TITLES = new String[3];
            TITLES[0] = getResources().getString(R.string.deposit);
            TITLES[1] = getResources().getString(R.string.withdraw);
            TITLES[2] = getResources().getString(R.string.transfer);
        } else if (ltb.getCoinName().equals("USDT")) {
            TITLES = new String[3];
            TITLES[0] = getResources().getString(R.string.deposit);
            TITLES[1] = getResources().getString(R.string.withdraw);
            TITLES[2] = getResources().getString(R.string.transfer);
        } else {
            TITLES = new String[2];
            TITLES[0] = getResources().getString(R.string.deposit);
            TITLES[1] = getResources().getString(R.string.withdraw);
        }
        setTitleText(R.string.type2);
    }

    @Override
    protected void initData() {
        ArrayList<Fragment> fragment = new ArrayList<>();
        if (ltb.getCoinName().equals("ACN")) {
            fragment.add(new depositFragment());
            fragment.add(new withdrawFragment());
            fragment.add(new transferFragment());
        } else if (ltb.getCoinName().equals("USDT")) {
            fragment.add(new depositFragment());
            fragment.add(new withdrawFragment());
            fragment.add(new CutFragment());
        } else {
            fragment.add(new depositFragment());
            fragment.add(new withdrawFragment());
        }
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }


}
