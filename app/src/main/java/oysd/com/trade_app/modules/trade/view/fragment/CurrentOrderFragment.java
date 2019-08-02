package oysd.com.trade_app.modules.trade.view.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.modules.trade.adapter.TradeCurrentAdapter;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.contract.CurrentContract;
import oysd.com.trade_app.modules.trade.presenter.CurrentPresenter;
import oysd.com.trade_app.modules.trade.view.activity.saleAndBuyActivity;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

/**
 * BuyOrSell - 当前委托 fragment.
 */
public class CurrentOrderFragment extends LazyLoadBaseFragment implements CurrentContract.View {

    @BindView(R.id.entrust_type)
    NiceSpinner entrust_type;
    MarketIdBean mb;
    TradeCurrentAdapter tradeCurrentAdapter;

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

    @BindView(R.id.tv_trade_all_revoke)
    TextView tv_trade_all_revoke;

    CurrentContract.Presenter presenter = new CurrentPresenter(this);
    int page = 1;
    int limit = 10;
    //条件选择index 0全部，1买入，2卖出
    int selectIndex = 0;
    List<String> dataset;
    List<CurrentBean> golobCb = new ArrayList<>();

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                page = 1;
                if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
                    selectIndex = 0;
                    presenter.current(page, limit, mb.getMarketId(), null);
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.unsettled))) {
                    selectIndex = 1;
                    presenter.current(page, limit, mb.getMarketId(), "1");
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.some_clinch_a_deal))) {
                    selectIndex = 2;
                    presenter.current(page, limit, mb.getMarketId(), "2");
                }
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
                page++;
                if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
                    selectIndex = 0;
                    presenter.current(page, limit, mb.getMarketId(), null);
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.unsettled))) {
                    selectIndex = 1;
                    presenter.current(page, limit, mb.getMarketId(), "1");
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.some_clinch_a_deal))) {
                    selectIndex = 2;
                    presenter.current(page, limit, mb.getMarketId(), "2");
                }
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

        if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
            selectIndex = 0;
            presenter.current(page, limit, mb.getMarketId(), null);
        } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.unsettled))) {
            selectIndex = 1;
            presenter.current(page, limit, mb.getMarketId(), "1");
        } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.some_clinch_a_deal))) {
            selectIndex = 2;
            presenter.current(page, limit, mb.getMarketId(), "2");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.my_entrust_fragment;
    }

    @Override
    protected void initView(View rootView) {
        setRefreshLayout();
        dataset = new LinkedList<>(Arrays.asList(getResources().getString(R.string.all), getResources().getString(R.string.unsettled), getResources().getString(R.string.some_clinch_a_deal)));
        entrust_type.attachDataSource(dataset);
        mb = ((saleAndBuyActivity) getActivity()).getMarketIdBean();

        // RecyclerView settings.
        tradeCurrentAdapter = new TradeCurrentAdapter(getActivity(), R.layout.trade_current_item);

        tradeCurrentAdapter.setOnTradeRevokeListener(new TradeCurrentAdapter.OnTradeRevokeListener() {
            @Override
            public void onRevoke(CurrentBean currentBean, int position) {
                showPasswordDialog(false, getResources().getString(R.string.dialogMsg4), currentBean.getId());
            }
        });

        recyclerView.setAdapter(tradeCurrentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        entrust_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                page = 1;
                if (dataset.get(position).equals(getResources().getString(R.string.all))) {
                    selectIndex = 0;
                    presenter.current(page, limit, mb.getMarketId(), null);
                } else if (dataset.get(position).equals(getResources().getString(R.string.unsettled))) {
                    selectIndex = 1;
                    presenter.current(page, limit, mb.getMarketId(), "1");
                } else if (dataset.get(position).equals(getResources().getString(R.string.some_clinch_a_deal))) {
                    selectIndex = 2;
                    presenter.current(page, limit, mb.getMarketId(), "2");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //撤销
        tv_trade_all_revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog(true, getResources().getString(R.string.dialogMsg3), -1);
            }
        });
    }

    //isAll=true;全部撤销，false 部分撤销, isAll为false时，需要传入id，其他默认传-1
    private void showPasswordDialog(boolean isAll, String str, int id) {
        PasswordDialog dialog = new PasswordDialog(context, null, str, false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                presenter.rollbackOne(id);
//                if (isAll) {
//                    if (selectIndex == 0) {
//                        presenter.rollback(mb.getMarketId(), null);
//                    } else if (selectIndex == 1) {
//                        presenter.rollback(mb.getMarketId(), "1");
//                    } else if (selectIndex == 2) {
//                        presenter.rollback(mb.getMarketId(), "2");
//                    }
//                } else {
//                    presenter.rollbackOne(id);
//                }
            }
        });
        dialog.show();
    }

    @Override
    public void CurrentSuccess(ResponsePaging<CurrentBean> response) {

        if (refreshLayout == null) return;
        if (response.getPagingData() == null) return;
        List<CurrentBean> list = response.getPagingData().getItem();
        int size = list.size();
        if (page == 1) {
            tradeCurrentAdapter.setDataSet(list);
            if (size == 0) {
                llNoData.setVisibility(View.VISIBLE);
            } else {
                llNoData.setVisibility(View.GONE);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }
        } else {
            tradeCurrentAdapter.addDataSet(list);
            if (size < limit) refreshLayout.setNoMoreData(true);
        }

        tradeCurrentAdapter.notifyDataSetChanged();
    }

    @Override
    public void CurrentFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }

    @Override
    public void rollbackSuccess() {
        if (selectIndex == 0) {
            presenter.rollback(mb.getMarketId(), null);
        } else if (selectIndex == 1) {
            presenter.rollback(mb.getMarketId(), "1");
        } else if (selectIndex == 2) {
            presenter.rollback(mb.getMarketId(), "2");
        }
    }

    @Override
    public void rollbackFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void rollbackOneSuccess() {
        if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
            selectIndex = 0;
            presenter.current(page, limit, mb.getMarketId(), null);
        } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.unsettled))) {
            selectIndex = 1;
            presenter.current(page, limit, mb.getMarketId(), "1");
        } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.some_clinch_a_deal))) {
            selectIndex = 2;
            presenter.current(page, limit, mb.getMarketId(), "2");
        }
//        if (selectIndex == 0) {
//            presenter.rollback(mb.getMarketId(), null);
//        } else if (selectIndex == 1) {
//            presenter.rollback(mb.getMarketId(), "1");
//        } else if (selectIndex == 2) {
//            presenter.rollback(mb.getMarketId(), "2");
//        }
    }

    @Override
    public void rollbackOneFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }

}
