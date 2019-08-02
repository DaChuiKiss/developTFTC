package oysd.com.trade_app.modules.mycenter.contract;


import java.io.File;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;

public interface SystemSettingContract {

    interface Presenter extends BasePresenter<SystemSettingContract.View> {
        void logout();
        void getNewestAppVersionInfo();
    }

    interface View extends BaseView<SystemSettingContract.Presenter> {

        void logoutSuccess();
        void logoutFailed(int code,String msg);
        void getNewestAppVersionInfoSuccess(AppVersionInfoBean appBean);
        void getNewestAppVersionInfoFail(int code,String msg);
    }

}
