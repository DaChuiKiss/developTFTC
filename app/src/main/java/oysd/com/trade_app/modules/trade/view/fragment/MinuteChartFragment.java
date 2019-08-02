package oysd.com.trade_app.modules.trade.view.fragment;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.MinuteChartView;
import com.github.tifezh.kchartlib.chart.formatter.TimeFormatter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.modules.trade.adapter.KChartAdapter;
import oysd.com.trade_app.modules.trade.bean.KLineEntity;
import oysd.com.trade_app.modules.trade.bean.MinuteLineEntity;
import oysd.com.trade_app.modules.trade.bean.SocketKLine;
import oysd.com.trade_app.modules.trade.util.DataHelper;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.util.ACache;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.LoadingImgDialog;
import oysd.com.trade_app.util.ToastUtils;

/**
 * Created by tifezh on 2017/7/20.
 */

public class MinuteChartFragment extends LazyLoadBaseFragment implements MinuteChartView.KChartRefreshListener {

    @BindView(R.id.minuteChartView)
    MinuteChartView mMinuteChartView;

    private KChartAdapter mAdapter;
    LoadingImgDialog loadingColorDialog;
    private ACache mCache;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_minute_chart;
    }

    @Override
    protected void initView(View rootView) {

    }


    @Override
    public void onFragmentFirstVisible() {
        long currentClickTime1 = System.currentTimeMillis();

        mAdapter = new KChartAdapter();
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
        long currentClickTime2 = System.currentTimeMillis();
        Log.d("initView", "initView: " + (currentClickTime2 - currentClickTime1));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMinuteChartView.setGridRows(2);
            mMinuteChartView.setGridColumns(6);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mMinuteChartView.setGridRows(2);
            mMinuteChartView.setGridColumns(3);
        }
    }


    Disposable disposable;
    boolean isObject;

    public void kilineSocket1Min() {
        disposable = SocketClient.getInstance().topicKLineInfo(((KLineTradeActivity) getActivity()).getMarketId(), 60 * 1000,  new SocketClient.OnSocketReceivedListener() {
            @Override
            public void onReceive(String response) {
                            currentClickTime2 = System.currentTimeMillis();
                            Log.d("socket", "onReceive: 接收数据花的时间" + (currentClickTime2 - currentClickTime1));
                            Gson gson = new Gson();
                            isObject = false;
                            List<KLineEntity> data = new ArrayList<KLineEntity>();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.has("data")) {
                                    mCache.put("kline1Min" + ((KLineTradeActivity) getActivity()).getMarketId(), response);
                                    isObject = false;
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

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //第一次加载时开始动画
                                    if (mAdapter.getCount() == 0) {
                                        mMinuteChartView.startAnimation();
                                    }
                                    mAdapter.addFooterData(data, isObject, loadingColorDialog);


                                }
                            });
                        } catch(JSONException e){
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
    public void onLoadMoreBegin(KChartView chart) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    protected void initData() {
    }

    long currentClickTime1, currentClickTime2, currentClickTime3;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getCacheValue();
        currentClickTime1 = System.currentTimeMillis();
        //获取新数据
        kilineSocket1Min();
    }


    //获取缓存数据
    public void getCacheValue() {
        mCache = ACache.get(getActivity());
        String response = mCache.getAsString("kline1Min" + ((KLineTradeActivity) getActivity()).getMarketId());
        if (response == null) {
            loadingColorDialog = new LoadingImgDialog(getContext(), R.mipmap.loading);
            loadingColorDialog.show();
        } else {
            Gson gson = new Gson();
            isObject = false;
            List<KLineEntity> data = new ArrayList<KLineEntity>();
            try {
                JSONObject obj = new JSONObject(response);
                SocketKLine sk = gson.fromJson(response, SocketKLine.class);
                List<SocketKLine.DataBean> lsdb = sk.getData();
                if(lsdb!=null) {
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
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //第一次加载时开始动画
                        if (mAdapter.getCount() == 0) {
                            mMinuteChartView.startAnimation();
                        }
                        mAdapter.addFooterData(data, isObject, loadingColorDialog);


                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        disposable.dispose();
        if (loadingColorDialog != null && loadingColorDialog.isShowing())
            loadingColorDialog.dismiss();
    }

}
