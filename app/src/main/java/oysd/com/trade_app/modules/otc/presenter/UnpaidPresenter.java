package oysd.com.trade_app.modules.otc.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.otc.bean.DetailPaymentInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;
import oysd.com.trade_app.modules.otc.contract.UnpaidContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;

/**
 * Created by Liam on 2018/8/25.
 */
public class UnpaidPresenter implements UnpaidContract.Presenter {

    private UnpaidContract.View view;

    public UnpaidPresenter(UnpaidContract.View view) {
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
    public void getDetailPaymentInfo(int orderId, int paymentMethodType) {
        RetrofitHelper.createApi(OtcApi.class)
                .getDetailPaymentInfo(orderId, paymentMethodType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<DetailPaymentInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(DetailPaymentInfoBean response) {
                        view.getDetailPaymentInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getDetailPaymentInfoFailed(code, msg);
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
    public void userAlreadyPaid(int orderId, int receivableType) {
        RetrofitHelper.createApi(OtcApi.class)
                .userAlreadyPaid(orderId, receivableType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.userAlreadyPaidSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.userAlreadyPaidFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(UnpaidContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
