package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.contract.OTCOrderContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;

public class OTCOrderPresenter implements OTCOrderContract.Presenter {

    private OTCOrderContract.View view;

    public OTCOrderPresenter(OTCOrderContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(OTCOrderContract.View view) {
    }

    @Override
    public void detachView() {
    }

    @Override
    public void getOnlineOrderList(int page, int limit, String transactionType, String status) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getOnlineOrderList(page, limit, transactionType, status)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponsePaging<OtcOrderBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<OtcOrderBean> response) {
                        view.getOnlineOrderListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getOnlineOrderListFailed(code, msg);
                    }
                });
    }

}
