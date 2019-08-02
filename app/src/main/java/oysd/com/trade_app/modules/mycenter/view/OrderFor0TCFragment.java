package oysd.com.trade_app.modules.mycenter.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.MyOrderOTCAdapter;
import oysd.com.trade_app.modules.mycenter.contract.OTCOrderContract;
import oysd.com.trade_app.modules.mycenter.presenter.OTCOrderPresenter;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.modules.otc.view.activity.AppealingActivity;
import oysd.com.trade_app.modules.otc.view.activity.PaidActivity;
import oysd.com.trade_app.modules.otc.view.activity.TradeCanceledActivity;
import oysd.com.trade_app.modules.otc.view.activity.TradeCompletedActivity;
import oysd.com.trade_app.modules.otc.view.activity.UnpaidActivity;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;

public class OrderFor0TCFragment
        extends LazyLoadBaseFragment implements OTCOrderContract.View {

    @BindView(R.id.srl_smart_refresh)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv_smart_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.ll_empty_no_order)
    LinearLayout llNoOrder;

    @BindView(R.id.ll_empty_no_data)
    LinearLayout llNodata;

    @BindView(R.id.nc_mycenter_order_otc_pay_type)
    NiceSpinner spinner;

    private OTCOrderContract.Presenter presenter = new OTCOrderPresenter(this);

    private int page = 1;
    private int limit = 5;

    private List<String> index;
    private String selectedIndex = null;
    private MyOrderOTCAdapter adapter;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_order_for_otc;
    }

    @Override
    protected void initView(View rootView) {
        adapter = new MyOrderOTCAdapter(context, R.layout.mycenter_order_otc_item);
        adapter.setOnItemSelectedListener(new MyOrderOTCAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(OtcOrderBean otcOrderBean, int position) {
                gotoOrderInfoPage(otcOrderBean);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setRefreshLayout();

        index = new LinkedList<>(Arrays.asList(getResources().getString(R.string.otc_order_type1),
                getResources().getString(R.string.otc_order_type2),
                getResources().getString(R.string.otc_order_type3),
                getResources().getString(R.string.otc_order_type4),
                getResources().getString(R.string.otc_order_type5),
                getResources().getString(R.string.otc_order_type6)));

        spinner.attachDataSource(index);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position == 0 ? null : String.valueOf(position);
                page = 1;
                requestNetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                page = 1;
                requestNetData();
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                requestNetData();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void initData() {
        page = 1;
        requestNetData();
    }

    // 请求网络数据.
    private void requestNetData() {
        presenter.getOnlineOrderList(page, limit, null, selectedIndex);
    }

    @Override
    public void getOnlineOrderListSuccess(ResponsePaging<OtcOrderBean> response) {
        if(llNodata==null)return;
        if (response.getPagingData() == null) return;

        List<OtcOrderBean> list = response.getPagingData().getItem();
        int size = list.size();

        if (page == 1) {
            adapter.setDataSet(list);
            if (size == 0) {
                llNodata.setVisibility(View.VISIBLE);
            } else {
                if (llNodata.getVisibility() == View.VISIBLE) llNodata.setVisibility(View.GONE);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }
        } else {
            adapter.addDataSet(list);
            if (size < limit) refreshLayout.setNoMoreData(true);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void getOnlineOrderListFailed(int code, String msg) {
        Logger.d("code = " + code + " , msg = " + msg);
        ToastUtils.showShort(App.getContext(),msg);
    }


    /**
     * 跳转到订单的详细的页面.
     *
     * @param otcOrderBean OtcOrderBean
     */
    public void gotoOrderInfoPage(@NonNull OtcOrderBean otcOrderBean) {
        int orderId = otcOrderBean.getId();
        int transactionType = otcOrderBean.getTransactionType();

        Bundle bundle = new Bundle();

        switch (otcOrderBean.getStatus()) {
            case Values.OrderStatus.UNPAID:
                bundle.putInt(UnpaidActivity.EXTRA_ORDER_ID, orderId);
                IntentUtils.startActivity(context, UnpaidActivity.class, bundle);
                break;

            case Values.OrderStatus.PAID:
                bundle.putInt(PaidActivity.EXTRA_ORDER_ID, orderId);
                IntentUtils.startActivity(context, PaidActivity.class, bundle);
                break;

            case Values.OrderStatus.COMPLETED:
                bundle.putInt(TradeCompletedActivity.EXTRA_ORDER_ID, orderId);
                IntentUtils.startActivity(context, TradeCompletedActivity.class, bundle);
                break;

            case Values.OrderStatus.CANCELED:
                bundle.putInt(TradeCanceledActivity.EXTRA_ORDER_ID, orderId);
                IntentUtils.startActivity(context, TradeCanceledActivity.class, bundle);
                break;

            case Values.OrderStatus.APPEALING:
                bundle.putInt(AppealingActivity.EXTRA_ORDER_ID, orderId);
                IntentUtils.startActivity(context, AppealingActivity.class, bundle);
                break;

            default:
                break;
        }
    }

    /**
     * 从其它页面返回时更新 list 数据。
     */
    @Override
    public void onResume() {
        super.onResume();

        page = 1;
        requestNetData();
    }

}
