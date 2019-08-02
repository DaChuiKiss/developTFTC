package oysd.com.trade_app.modules.mycenter.presenter;

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
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.bean.UserInviteReturnAmountBean;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.contract.UserInviteReturnAmountContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class UserInviteReturnAmountPresenter implements UserInviteReturnAmountContract.Presenter {


    private UserInviteReturnAmountContract.View view;

    public UserInviteReturnAmountPresenter(UserInviteReturnAmountContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(UserInviteReturnAmountContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getUserInviteReturnAmount() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserInviteReturnAmount()
                .compose(RxSchedulers.<ResponseEntity<UserInviteReturnAmountBean>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<UserInviteReturnAmountBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<UserInviteReturnAmountBean> response) {
                        view.getUserInviteReturnAmountSuncess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserInviteReturnAmountFailed(code,msg);
                    }
                });
    }
}
