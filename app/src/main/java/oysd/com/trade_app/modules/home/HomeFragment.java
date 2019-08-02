package oysd.com.trade_app.modules.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.listener.ExceptionHandler;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oysd.com.trade_app.App;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.home.adapter.HomeAdapter;
import oysd.com.trade_app.modules.home.bean.AnnouncementBean;
import oysd.com.trade_app.modules.home.bean.BannerBean;
import oysd.com.trade_app.modules.home.bean.TimeZoneInfoBean;
import oysd.com.trade_app.modules.home.bean.UpListBean;
import oysd.com.trade_app.modules.home.contract.HomeContract;
import oysd.com.trade_app.modules.home.presenter.HomePresenter;
import oysd.com.trade_app.modules.home.view.appstore.AppActivity;
import oysd.com.trade_app.modules.home.view.HomeWebActivity;
import oysd.com.trade_app.modules.home.view.information.InformationActivity;
import oysd.com.trade_app.modules.home.view.information.MarqueeViewActivity;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.mycenter.http.UpdateAppHttpUtil;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.modules.mycenter.view.MyMemberActivity;
import oysd.com.trade_app.modules.mycenter.view.UrlWebActivity;
import oysd.com.trade_app.modules.trade.adapter.FragmentAdapter;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.view.NoScrollViewPager;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivityTest;
import oysd.com.trade_app.modules.trade.view.fragment.TradeCoinFragment;
import oysd.com.trade_app.modules.trade.view.fragment.TradeMySelectionsFragment;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.NetUtils;
import oysd.com.trade_app.util.RootUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;


public class HomeFragment extends LazyLoadBaseFragment
        implements OnBannerListener, HomeContract.View {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

    @BindView(R.id.ll_home_charge)
    LinearLayout llCharge;

    @BindView(R.id.ll_home_guide)
    LinearLayout llGuide;

    @BindView(R.id.srl_smart_refresh)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv_smart_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.ll_empty_no_net)
    LinearLayout llNoNet;

    @BindView(R.id.ll_empty_no_order)
    LinearLayout llNoOrder;

    @BindView(R.id.ll_empty_no_data)
    LinearLayout llNoData;

    @BindView(R.id.tv_home_title_1)
    TextView tv_home_title_1;

    @BindView(R.id.tv_home_title_2)
    TextView tv_home_title_2;

    @BindView(R.id.ll_goto_invitation)
    LinearLayout ll_goto_invitation;

    @BindView(R.id.ll_goto_vip)
    LinearLayout ll_goto_vip;

    @BindView(R.id.ll_goto_information)
    LinearLayout ll_goto_information;

    @BindView(R.id.ll_goto_app)
    LinearLayout ll_goto_app;

//    @BindView(R.id.sv_home)
//    ScrollView sv_home;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    private String testUrl = "https://www.baidu.com";

    public static String[] TITLES;

    // banner
    private List<String> imageUrls = new ArrayList<>();
    private List<String> imageTitles = new ArrayList<>();
    private List<String> urlLink = new ArrayList<>();
    // 公告数据
    private List<AnnouncementBean> annList = new ArrayList<>();

    private HomeAdapter homeAdapter;
    private List<UpListBean> dataset = new ArrayList<>();
    private Disposable disposable;
    HomeContract.Presenter presenter = new HomePresenter(this);
    AnnouncementBean leftAB, rightAB;


    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        presenter.getDealZoneInfo(1, 10);
    }

    @Override
    protected void initClicks() {
        llCharge.setOnClickListener(this);
        llGuide.setOnClickListener(this);
        ll_goto_app.setOnClickListener(this);
        ll_goto_information.setOnClickListener(this);
        ll_goto_vip.setOnClickListener(this);
        ll_goto_invitation.setOnClickListener(this);
    }

    private void setRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.getDealZoneInfo(1, 10);
                presenter.getOtcShowAnnouncementList();
                presenter.getQueryShowList();
                presenter.getBanner();
                presenter.getUpList(2);
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // no need to load more.
            }
        });
    }

    private void setBanner() {
        // 设置内置样式，共有六种可以点入方法内逐一体验使用。

        if (Utils.isChina()) {
            imageUrls.add("file:///android_asset/banner.png");
        } else {
            imageUrls.add("file:///android_asset/banner_en.png");
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        // 设置图片网址或地址的集合
        banner.setImages(imageUrls);
        // 设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        // 设置轮播图的标题集合
        banner.setBannerTitles(imageTitles);
        // 设置轮播间隔时间
        banner.setDelayTime(3000);
        // 设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        // 设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                // 以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                // 必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.ll_home_charge:
                    if (leftAB != null) {
                        jumpToWeb(Constants.AD_URL + leftAB.getId(), leftAB.getTitle());
                    }
                    break;

                case R.id.ll_home_guide:
                    if (rightAB != null) {
                        jumpToWeb(Constants.AD_URL + rightAB.getId(), rightAB.getTitle());
                    }
                    break;
                case R.id.ll_goto_invitation:
                    if (InfoProvider.getInstance().getLogin()) {
                        if (InfoProvider.getInstance().getGesture()) {
                            if (!Global.isLogin) {
                                IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                            } else {
                                String title = getResources().getString(R.string.type4);
                                String token = InfoProvider.getInstance().getToken();

                                if (Utils.isChina()) {
                                    // jumpToWeb(title, Constants.URL + "/notice/dist/index.html#/invite/?id=" + token);
                                    jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_zh?token=" + token,
                                            UrlWebActivity.TYPE_POSTER);
                                } else {
                                    jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_en?token=" + token,
                                            UrlWebActivity.TYPE_POSTER);
                                }
                            }
                        } else {
                            String title = getResources().getString(R.string.type4);
                            String token = InfoProvider.getInstance().getToken();
                            if (Utils.isChina()) {
                                // jumpToWeb(title, Constants.URL + "/notice/dist/index.html#/invite/?id=" + token);
                                jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_zh?token=" + token,
                                        UrlWebActivity.TYPE_POSTER);
                            } else {
                                jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_en?token=" + token,
                                        UrlWebActivity.TYPE_POSTER);
                            }
                        }
                    } else {
                        IntentUtils.startActivity(context, LoginActivity.class);
                    }
                    break;
                case R.id.ll_goto_vip:
                    if (InfoProvider.getInstance().getLogin()) {
                        if (InfoProvider.getInstance().getGesture()) {
                            if (!Global.isLogin) {
                                IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                            } else {
                                IntentUtils.startActivity(context, MyMemberActivity.class);
                            }
                        } else {
                            IntentUtils.startActivity(context, MyMemberActivity.class);
                        }
                    } else {
                        IntentUtils.startActivity(context, LoginActivity.class);
                    }

                    break;
                case R.id.ll_goto_information:
                    IntentUtils.startActivity(context, InformationActivity.class);
                    break;
                case R.id.ll_goto_app:
                    IntentUtils.startActivity(context, AppActivity.class);
                    break;
                default:
                    break;
            }
        }
    }


    // 跳转到指定静态页面
    private void jumpToWeb(String title, String url, int type) {
        Intent intent = new Intent(getActivity(), UrlWebActivity.class);

        intent.putExtra(UrlWebActivity.EXTRA_URL, url);
        intent.putExtra(UrlWebActivity.EXTRA_TITLE, title);
        if (type != 0) intent.putExtra(UrlWebActivity.EXTRA_TYPE, type);

        context.startActivity(intent);
    }

    // 在 home 上跳转到另外一个页面。
    private void jumpToWeb(@NonNull String url, @Nullable String title) {
        Intent intent = new Intent(getActivity(), HomeWebActivity.class);
        intent.putExtra(HomeWebActivity.EXTRA_URL, url);
        intent.putExtra(HomeWebActivity.EXTRA_TITLE, title);
        getActivity().startActivity(intent);
    }

    // 轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {

        //    jumpToWeb(testUrl, imageTitles.get(position));
        if (urlLink.size() > 0) {
            //轮播图设置点击不可跳转
            //jumpToWeb(urlLink.get(position), imageTitles.get(position));
        }
    }

    @Override
    public void getBannerSuccess(ResponseList<BannerBean> response) {
        if (banner == null) return;
        List<BannerBean> bannerList = response.getData();

        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> tempLink = new ArrayList<>();

        for (BannerBean bannerBean : bannerList) {
            images.add(bannerBean.getBigImagePath());
            titles.add(bannerBean.getTitle());
            tempLink.add(bannerBean.getLinkUrl());
        }

        if (EmptyUtils.isNotEmpty(images)) {
            imageUrls = images;
            imageTitles = titles;
            urlLink = tempLink;
            banner.update(imageUrls, imageTitles);
        }
    }

    @Override
    public void getBannerFailed(int code, String msg) {

    }

    @Override
    public void getUpListSuccess(ResponseList<UpListBean> response) {
        if (homeAdapter == null || llNoData == null) return;
        List<UpListBean> upList = response.getData();
        if (upList == null || upList.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
        } else {
            dataset = upList;
            llNoData.setVisibility(View.GONE);
            homeAdapter.setDataSet(dataset);
        }
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public void getUpListFail(int code, String msg) {
    }

    @Override
    public void getOtcShowAnnouncementListSuccess(ResponseList<AnnouncementBean> response) {
        if (marqueeView == null) return;

        List<AnnouncementBean> abList = response.getData();
        if (abList != null) {
            if (abList.size() >= 2) {
                leftAB = abList.get(0);
                tv_home_title_1.setText(abList.get(0).getTitle());

                rightAB = abList.get(1);
                tv_home_title_2.setText(abList.get(1).getTitle());
            } else if (abList.size() == 1) {
                leftAB = abList.get(0);
                tv_home_title_1.setText(abList.get(0).getTitle());
            }
        }
    }

    @Override
    public void getOtcShowAnnouncementListFail(int code, String msg) {

    }

    @Override
    public void getQueryShowListSuccess(ResponseList<AnnouncementBean> response) {
        annList = response.getData();
        List<String> info = new ArrayList<>();
        for (int i = 0; i < annList.size(); i++) {
            info.add(annList.get(i).getTitle());
        }
        // 在代码里设置自己的动画
        if (info != null && marqueeView != null) {
            marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
            //sv_home.scrollTo(0, 0);
        }
    }

    @Override
    public void getQueryShowListFail(int code, String msg) {

    }

    @Override
    public void getDealZoneInfoSuccess(ResponseList<DealZoneBean> response) {
        //        ToastUtils.showLong(App.getContext(), response.getData().toString());
        List<DealZoneBean> dbList = response.getData();
        ArrayList<Fragment> fragment = new ArrayList<>();
        TITLES = new String[dbList.size()];
        //fragment.add(new TradeMySelectionsFragment());
        //TITLES[0] = getActivity().getResources().getString(R.string.favorites);
        for (int i = 0; i < TITLES.length; i++) {
            TITLES[i] = dbList.get(i).getName();
            fragment.add(TradeCoinFragment.newInstance(dbList.get(i).getId(), "Home"));
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


    // 自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.ad_layout;
    }

    @Override
    protected void initView(View rootView) {

        // need to change StatusBar text color.
        // StatusBarUtils.setTranslucentStatus(getActivity(), Color.TRANSPARENT);

        setBanner();
        setRefreshLayout();

        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                if (!Utils.isFastClick(textView)) {
                    //AD_URL:http://bourse.oss-cn-shenzhen.aliyuncs.com/notice/dist/index.html#/notice/?id=
                    //jumpToWeb(Constants.AD_URL + annList.get(position).getId(), annList.get(position).getTitle());
                    Intent intent = new Intent(getActivity(), MarqueeViewActivity.class);
                    intent.putExtra("title", annList.get(position).getTitle());
                    intent.putExtra("content", annList.get(position).getContent());
                    startActivity(intent);
                }
            }
        });

        // RecyclerView settings.
        homeAdapter = new HomeAdapter(getActivity(), R.layout.home_trade_item);
        homeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isFastClick()) {
                    Intent intent = new Intent(getActivity(), KLineTradeActivityTest.class);
                    intent.putExtra("marketId", dataset.get(position).getMarketId());
                    intent.putExtra("marketName", dataset.get(position).getMarketName());
                    startActivity(intent);
                    // ToastUtils.showLong(context, "clicks on " + position);
                }
            }
        });

        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.getBanner();
        presenter.getNewestAppVersionInfo();
        presenter.getTimeZone();
    }

    @Override
    protected void initData() {
        // 环境检测。
        detectEnvironment();
    }

    private void detectEnvironment() {
        if (NetUtils.isWifiProxy(context)) {
            ToastUtils.showLong(context, R.string.toast_proxy);
        }
        if (RootUtils.isDeviceRooted()) {
            ToastUtils.showLong(context, R.string.toast_root);
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        presenter.getUpList(2);
        presenter.getOtcShowAnnouncementList();
        presenter.getQueryShowList();
        run();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        stop();
    }

    private void run() {
        disposable = Observable.interval(Global.delayTime, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        presenter.getUpList(2);
                        //ToastUtils.showLong(App.getContext(),"首页");
                        Logger.d("首页");
                    }
                });
    }

    private void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    @Override
    public void getNewestAppVersionInfoSuccess(AppVersionInfoBean appBean) {

        if (appBean != null) {
            //获取本地应用的版本号
            if (packageCode(App.getContext()) >= appBean.getVersionRecognition()) {
            } else {
                new UpdateAppManager
                        .Builder()
                        //当前Activity
                        .setActivity(getActivity())
                        //更新参数
                        .handleException(new ExceptionHandler() {
                            @Override
                            public void onException(Exception e) {
                                e.printStackTrace();
                            }
                        })
                        //实现httpManager接口的对象
                        .setHttpManager(new UpdateAppHttpUtil())
                        .build()
                        .update_str(updateStrInfo(appBean));
            }
        } else {
            // Liam 20181013:
            // 如果没有新版本，后台可能返回 data=null, 那么 AppVersionInfoBean 为 null ，
            // 不应该提示错误。
            // ToastUtils.showLong(App.getContext(), R.string.system_error);
        }

    }


    public String updateStrInfo(AppVersionInfoBean ab) {
        JSONObject json = new JSONObject();
        try {
            if (ab.getStatus() == 1) {

                json.put("update", "Yes");

            } else {
                json.put("update", "No");
            }
            json.put("new_version", ab.getVsersionNo());
            json.put("apk_file_url", ab.getDownUrl());
            json.put("update_log", "    ");
            if (ab.getIsCompulsory() == 1) {
                json.put("constraint", "true");
            } else {
                json.put("constraint", "false");
            }
            json.put("new_md5", "b97bea014531123f94c3ba7b7afbaad2");
            json.put("target_size", "17M");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


    //获取版本co
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    //获取版本名
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void getNewestAppVersionInfoFail(int code, String msg) {

    }

    @Override
    public void getTimeZoneSuccess(TimeZoneInfoBean response) {
        InfoProvider.getInstance().saveTimeZone(response.getTimeZoneId());
    }

    @Override
    public void getTimeZoneFailed(int code, String msg) {
        Logger.e("Failed to get TimeZone. code = " + code + " , msg = " + msg);
    }

}



