package oysd.com.trade_app.modules.mycenter.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;

public interface MyAdsContract {

    interface Presenter extends BasePresenter<View> {

        void getAds(int page, int limit, int userAccountId);

        void cancelAd(int adId);
    }

    interface View extends BaseView<Presenter> {

        void getAdsSuccess(ResponsePaging<OtcAdBean> response);

        void getAdsFailed(int code, String msg);

        void cancelAdSuccess();

        void cancelAdFailed(int code, String msg);

    }

}
