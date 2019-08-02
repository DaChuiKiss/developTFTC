package oysd.com.trade_app.modules.trade.view.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.modules.trade.adapter.TradeOrderBuyAdapter;
import oysd.com.trade_app.modules.trade.adapter.TradeOrderSaleAdapter;
import oysd.com.trade_app.modules.trade.bean.SocketOrder;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivityTest;
import oysd.com.trade_app.util.ToastUtils;

/**
 * K 线图页面 - 委托 fragment.
 */
public class OrderFragment extends LazyLoadBaseFragment {
    @BindView(R.id.lv_entrust_buy)
    RecyclerView lv_entrust_buy;

    @BindView(R.id.lv_entrust_sale)
    RecyclerView lv_entrust_sale;

    TradeOrderBuyAdapter tradeBuyAdapter;
    TradeOrderSaleAdapter tradeSaleAdapter;
    List<SocketOrder.BuyEntrustListBean> bbGloble = new ArrayList<>();
    List<SocketOrder.SellEntrustListBean> sbGloble = new ArrayList<>();
    int marketId;
    int index = 10;

    @Override
    protected int getLayoutRes() {
        return R.layout.entrust_activity;
    }

    @Override
    protected void initView(View rootView) {
        bbGloble.clear();
        sbGloble.clear();
        //初始化
        for (int i = 0; i < index; i++) {
            SocketOrder.BuyEntrustListBean bb2 = new SocketOrder.BuyEntrustListBean();
            SocketOrder.SellEntrustListBean sb2 = new SocketOrder.SellEntrustListBean();
            bb2.setPrice(BigDecimal.ZERO);
            bb2.setVolume(BigDecimal.ZERO);
            sb2.setPrice(BigDecimal.ZERO);
            sb2.setVolume(BigDecimal.ZERO);
            bbGloble.add(bb2);
            sbGloble.add(sb2);
        }
    }


    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        tradeBuyAdapter = new TradeOrderBuyAdapter(getActivity(), R.layout.activity_order_buy_item);
        lv_entrust_buy.setAdapter(tradeBuyAdapter);
        lv_entrust_buy.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_entrust_buy.setItemAnimator(new DefaultItemAnimator());
        tradeBuyAdapter.setDataSet(bbGloble);

        tradeSaleAdapter = new TradeOrderSaleAdapter(getActivity(), R.layout.activity_order_sale_item);
        lv_entrust_sale.setAdapter(tradeSaleAdapter);
        lv_entrust_sale.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_entrust_sale.setItemAnimator(new DefaultItemAnimator());
        tradeSaleAdapter.setDataSet(sbGloble);
    }

    @Override
    protected void initData() {
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                kilineSocketOrder();
            }
        }).start();
    }

    Disposable disposable;

    public void kilineSocketOrder() {
        disposable = SocketClient.getInstance().topicMarketDepth(((KLineTradeActivityTest) getActivity()).getMarketId(), new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                //ToastUtils.showLong(App.getContext(), "OrderFragment" + response);
                String s = response.replace("\n", "");
                Gson gson = new Gson();
                SocketOrder so = gson.fromJson(s, SocketOrder.class);
                bbGloble.clear();
                sbGloble.clear();
                //初始化
                for (int i = 0; i < index; i++) {
                    SocketOrder.BuyEntrustListBean bb2 = new SocketOrder.BuyEntrustListBean();
                    SocketOrder.SellEntrustListBean sb2 = new SocketOrder.SellEntrustListBean();
                    bb2.setPrice(BigDecimal.ZERO);
                    bb2.setVolume(BigDecimal.ZERO);
                    sb2.setPrice(BigDecimal.ZERO);
                    sb2.setVolume(BigDecimal.ZERO);
                    bbGloble.add(bb2);
                    sbGloble.add(sb2);
                }

                List<SocketOrder.BuyEntrustListBean> bb = so.getBuyEntrustList();
                List<SocketOrder.SellEntrustListBean> sb = so.getSellEntrustList();

                for (int i = 0; i < bb.size(); i++) {
                    bbGloble.get(i).setPrice(bb.get(i).getPrice());
                    bbGloble.get(i).setVolume(bb.get(i).getVolume());
                }

                for (int i = 0; i < sb.size(); i++) {
                    sbGloble.get(i).setPrice(sb.get(i).getPrice());
                    sbGloble.get(i).setVolume(sb.get(i).getVolume());
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tradeSaleAdapter.setDataSet(sbGloble);
                        tradeSaleAdapter.notifyDataSetChanged();
                        tradeBuyAdapter.setDataSet(bbGloble);
                        tradeBuyAdapter.notifyDataSetChanged();
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
}

//    private Handler handler = new Handler();
//    private Runnable runnable = new Runnable() {
//        public void run() {
//            this.update();
//            handler.postDelayed(this, Global.delayTime);// 间隔120秒
//        }
//
//        void update() {
//            //刷新msg的内容
//           // ToastUtils.showLong(App.getContext(), "买卖单委托定时刷新");
//            //presenter.getUpList(1);
//            presenter.getBuyAndSaleList(10, marketId, 1);
//            presenter.getBuyAndSaleList(10, marketId, 2);
//        }
//    };


