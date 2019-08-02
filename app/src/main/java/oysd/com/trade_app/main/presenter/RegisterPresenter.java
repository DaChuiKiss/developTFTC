package oysd.com.trade_app.main.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.contract.RegisterContract;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.util.Utils;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void getVerCode(@NonNull Map<String, Object> params) {
        RetrofitHelper.createApi(MainApi.class)
                .sendVerCode(Utils.mapToBody(params))
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
    public void register(Map<String, Object> params) {
        RetrofitHelper.createApi(MainApi.class)
                .register(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<UserBean>>ioMain())
                .subscribe(new DataObserver<UserBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        view.registerSuccess(userBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.registerFailed(code, msg);
                    }
                });
    }

    @Override
    public void captcha(Map<String, Object> params) {
        RetrofitHelper.createApi(MainApi.class)
                .captcha(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.getCaptchaSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getCaptchaFail(code, msg);
                    }
                });
    }

    @Override
    public void attachView(RegisterContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
