package oysd.com.trade_app.modules.trade.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.OrderRecordBean;

/**
 * CurrentOrdersFragment contract.
 * Created by Liam on 2018/7/25
 */
public interface CurrentOrdersContract {

    interface View extends BaseView<Presenter> {

        void getCurrentOrdersSuccess(ResponseList<OrderRecordBean> response);

        void getCurrentOrdersFailed(int code, String msg);

        void rollbackSuccess();

        void rollbackFailed(int code, String msg);

        void rollbackOneSuccess();

        void rollbackOneFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getCurrentOrders(int page, int limit, int marketId);

        void rollback(int marketId);

        void rollbackOne(int marketId);
    }

}
