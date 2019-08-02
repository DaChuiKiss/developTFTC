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
import butterknife.ButterKnife;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.MyOTCAccountAdapter;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.MessageEvent;
import oysd.com.trade_app.modules.mycenter.bean.OTCConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountOTCContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountOTCPresenter;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.EventBusUtil;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class c2cFragment extends LazyLoadBaseFragment implements MyAccountOTCContract.View {

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

    @BindView(R.id.tv_myaccount_btc)
    TextView tv_myaccount_btc;

    @BindView(R.id.tv_myaccount_price)
    TextView tv_myaccount_price;

//    @BindView(R.id.ll_myacount_title)
//    LinearLayout ll_myacount_title;

    @BindView(R.id.rl_myacount_total)
    RelativeLayout rl_myacount_total;

    MyOTCAccountAdapter myAccountAdapter;
    int page = 1;
    int limit = 15;
    List<LegalOTCBean> globleData = new ArrayList<>();
    MyAccountOTCContract.Presenter presenter = new MyAccountOTCPresenter(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.my_account_list;
    }

    @Override
    protected void initView(View rootView) {

        ButterKnife.bind(this, rootView);
        setRefreshLayout();
        globleData.clear();
        myAccountAdapter = new MyOTCAccountAdapter(getActivity(), R.layout.my_account_item);
        myAccountAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isFastClick(view)) {
                    Intent intent = new Intent(getActivity(), c2cToCointransferFragment.class);
                    intent.putExtra("LegalOTCBean", globleData.get(position));
                    startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(myAccountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //presenter.getOnlineTotalAmount();
        presenter.getLegalTenderList(page, limit);
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        //presenter.getOnlineTotalAmount();
        page = 1;
        presenter.getLegalTenderList(page, limit);
        EventBusUtil.post(new MessageEvent("c2cFragment"));
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //presenter.getOnlineTotalAmount();
                refreshLayout.setNoMoreData(false);
                page = 1;
                presenter.getLegalTenderList(page, limit);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
                // no need to load more.
                page++;
                presenter.getLegalTenderList(page, limit);
                refreshLayout.finishLoadMore();
            }
        });
    }


    @Override
    public void getOnlineTotalAmountSuccess(OTCConvertInfo totalConvertInfo) {
        if (totalConvertInfo != null && tv_myaccount_btc != null) {
            tv_myaccount_btc.setText(Utils.myAccountFormat(totalConvertInfo.getBtcAmount()));
            if (PreferencesUtils.getString("selectCoinType") == null || PreferencesUtils.getString("selectCoinType").equals("1")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getLegalTenderAmount())) + "HKD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("2")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getLegalTenderAmount())) + "USD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("3")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getLegalTenderAmount())) + "AUD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("4")) {
                tv_myaccount_price.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getLegalTenderAmount())) + "CNY");
            }
        }
    }

    @Override
    public void getOnlineTotalAmountFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }

    @Override
    public void getLegalTenderListSuccess(ResponsePaging<LegalOTCBean> response) {

//        if(response.getPagingData()!=null&&refreshLayout!=null) {
//            List<LegalOTCBean> lbls = response.getPagingData().getItem();
//            if (lbls.size() > 0) {
//                if (page <= response.getPagingData().getTotalPage()) {
//                    if (lbls.size() < limit) {
//                        refreshLayout.setNoMoreData(true);
//                    }
//                    if (page == 1) {
//                        globleData.clear();
//                        globleData.addAll(lbls);
//                    } else {
//                        globleData.addAll(lbls);
//                    }
//                    myAccountAdapter.setDataSet(globleData);
//                    myAccountAdapter.notifyDataSetChanged();
//                }
//            }
//        }

        if (llNoData == null) return;
        if (response.getPagingData() == null) return;
        List<LegalOTCBean> lbls = response.getPagingData().getItem();
        int size = lbls.size();

        if (page == 1) {
            globleData = lbls;
            myAccountAdapter.setDataSet(lbls);
            if (size == 0) {
                llNoData.setVisibility(View.VISIBLE);
                //ll_myacount_title.setVisibility(View.GONE);
                rl_myacount_total.setVisibility(View.GONE);
            } else {
                if (llNoData.getVisibility() == View.VISIBLE) {
                    llNoData.setVisibility(View.GONE);
                    //ll_myacount_title.setVisibility(View.VISIBLE);
                    rl_myacount_total.setVisibility(View.VISIBLE);
                }
                if (size < limit) refreshLayout.setNoMoreData(true);
            }
        } else {
            globleData.addAll(lbls);
            myAccountAdapter.addDataSet(lbls);
            if (size < limit) refreshLayout.setNoMoreData(true);
        }
        myAccountAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLegalTenderListFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }
}
