package oysd.com.trade_app.modules.otc;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.modules.otc.contract.OtcTradeContract;
import oysd.com.trade_app.modules.otc.view.activity.PublishAdsActivity;
import oysd.com.trade_app.modules.otc.view.fragment.OtcTradeFragment;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Utils;

/**
 * Created by yx on 16/4/3.
 */
public class OtcFragment extends LazyLoadBaseFragment {

    @Inject
    OtcTradeContract.Presenter presenter;

    @BindView(R.id.tv_otc_publish_ad)
    TextView tvPublishAd;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    private String[] titles;
    private List<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_otc;
    }

    @Override
    protected void initView(View rootView) {
        String buy = getString(R.string.buy1);
        String sale = getString(R.string.sale1);
        titles = new String[]{buy, sale};

        fragments = new ArrayList<>();
        fragments.add(OtcTradeFragment.newInstance(buy));
        fragments.add(OtcTradeFragment.newInstance(sale));
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initClicks() {
        tvPublishAd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (Utils.isFastClick()) return;
        switch (v.getId()) {
            case R.id.tv_otc_publish_ad:
                if (InfoProvider.getInstance().getLogin()) {
                    if (InfoProvider.getInstance().getGesture()) {
                        if (!Global.isLogin) {
                            IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                        } else {
                            IntentUtils.startActivity(context, PublishAdsActivity.class);
                        }
                    } else {
                        IntentUtils.startActivity(context, PublishAdsActivity.class);
                    }
                } else {
                    Global.isChildActivity = true;
                    IntentUtils.startActivity(context, LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

}
