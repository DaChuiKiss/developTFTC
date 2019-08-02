package oysd.com.trade_app.modules.mycenter.contract;


import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;

public interface CoinOrderContract {

    interface Presenter extends BasePresenter<CoinOrderContract.View> {
        void getAll(int page,int limit);
        void rollbackOne(int id);
    }

    interface View extends BaseView<CoinOrderContract.Presenter> {

        void rollbackOneSuccess();
        void rollbackOneFailed(int code, String msg);
        void getAllSuccess(ResponsePaging<CoinOrderBean> response);
        void getAllFailed(int code, String msg);
    }

}
