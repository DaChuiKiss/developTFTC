package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface UpdateAccountPwdContract {

    interface Presenter extends BasePresenter<UpdateAccountPwdContract.View> {
        void updateDealPwd(Map<String, Object> params);
        void getVerCode(Map<String, Object> params);
    }

    interface View extends BaseView<UpdateAccountPwdContract.Presenter> {

        void updateDealPwdSuccess();
        void updateDealPwdFailed(int code,String msg);

        void getVerCodeSuccess();
        void getVerCodeFailed(int code,String msg);
    }

}
