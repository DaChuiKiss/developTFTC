package oysd.com.trade_app.modules.mycenter.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.bean.ExtractVirtualCoinParamBean;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;
import oysd.com.trade_app.modules.mycenter.contract.ExtractVirtualCoinParamContract;
import oysd.com.trade_app.modules.mycenter.contract.WalletAddressContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

public class ExtractVirtualCoinParamPresenter implements ExtractVirtualCoinParamContract.Presenter {


    private ExtractVirtualCoinParamContract.View view;

    public ExtractVirtualCoinParamPresenter(ExtractVirtualCoinParamContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(ExtractVirtualCoinParamContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void getExtractVirtualCoinParam(int coinId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getExtractVirtualCoinParam(coinId)
                .compose(RxSchedulers.<ResponseEntity<ExtractVirtualCoinParamBean>>ioMain())
                .subscribe(new DataObserver<ExtractVirtualCoinParamBean>(App.getContext()) {
                    @Override
                    public void onSuccess(ExtractVirtualCoinParamBean eb) {
                        view.getExtractVirtualCoinParamSuccess(eb);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getExtractVirtualCoinParamFailed(code, msg);
                    }
                });
    }

    @Override
    public void getVerCode(@NonNull Map<String, Object> params) {
        //获取验证码接口变动
        RetrofitHelper.createApi(MainApi.class)
                .sendTakeOutMsg()
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.getVerCodeSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getVerCodeFailed(code, msg);
                    }
                });
    }

    @Override
    public void sponsorTakeOutCoinApply(Map<String, Object> params) {
        RetrofitHelper.createApi(MycenterApi.class)
                .sponsorTakeOutCoinApply(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.sponsorTakeOutCoinApplySuccess(emptyBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.sponsorTakeOutCoinApplyFailed(code, msg);
                    }
                });
    }
}
