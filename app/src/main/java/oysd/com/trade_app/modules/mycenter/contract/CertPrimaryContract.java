package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;

public interface CertPrimaryContract {

    interface Presenter extends BasePresenter<CertPrimaryContract.View> {
        void setCertPrimary(Map<String, Object> params);
    }

    interface View extends BaseView<CertPrimaryContract.Presenter> {
        void certPrimarySuccess();
        void certPrimaryFailed(int code,String msg);
    }

}
