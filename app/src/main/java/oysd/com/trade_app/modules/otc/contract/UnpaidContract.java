package oysd.com.trade_app.modules.otc.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.DetailPaymentInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;

/**
 * Unpaid activity contract.
 * Created by Liam on 2018/8/25.
 */
public interface UnpaidContract {

    interface View extends BaseView<Presenter> {

        void getOrderDetailSuccess(OtcOrderDetailBean response);

        void getOrderDetailFailed(int code, String msg);

        void getDetailPaymentInfoSuccess(DetailPaymentInfoBean response);

        void getDetailPaymentInfoFailed(int code, String msg);

        void cancelOrderSuccess();

        void cancelOrderFailed(int code, String msg);

        void userAlreadyPaidSuccess();

        void userAlreadyPaidFailed(int code, String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void getOrderDetail(int orderId);

        void getDetailPaymentInfo(int orderId, int paymentMethodType);

        void cancelOrder(int orderId);

        void userAlreadyPaid(int orderId, int receivableType);
    }

}
