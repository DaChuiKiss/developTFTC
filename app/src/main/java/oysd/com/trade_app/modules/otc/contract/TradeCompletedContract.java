package oysd.com.trade_app.modules.otc.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;

/**
 * Created by Liam on 2018/8/27.
 */
public interface TradeCompletedContract {

    interface View extends BaseView<Presenter> {

        void getFinishedOrderDetailSuccess(OtcFinishedOrderDetailBean response);

        void getFinishedOrderDetailFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getFinishedOrderDetail(int orderId);
    }

}
