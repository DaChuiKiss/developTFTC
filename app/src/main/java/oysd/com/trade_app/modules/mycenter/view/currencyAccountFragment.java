package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.MyAccountAdapter;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.MessageEvent;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountCurrencyContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountCurrencyPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.EventBusUtil;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class currencyAccountFragment extends LazyLoadBaseFragment
        implements MyAccountCurrencyContract.View {

    @BindView(R.id.tv_myaccount_btc)
    TextView tv_myaccount_btc;

    @BindView(R.id.tv_myaccount_price)
    TextView tv_myaccount_price;

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

//    @BindView(R.id.ll_myacount_title)
//    LinearLayout ll_myacount_title;

//    @BindView(R.id.rl_myacount_total)
//    RelativeLayout rl_myacount_total;

    List<LegalTenderBean> globleData = new ArrayList<>();
    MyAccountAdapter myAccountAdapter;
    int page = 1;
    int limit = 15;

    MyAccountCurrencyContract.Presenter presenter = new MyAccountCurrencyPresenter(this);


    @Override
    protected int getLayoutRes() {
        return R.layout.my_account_list;
    }

    @Override
    protected void initView(View rootView) {
        setRefreshLayout();
        globleData.clear();
        myAccountAdapter = new MyAccountAdapter(getActivity(), R.layout.my_account_item);
        myAccountAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isFastClick()) {
                    Intent intent = new Intent(getActivity(), CoinChargeActivity.class);
                    intent.putExtra("LegalTenderBean", globleData.get(position));
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(myAccountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        presenter.getTotalConvertInto();
        presenter.queryLegalTenderList(page, limit, "");
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //presenter.getTotalConvertInto();
                refreshLayout.setNoMoreData(false);
                page = 1;
                presenter.queryLegalTenderList(page, limit, "");
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
                page++;
                presenter.queryLegalTenderList(page, limit, "");
                refreshLayout.finishLoadMore();
            }
        });
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        //presenter.getTotalConvertInto();
        page =1;
        presenter.queryLegalTenderList(page, limit, "");
        EventBusUtil.post(new MessageEvent("currencyAccountFragment"));
    }



    @Override
    public void getTotalConvertIntoSuccess(TotalConvertInfo totalConvertInfo) {
        if(tv_myaccount_btc!=null) {
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
    public void queryLegalTenderListSuccess(ResponsePaging<LegalTenderBean> response) {
//        List<LegalTenderBean> lbls = response.getPagingData().getItem();
//        if(refreshLayout!=null&&myAccountAdapter!=null) {
//            if (page <= response.getPagingData().getTotalPage()) {
//                if (lbls.size() < limit) {
//                    refreshLayout.setNoMoreData(true);
//                }
//                if (page == 1) {
//                    globleData.clear();
//                    globleData.addAll(lbls);
//                } else {
//                    globleData.addAll(lbls);
//                }
//                myAccountAdapter.setDataSet(globleData);
//                myAccountAdapter.notifyDataSetChanged();
//            }
//        }

        synchronized (this) {
            if (refreshLayout == null) return;
            if (response.getPagingData() == null) return;
            List<LegalTenderBean> lbls = response.getPagingData().getItem();
            int size = lbls.size();
            if (page == 1) {
                globleData = lbls;
                myAccountAdapter.setDataSet(lbls);
                if (size == 0) {
                    llNoData.setVisibility(View.VISIBLE);
                    //ll_myacount_title.setVisibility(View.GONE);
                    //rl_myacount_total.setVisibility(View.GONE);
                } else {
                    llNoData.setVisibility(View.GONE);
                    //ll_myacount_title.setVisibility(View.VISIBLE);
                    //rl_myacount_total.setVisibility(View.VISIBLE);
                    if (size < limit) refreshLayout.setNoMoreData(true);
                }
            } else {
                globleData.addAll(lbls);
                myAccountAdapter.addDataSet(lbls);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }
        }
        myAccountAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryLegalTenderListFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }

}
