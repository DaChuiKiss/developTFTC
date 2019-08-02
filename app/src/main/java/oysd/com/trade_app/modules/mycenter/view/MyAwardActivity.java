package oysd.com.trade_app.modules.mycenter.view;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.PagingDataBean;
import oysd.com.trade_app.modules.mycenter.adapter.MyAwardAdapter;
import oysd.com.trade_app.modules.mycenter.bean.AwardBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAwardContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAwardPresenter;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的奖励
 */
public class MyAwardActivity extends BaseToolActivity implements MyAwardContract.View {
    @BindView(R.id.tv_usdt)
    TextView mUsdt;
    @BindView(R.id.tv_acn)
    TextView mAcn;
    @BindView(R.id.srl_smart_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_empty_no_data)
    LinearLayout llNoData;
    @BindView(R.id.rv_smart_recycler)
    RecyclerView mRecyclerView;
    MyAwardAdapter myAwardAdapter;
    MyAwardContract.Presenter presenter = new MyAwardPresenter(this);

    private int page = 1;
    private int limit = 15;

    private String nytReturnAmount;
    private String vcReturnAmount;

    @Override
    protected int setView() {
        return R.layout.activity_my_award;
    }

    @Override
    protected void initData() {
        setRefreshLayout();
        nytReturnAmount = getIntent().getStringExtra("nytReturnAmount");
        vcReturnAmount = getIntent().getStringExtra("vcReturnAmount");
        mUsdt.setText(FormatUtils.to8NoComma(nytReturnAmount) + "NYT");
        mAcn.setText(FormatUtils.to8NoComma(vcReturnAmount) + "VC");
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

    // 请求网络数据
    private void requestNetData() {
        presenter.getUserInvitationReturnRecordList(page, limit);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.my_award);
        myAwardAdapter = new MyAwardAdapter(this, R.layout.my_award_item);
        mRecyclerView.setAdapter(myAwardAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        page = 1;

        presenter.getUserInvitationRecordList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNetData();
    }

    @Override
    public void getUserInvitationRecordListSuncess(EmptyBean emptyBean) {

    }

    @Override
    public void getUserInvitationRecordListFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void getUserInvitationReturnRecordListSuncess(PagingDataBean<AwardBean> response) {
//        double usdn = 0;
//        double acn = 0;
//        if (response.getItem() != null && response.getItem().size() > 0) {
//            for (AwardBean data : response.getItem()) {
//                if (data.getCoinName().equals("USDT")) {
//                    if (!TextUtils.isEmpty(data.getCommission())) {
//                        usdn += Double.parseDouble(data.getCommission());
//                    }
//                } else if (data.getCoinName().equals("ACN")) {
//                    if (!TextUtils.isEmpty(data.getCommission())) {
//                        acn += Double.parseDouble(data.getCommission());
//                    }
//                }
//            }
//        }
//
//        mUsdt.setText(FormatUtils.to8NoComma(usdn) + "USDN");
//        mAcn.setText(FormatUtils.to8NoComma(acn) + "ACN");
//

        List<AwardBean> list = response.getItem();
        int size = list.size();
        if (myAwardAdapter != null) {
            if (page == 1) {
                myAwardAdapter.setDataSet(list);
                if (size == 0) {
                    llNoData.setVisibility(View.VISIBLE);
                } else {
                    llNoData.setVisibility(View.GONE);
                    if (size < limit) refreshLayout.setNoMoreData(true);
                }

            } else {
                myAwardAdapter.addDataSet(list);
                if (size < limit) refreshLayout.setNoMoreData(true);
            }
            myAwardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getUserInvitationReturnRecordListFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

}

