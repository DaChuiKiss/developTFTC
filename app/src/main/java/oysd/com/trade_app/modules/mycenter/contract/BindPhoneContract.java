package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface BindPhoneContract {

    interface Presenter extends BasePresenter<BindPhoneContract.View> {
        void bindPhone(Map<String, Object> params);
        void getVerCode(Map<String, Object> params);
    }

    interface View extends BaseView<BindPhoneContract.Presenter> {
        void bindPhoneSuncess();
        void bindPhoneFailed(int code,String msg);
        void getVerCodeSuccess();
        void getVerCodeFailed(int code,String msg);
    }

}
