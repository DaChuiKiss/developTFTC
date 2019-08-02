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
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.contract.MycenterContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class MycenterPresenter implements MycenterContract.Presenter {


    private MycenterContract.View view;

    public MycenterPresenter(MycenterContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MycenterContract.View view) {

    }

    @Override
    public void detachView() {

    }

    /**
     * 总资产
     */
    @Override
    public void getTotalAmount() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserAccountTotalAmount()
                .compose(RxSchedulers.<ResponseEntity<TotalConvertInfo>>ioMain())
                .subscribe(new DataObserver<TotalConvertInfo>(App.getContext()) {
                    @Override
                    public void onSuccess(TotalConvertInfo totalConvertInfo) {
                        view.getTotalAmountSuccess(totalConvertInfo);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getTotalAmountFailed(code, msg);
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
                        view.getUserInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void getUserVipInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserVipInfo()
                .compose(RxSchedulers.<ResponseEntity<UserVipInfoBean>>ioMain())
                .subscribe(new DataObserver<UserVipInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserVipInfoBean ub_vip) {
                        view.getUserVipInfoSuccess(ub_vip);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserVipInfoFailed(code, msg);
                    }
                });
    }

}
