package oysd.com.trade_app.modules.trade.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.adapter.HomeAdapter;
import oysd.com.trade_app.modules.trade.adapter.TradeAdapter;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.presenter.TradePresenter;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivityTest;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 交易 - 货币 section fragment.
 */
@SuppressLint("ValidFragment")
public class TradeCoinFragment extends LazyLoadBaseFragment implements TradeContract.View {

    public static final String EXTRA_ZONE_ID = "zoneId";
    public static final String EXTRA_ZONE_TPYE = "type";

    private int zoneId;
    private String type;
    TradeContract.Presenter presenter = new TradePresenter(this);
    TradeAdapter tradeAdapter;
    HomeAdapter homeAdapter;

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

    List<MarketListBean> mblsData = new ArrayList<>();

    public static TradeCoinFragment newInstance(int zoneId, String home) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ZONE_ID, zoneId);
        args.putString(EXTRA_ZONE_TPYE, home);

        TradeCoinFragment fragment = new TradeCoinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (type.equals("Home")) {
                    presenter.getMarketPriceChangeRatioList(1, 10, zoneId);
                } else if (type.equals("Trade")) {
                    presenter.getTransactionMarketList(1, 10, zoneId);
                }
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
            }
        });
    }

    @Override
    public void getDealZoneInfoSuccess(ResponseList<DealZoneBean> response) {


    }

    @Override
    public void getDealZoneInfoFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }

    //根据交易分区ID查询交易市场列表
    @Override
    public void getTransactionMarketListSuccess(ResponseList<MarketListBean> response) {
        if (llNoData == null) return;
        List<MarketListBean> mklb = response.getData();
        if (mklb == null || mklb.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            llNoData.setVisibility(View.GONE);
            mblsData = mklb;
            tradeAdapter.setDataSet(mblsData);
        }
        tradeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTransactionMarketListFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    //首页涨幅
    @Override
    public void getMarketPriceChangeRatioListSuccess(ResponseList<MarketListBean> response) {
        if (llNoData == null) return;
        List<MarketListBean> mklb = response.getData();
        if (mklb == null || mklb.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            llNoData.setVisibility(View.GONE);
            mblsData = mklb;
            homeAdapter.setDataSet(mblsData);
        }
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMarketPriceChangeRatioListFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_classic_header_with_grid_view;
    }

    @Override
    protected void initView(View rootView) {
        if (bundle == null) {
            throw new IllegalArgumentException("must set zoneId of TradeCoinFragment");
        }
        zoneId = bundle.getInt(EXTRA_ZONE_ID);
        type = bundle.getString(EXTRA_ZONE_TPYE);
        if (TextUtils.isEmpty(type)) {
            Logger.d("type--------------------------------------------------->null");
            type = "Trade";
        }
        setRefreshLayout();

        if (type.equals("Home")) {
            // RecyclerView settings.
            homeAdapter = new HomeAdapter(getActivity(), R.layout.home_item);
            homeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!Utils.isFastClick()) {
                        Intent intent = new Intent(getActivity(), KLineTradeActivityTest.class);
                        intent.putExtra("marketId", mblsData.get(position).getMarketId());
                        intent.putExtra("marketName", mblsData.get(position).getMarketName());
                        startActivity(intent);
                    }
                }
            });
            recyclerView.setAdapter(homeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            presenter.getMarketPriceChangeRatioList(1, 10, zoneId);
        } else if (type.equals("Trade")) {
            // RecyclerView settings.
            tradeAdapter = new TradeAdapter(getActivity(), R.layout.trade_item);
            tradeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!Utils.isFastClick()) {
                        Intent intent = new Intent(getActivity(), KLineTradeActivityTest.class);
                        intent.putExtra("marketId", mblsData.get(position).getMarketId());
                        intent.putExtra("marketName", mblsData.get(position).getMarketName());
                        startActivity(intent);
                    }
                }
            });
            recyclerView.setAdapter(tradeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            presenter.getTransactionMarketList(1, 10, zoneId);
        }

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (TextUtils.isEmpty(type)) {
            presenter.getTransactionMarketList(1, 10, zoneId);
        } else {
            if (type.equals("Home")) {
                presenter.getMarketPriceChangeRatioList(1, 10, zoneId);
            } else if (type.equals("Trade")) {
                presenter.getTransactionMarketList(1, 10, zoneId);
            }
        }
        run();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        stop();
    }

    private Disposable disposable;

    private void run() {
        disposable = Observable.interval(Global.delayTime, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (TextUtils.isEmpty(type)) {
                            presenter.getTransactionMarketList(1, 10, zoneId);
                        } else {
                            if (type.equals("Home")) {
                                presenter.getMarketPriceChangeRatioList(1, 10, zoneId);
                            } else if (type.equals("Trade")) {
                                presenter.getTransactionMarketList(1, 10, zoneId);
                            }
                        }
                    }
                });
    }

    private void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
