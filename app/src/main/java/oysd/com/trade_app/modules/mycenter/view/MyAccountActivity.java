package oysd.com.trade_app.modules.mycenter.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.EventLog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.MessageEvent;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountCurrencyContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountCurrencyPresenter;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.EventBusUtil;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户
 */
public class MyAccountActivity extends BaseToolActivity implements MyAccountCurrencyContract.View {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tv_myaccount_btc)
    TextView tv_myaccount_btc;
    @BindView(R.id.tv_myaccount_price)
    TextView tv_myaccount_price;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    public static String[] TITLES;

    MyAccountCurrencyContract.Presenter presenter = new MyAccountCurrencyPresenter(this);

    @Override
    protected int setView() {
        return R.layout.myaccount_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        setMenuImage01(R.mipmap.hua_zhuan_2);
        EventBusUtil.register(this);
        TITLES = new String[3];
        TITLES[0] = getResources().getString(R.string.exchange_account);
        TITLES[1] = getResources().getString(R.string.otc_account);
        TITLES[2] = getResources().getString(R.string.voucher_account);
        setTitleText(R.string.type2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }


    //没有网络
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (!TextUtils.isEmpty(messageEvent.getMessage())) {
            if (messageEvent.getMessage().equals("currencyAccountFragment")) {
                Logger.d("currencyAccountFragment");
                setMenuImage01Show(false);
                ll_content.setVisibility(View.VISIBLE);
                ll_top.setVisibility(View.VISIBLE);
            } else if (messageEvent.getMessage().equals("c2cFragment")) {
                Logger.d("c2cFragment");
                setMenuImage01Show(false);
                ll_content.setVisibility(View.GONE);
                ll_top.setVisibility(View.VISIBLE);
            } else if (messageEvent.getMessage().equals("VoucherFragment")) {
                ll_content.setVisibility(View.GONE);
                ll_top.setVisibility(View.GONE);
                setMenuImage01Show(true);
            }
        }
    }

    @Override
    protected void clickImage01() {
        super.clickImage01();
        Logger.d("clickImage01—_—>");
    }

    protected void initData() {
        super.initData();
        ArrayList<Fragment> fragment = new ArrayList<>();
        fragment.add(new currencyAccountFragment());
        fragment.add(new c2cFragment());
        fragment.add(new VoucherFragment());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getTotalConvertInto();
    }

    @Override
    public void getTotalConvertIntoSuccess(TotalConvertInfo totalConvertInfo) {
        if (tv_myaccount_btc != null) {
            tv_myaccount_btc.setText(Utils.myAccountFormat(totalConvertInfo.getBtcBalance()));
            if (PreferencesUtils.getString("selectCoinType") == null || PreferencesUtils.getString("selectCoinType").equals("1")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "HKD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("2")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "USD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("3")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "AUD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("4")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "CNY");
            }
        }
    }

    @Override
    public void getTotalConvertIntoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void queryLegalTenderListSuccess(ResponsePaging<LegalTenderBean> totalConvertInfo) {

    }

    @Override
    public void queryLegalTenderListFailed(int code, String msg) {

    }
}
