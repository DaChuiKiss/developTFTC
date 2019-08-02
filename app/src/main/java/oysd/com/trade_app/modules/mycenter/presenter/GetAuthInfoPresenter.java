package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.GetAuthInfoContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class GetAuthInfoPresenter implements GetAuthInfoContract.Presenter {


    private GetAuthInfoContract.View view;

    public GetAuthInfoPresenter(GetAuthInfoContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(GetAuthInfoContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getAuthInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getgAuthInfo()
                .compose(RxSchedulers.<ResponseEntity<AuthInfoBean>>ioMain())
                .subscribe(new DataObserver<AuthInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(AuthInfoBean authInfoBean) {
                        view.getAuthInfoSuccess(authInfoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getAuthInfoFailed(code,msg);
                    }
                });
    }
}
