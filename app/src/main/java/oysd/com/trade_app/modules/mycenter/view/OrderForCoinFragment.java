package oysd.com.trade_app.modules.mycenter.view;

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

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.MyOrderCoinAdapter;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.modules.mycenter.contract.CoinOrderContract;
import oysd.com.trade_app.modules.mycenter.presenter.CoinOrderPresenter;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;


public class OrderForCoinFragment extends LazyLoadBaseFragment implements CoinOrderContract.View {

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

    MyOrderCoinAdapter adapter;
    int page = 1;
    int limit = 10;
    List<CoinOrderBean> globleCob = new ArrayList<>();
    CoinOrderContract.Presenter presenter = new CoinOrderPresenter(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_order_coin;
    }

    @Override
    protected void initView(View rootView) {
        adapter = new MyOrderCoinAdapter(getActivity(), R.layout.mycenter_order_coin_item);

        adapter.setOnTradeRevokeListener(new MyOrderCoinAdapter.OnTradeRevokeListener() {
            @Override
            public void onRevoke(CoinOrderBean cb, int position) {
                showPasswordDialog(cb.getId());
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.getAll(page, limit);
        setRefreshLayout();
    }

    //需要传入id
    private void showPasswordDialog(int id) {
        PasswordDialog dialog = new PasswordDialog(context, null, getResources().getString(R.string.dialogMsg4), false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                presenter.rollbackOne(id);
            }
        });
        dialog.show();
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                page = 1;
                presenter.getAll(page, limit);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
                page++;
                presenter.getAll(page, limit);
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void rollbackOneSuccess() {
        page = 1;
        presenter.getAll(page, limit);
    }

    @Override
    public void rollbackOneFailed(int code, String msg) {
    }

    @Override
    public void getAllSuccess(ResponsePaging<CoinOrderBean> response) {
        if (refreshLayout == null) return;
        if (response.getPagingData() == null) return;

        List<CoinOrderBean> list = response.getPagingData().getItem();
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
    public void getAllFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }

}
