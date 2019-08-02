package oysd.com.trade_app.main.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.UserBean;

public interface RegisterContract {

    interface Presenter extends BasePresenter<View> {

        void getVerCode(Map<String, Object> params);
        void isExists(String account);
        void register(Map<String, Object> params);
        void captcha(Map<String, Object> params);
    }

    interface View extends BaseView<Presenter> {
        void getCaptchaSuccess();
        void getCaptchaFail(int code,String msg);
        void getVerCodeSuccess();

        void getVerCodeFailed(int code, String msg);

        void registerSuccess(UserBean userBean);

        void registerFailed(int code, String msg);

        void isExistsSuccess(Boolean flag);
        void isExistsFailed(int code,String msg);
    }

}
