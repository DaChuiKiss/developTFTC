package oysd.com.trade_app.modules.otc.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.AppealInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;

/**
 * Created by Liam on 2018/8/28.
 */
public interface AppealingContract {

    interface View extends BaseView<Presenter> {

        void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response);

        void getFinishedOrderDetailFailed(int code, String msg);

        void getAppealInfoSuccess(AppealInfoBean response);

        void getAppealInfoFailed(int code, String msg);

        void cancelOrderSuccess();

        void cancelOrderFailed(int code, String msg);

        void cancelAppealSuccess();

        void cancelAppealFailed(int code, String msg);

        void releaseCoinsSuccess();

        void releaseCoinsFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getFinishedOrderDetail(int orderId);

        void getAppealInfo(int orderId);

        void cancelOrder(int orderId);

        void cancelAppeal(int appealId);

        void releaseCoins(Map<String, Object> params);
    }
}
