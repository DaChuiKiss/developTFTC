package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;

public interface BindEmailContract {

    interface Presenter extends BasePresenter<BindEmailContract.View> {
        void bindPhone(Map<String, Object> params);
        void getVerCode(Map<String, Object> params);
    }

    interface View extends BaseView<BindEmailContract.Presenter> {
        void bindEmailSuncess();
        void bindEmailFailed(int code,String msg);

        void getVerCodeSuccess();
        void getVerCodeFailed(int code,String msg);
    }

}
