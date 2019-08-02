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
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.adapter.TradeHistoryAdapter;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.HistoryBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.contract.HistoryContract;
import oysd.com.trade_app.modules.trade.presenter.HistoryPresenter;
import oysd.com.trade_app.modules.trade.view.activity.saleAndBuyActivity;
import oysd.com.trade_app.util.ToastUtils;

/**
 * BuyOrSell - 历史委托 fragment.
 */
public class OrderHistoryFragment extends LazyLoadBaseFragment implements HistoryContract.View {

    @BindView(R.id.entrust_type)
    NiceSpinner entrust_type;

    TradeHistoryAdapter adapter;
    HistoryContract.Presenter presenter = new HistoryPresenter(this);
    MarketIdBean mb;

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


    List<String> dataset;
    int page = 1;
    int limit = 10;
    List<HistoryBean> globleHb = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.my_transation_record;
    }

     @Override
    protected void initView(View rootView) {
        mb = ((saleAndBuyActivity) getActivity()).getMarketIdBean();
        setRefreshLayout();
        dataset = new LinkedList<>(Arrays.asList(getResources().getString(R.string.all),  getResources().getString(R.string.traded), getResources().getString(R.string.revocation)));
        entrust_type.attachDataSource(dataset);
        adapter = new TradeHistoryAdapter(getActivity(), R.layout.trade_history_item);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        entrust_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                page = 1;
                if (dataset.get(position).equals(getResources().getString(R.string.all))) {
                    presenter.history(page, limit, mb.getMarketId(), null);
                }  else if (dataset.get(position).equals(getResources().getString(R.string.traded))) {
                    presenter.history(page, limit, mb.getMarketId(), "3");
                } else if (dataset.get(position).equals(getResources().getString(R.string.revocation))) {
                    presenter.history(page, limit, mb.getMarketId(), "4");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
            presenter.history(page, limit, mb.getMarketId(), null);
        }   else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.traded))) {
            presenter.history(page, limit, mb.getMarketId(), "3");
        } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.revocation))) {
            presenter.history(page, limit, mb.getMarketId(), "4");
        }
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                page = 1;
                if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
                    presenter.history(page, limit, mb.getMarketId(), null);
                }  else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.traded))) {
                    presenter.history(page, limit, mb.getMarketId(), "3");
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.revocation))) {
                    presenter.history(page, limit, mb.getMarketId(), "4");
                }
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.all))) {
                    presenter.history(page, limit, mb.getMarketId(), null);
                }   else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.traded))) {
                    presenter.history(page, limit, mb.getMarketId(), "3");
                } else if (dataset.get(entrust_type.getSelectedIndex()).equals(getResources().getString(R.string.revocation))) {
                    presenter.history(page, limit, mb.getMarketId(), "4");
                }
                refreshLayout.finishLoadMore();
            }
        });
    }


    @Override
    public void historySuccess(ResponsePaging<HistoryBean> response) {
        if (refreshLayout == null) return;
        if (response.getPagingData() == null) return;
        List<HistoryBean> list = response.getPagingData().getItem();
        int size = list.size();
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

    @Override
    public void historyFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }

}
