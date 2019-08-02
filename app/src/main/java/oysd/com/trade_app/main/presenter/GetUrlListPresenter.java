package oysd.com.trade_app.main.presenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.main.contract.GetCountryPhoneContract;
import oysd.com.trade_app.main.contract.GetUrlContract;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Global;

public class GetUrlListPresenter implements GetUrlContract.Presenter {

    private GetUrlContract.View view;

    public GetUrlListPresenter(GetUrlContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(GetUrlContract.View view) {

    }


    @Override
    public void detachView() {

    }

    @Override
    public void getUrlList() {
        RetrofitHelper.createApi(MainApi.class)
                .urlList()
                .compose(RxSchedulers.<ResponseList<UrlBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<UrlBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<UrlBean> response) {
                        view.getUrlListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUrlListFail(code,msg);
                    }
                });
    }


    @Override
    public void getUserInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserInfo()
                .compose(RxSchedulers.<ResponseEntity<UserInfoBean>>ioMain())
                .subscribe(new DataObserver<UserInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        view.getUserInfoSuccess(userInfoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserInfoFailed(code,msg);
                    }
                });
    }

    @Override
    public void accessTokenIsOk() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getgAuthInfo()
                .compose(RxSchedulers.<ResponseEntity<AuthInfoBean>>ioMain())
                .subscribe(new Observer<ResponseEntity<AuthInfoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<AuthInfoBean> response) {
                        int code = response.getStatus().getCode();
                        view.accessTokenIsOk(code,response.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.netIsError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
