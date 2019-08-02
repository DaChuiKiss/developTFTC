package oysd.com.trade_app.modules.trade.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.OrderRecordBean;

/**
 * OrdersHistoryFragment contract.
 * Created by Liam on 2018/7/25
 */
public interface OrdersHistoryContract {

    interface View extends BaseView<Presenter> {

        void getOrdersHistorySuccess(ResponseList<OrderRecordBean> response);

        void getOrdersHistoryFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getOrdersHistory(int page, int limit, int marketId);
    }
}
