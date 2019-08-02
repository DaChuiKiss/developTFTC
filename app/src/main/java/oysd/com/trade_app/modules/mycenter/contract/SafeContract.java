package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;

public interface SafeContract {

    interface Presenter extends BasePresenter<SafeContract.View> {
        void getSafeInfo();
    }

    interface View extends BaseView<SafeContract.Presenter> {
        void safeInfoSuccess(UserInfoBean safeBean);
        void safeInfoFailed();
    }

}
