package oysd.com.trade_app.main.presenter;

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
import oysd.com.trade_app.main.contract.RestPwdContract;
import oysd.com.trade_app.main.http.MainApi;

public class ResetPwdPresenter implements RestPwdContract.Presenter {

    private RestPwdContract.View view;

    public ResetPwdPresenter(RestPwdContract.View view) {
        this.view = view;
    }

    @Override
    public void resetPwd(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MainApi.class)
                .resetPwd(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.resetPwdSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.resetPwdFail(code,msg);
                    }
                });
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
    public void isExists(String account) {

        RetrofitHelper.createApi(MainApi.class)
                .isExists(account)
                .compose(RxSchedulers.<ResponseEntity<Boolean>>ioMain())
                .subscribe(new DataObserver<Boolean>(App.getContext()) {
                    @Override
                    public void onSuccess(Boolean flag) {
                        view.isExistsSuccess(flag);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.isExistsFailed(code,msg);
                    }
                });
    }

    @Override
    public void attachView(RestPwdContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
