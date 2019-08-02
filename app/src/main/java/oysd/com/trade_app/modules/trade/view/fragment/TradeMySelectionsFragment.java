package oysd.com.trade_app.modules.trade.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import oysd.com.trade_app.modules.trade.adapter.TradeAdapter;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.UseOptionalMarketContract;
import oysd.com.trade_app.modules.trade.presenter.UserOptionalMarketPresenter;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivityTest;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 交易 - 自选 section fragment.
 */
public class TradeMySelectionsFragment extends LazyLoadBaseFragment
        implements UseOptionalMarketContract.View {

    UseOptionalMarketContract.Presenter presenter = new UserOptionalMarketPresenter(this);

    TradeAdapter tradeAdapter;

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
    private Disposable disposable;

    private void setRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!InfoProvider.getInstance().getLogin()) {
                    presenter.getOptionalTransactionMarketList(InfoProvider.getInstance().getMarketIdList());

                } else {
                    presenter.getUseOptionalMarket();
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
    public void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response) {
        if (llNoData == null) return;
        List<MarketListBean> mklb = response.getData();
        if (mklb == null || mklb.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            mblsData = mklb;
            llNoData.setVisibility(View.GONE);
            tradeAdapter.setDataSet(mblsData);
            tradeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getUseOptionalMarketFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void getOptionalTransactionMarketListSuccess(ResponseList<MarketListBean> response) {
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
    public void getOptionalTransactionMarketListFailed(int code, String msg) {

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
        tradeAdapter = new TradeAdapter(getActivity(), R.layout.trade_item);
        recyclerView.setAdapter(tradeAdapter);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRefreshLayout();

    }

    Timer mTimer;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (InfoProvider.getInstance().getLogin()) {
            presenter.getUseOptionalMarket();
        } else {
            if (InfoProvider.getInstance().getMarketIdList() != null && InfoProvider.getInstance().getMarketIdList().size() > 0) {
                llNoData.setVisibility(View.GONE);
                presenter.getOptionalTransactionMarketList(InfoProvider.getInstance().getMarketIdList());
            } else {
                llNoData.setVisibility(View.VISIBLE);
            }
        }
        run();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        stop();
    }


    private void run() {
        disposable = Observable.interval(Global.delayTime, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (InfoProvider.getInstance().getLogin()) {
                            presenter.getUseOptionalMarket();
                        } else {
                            if (InfoProvider.getInstance().getMarketIdList() != null && InfoProvider.getInstance().getMarketIdList().size() > 0) {
                                presenter.getOptionalTransactionMarketList(InfoProvider.getInstance().getMarketIdList());
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
