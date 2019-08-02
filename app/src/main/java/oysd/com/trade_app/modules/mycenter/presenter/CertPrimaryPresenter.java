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
import oysd.com.trade_app.modules.mycenter.contract.CertPrimaryContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class CertPrimaryPresenter implements CertPrimaryContract.Presenter {


    private CertPrimaryContract.View view;

    public CertPrimaryPresenter(CertPrimaryContract.View view) {
        this.view = view;
    }


    @Override
    public void setCertPrimary(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MycenterApi.class)
                .setCertPrimary(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.certPrimarySuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.certPrimaryFailed(code,msg);
                    }
                });
    }

    @Override
    public void attachView(CertPrimaryContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
