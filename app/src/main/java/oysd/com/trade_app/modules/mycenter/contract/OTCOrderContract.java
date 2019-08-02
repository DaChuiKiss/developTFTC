package oysd.com.trade_app.modules.mycenter.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;

public interface OTCOrderContract {

    interface Presenter extends BasePresenter<OTCOrderContract.View> {

        void getOnlineOrderList(int page, int limit, String transactionType, String status);
    }

    interface View extends BaseView<OTCOrderContract.Presenter> {

        void getOnlineOrderListSuccess(ResponsePaging<OtcOrderBean> response);

        void getOnlineOrderListFailed(int code, String msg);
    }

}
