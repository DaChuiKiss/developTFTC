package oysd.com.trade_app.modules.home.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.home.bean.AnnouncementBean;
import oysd.com.trade_app.modules.home.bean.BannerBean;
import oysd.com.trade_app.modules.home.bean.TimeZoneInfoBean;
import oysd.com.trade_app.modules.home.bean.UpListBean;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;

public interface HomeContract {

    interface Presenter extends BasePresenter<View> {
        void getDealZoneInfo(int page, int limit);
        void getBanner();

        void getUpList(long type);

        void getOtcShowAnnouncementList();

        void getQueryShowList();

        void getNewestAppVersionInfo();

        void getTimeZone();
    }

    interface View extends BaseView<Presenter> {

        void getBannerSuccess(ResponseList<BannerBean> response);

        void getBannerFailed(int code, String msg);

        void getUpListSuccess(ResponseList<UpListBean> response);

        void getUpListFail(int code, String msg);

        void getOtcShowAnnouncementListSuccess(ResponseList<AnnouncementBean> response);

        void getOtcShowAnnouncementListFail(int code, String msg);

        void getQueryShowListSuccess(ResponseList<AnnouncementBean> response);

        void getQueryShowListFail(int code, String msg);

        void getNewestAppVersionInfoSuccess(AppVersionInfoBean appBean);

        void getNewestAppVersionInfoFail(int code, String msg);

        void getTimeZoneSuccess(TimeZoneInfoBean response);

        void getTimeZoneFailed(int code, String msg);
        void getDealZoneInfoSuccess(ResponseList<DealZoneBean> response);

        void getDealZoneInfoFailed(int code, String msg);

    }

}
