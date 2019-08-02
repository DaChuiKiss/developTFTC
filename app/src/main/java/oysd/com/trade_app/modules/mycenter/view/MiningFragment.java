package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.home.adapter.HomeAdapter;
import oysd.com.trade_app.modules.mycenter.adapter.UserCostDividendRecordAdapter;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendBean;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.modules.mycenter.contract.UserCostDividendContract;
import oysd.com.trade_app.modules.mycenter.presenter.UserCostDividendPresenter;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class MiningFragment extends LazyLoadBaseFragment implements UserCostDividendContract.View {


    UserCostDividendContract.Presenter presenter = new UserCostDividendPresenter(this);

    @BindView(R.id.tv_mycenter_costDividend)
    TextView tv_mycenter_costDividend;
    @BindView(R.id.rv_smart_recycler)
    RecyclerView rv_smart_recycler;

    @BindView(R.id.ll_mycenter_mining_title)
    LinearLayout ll_mycenter_mining_title;

    @BindView(R.id.ll_empty_no_data)
    LinearLayout ll_empty_no_data;

    @BindView(R.id.bt_mycenter_mining_tovip)
    Button bt_mycenter_mining_tovip;

    UserCostDividendRecordAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.mining;
    }

    @Override
    protected void initView(View rootView) {

        //if (Global.isVip) {
            ll_mycenter_mining_title.setVisibility(View.VISIBLE);
            bt_mycenter_mining_tovip.setVisibility(View.GONE);
            presenter.getUserCostDividend();
            presenter.getUserCostDividendRecordList();
//        } else {
//            ll_mycenter_mining_title.setVisibility(View.GONE);
//            ll_empty_no_data.setVisibility(View.GONE);
//            rv_smart_recycler.setVisibility(View.GONE);
//            bt_mycenter_mining_tovip.setVisibility(View.VISIBLE);
//        }
        adapter = new UserCostDividendRecordAdapter(getActivity(), R.layout.user_cost_dividend_record_item);
        rv_smart_recycler.setAdapter(adapter);
        rv_smart_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_smart_recycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_mining_tovip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_mycenter_mining_tovip:
                Intent intent = new Intent(getActivity(), MyMemberActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
       // if (Global.isVip) {
            ll_mycenter_mining_title.setVisibility(View.VISIBLE);
            bt_mycenter_mining_tovip.setVisibility(View.GONE);
            presenter.getUserCostDividend();
            presenter.getUserCostDividendRecordList();
//        } else {
//            ll_mycenter_mining_title.setVisibility(View.GONE);
//            ll_empty_no_data.setVisibility(View.GONE);
//            rv_smart_recycler.setVisibility(View.GONE);
//            bt_mycenter_mining_tovip.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void getUserCostDividendSuccess(ResponseEntity<String> response) {
        if (tv_mycenter_costDividend == null) return;
        if (response.getData() == null) {
            tv_mycenter_costDividend.setText("0");
        } else {
            tv_mycenter_costDividend.setText(response.getData());
        }
    }

    @Override
    public void getUserCostDividendFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }

    @Override
    public void getUserCostDividendRecordListSuccess(ResponsePaging<UserCostDividendRecord> response) {
        if (adapter == null) return;
        List<UserCostDividendRecord> udrList = response.getPagingData().getItem();
        if(ll_empty_no_data==null)return;
        if (udrList.size() == 0) {
            ll_empty_no_data.setVisibility(View.VISIBLE);
            rv_smart_recycler.setVisibility(View.GONE);
        } else {
            ll_empty_no_data.setVisibility(View.GONE);
            rv_smart_recycler.setVisibility(View.VISIBLE);
        }

        adapter.setDataSet(udrList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getUserCostDividendRecordListFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }
}
