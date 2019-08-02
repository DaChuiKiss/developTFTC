package oysd.com.trade_app.modules.mycenter.presenter;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.contract.CertPrimaryContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class BindEmailPresenter implements BindEmailContract.Presenter {


    private BindEmailContract.View view;

    public BindEmailPresenter(BindEmailContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(BindEmailContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getVerCode(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MainApi.class)
                .sendVerCode(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.getVerCodeSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getVerCodeFailed(code,msg);
                    }
                });
    }

    @Override
    public void bindPhone(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MycenterApi.class)
                .bindEmail(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.bindEmailSuncess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.bindEmailFailed(code,msg);
                    }
                });
    }
}
