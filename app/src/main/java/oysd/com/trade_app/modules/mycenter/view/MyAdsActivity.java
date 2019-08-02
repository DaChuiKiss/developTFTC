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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.AdsAdapter;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAdsContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAdsPresenter;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;


public class MyAdsActivity extends BaseToolActivity implements MyAdsContract.View {

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

    @BindView(R.id.ll_myads_title)
    LinearLayout ll_myads_title;

    MyAdsContract.Presenter presenter = new MyAdsPresenter(this);

    int page = 1;
    int limit = 5;
    private AdsAdapter adapter;
    private int userAccountId = 0;

    int otcId=-1;
    @Override
    protected int setView() {
        return R.layout.activity_my_ads;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type1);

        // 实例化一个适配器
        adapter = new AdsAdapter(this, R.layout.item_my_ads);
        adapter.setOnTradeRevokeListener(new AdsAdapter.OnTradeRevokeListener() {
            @Override
            public void onRevoke(OtcAdBean otcAdBean, int position) {
                    otcId =otcAdBean.getId();
                    showPasswordDialog();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setRefreshLayout();
    }
    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(context,null,getResources().getString(R.string.dialogMsg7),false,false,R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                if(otcId!=-1) {
                    presenter.cancelAd(otcId);
                }
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
                presenter.getAds(page, limit, userAccountId);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                presenter.getAds(page, limit, userAccountId);
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void initData() {
        UserInfoBean userInfoBean = InfoProvider.getInstance().getUserInfo();
        userAccountId = userInfoBean != null ? userInfoBean.getUserAccountId() : 0;
        presenter.getAds(page, limit, userAccountId);
    }

    @Override
    public void getAdsSuccess(ResponsePaging<OtcAdBean> response) {
        if(llNoData==null)return;
        if (response.getPagingData() == null) {
            adapter.notifyDataSetChanged();
            llNoData.setVisibility(View.VISIBLE);
            return;
        }
        List<OtcAdBean> list = response.getPagingData().getItem();
        if (EmptyUtils.isEmpty(list)) {
            llNoData.setVisibility(View.VISIBLE);
            adapter.setDataSet(list);
            adapter.notifyDataSetChanged();
            return;
        }

        int size = list.size();

        if (page == 1) {
            adapter.setDataSet(list);
            if (size == 0) {
                llNoData.setVisibility(View.VISIBLE);
            } else {
                if (llNoData.getVisibility() == View.VISIBLE) llNoData.setVisibility(View.GONE);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }

        } else {
            adapter.addDataSet(list);
            if (size < limit) refreshLayout.setNoMoreData(true);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAdsFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void cancelAdSuccess() {
        // 重新取数据
        page = 1;
        presenter.getAds(page, limit, userAccountId);
    }

    @Override
    public void cancelAdFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
