package oysd.com.trade_app.modules.otc.presenter;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.modules.otc.contract.OtcTradeContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/7/23
 */
public class OtcTradePresenter implements OtcTradeContract.Presenter {

    private OtcTradeContract.View view;

    public OtcTradePresenter(OtcTradeContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(OtcTradeContract.View view) {
    }

    @Override
    public void detachView() {
    }

    @Override
    public void getEntrustList(int page, int limit, String userAccountId, String transactionType,
                               String status, String coinId, String settlementCurrency) {
        RetrofitHelper.createApi(OtcApi.class)
                .getEntrustList(page, limit, userAccountId, transactionType, status, coinId,
                        settlementCurrency)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponsePaging<OtcAdBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<OtcAdBean> response) {
                        view.getEntrustListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getEntrustListFailed(code, msg);
                    }
                });
    }

    @Override
    public void addOrder(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .addOrder(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<OtcOrderBean>(App.getContext()) {
                    @Override
                    public void onSuccess(OtcOrderBean otcOrderBean) {
                        view.addOrderSuccess(otcOrderBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.addOrderFailed(code, msg);
                    }
                });
    }

    @Override
    public void getCoinsConfig(int transactionType) {
        RetrofitHelper.createApi(OtcApi.class)
                .getCoinsConfig(transactionType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<CoinConfigBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<CoinConfigBean> response) {
                        view.getCoinsConfigSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getCoinsConfigFailed(code, msg);
                    }
                });
    }

}
