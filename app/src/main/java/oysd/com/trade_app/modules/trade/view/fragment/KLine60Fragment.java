package oysd.com.trade_app.modules.trade.view.fragment;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;
import com.github.tifezh.kchartlib.chart.formatter.TimeFormatter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.modules.trade.adapter.KChartAdapter;
import oysd.com.trade_app.modules.trade.bean.KLineEntity;
import oysd.com.trade_app.modules.trade.bean.SocketBean;
import oysd.com.trade_app.modules.trade.bean.SocketKLine;
import oysd.com.trade_app.modules.trade.util.DataHelper;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.util.ACache;
import oysd.com.trade_app.util.LoadingImgDialog;
import oysd.com.trade_app.util.ToastUtils;

/**
 * Created by Arvin on 2016/10/25.
 */
public class KLine60Fragment extends LazyLoadBaseFragment implements KChartView.KChartRefreshListener {

    @BindView(R.id.kchart_view)
    KChartView mKChartView;

    private KChartAdapter mAdapter;

    View v;

    Thread thread;
    LoadingImgDialog loadingColorDialog;
    @Override
    protected int getLayoutRes() {
        return R.layout.chart_kline_frag;
    }

    @Override
    protected void initView(View rootView) {
        v = rootView;
    }

    @Override
    public void onFragmentFirstVisible() {
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
    }
//    List<KLineEntity> data;
    @Override
    protected void initData() {
        mKChartView.setRefreshListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mKChartView.setGridRows(2);
            mKChartView.setGridColumns(6);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mKChartView.setGridRows(2);
            mKChartView.setGridColumns(3);
        }
    }
    Disposable disposable;
    boolean isObject;
    public void kilineSocket60(){
       disposable= SocketClient.getInstance().topicKLineInfo(((KLineTradeActivity)getActivity()).getMarketId(),60*60*1000,  new SocketClient.OnSocketReceivedListener(){
            @Override
            public void onReceive(String response) {
               // ToastUtils.showLong(App.getContext(), response);
                Gson gson = new Gson();
                isObject=false;
                List<KLineEntity> data = new ArrayList<KLineEntity>();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("data")) {
                        isObject=false;
                        mCache.put("kline60min"+((KLineTradeActivity)getActivity()).getMarketId(),response);
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
                        isObject=true;
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
                                mKChartView.startAnimation();
                            }
                           mAdapter.addFooterData(data,isObject,loadingColorDialog);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable throwable) {
                if(loadingColorDialog!=null&&loadingColorDialog.isShowing())
                    loadingColorDialog.dismiss();
            }
        });
    }
    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getCacheValue();
        kilineSocket60();

    }

    private ACache mCache;
    //获取缓存数据
    public void getCacheValue(){
        mCache = ACache.get(getActivity());
        String response =  mCache.getAsString("kline60min"+((KLineTradeActivity)getActivity()).getMarketId());
        if(response==null) {
            loadingColorDialog = new LoadingImgDialog(getContext(), R.mipmap.loading);
            loadingColorDialog.show();
        }else{
            Gson gson = new Gson();
            isObject=false;
            List<KLineEntity> data = new ArrayList<KLineEntity>();
            try {
                JSONObject obj =new JSONObject(response);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //第一次加载时开始动画
                        if (mAdapter.getCount() == 0) {
                            mKChartView.startAnimation();
                        }
                        mAdapter.addFooterData(data,isObject,loadingColorDialog);
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
        if(loadingColorDialog!=null&&loadingColorDialog.isShowing())
            loadingColorDialog.dismiss();
    }

    @Override
    public void onLoadMoreBegin(KChartView chart) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                //加载完成，还有更多数据
//                if (data.size() > 0) {
//                    mKChartView.refreshComplete();
//                }
//                //加载完成，没有更多数据
//                else {
//                    mKChartView.refreshEnd();
//                }
            }
        });

    }

}
