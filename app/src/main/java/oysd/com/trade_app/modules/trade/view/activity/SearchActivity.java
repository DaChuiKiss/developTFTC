package oysd.com.trade_app.modules.trade.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.Tools.CountrySortModel;
import oysd.com.trade_app.modules.home.adapter.HomeAdapter;
import oysd.com.trade_app.modules.trade.adapter.SearchAdapter;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.SearchCoinContract;
import oysd.com.trade_app.modules.trade.presenter.SeachCoinPresenter;

public class SearchActivity extends BaseToolActivity implements SearchCoinContract.View {

    @BindView(R.id.rv_home_recycler)
    RecyclerView rv_home_recycler;
    private SearchAdapter searchAdapter;

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    @BindView(R.id.tv_search_coin)
    EditText tv_search_coin;
    List<MarketListBean> mbls;
    static MarketListBean marketListBean;
    boolean isUserMarket;
    SearchCoinContract.Presenter presenter = new SeachCoinPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_search_trade_coin;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                SearchActivity.this.finish();
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {

        setTitleText(R.string.search);
        searchAdapter = new SearchAdapter(this, R.layout.activity_search_item);
        rv_home_recycler.setAdapter(searchAdapter);
        rv_home_recycler.setLayoutManager(new LinearLayoutManager(this));
        rv_home_recycler.setItemAnimator(new DefaultItemAnimator());

        tv_search_coin.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getTransactionMarketList(s.toString());
            }
        });

        searchAdapter.setOnTradeRevokeListener(new SearchAdapter.OnTradeRevokeListener() {
            @Override
            public void onRevoke(MarketListBean searchBean, int position) {
                if (!InfoProvider.getInstance().getLogin()) {
                    quickStartActivity(LoginActivity.class);
                } else {
                    marketListBean = searchBean;
//                        if(searchBean.isSelect()) {
//                            presenter.removeOptional(searchBean.getMarketId());
//                        }else{
//                            Map<String, Object> params = new HashMap<>();
//                            params.put("marketId", searchBean.getMarketId());
//                            presenter.addOptional(params);
//                        }
                    //未登入状态就去改变本地保存的自选列表
                    if (!InfoProvider.getInstance().getLogin()) {
                        if (searchBean.isSelect()) {
                            marketListBean.setSelect(false);
                            InfoProvider.getInstance().deleteMarketId(searchBean.getMarketId());
                        } else {
                            marketListBean.setSelect(true);
                            InfoProvider.getInstance().saveMarketId(searchBean.getMarketId());
                        }
                        searchAdapter.notifyDataSetChanged();
                    } else {
                        //登入状态
                        if (searchBean.isSelect()) {
                            presenter.removeOptional(searchBean.getMarketId());
                        } else {
                            //选中
                            Map<String, Object> params = new HashMap<>();
                            params.put("marketId", searchBean.getMarketId());
                            presenter.addOptional(params);
                        }
                    }
                }
            }
        });

        searchAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchActivity.this, KLineTradeActivityTest.class);
                intent.putExtra("marketId", mbls.get(position).getMarketId());
                intent.putExtra("marketName", mbls.get(position).getMarketName());
                startActivity(intent);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                SearchActivity.this.finish();
            }
        });


        super.initView();

    }

    @Override
    public void getTransactionMarketListSuccess(ResponseList<MarketListBean> response) {
        mbls = response.getData();
        isUserMarket = false;
        if (mbls.size() > 0) {
            //判断登录还是未登录状态自选情况
            if (!InfoProvider.getInstance().getLogin()) {
                for (int i = 0; i < mbls.size(); i++) {
                    if (InfoProvider.getInstance().isUserMarketId(mbls.get(i).getMarketId())) {
                        isSelect(true, mbls.get(i));
                        isUserMarket = true;
                    } else {
                        isSelect(false, mbls.get(i));
                        isUserMarket = false;
                    }
                }

                searchAdapter.setDataSet(mbls);
                searchAdapter.notifyDataSetChanged();
            } else {
                //登入状态
                presenter.getUseOptionalMarket();
            }
        }
    }

    @Override
    public void getTransactionMarketListFailed(int code, String msg) {

    }


    @Override
    public void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response) {


        if (response.getData().size() > 0) {
            for (int i = 0; i < mbls.size(); i++) {
                isUserMarket = false;
                for (int j = 0; j < response.getData().size(); j++) {
                    if (mbls.get(i).getMarketId() == response.getData().get(j).getMarketId()) {
                        isSelect(true, mbls.get(i));
                        isUserMarket = true;
                        break;
                    } else {
                        isUserMarket = false;
                        isSelect(false, mbls.get(i));
                    }
                }
            }

        }
        searchAdapter.setDataSet(mbls);
        searchAdapter.notifyDataSetChanged();

    }

    @Override
    public void getUseOptionalMarketFailed(int code, String msg) {

    }

    @Override
    public void removeOptionalSuccess() {
        marketListBean.setSelect(false);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeOptionalFailed(int code, String msg) {
        marketListBean.setSelect(true);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void addOptionalSuccess() {
        marketListBean.setSelect(true);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void addOptionalFailed(int code, String msg) {
        marketListBean.setSelect(false);
        searchAdapter.notifyDataSetChanged();
    }

    public void isSelect(boolean flag, MarketListBean mb) {
        if (flag) {
            mb.setSelect(true);
        } else {
            mb.setSelect(false);
        }
    }
}
