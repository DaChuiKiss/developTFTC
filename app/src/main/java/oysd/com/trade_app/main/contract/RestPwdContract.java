package oysd.com.trade_app.main.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;

/**
 * 主要功能，实现了获取国家手机号的接口类
 */
public interface RestPwdContract {

    interface Presenter extends BasePresenter<RestPwdContract.View> {
        void resetPwd(Map<String, Object> params);
        void getVerCode(Map<String, Object> params);
        void isExists(String account);

    }

    interface View extends BaseView<RestPwdContract.Presenter> {

        void resetPwdSuccess();
        void resetPwdFail(int code,String msg);

        void getVerCodeSuccess();

        void getVerCodeFailed(int code,String msg);

        void isExistsSuccess(Boolean flag);
        void isExistsFailed(int code,String msg);
    }

}
