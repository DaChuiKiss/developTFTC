package oysd.com.trade_app.modules.trade.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.bean.SocketOrder;
import oysd.com.trade_app.modules.trade.contract.MarketInfoContract;
import oysd.com.trade_app.modules.trade.presenter.MarketInfoPresenter;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.modules.trade.view.fragment.BuyFragment;
import oysd.com.trade_app.modules.trade.view.fragment.CurrentOrderFragment;
import oysd.com.trade_app.modules.trade.view.fragment.OrderHistoryFragment;
import oysd.com.trade_app.modules.trade.view.fragment.SaleFragment;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;

public class saleAndBuyActivity extends BaseToolActivity implements MarketInfoContract.View {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    public static String[] TITLES;
    boolean isUserMarket = false;

    public MarketIdBean getMarketIdBean() {
        return marketIdBean;
    }

    public void setMarketIdBean(MarketIdBean marketIdBean) {
        this.marketIdBean = marketIdBean;
    }

    private MarketIdBean marketIdBean;

    public MarketInfoBean getMinfo() {
        return minfo;
    }

    public void setMinfo(MarketInfoBean minfo) {
        this.minfo = minfo;
    }

    private MarketInfoBean minfo;
    private int indexClick;

    public int getFagmentIndex() {
        return fagmentIndex;
    }

    public void setFagmentIndex(int fagmentIndex) {
        this.fagmentIndex = fagmentIndex;
    }

    int fagmentIndex = 0;
    ArrayList<Fragment> fragment;

    @Override
    protected int setView() {
        return R.layout.activity_sale_buy;
    }

    MarketInfoContract.Presenter presenter = new MarketInfoPresenter(this);

    @Override
    protected void initData() {
        super.initData();
        TITLES = new String[4];
        TITLES[0] = getResources().getString(R.string.buy);
        TITLES[1] = getResources().getString(R.string.sale);
        TITLES[2] = getResources().getString(R.string.current);
        TITLES[3] = getResources().getString(R.string.history);
        fragment = new ArrayList<>();
        fragment.add(new BuyFragment());
        fragment.add(new SaleFragment());
        fragment.add(new CurrentOrderFragment());
        fragment.add(new OrderHistoryFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragment, TITLES);
        viewPager.setNoScroll(false);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void clickImage01() {
        super.clickImage01();
        //未登入状态就去改变本地保存的自选列表
        if (!InfoProvider.getInstance().getLogin()) {
            if (isUserMarket) {
                isUserMarket = false;
                isSelect(false);
                InfoProvider.getInstance().deleteMarketId(minfo.getMarketId());
            } else {
                isUserMarket = true;
                isSelect(true);
                InfoProvider.getInstance().saveMarketId(minfo.getMarketId());
            }
        } else {
            //登入状态
            if (isUserMarket) {
                isUserMarket = false;
                presenter.removeOptional(minfo.getMarketId());
            } else {
                isUserMarket = true;
                Map<String, Object> params = new HashMap<>();
                params.put("marketId", minfo.getMarketId());
                presenter.addOptional(params);
            }
        }
    }

    @Override
    protected void clickImage02() {
        super.clickImage02();
        saleAndBuyActivity.this.finish();
    }

    @Override
    protected void initView() {
        super.initView();
        setMenuImage01Show(true);
        Global.isChildActivity = false;
        setMenuImage02Show(true);
        setMenuImage02(R.mipmap.suju);
        Intent intent = this.getIntent();
        marketIdBean = intent.getExtras().getParcelable("MarketIdBean");
        minfo = intent.getExtras().getParcelable("MarketInfoBean");
        indexClick = intent.getExtras().getInt("index");
        setTitleText(minfo.getMarketName());
        // marketIdBean = (MarketIdBean) intent.getParcelableExtra("MarketIdBean");
        presenter.getUseOptionalMarket();
        presenter.getTransactionMarketInfo(minfo.getMarketId());

    }

    @Override
    protected void onResume() {
//        /**
//         * 先屏蔽掉Socket
//         */
//        if (!SocketClient.getInstance().isConnect()) {
//            SocketClient.getInstance().connect();
//        }
        tabLayout.getTabAt(indexClick).select();
        //初始化买卖挂单
        bList.clear();
        sList.clear();
        for (int i = 0; i < index; i++) {
            SocketOrder.BuyEntrustListBean bb2 = new SocketOrder.BuyEntrustListBean();
            SocketOrder.SellEntrustListBean sb2 = new SocketOrder.SellEntrustListBean();
            bb2.setPrice(BigDecimal.ZERO);
            bb2.setVolume(BigDecimal.ZERO);
            sb2.setPrice(BigDecimal.ZERO);
            sb2.setVolume(BigDecimal.ZERO);
            bList.add(bb2);
            sList.add(sb2);
        }
        run();
        kilineSocketOrder();

        super.onResume();
    }


    protected void onPause() {
        //Socket断开连接
        SocketClient.getInstance().disConnect();
        stop();
        super.onPause();
    }

    private void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void run() {
        disposable = Observable.interval(Global.delayTime, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.getTransactionMarketInfo(marketIdBean.getMarketId());
                        presenter.getByMarketId(marketIdBean.getMarketId());

                    }
                });
    }

    public List<SocketOrder.BuyEntrustListBean> getbList() {
        return bList;
    }

    public void setbList(List<SocketOrder.BuyEntrustListBean> bList) {
        this.bList = bList;
    }

    public List<SocketOrder.SellEntrustListBean> getsList() {
        return sList;
    }

    public void setsList(List<SocketOrder.SellEntrustListBean> sList) {
        this.sList = sList;
    }

    List<SocketOrder.BuyEntrustListBean> bList = new ArrayList<>();
    List<SocketOrder.SellEntrustListBean> sList = new ArrayList<>();
    int index = 5;

    Disposable disposable;

    // TODO: 2019/7/18  买入或者卖出，两次以上不刷新，返回则崩溃
    public void kilineSocketOrder() {
        disposable = SocketClient.getInstance().topicMarketDepth(marketIdBean.getMarketId(), new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                //ToastUtils.showLong(App.getContext(), response);
                String s = response.replace("\n", "");
                Gson gson = new Gson();
                SocketOrder so = gson.fromJson(s, SocketOrder.class);
                bList.clear();
                sList.clear();
                //初始化
                for (int i = 0; i < index; i++) {
                    SocketOrder.BuyEntrustListBean bb2 = new SocketOrder.BuyEntrustListBean();
                    SocketOrder.SellEntrustListBean sb2 = new SocketOrder.SellEntrustListBean();
                    bb2.setPrice(BigDecimal.ZERO);
                    bb2.setVolume(BigDecimal.ZERO);
                    sb2.setPrice(BigDecimal.ZERO);
                    sb2.setVolume(BigDecimal.ZERO);
                    bList.add(bb2);
                    sList.add(sb2);
                }

                List<SocketOrder.BuyEntrustListBean> bb = so.getBuyEntrustList();
                List<SocketOrder.SellEntrustListBean> sb = so.getSellEntrustList();

//                //List反转
//                List<SocketOrder.SellEntrustListBean> sbLs =new ArrayList<>();
//                if(sb!=null&&sb.size()>0) {
//                    for (int i = sb.size() - 1; i >= 0; i--) {
//                        sbLs.add(sb.get(i));
//                    }
//                }

                List<SocketOrder.SellEntrustListBean> tempSList = new ArrayList<>();
                //初始化
                for (int i = 0; i < index; i++) {
                    SocketOrder.SellEntrustListBean sb2 = new SocketOrder.SellEntrustListBean();
                    sb2.setPrice(BigDecimal.ZERO);
                    sb2.setVolume(BigDecimal.ZERO);
                    tempSList.add(sb2);
                }
                if (bb.size() >= index) {
                    for (int i = 0; i < index; i++) {
                        bList.get(i).setPrice(bb.get(i).getPrice());
                        bList.get(i).setVolume(bb.get(i).getVolume());
                    }
                } else {
                    for (int i = 0; i < bb.size(); i++) {
                        bList.get(i).setPrice(bb.get(i).getPrice());
                        bList.get(i).setVolume(bb.get(i).getVolume());
                    }
                }

                if (sb.size() >= index) {
                    for (int i = 0; i < index; i++) {
                        tempSList.get(i).setPrice(sb.get(i).getPrice());
                        tempSList.get(i).setVolume(sb.get(i).getVolume());
                    }
                } else {
                    for (int i = 0; i < sb.size(); i++) {
                        tempSList.get(i).setPrice(sb.get(i).getPrice());
                        tempSList.get(i).setVolume(sb.get(i).getVolume());
                    }
                }


                if (tempSList != null && sList.size() >= tempSList.size()) {
                    for (int i = 0; i < tempSList.size(); i++) {
                        sList.get(sList.size() - 1 - i).setPrice(tempSList.get(i).getPrice());
                        sList.get(sList.size() - 1 - i).setVolume(tempSList.get(i).getVolume());
                    }

                }

                if (getFagmentIndex() == 1) {
                    ((BuyFragment) fragment.get(0)).reflash(sList, bList);
                } else if (getFagmentIndex() == 2) {
                    ((SaleFragment) fragment.get(1)).reflash(sList, bList);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
        Logger.d("---------------ddddd" + disposable);
    }

    public void isSelect(boolean flag) {
        if (flag) {
            setMenuImage01(R.mipmap.is_select_ok_1);
        } else {
            setMenuImage01(R.mipmap.is_select_no_1);
        }
    }

    @Override
    public void getTransactionMarketInfoSuccess(MarketInfoBean response) {

    }

    @Override
    public void getTransactionMarketInfoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void removeOptionalSuccess() {
        isUserMarket = false;
        isSelect(false);
    }

    @Override
    public void removeOptionalFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void addOptionalSuccess() {
        isUserMarket = true;
        isSelect(true);
        ToastUtils.showLong(App.getContext(), R.string.add_optional_success);
    }

    @Override
    public void addOptionalFailed(int code, String msg) {
        ToastUtils.showLong(this, code + msg);
    }

    @Override
    public void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response) {
        List<MarketListBean> mbls = response.getData();
        if (mbls.size() > 0) {
            for (int i = 0; i < mbls.size(); i++) {
                if (mbls.get(i).getMarketId() == minfo.getMarketId()) {
                    isUserMarket = true;
                }
            }
            if (isUserMarket) {
                isSelect(true);
            } else {
                isSelect(false);
            }
        } else {
            if (isUserMarket) {
                isSelect(true);
            } else {
                isSelect(false);
            }
        }
    }

    @Override
    public void getUseOptionalMarketFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), code + msg);
    }

    @Override
    public void getByMarketIdSuccess(MarketIdBean mb) {
    }

    @Override
    public void getByMarketIdFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }
}
