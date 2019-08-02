package oysd.com.trade_app.modules.home.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.home.bean.AnnouncementBean;
import oysd.com.trade_app.modules.home.bean.BannerBean;
import oysd.com.trade_app.modules.home.bean.TimeZoneInfoBean;
import oysd.com.trade_app.modules.home.bean.UpListBean;
import oysd.com.trade_app.modules.home.contract.HomeContract;
import oysd.com.trade_app.modules.home.http.HomeApi;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.http.TradeApi;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HomeContract.View view) {
    }

    @Override
    public void detachView() {
    }

    @Override
    public void getBanner() {
        RetrofitHelper.createApi(HomeApi.class)
                .getBanner()
                .compose(RxSchedulers.<ResponseList<BannerBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<BannerBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<BannerBean> response) {
                        view.getBannerSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getBannerFailed(code, msg);
                    }
                });
    }

    @Override
    public void getUpList(long type) {
        RetrofitHelper.createApi(HomeApi.class)
                .getMarketPriceChangeRatioList(type)
                .compose(RxSchedulers.<ResponseList<UpListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<UpListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<UpListBean> response) {
                        view.getUpListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUpListFail(code, msg);
                    }
                });
    }

    @Override
    public void getOtcShowAnnouncementList() {
        RetrofitHelper.createApi(HomeApi.class)
                .otcShowAnnouncementList()
                .compose(RxSchedulers.<ResponseList<AnnouncementBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<AnnouncementBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<AnnouncementBean> response) {
                        view.getOtcShowAnnouncementListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getOtcShowAnnouncementListFail(code, msg);
                    }
                });
    }

    @Override
    public void getQueryShowList() {
        RetrofitHelper.createApi(HomeApi.class)
                .queryShowList()
                .compose(RxSchedulers.<ResponseList<AnnouncementBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<AnnouncementBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<AnnouncementBean> response) {
                        view.getQueryShowListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getQueryShowListFail(code, msg);
                    }
                });
    }

    @Override
    public void getNewestAppVersionInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getNewestAppVersionInfo("1")
                .compose(RxSchedulers.<ResponseEntity<AppVersionInfoBean>>ioMain())
                .subscribe(new DataObserver<AppVersionInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(AppVersionInfoBean appVersionInfoBean) {
                        view.getNewestAppVersionInfoSuccess(appVersionInfoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getNewestAppVersionInfoFail(code,msg);
                    }
                });
    }

    @Override
    public void getTimeZone() {
        RetrofitHelper.createApi(HomeApi.class)
                .getTimeZone()
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<TimeZoneInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(TimeZoneInfoBean response) {
                        view.getTimeZoneSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getTimeZoneFailed(code, msg);
                    }
                });
    }

    @Override
    public void getDealZoneInfo(int page, int limit) {
        RetrofitHelper.createApi(TradeApi.class)
                .reqDealZoneInfo(page, limit)
                .compose(RxSchedulers.<ResponseList<DealZoneBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<DealZoneBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<DealZoneBean> response) {
                        view.getDealZoneInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getDealZoneInfoFailed(code, msg);
                    }
                });
    }


}
