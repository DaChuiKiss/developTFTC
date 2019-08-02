package oysd.com.trade_app.modules.mycenter.contract;


import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;

public interface MyAccountTransferContract {

    interface Presenter extends BasePresenter<MyAccountTransferContract.View> {
        void c2cTransferAccounts(@NonNull Map<String, Object> params);
    }

    interface View extends BaseView<MyAccountTransferContract.Presenter> {
        void c2cTransferAccountsSuccess(EmptyBean tb);
        void c2cTransferAccountsFailed(int code, String msg);
    }

}
