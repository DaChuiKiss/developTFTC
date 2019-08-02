package oysd.com.trade_app.modules.mycenter.contract;
import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.UserInviteReturnAmountBean;

public interface UserInviteReturnAmountContract {

    interface Presenter extends BasePresenter<UserInviteReturnAmountContract.View> {
        void getUserInviteReturnAmount();
    }

    interface View extends BaseView<UserInviteReturnAmountContract.Presenter> {
        void getUserInviteReturnAmountSuncess(ResponseEntity<UserInviteReturnAmountBean> response);
        void getUserInviteReturnAmountFailed(int code, String msg);
    }

}
