package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface UpdateGesturePwdContract {

    interface Presenter extends BasePresenter<UpdateGesturePwdContract.View> {
        void updateGesturePwd(Map<String, Object> params);
    }

    interface View extends BaseView<UpdateGesturePwdContract.Presenter> {
        void updateGesturePwdSuccess();
        void updateGesturePwdFailed(int code, String msg);
    }

}
