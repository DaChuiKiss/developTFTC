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
import oysd.com.trade_app.modules.mycenter.contract.UpdateAccountPwdContract;
import oysd.com.trade_app.modules.mycenter.contract.UpdateLoginPwdContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class UpdateLoginPwdPresenter implements UpdateLoginPwdContract.Presenter {


    private UpdateLoginPwdContract.View view;

    public UpdateLoginPwdPresenter(UpdateLoginPwdContract.View view) {
        this.view = view;
    }


    @Override
    public void updateLoginPwd(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MycenterApi.class)
                .updateLoginPwd(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.updateLoginPwdSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.updateLoginPwdFailed(code,msg);
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
    public void attachView(UpdateLoginPwdContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
