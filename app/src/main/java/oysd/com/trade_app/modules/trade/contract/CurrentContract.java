package oysd.com.trade_app.modules.trade.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public interface CurrentContract {

    interface View extends BaseView<Presenter> {

        void CurrentSuccess(ResponsePaging<CurrentBean> response);

        void CurrentFailed(int code, String msg);

        void rollbackSuccess();

        void rollbackFailed(int code, String msg);

        void rollbackOneSuccess();
        void rollbackOneFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void current(int page,int limit,int marketId,String type);

        void rollback(int marketId,String type);
        void rollbackOne(int id);
    }

}
