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
import oysd.com.trade_app.modules.mycenter.contract.SetAccountContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class SetAccountPresenter implements SetAccountContract.Presenter {


    private SetAccountContract.View view;

    public SetAccountPresenter(SetAccountContract.View view) {
        this.view = view;
    }

    @Override
    public void addDealPwd(Map<String, Object> params) {

        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MycenterApi.class)
                .addDealPwd(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.addDealPwdSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.addDealPwdFailed(code,msg);
                    }
                });
    }


    @Override
    public void attachView(SetAccountContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
