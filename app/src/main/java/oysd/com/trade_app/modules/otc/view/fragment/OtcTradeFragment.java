package oysd.com.trade_app.modules.otc.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.modules.otc.OtcPopupWindow;
import oysd.com.trade_app.modules.otc.adapter.OtcTradeAdapter;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.modules.otc.contract.OtcTradeContract;
import oysd.com.trade_app.modules.otc.presenter.OtcTradePresenter;
import oysd.com.trade_app.modules.otc.view.activity.PaymentSettingActivity;
import oysd.com.trade_app.modules.otc.view.activity.UnpaidActivity;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

/**
 * OTC buy or OTC sell fragment.
 * Created by Liam on 2018/8/13
 */
public class OtcTradeFragment
        extends LazyLoadBaseFragment implements OtcTradeContract.View {

    public static final String EXTRA_TYPE = "type";
    // Type: buy / sale
    private String type;
    private int intType;

    @BindView(R.id.sp_otc_trade_sp1)
    NiceSpinner spinner1;

    @BindView(R.id.sp_otc_trade_sp2)
    NiceSpinner spinner2;

    @BindView(R.id.tv_otc_trade_set_payment)
    TextView tvSetPayment;

    @BindView(R.id.srl_smart_refresh)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv_smart_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.ll_empty_no_net)
    LinearLayout llNoNet;

    @BindView(R.id.ll_empty_no_order)
    LinearLayout llNoOrder;

    @BindView(R.id.ll_empty_no_data)
    LinearLayout llNoData;

    // use dagger.
    private OtcTradePresenter presenter = new OtcTradePresenter(this);
    private OtcTradeAdapter adapter;

    private int page = 1;
    private int limit = 15;

    private String userAccountId;
    private String transactionType;
    private String status;
    private String coinId;
    private String settlementCurrency;

    private OtcAdBean otcAdBean;            // 选择的 item
    private String money;                   // 设置好的 money
    private String quantity;                // 设置好的 quantity

    private List<String> coinNames = new ArrayList<>();
    private List<String> currencies = new ArrayList<>();
    private List<CoinConfigBean> coinConfigBeans = null;


    public static OtcTradeFragment newInstance(@NonNull String type) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);

        OtcTradeFragment fragment = new OtcTradeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //申诉，二维码，高级认证
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_otc_trade;
    }

    @Override
    protected void initView(View rootView) {
        if (bundle == null || EmptyUtils.isEmpty(bundle.getString(EXTRA_TYPE))) {
            throw new IllegalArgumentException("must set type of OtcTradeFragment.");
        }
        type = bundle.getString(EXTRA_TYPE);

        // 购买=1；卖出=2。需要 intType 来获取所以可交易的 coins .
        intType = getString(R.string.buy1).equals(type) ?
                Values.TransactionType.BUY : Values.TransactionType.SALE;

        // 因为发布的求购的广告，应该在“出售”所在列表中，所以在请求 list 时
        // 需要将这两个值颠倒过来。
        transactionType = getString(R.string.buy1).equals(type) ? String.valueOf(Values.TransactionType.SALE)
                : String.valueOf(Values.TransactionType.BUY);
    }

    @Override
    protected void initClicks() {
        tvSetPayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isFastClick()) return;
        switch (v.getId()) {
            case R.id.tv_otc_trade_set_payment:
                if (!InfoProvider.getInstance().getLogin()) {
                    Global.isChildActivity = true;
                    IntentUtils.startActivity(context, LoginActivity.class);
                    return;
                }
                if (InfoProvider.getInstance().getGesture()) {
                    if (!Global.isLogin) {
                        IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                    } else {
                        IntentUtils.startActivity(context, PaymentSettingActivity.class);
                    }
                } else {
                    IntentUtils.startActivity(context, PaymentSettingActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        setRefreshLayout();
        setRecyclerView();

        coinId = null;
        coinNames.add(context.getString(R.string.all));
        spinner1.attachDataSource(coinNames);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    coinId = null;
                } else {
                    coinId = String.valueOf(coinConfigBeans.get(position - 1).getCoinId());
                }

                page = 1;
                requestNetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        settlementCurrency = String.valueOf(Values.Currency.HKD);   // 初始值-港元

        currencies.add(Values.Currency.HKD_VALUE);
//        currencies.add(Values.Currency.CNY_VALUE);
//        currencies.add(Values.Currency.USD_VALUE);
//        currencies.add(Values.Currency.AUD_VALUE);
        spinner2.attachDataSource(currencies);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settlementCurrency = String.valueOf(ViewHelper.getCurrencyIdByName(currencies.get(position)));
                page = 1;
                requestNetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    // 请求网络数据
    private void requestNetData() {
        Logger.d("settlementCurrency" + settlementCurrency);
        presenter.getEntrustList(page, limit, userAccountId, transactionType, status, coinId, settlementCurrency);
    }

    private OtcPopupWindow.OnSubmitClickListener listener = new OtcPopupWindow.OnSubmitClickListener() {
        @Override
        public void onSubmitClick(String money, String quantity) {
            if (!InfoProvider.getInstance().getLogin()) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            } else {
                OtcTradeFragment.this.money = money;
                OtcTradeFragment.this.quantity = quantity;

                Map<String, Object> params = new HashMap<>();
                params.put("onlineEntrustId", otcAdBean.getId());
                params.put("transationAmount", money);
                params.put("transationQuantity", quantity);
                presenter.addOrder(params);
            }
        }
    };

    private void setRecyclerView() {
        adapter = new OtcTradeAdapter(context, type);
        adapter.setOnItemClickListener(otcAdBean -> {
            if (!Utils.isFastClick()) {
                OtcTradeFragment.this.otcAdBean = otcAdBean;
                OtcPopupWindow popupWindow = new OtcPopupWindow(context, type, otcAdBean, listener);
                popupWindow.showAtLocation(OtcTradeFragment.this.getActivity().findViewById(R.id.rl_main_page),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addItemDecoration(
        //         new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            requestNetData();
            refreshLayout.finishRefresh();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            requestNetData();
            refreshLayout.finishLoadMore();
        });
    }

    @Override
    public void getEntrustListSuccess(ResponsePaging<OtcAdBean> response) {
        List<OtcAdBean> list = response.getPagingData().getItem();
        int size = list.size();

        if (adapter != null) {
            if (page == 1) {
                adapter.setDataSet(list);
                if (size == 0) {
                    llNoData.setVisibility(View.VISIBLE);
                } else {
                    llNoData.setVisibility(View.GONE);
                    if (size < limit) refreshLayout.setNoMoreData(true);
                }

            } else {
                adapter.addDataSet(list);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getEntrustListFailed(int code, String msg) {
        // show msg which returned from server.
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void addOrderSuccess(OtcOrderBean otcOrderBean) {
        Bundle bundle = new Bundle();
        bundle.putInt(UnpaidActivity.EXTRA_ORDER_ID, otcOrderBean.getId());
        IntentUtils.startActivity(context, UnpaidActivity.class, bundle);
    }

    @Override
    public void addOrderFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void getCoinsConfigSuccess(List<CoinConfigBean> response) {
        coinConfigBeans = response;
        coinNames.clear();
        coinNames.add(context.getString(R.string.all));
        for (CoinConfigBean bean : response) {
            coinNames.add(bean.getCoinName());
        }
    }

    @Override
    public void getCoinsConfigFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        presenter.getCoinsConfig(intType);
        page = 1;
        requestNetData();
    }
}
