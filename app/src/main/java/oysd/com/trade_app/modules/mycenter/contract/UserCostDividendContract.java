package oysd.com.trade_app.modules.mycenter.contract;


import java.math.BigDecimal;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendBean;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;

public interface UserCostDividendContract {

    interface Presenter extends BasePresenter<View> {

        void getUserCostDividend();
        void getUserCostDividendRecordList();
    }

    interface View extends BaseView<Presenter> {

        void getUserCostDividendSuccess(ResponseEntity<String> response);

        void getUserCostDividendFailed(int code, String msg);

        void getUserCostDividendRecordListSuccess(ResponsePaging<UserCostDividendRecord> response);

        void getUserCostDividendRecordListFailed(int code, String msg);
    }

}
