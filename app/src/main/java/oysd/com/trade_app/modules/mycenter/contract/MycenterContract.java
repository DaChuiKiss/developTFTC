package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;

public interface MycenterContract {

    interface Presenter extends BasePresenter<MycenterContract.View> {
        void getTotalAmount();
        void getUserInfo();
        void getUserVipInfo();
    }

    interface View extends BaseView<MycenterContract.Presenter> {

        void getUserVipInfoSuccess(UserVipInfoBean ub_vip);
        void getUserVipInfoFailed(int code, String msg);

        void getUserInfoSuccess(UserInfoBean ub);
        void getUserInfoFailed(int code, String msg);
        void getTotalAmountSuccess(TotalConvertInfo totalConvertInfo);
        void getTotalAmountFailed(int code, String msg);
    }

}
