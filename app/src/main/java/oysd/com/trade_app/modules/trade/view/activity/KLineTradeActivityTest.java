package oysd.com.trade_app.modules.trade.view.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.chart.formatter.TimeFormatter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.adapter.KChartAdapter;
import oysd.com.trade_app.modules.trade.bean.KLineEntity;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.bean.SocketKLine;
import oysd.com.trade_app.modules.trade.contract.MarketInfoContract;
import oysd.com.trade_app.modules.trade.presenter.MarketInfoPresenter;
import oysd.com.trade_app.modules.trade.util.DataHelper;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.modules.trade.view.fragment.DetailFragment;
import oysd.com.trade_app.modules.trade.view.fragment.OrderFragment;
import oysd.com.trade_app.util.ACache;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.InfoUtils;
import oysd.com.trade_app.util.LoadingImgDialog;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.SpannableEditor;
import oysd.com.trade_app.util.StatusBarUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc K线图
 */
public class KLineTradeActivityTest extends BaseToolActivity implements MarketInfoContract.View, TabLayout.OnTabSelectedListener {
    public static String[] TITLES;
    public static String[] TITLES_2;
    @BindView(R.id.buy_id)
    TextView buy_id;
    @BindView(R.id.sale_id)
    TextView sale_id;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_layout_2)
    TabLayout tabLayout_2;
    @BindView(R.id.viewpager_2)
    NoScrollViewPager viewPager_2;
    @BindView(R.id.tv_trade_coin_name)
    TextView tv_trade_coin_name;
    @BindView(R.id.ll)
    LinearLayout rlTitleBar;
    @BindView(R.id.tv_trade_padding1)
    RelativeLayout tv_trade_padding1;
    @BindView(R.id.tv_goto)
    ImageView tv_goto;
    @BindView(R.id.tv_trade_coin_rmb_price)
    TextView tv_trade_coin_rmb_price;
    @BindView(R.id.tv_trade_coin_usdt_price)
    TextView tv_trade_coin_usdt_price;
    @BindView(R.id.tv_trade_coin_prent)
    TextView tv_trade_coin_prent;
    @BindView(R.id.tv_trade_coin_hight)
    TextView tv_trade_coin_hight;
    @BindView(R.id.tv_trade_coin_low)
    TextView tv_trade_coin_low;
    @BindView(R.id.tv_trade_coin_24h_volum)
    TextView tv_trade_coin_24h_volum;
    @BindView(R.id.userMarket)
    ImageView userMarket;
    @BindView(R.id.ll_select)
    LinearLayout ll_select;
    @BindView(R.id.ll_status)
    LinearLayout ll_status;
    @BindView(R.id.ll_status_3)
    LinearLayout ll_status_3;
    @BindView(R.id.vv_status_1)
    View vv_status_1;
    @BindView(R.id.kchart_view)
    KChartView mKChartView;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.minuteChartView)
    MinuteChartView mMinuteChartView;

    private KChartAdapter mAdapter;
    MarketIdBean mb;
    MarketInfoBean minfo;
    MarketInfoContract.Presenter presenter = new MarketInfoPresenter(this);
    //是否是自选
    boolean isUserMarket = false;

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    private int index = 0;//TabLayout选中下标
    private int[] KL_INTERVAL = {1, 5, 15, 30, 60, 4 * 60, 24 * 60, 7 * 24 * 60};//单位: Min
    //币的id
    private int marketId;
    private int indexClick = 0;
    private Fragment[] mFragmentArrays = new Fragment[8];
    private ACache mCache;
    LoadingImgDialog loadingColorDialog;

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int setView() {
        return R.layout.trade_info_test;
    }

    private Handler popupHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mCache = ACache.get(KLineTradeActivityTest.this);
                    initCacheInfo(1 * 60 * 1000);
                    kilineSocket1(1 * 60 * 1000);
                    topicMarketNewestPrice(InfoUtils.getLegalTender());
                    break;
            }
        }

    };


    @Override
    protected void initView() {
        long currentClickTime1 = System.currentTimeMillis();
        super.initView();
        //先注释这行
        popupHandler.sendEmptyMessageDelayed(0, 1000);
        TITLES = new String[8];
        TITLES[0] = getResources().getString(R.string.timeLine);
        TITLES[1] = getResources().getString(R.string.time5Line);
        TITLES[2] = getResources().getString(R.string.time15Line);
        TITLES[3] = getResources().getString(R.string.time30Line);
        TITLES[4] = getResources().getString(R.string.time60Line);
        TITLES[5] = getResources().getString(R.string.time4hLine);
        TITLES[6] = getResources().getString(R.string.timeDayLine);
        TITLES[7] = getResources().getString(R.string.timeWeekLine);
        mAdapter = new KChartAdapter();
        mKChartView.setAdapter(mAdapter);
        mKChartView.setDateTimeFormatter(new TimeFormatter());
        mKChartView.setGridRows(2);
        mKChartView.setGridColumns(3);
        mKChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                KLineEntity data = (KLineEntity) point;
                Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
            }
        });

        mMinuteChartView.setAdapter(mAdapter);
        mMinuteChartView.setDateTimeFormatter(new TimeFormatter());
        mMinuteChartView.setGridRows(2);
        mMinuteChartView.setGridColumns(3);
        mMinuteChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                KLineEntity data = (KLineEntity) point;
                Log.i("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());
            }
        });

        for (int i = 0; i < TITLES.length; i++) {
            TextView v = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_kline, null);
            v.setText(TITLES[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(v), i == index);
        }
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        TITLES_2 = new String[2];
        TITLES_2[0] = getResources().getString(R.string.entrust);
        TITLES_2[1] = getResources().getString(R.string.detail);
        ArrayList<Fragment> fragments_2 = new ArrayList<>();
        fragments_2.add(new OrderFragment());
        fragments_2.add(new DetailFragment());
        FragmentAdapter adapter_2 = new FragmentAdapter(getSupportFragmentManager(), fragments_2, TITLES_2);
        viewPager_2.setAdapter(adapter_2);
        tabLayout_2.setupWithViewPager(viewPager_2);
        viewPager_2.setOffscreenPageLimit(0);

        setTitleText(this.getIntent().getExtras().getString("marketName"));
        marketId = this.getIntent().getExtras().getInt("marketId");
        setMenuImage01Show(true);
        setMenuImage01(R.mipmap.qb);
        sale_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastClick()) {
                    indexClick = 1;
                    Global.isChildActivity = true;
                    if (InfoProvider.getInstance().getLogin()) {
                        if (InfoProvider.getInstance().getGesture()) {
                            if (!Global.isLogin) {
                                quickStartActivity(DefaultPatternCheckingActivity.class);
                            } else {
                                Bundle bundle = new Bundle();
                                if (mb != null && minfo != null) {
                                    bundle.putParcelable("MarketIdBean", mb);
                                    bundle.putParcelable("MarketInfoBean", minfo);// 序列化
                                    bundle.putInt("index", indexClick);
                                    quickStartActivity(saleAndBuyActivity.class, bundle);
                                }
                            }
                        } else {
                            Bundle bundle = new Bundle();
                            if (mb != null && minfo != null) {
                                bundle.putParcelable("MarketIdBean", mb);
                                bundle.putParcelable("MarketInfoBean", minfo);// 序列化
                                bundle.putInt("index", indexClick);
                                quickStartActivity(saleAndBuyActivity.class, bundle);
                            }

                        }
                    } else {
                        quickStartActivity(LoginActivity.class);
                    }
                }
            }
        });
        buy_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastClick()) {
                    indexClick = 0;
                    Global.isChildActivity = true;
                    if (InfoProvider.getInstance().getLogin()) {
                        if (InfoProvider.getInstance().getGesture()) {
                            if (!Global.isLogin) {
                                quickStartActivity(DefaultPatternCheckingActivity.class);
                            } else {
                                Bundle bundle = new Bundle();
                                if (mb != null && minfo != null) {

                                    bundle.putParcelable("MarketIdBean", mb);
                                    bundle.putParcelable("MarketInfoBean", minfo);// 序列化
                                    bundle.putInt("index", indexClick);
                                    quickStartActivity(saleAndBuyActivity.class, bundle);
                                } else {
                                }

                            }
                        } else {
                            Bundle bundle = new Bundle();
                            if (mb != null && minfo != null) {
                                bundle.putParcelable("MarketIdBean", mb);
                                bundle.putParcelable("MarketInfoBean", minfo);// 序列化
                                bundle.putInt("index", indexClick);
                                quickStartActivity(saleAndBuyActivity.class, bundle);
                            } else {
                            }

                        }
                    } else {
                        quickStartActivity(LoginActivity.class);
                    }
                }
            }
        });
        long currentClickTime2 = System.currentTimeMillis();
        Log.d("initView", "initView: " + (currentClickTime2 - currentClickTime1));
    }

    boolean isObject;

    /**
     * 获取K线图数据5
     *
     * @param min
     */
    public void kilineSocket5(int min) {
        disposable = SocketClient.getInstance().topicKLineInfo(marketId, min, new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                //ToastUtils.showLong(KLineTradeActivityTest.this, "获取K线图数据5--->onReceive");
                Gson gson = new Gson();
                isObject = false;
                List<KLineEntity> data = new ArrayList<KLineEntity>();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("data")) {
                        isObject = false;
                        mCache.put(min + "" + marketId, response);
                        SocketKLine sk = gson.fromJson(response, SocketKLine.class);
                        List<SocketKLine.DataBean> lsdb = sk.getData();
                        for (int i = 0; i < lsdb.size(); i++) {
                            KLineEntity kLineEntity = new KLineEntity();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            kLineEntity.Date = format.format(Float.parseFloat(String.valueOf(lsdb.get(i).getTime())));
                            kLineEntity.Open = lsdb.get(i).getOpen().floatValue();
                            kLineEntity.High = lsdb.get(i).getHigh().floatValue();
                            kLineEntity.Low = lsdb.get(i).getLow().floatValue();
                            kLineEntity.Close = lsdb.get(i).getClose().floatValue();
                            kLineEntity.Volume = lsdb.get(i).getVolume().floatValue();
                            data.add(kLineEntity);
                        }
                    } else {
                        isObject = true;
                        SocketKLine.DataBean skDb = gson.fromJson(response, SocketKLine.DataBean.class);
                        KLineEntity kLineEntity = new KLineEntity();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        kLineEntity.Date = format.format(Float.parseFloat(String.valueOf(skDb.getTime())));
                        kLineEntity.Open = skDb.getOpen().floatValue();
                        kLineEntity.High = skDb.getHigh().floatValue();
                        kLineEntity.Low = skDb.getLow().floatValue();
                        kLineEntity.Close = skDb.getClose().floatValue();
                        kLineEntity.Volume = skDb.getVolume().floatValue();
                        data.add(kLineEntity);
                    }
                    DataHelper.calculate(data);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter != null) {
                                //第一次加载时开始动画
                                if (mAdapter.getCount() == 0) {
                                    mKChartView.startAnimation();
                                }
                                mAdapter.addFooterData(data, isObject, loadingColorDialog);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (loadingColorDialog != null && loadingColorDialog.isShowing())
                    loadingColorDialog.dismiss();
            }
        });

    }

    /**
     * 订阅 市场最新价格 数据。
     *
     * @param legalTender
     */
    public void topicMarketNewestPrice(String legalTender) {

        disposable = SocketClient.getInstance().topicMarketNewestPrice(marketId, legalTender, new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                Gson gson = new Gson();
                MarketInfoBean marketInfoBean = gson.fromJson(response, MarketInfoBean.class);
                minfo = marketInfoBean;
                initMarketInfo(marketInfoBean);
            }

            @Override
            public void onError(Throwable throwable) {
                if (loadingColorDialog != null && loadingColorDialog.isShowing())
                    loadingColorDialog.dismiss();
            }
        });
    }

    /**
     * 初始化K线图缓存
     *
     * @param min
     */
    public void initCacheInfo(int min) {
        Logger.d("initCacheInfo");
        String response = mCache.getAsString(min + "" + marketId);
        if (response == null) {
            loadingColorDialog = new LoadingImgDialog(KLineTradeActivityTest.this, R.mipmap.loading);
            loadingColorDialog.show();
        } else {
            Gson gson = new Gson();
            isObject = false;
            List<KLineEntity> data = new ArrayList<KLineEntity>();
            try {
                JSONObject obj = new JSONObject(response);
                SocketKLine sk = gson.fromJson(response, SocketKLine.class);
                List<SocketKLine.DataBean> lsdb = sk.getData();
                for (int i = 0; i < lsdb.size(); i++) {
                    KLineEntity kLineEntity = new KLineEntity();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    kLineEntity.Date = format.format(Float.parseFloat(String.valueOf(lsdb.get(i).getTime())));
                    kLineEntity.Open = Float.parseFloat(lsdb.get(i).getOpen().toPlainString());
                    kLineEntity.High = lsdb.get(i).getHigh().floatValue();
                    kLineEntity.Low = lsdb.get(i).getLow().floatValue();
                    kLineEntity.Close = lsdb.get(i).getClose().floatValue();
                    kLineEntity.Volume = lsdb.get(i).getVolume().floatValue();
                    data.add(kLineEntity);
                }
                DataHelper.calculate(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //第一次加载时开始动画
                        if (mAdapter.getCount() == 0) {
                            if (mKChartView != null) {
                                mKChartView.startAnimation();
                            }
                        }
                        mAdapter.addFooterData(data, isObject, loadingColorDialog);
                        if (scrollview != null) {
                            scrollview.scrollTo(0, 0);
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取K线图数据1
     *
     * @param min
     */
    public void kilineSocket1(int min) {
        disposable = SocketClient.getInstance().topicKLineInfo(marketId, min, new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                //ToastUtils.showLong(KLineTradeActivityTest.this, "获取K线图数据1--->onReceive");
                Gson gson = new Gson();
                isObject = false;
                List<KLineEntity> data = new ArrayList<KLineEntity>();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("data")) {
                        isObject = false;
                        mCache.put(min + "" + marketId, response);
                        SocketKLine sk = gson.fromJson(response, SocketKLine.class);
                        List<SocketKLine.DataBean> lsdb = sk.getData();
                        for (int i = 0; i < lsdb.size(); i++) {
                            KLineEntity kLineEntity = new KLineEntity();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            kLineEntity.Date = format.format(Float.parseFloat(String.valueOf(lsdb.get(i).getTime())));
                            kLineEntity.Open = lsdb.get(i).getOpen().floatValue();
                            kLineEntity.High = lsdb.get(i).getHigh().floatValue();
                            kLineEntity.Low = lsdb.get(i).getLow().floatValue();
                            kLineEntity.Close = lsdb.get(i).getClose().floatValue();
                            kLineEntity.Volume = lsdb.get(i).getVolume().floatValue();
                            data.add(kLineEntity);
                        }
                    } else {
                        isObject = true;
                        SocketKLine.DataBean skDb = gson.fromJson(response, SocketKLine.DataBean.class);
                        KLineEntity kLineEntity = new KLineEntity();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        kLineEntity.Date = format.format(Float.parseFloat(String.valueOf(skDb.getTime())));
                        kLineEntity.Open = skDb.getOpen().floatValue();
                        kLineEntity.High = skDb.getHigh().floatValue();
                        kLineEntity.Low = skDb.getLow().floatValue();
                        kLineEntity.Close = skDb.getClose().floatValue();
                        kLineEntity.Volume = skDb.getVolume().floatValue();
                        data.add(kLineEntity);
                    }
                    DataHelper.calculate(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter != null) {
                                //第一次加载时开始动画
                                if (mAdapter.getCount() == 0) {
                                    mMinuteChartView.startAnimation();
                                }
                                mAdapter.addFooterData(data, isObject, loadingColorDialog);
                                scrollview.scrollTo(0, 0);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (loadingColorDialog != null && loadingColorDialog.isShowing())
                    loadingColorDialog.dismiss();
            }
        });
    }


    @Override
    protected void clickImage01() {
        super.clickImage01();
        //切换横屏
        StatusBarUtils.setStatusBarLightMode(KLineTradeActivityTest.this, true);
        tv_goto.setVisibility(View.VISIBLE);
        tv_trade_padding1.setVisibility(View.VISIBLE);
        rlTitleBar.setVisibility(View.GONE);
        ll_status_3.setVisibility(View.GONE);
        vv_status_1.setVisibility(View.GONE);
        ll_status.setVisibility(View.GONE);
        tabLayout_2.setVisibility(View.GONE);
        viewPager_2.setVisibility(View.GONE);
        KLineTradeActivityTest.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        tv_goto.setOnClickListener(this);
        ll_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.ll_select:
                    if (!InfoProvider.getInstance().getLogin()) {
                        quickStartActivity(LoginActivity.class);
                    } else {
                        //未登入状态就去改变本地保存的自选列表
                        if (!InfoProvider.getInstance().getLogin()) {
                            if (isUserMarket) {
                                isUserMarket = false;
                                isSelect(false);
                                InfoProvider.getInstance().deleteMarketId(marketId);
                            } else {
                                isUserMarket = true;
                                isSelect(true);
                                InfoProvider.getInstance().saveMarketId(marketId);
                            }
                        } else {
                            //登入状态
                            if (isUserMarket) {
                                isUserMarket = false;
                                presenter.removeOptional(marketId);
                            } else {
                                isUserMarket = true;
                                Map<String, Object> params = new HashMap<>();
                                params.put("marketId", marketId);
                                presenter.addOptional(params);
                            }
                        }
                    }
                    break;
                case R.id.tv_goto:
                    //切换竖屏
                    StatusBarUtils.setStatusBarLightMode(KLineTradeActivityTest.this, false);
                    tv_goto.setVisibility(View.GONE);
                    ll_status_3.setVisibility(View.VISIBLE);
                    vv_status_1.setVisibility(View.VISIBLE);
                    ll_status.setVisibility(View.VISIBLE);
                    tabLayout_2.setVisibility(View.VISIBLE);
                    viewPager_2.setVisibility(View.VISIBLE);
                    rlTitleBar.setVisibility(View.VISIBLE);
                    tv_trade_padding1.setVisibility(View.GONE);
                    KLineTradeActivityTest.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUserMarket = false;
        //判断登录还是未登录状态自选情况
        Log.d("marketId", marketId + "marketId");
        if (!InfoProvider.getInstance().getLogin()) {
            if (InfoProvider.getInstance().isUserMarketId(marketId)) {
                isSelect(true);
                isUserMarket = true;
            } else {
                isSelect(false);
                isUserMarket = false;
            }
        } else {
            //登入状态
            presenter.getUseOptionalMarket();
        }
        if (!SocketClient.getInstance().isConnect()) {
            SocketClient.getInstance();
        }
        presenter.getTransactionMarketInfo(marketId);
        if (InfoProvider.getInstance().getLogin()) {
            presenter.getByMarketId(marketId);
        }
        run();
    }

    protected void onPause() {
        stop();
        //Socket断开连接
        SocketClient.getInstance().disConnect();
        //将SocketClient设置为null
        SocketClient.getInstance().setInstance();
        super.onPause();
    }

    //添加自选
    public void isSelect(boolean flag) {
        if (flag) {
            userMarket.setBackgroundResource(R.mipmap.is_select_ok);
        } else {
            userMarket.setBackgroundResource(R.mipmap.is_select_no);
        }
    }

    //交易市场信息
    @Override
    public void getTransactionMarketInfoSuccess(MarketInfoBean marketInfoBean) {
        minfo = marketInfoBean;
        initMarketInfo(marketInfoBean);
    }


    //更新
    private void initMarketInfo(MarketInfoBean marketInfoBean) {

        if (tv_trade_coin_name != null) {
            String[] strings = marketInfoBean.getMarketName().split("[/]");
            SpannableEditor spannableEditor = new SpannableEditor(context, marketInfoBean.getMarketName());
            spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_normal), strings[0]);
            spannableEditor.setTextColor(ContextCompat.getColor(context, R.color.text_light), strings[1]);
            spannableEditor.setTextSize(Utils.dip2px(context, 15), strings[0]);
            spannableEditor.setTextSize(Utils.dip2px(context, 13), strings[1]);
            tv_trade_coin_name.setText(spannableEditor.build());

            //SpannableEditor spannableEditor2 = new SpannableEditor(context, EmptyUtils.getBigDecimalValue(marketInfoBean.getNewestPrice()) + marketInfoBean.getBuyCoinName());
            SpannableEditor spannableEditor2 = new SpannableEditor(context, EmptyUtils.getBigDecimalValue(marketInfoBean.getNewestPrice()));
            spannableEditor2.setTextColor(ContextCompat.getColor(context, R.color.text_pink), EmptyUtils.getBigDecimalValue(marketInfoBean.getNewestPrice()));
            //spannableEditor2.setTextColor(ContextCompat.getColor(context, R.color.text_light), marketInfoBean.getBuyCoinName());
            spannableEditor2.setTextSize(Utils.dip2px(context, 17), EmptyUtils.getBigDecimalValue(marketInfoBean.getNewestPrice()));
            //spannableEditor2.setTextSize(Utils.dip2px(context, 12), marketInfoBean.getBuyCoinName());

            tv_trade_coin_usdt_price.setText(spannableEditor2.build());
            tv_trade_coin_rmb_price.setText(FormatUtils.to4(EmptyUtils.getBigDecimalValue(marketInfoBean.getLegalTenderVal())) + marketInfoBean.getSymbol());
            tv_trade_coin_hight.setText(EmptyUtils.getBigDecimalValue(marketInfoBean.getTopAmount()));
            tv_trade_coin_low.setText(EmptyUtils.getBigDecimalValue(marketInfoBean.getLowAmount()));
            tv_trade_coin_24h_volum.setText(Utils.getKtotal(marketInfoBean.getVolume()));
            if (marketInfoBean.getPriceChangeRatio().compareTo(BigDecimal.ZERO) >= 0) {
                Drawable nav_up = getResources().getDrawable(R.mipmap.up);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                tv_trade_coin_prent.setCompoundDrawables(null, null, nav_up, null);
                tv_trade_coin_prent.setTextColor(getResources().getColor(R.color.text_pink));
                tv_trade_coin_prent.setText("+" + FormatUtils.to2(marketInfoBean.getPriceChangeRatio()) + "%");
            } else {
                Drawable nav_up = getResources().getDrawable(R.mipmap.down);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                tv_trade_coin_prent.setTextColor(getResources().getColor(R.color.text_red));
                tv_trade_coin_prent.setCompoundDrawables(null, null, nav_up, null);
                tv_trade_coin_prent.setText(FormatUtils.to2(marketInfoBean.getPriceChangeRatio()) + "%");
            }
        }
    }


    @Override
    public void getTransactionMarketInfoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response) {
        if (userMarket == null) return;
        List<MarketListBean> mbls = response.getData();
        if (mbls.size() > 0) {
            for (int i = 0; i < mbls.size(); i++) {
                if (mbls.get(i).getMarketId() == marketId) {
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
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getByMarketIdSuccess(MarketIdBean mb) {
        this.mb = mb;
    }

    @Override
    public void getByMarketIdFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void removeOptionalSuccess() {
        isUserMarket = false;
        isSelect(false);
    }

    @Override
    public void removeOptionalFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void addOptionalSuccess() {
        isUserMarket = true;
        isSelect(true);
        ToastUtils.showLong(App.getContext(), R.string.add_optional_success);
    }

    @Override
    public void addOptionalFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    private Disposable disposable;

    private void run() {
        disposable = Observable.interval(Global.delayTime, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.getTransactionMarketInfo(marketId);
                        if (mb == null && InfoProvider.getInstance().getLogin()) {
                            presenter.getByMarketId(marketId);
                        }
                    }
                });
    }

    private void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return false;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (disposable != null) {
            disposable.dispose();
        }
        index = tab.getPosition();
        if (index == 0) {
            mMinuteChartView.setVisibility(View.VISIBLE);
            mKChartView.setVisibility(View.GONE);
            initCacheInfo(1 * 60 * 1000);
            kilineSocket1(1 * 60 * 1000);
        } else {
            mMinuteChartView.setVisibility(View.GONE);
            mKChartView.setVisibility(View.VISIBLE);
            initCacheInfo(KL_INTERVAL[index] * 60 * 1000);
            kilineSocket5(KL_INTERVAL[index] * 60 * 1000);


        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


}
