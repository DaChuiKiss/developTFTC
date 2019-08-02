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
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.contract.SystemSettingContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class SystemSettingPresenter implements SystemSettingContract.Presenter {


    private SystemSettingContract.View view;

    public SystemSettingPresenter(SystemSettingContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(SystemSettingContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void logout() {
        RetrofitHelper.createApi(MycenterApi.class)
                .logout()
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.logoutSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.logoutFailed(code,msg);
                    }
                });
    }

    @Override
    public void getNewestAppVersionInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getNewestAppVersionInfo("1")
                .compose(RxSchedulers.<ResponseEntity<AppVersionInfoBean>>ioMain())
                .subscribe(new DataObserver<AppVersionInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(AppVersionInfoBean appVersionInfoBean) {
                        view.getNewestAppVersionInfoSuccess(appVersionInfoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getNewestAppVersionInfoFail(code,msg);
                    }
                });
    }
}
