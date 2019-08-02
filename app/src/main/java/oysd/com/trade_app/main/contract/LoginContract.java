package oysd.com.trade_app.main.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.UserBean;

public interface LoginContract {

    interface Presenter extends BasePresenter<View> {

        void login(Map<String, Object> params);
    }

    interface View extends BaseView<Presenter> {

        void loginSuccess(UserBean userBean);

        void loginFailed(int code, String msg);
    }

}
