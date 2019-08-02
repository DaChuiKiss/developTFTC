package oysd.com.trade_app.main.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;

/**
 * 主要功能，实现了获取国家手机号的接口类
 */
public interface GetUrlContract {

    interface Presenter extends BasePresenter<GetUrlContract.View> {
        void getUrlList();
        void getUserInfo();
        void accessTokenIsOk();

    }

    interface View extends BaseView<GetUrlContract.Presenter> {

        void getUserInfoSuccess(UserInfoBean ub);
        void getUserInfoFailed(int code, String msg);
        void getUrlListSuccess(ResponseList<UrlBean> response);
        void getUrlListFail(int code, String msg);
        void accessTokenIsOk(int code, AuthInfoBean authInfoBean);
        void netIsError();
    }

}
