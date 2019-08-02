package oysd.com.trade_app.main.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.contract.LoginContract;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.util.Utils;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(@NonNull Map<String, Object> params) {
        RetrofitHelper.createApi(MainApi.class)
                .login(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<UserBean>>ioMain())
                .subscribe(new DataObserver<UserBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        view.loginSuccess(userBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.loginFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(LoginContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
