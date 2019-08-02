package oysd.com.trade_app.modules.trade.view.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.modules.trade.adapter.TradeDealAdapter;
import oysd.com.trade_app.modules.trade.bean.DealBean;
import oysd.com.trade_app.modules.trade.bean.SocketRecord;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivityTest;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.ToastUtils;

/**
 * K 线图页面 - 明细 fragment.
 */

public class DetailFragment extends LazyLoadBaseFragment {

    @BindView(R.id.lv_entrust_deal)
    RecyclerView lv_entrust_deal;

    TradeDealAdapter tradeDealAdapter;
    int marketId;

    @Override
    protected int getLayoutRes() {
        return R.layout.trade_detail_activity;
    }


    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        marketId = ((KLineTradeActivityTest) getActivity()).getMarketId();
        // presenter.getDealReachedList(10, marketId);
        tradeDealAdapter = new TradeDealAdapter(getActivity(), R.layout.activity_trade_deal_item);
        lv_entrust_deal.setAdapter(tradeDealAdapter);
        lv_entrust_deal.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_entrust_deal.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initView(View rootView) {


    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        isFirst = true;
        dblistGloble.clear();
        for (int i = 0; i < 10; i++) {
            DealBean db = new DealBean();
            db.setCount(BigDecimal.ZERO);
            db.setPrice(BigDecimal.ZERO);
            dblistGloble.add(db);
        }
        tradeDealAdapter.setDataSet(dblistGloble);
        tradeDealAdapter.notifyDataSetChanged();

        new Thread(new Runnable() {
            @Override
            public void run() {
                kilineSocketDetail();
            }
        }).start();
    }

    List<DealBean> dblistGloble = new ArrayList<DealBean>();
    Disposable disposable;
    boolean isFirst = true;

    public void kilineSocketDetail() {
        disposable = SocketClient.getInstance().topicReachedInfo(((KLineTradeActivityTest) getActivity()).getMarketId(), new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                //ToastUtils.showLong(App.getContext(), "DetailFragment"+response);
                String s = response.replace("\n", "");
                Gson gson = new Gson();
                List<SocketRecord> skLs = null;
                SocketRecord sr = null;
                if (isFirst) {
                    isFirst = false;
                    skLs = gson.fromJson(s, new TypeToken<List<SocketRecord>>() {
                    }.getType());
                    List<DealBean> tempList = new ArrayList<DealBean>();
                    for (int i = 0; i < skLs.size(); i++) {
                        DealBean db = new DealBean();
                        db.setPrice(skLs.get(i).getPrice());
                        db.setCount(skLs.get(i).getVolume());
                        db.setTime(DateTimeUtils.correctSvrTime(skLs.get(i).getDate(), DateTimeUtils.HH_MM_SS, DateTimeUtils.HH_MM_SS));
                        db.setType(skLs.get(i).getType());
                        tempList.add(db);
                    }
                    dblistGloble.addAll(0, tempList);
                } else {
                    sr = gson.fromJson(s, SocketRecord.class);
                    if (sr != null) {
                        DealBean db = new DealBean();
                        db.setPrice(sr.getPrice());
                        db.setCount(sr.getVolume());
                        db.setTime(DateTimeUtils.correctSvrTime(sr.getDate(), DateTimeUtils.HH_MM_SS, DateTimeUtils.HH_MM_SS));
                        db.setType(sr.getType());
                        dblistGloble.add(0, db);
                    }
                }
                if (dblistGloble.size() > 40) {
                    List<DealBean> tempList = new ArrayList<DealBean>();
                    tempList.addAll(dblistGloble.subList(0, 20));
                    dblistGloble.clear();
                    dblistGloble.addAll(tempList);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dblistGloble.size() > 10) {
                            tradeDealAdapter.setDataSet(dblistGloble.subList(0, 10));
                        } else {
                            tradeDealAdapter.setDataSet(dblistGloble);
                        }
                        tradeDealAdapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        if (disposable != null) {
            disposable.dispose();
        }

    }

    @Override
    protected void initData() {
        super.initData();

    }

}
