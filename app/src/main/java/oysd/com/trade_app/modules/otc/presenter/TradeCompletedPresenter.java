package oysd.com.trade_app.modules.otc.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.TradeCompletedContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;

/**
 * Created by Liam on 2018/8/27.
 */
public class TradeCompletedPresenter implements TradeCompletedContract.Presenter {

    TradeCompletedContract.View view;

    public TradeCompletedPresenter(TradeCompletedContract.View view) {
        this.view = view;
    }

    @Override
    public void getFinishedOrderDetail(int orderId) {
        RetrofitHelper.createApi(OtcApi.class)
                .getFinishedOrderDetail(orderId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<OtcFinishedOrderDetailBean>(App.getContext()) {
                    @Override
                    public void onSuccess(OtcFinishedOrderDetailBean response) {
                        view.getFinishedOrderDetailSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getFinishedOrderDetailFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(TradeCompletedContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
