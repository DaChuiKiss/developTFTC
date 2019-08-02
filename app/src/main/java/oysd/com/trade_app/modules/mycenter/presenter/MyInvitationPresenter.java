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
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;
import oysd.com.trade_app.modules.mycenter.bean.MyInvitationBean;
import oysd.com.trade_app.modules.mycenter.contract.MyInvitationContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的邀请-主持人
 */
public class MyInvitationPresenter implements MyInvitationContract.Presenter {


    private MyInvitationContract.View view;

    public MyInvitationPresenter(MyInvitationContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyInvitationContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void getHomeVo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getHomeVo()
                .compose(RxSchedulers.<ResponseEntity<HomeVoBean>>ioMain())
                .subscribe(new DataObserver<HomeVoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(HomeVoBean homeVoBean) {
                        view.getHomeVoSuncess(homeVoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getHomeVoFailed(code, msg);
                    }
                });
    }

    @Override
    public void getActivityInfo(int type) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getActivityInfo(type)
                .compose(RxSchedulers.<ResponseEntity<MyInvitationBean>>ioMain())
                .subscribe(new DataObserver<MyInvitationBean>(App.getContext()) {
                    @Override
                    public void onSuccess(MyInvitationBean homeVoBean) {
                        view.getActivityInfoSuncess(homeVoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getActivityInfoFailed(code, msg);
                    }
                });
    }


}
