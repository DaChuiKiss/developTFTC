package oysd.com.trade_app.modules.mycenter.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferContract;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferCutContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

public class MyAccountTransferCutPresenter implements MyAccountTransferCutContract.Presenter {


    private MyAccountTransferCutContract.View view;

    public MyAccountTransferCutPresenter(MyAccountTransferCutContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAccountTransferCutContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void coinExchange(@NonNull Map<String, Object> params) {

        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));
        RetrofitHelper.createApi(MycenterApi.class)
                .coinExchange(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<EmptyBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<EmptyBean> entity) {
                        view.coinExchangeSuccess(entity.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.coinExchangeFailed(code, msg);
                    }
                });
    }

    @Override
    public void coinExchangeRatio(int fromCoinId, int toCoinId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .coinExchangeRatio(fromCoinId, toCoinId)
                .compose(RxSchedulers.<ResponseEntity<String>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<String>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<String> entity) {
                        view.coinExchangeRatioSuccess(entity.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.coinExchangeRatioFailed(code, msg);
                    }
                });
    }

    @Override
    public void getCurrentUserLoginAccount(String type, String coinId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getCurrentUserLoginAccountByType(type, coinId)
                .compose(RxSchedulers.<ResponseEntity<String>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<String>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<String> entity) {
                        view.getCurrentUserLoginAccountSuccess(entity.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getCurrentUserLoginAccountFailed(code, msg);
                    }
                });
    }
}
