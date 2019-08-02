package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.SafeContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class SafePresenter implements SafeContract.Presenter {


    private SafeContract.View view;

    public SafePresenter(SafeContract.View view) {
        this.view = view;
    }




    @Override
    public void attachView(SafeContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getSafeInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserInfo()
                .compose(RxSchedulers.<ResponseEntity<UserInfoBean>>ioMain())
                .subscribe(new DataObserver<UserInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserInfoBean safeBean) {
                        view.safeInfoSuccess(safeBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.safeInfoFailed();
                    }
                });
    }
}
