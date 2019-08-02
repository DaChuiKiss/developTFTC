package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface SetAccountContract {

    interface Presenter extends BasePresenter<SetAccountContract.View> {
        void addDealPwd(Map<String, Object> params);
    }

    interface View extends BaseView<SetAccountContract.Presenter> {

        void addDealPwdSuccess();
        void addDealPwdFailed(int code,String msg);
    }

}
