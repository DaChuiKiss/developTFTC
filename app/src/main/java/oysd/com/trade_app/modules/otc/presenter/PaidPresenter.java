package oysd.com.trade_app.modules.otc.presenter;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.PaidContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/8/26.
 */
public class PaidPresenter implements PaidContract.Presenter {

    private PaidContract.View view;

    public PaidPresenter(PaidContract.View view) {
        this.view = view;
    }

    @Override
    public void getOrderDetail(int orderId) {
        RetrofitHelper.createApi(OtcApi.class)
                .getOrderDetail(orderId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<OtcOrderDetailBean>(App.getContext()) {
                    @Override
                    public void onSuccess(OtcOrderDetailBean response) {
                        view.getOrderDetailSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getOrderDetailFailed(code, msg);
                    }
                });
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
    public void cancelOrder(int orderId) {
        RetrofitHelper.createApi(OtcApi.class)
                .cancelOrder(orderId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.cancelOrderSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.cancelOrderFailed(code, msg);
                    }
                });
    }

    @Override
    public void releaseCoins(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .releaseCoins(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.releaseCoinsSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.releaseCoinsFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(PaidContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
