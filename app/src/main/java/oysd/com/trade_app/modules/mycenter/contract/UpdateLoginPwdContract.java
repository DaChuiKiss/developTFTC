package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface UpdateLoginPwdContract {

    interface Presenter extends BasePresenter<UpdateLoginPwdContract.View> {
        void updateLoginPwd(Map<String, Object> params);
        void getVerCode(Map<String, Object> params);
    }

    interface View extends BaseView<UpdateLoginPwdContract.Presenter> {

        void updateLoginPwdSuccess();
        void updateLoginPwdFailed(int code,String msg);

        void getVerCodeSuccess();
        void getVerCodeFailed(int code,String msg);
    }

}
