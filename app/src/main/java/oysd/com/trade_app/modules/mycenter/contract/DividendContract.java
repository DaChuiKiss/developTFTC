package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.DividendBean;

public interface DividendContract {

    interface Presenter extends BasePresenter<DividendContract.View> {
        void getDividend();
    }

    interface View extends BaseView<DividendContract.Presenter> {
        void getDividendSuccess(ResponseEntity<DividendBean> response);
        void getDividendFailed(int code, String msg);
    }

}
