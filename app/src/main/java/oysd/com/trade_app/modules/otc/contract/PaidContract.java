package oysd.com.trade_app.modules.otc.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;

/**
 * Created by Liam on 2018/8/26.
 */
public interface PaidContract {

    interface View extends BaseView<Presenter> {

        void getOrderDetailSuccess(OtcOrderDetailBean response);

        void getOrderDetailFailed(int code, String msg);

        void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response);

        void getFinishedOrderDetailFailed(int code, String msg);

        void cancelOrderSuccess();

        void cancelOrderFailed(int code, String msg);

        void releaseCoinsSuccess();

        void releaseCoinsFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getOrderDetail(int orderId);

        void getFinishedOrderDetail(int orderId);

        void cancelOrder(int orderId);

        void releaseCoins(Map<String, Object> params);
    }

}
