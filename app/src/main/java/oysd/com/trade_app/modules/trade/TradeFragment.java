package oysd.com.trade_app.modules.trade;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseFragment;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.di.DaggerTradeComponent;
import oysd.com.trade_app.modules.trade.di.TradeModule;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.modules.trade.view.activity.SearchActivity;
import oysd.com.trade_app.modules.trade.view.fragment.TradeCoinFragment;
import oysd.com.trade_app.modules.trade.view.fragment.TradeMySelectionsFragment;
import oysd.com.trade_app.util.ACache;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.ITabClickListener;

/**
 * Created by yx on 16/4/3.
 */
public class TradeFragment extends LazyLoadBaseFragment
        implements ITabClickListener, TradeContract.View {

    @Inject
    TradeContract.Presenter presenter;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    @BindView(R.id.gotoSearch)
    LinearLayout gotoSearch;

    public static String[] TITLES;

    private ACache mACache;

    @Override
    public void onMenuItemClick() {


    }

    @Override
    public BaseFragment getFragment() {
        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.trade_layout;
    }

    @Override
    protected void initView(View rootView) {
        DaggerTradeComponent.builder()
                .tradeModule(new TradeModule(this))
                .build()
                .inject(this);

        gotoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void getDealZoneInfoSuccess(ResponseList<DealZoneBean> response) {
//        ToastUtils.showLong(App.getContext(), response.getData().toString());
        List<DealZoneBean> dbList = response.getData();
        ArrayList<Fragment> fragment = new ArrayList<>();
        TITLES = new String[dbList.size() + 1];
        fragment.add(new TradeMySelectionsFragment());
        TITLES[0] = getActivity().getResources().getString(R.string.favorites);
        for (int i = 1; i < TITLES.length; i++) {
            TITLES[i] = dbList.get(i - 1).getName();
            fragment.add(TradeCoinFragment.newInstance(dbList.get(i - 1).getId(),"Trade"));
        }

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void getDealZoneInfoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        presenter.getDealZoneInfo(1, 10);

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void getTransactionMarketListSuccess(ResponseList<MarketListBean> response) {
    }

    @Override
    public void getTransactionMarketListFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getMarketPriceChangeRatioListSuccess(ResponseList<MarketListBean> response) {

    }

    @Override
    public void getMarketPriceChangeRatioListFailed(int code, String msg) {

    }

    // WebSocket debugging.
    public static void startWebSocket() {

        SocketClient.getInstance().connect();

        new Thread(() -> {
            try {
                Thread.sleep(5 * 1000);
                Logger.d("is connected : " + SocketClient.getInstance().isConnect());

                Disposable disposable = SocketClient.getInstance().topicKLineInfo(16, 60000, new SocketClient.OnSocketReceivedListener() {
                    @Override
                    public void onReceive(String response) {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }
                });

                Thread.sleep(5 * 1000);

                disposable.dispose();
                SocketClient.getInstance().disConnect();

                Thread.sleep(10 * 1000);
                Logger.d("is connected : " + SocketClient.getInstance().isConnect());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


//        SocketClient.getInstance().topicMarketNewestPrice(16, 1, new SocketClient.OnSocketReceivedListener() {
//            @Override
//            public void onReceive(String response) {
//
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//
//            }
//        });

        // SocketClient.getInstance().topic(16, 60000);
//        SocketClient.getInstance().topicMarketDepth(16, new SocketClient.OnSocketReceivedListener() {
//            @Override
//            public void onReceive(String response) {
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//            }
//        });

//        SocketClient.getInstance().topicKLineInfo(16, 60000, new SocketClient.OnSocketReceivedListener() {
//            @Override
//            public void onReceive(String response) {
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//            }
//        });

//        SocketClient.getInstance().topicReachedInfo(16, new SocketClient.OnSocketReceivedListener() {
//            @Override
//            public void onReceive(String response) {
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//            }
//        });




        /*
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("ws://192.168.0.64:9183/hello/websocket")
                //.url("ws://121.40.165.18:8800")
                //.url("wss://echo.websocket.org")
                .build();


        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Logger.d(response.toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Logger.d(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Logger.d(reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Logger.d(reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Logger.d(response.toString());
            }
        };

        WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send("hello connected!");
        */
    }

}
