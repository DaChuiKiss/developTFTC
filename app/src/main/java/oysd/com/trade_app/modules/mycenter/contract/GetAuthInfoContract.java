package oysd.com.trade_app.modules.mycenter.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;

public interface GetAuthInfoContract {

    interface Presenter extends BasePresenter<GetAuthInfoContract.View> {
        void getAuthInfo();
    }

    interface View extends BaseView<GetAuthInfoContract.Presenter> {
        void getAuthInfoSuccess(AuthInfoBean authInfoBean);
        void getAuthInfoFailed(int code,String msg);
    }

}
