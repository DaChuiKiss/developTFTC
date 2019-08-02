package oysd.com.trade_app.modules.mycenter.presenter;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.contract.CertSeniorContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class CertSeniorPresenter implements CertSeniorContract.Presenter {


    private CertSeniorContract.View view;

    public CertSeniorPresenter(CertSeniorContract.View view) {
        this.view = view;
    }



    @Override
    public void setCertSenior(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MycenterApi.class)
                .setCertSenior(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.certSeniorSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.certSeniorFailed();
                    }
                });
    }

    @Override
    public void upImage(File file) {


        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);

        RetrofitHelper.createApi(MycenterApi.class)
                .upImage(body)
                .compose(RxSchedulers.<ResponseEntity<ImageBean>>ioMain())
                .subscribe(new DataObserver<ImageBean>(App.getContext()) {
                    @Override
                    public void onSuccess(ImageBean imageBean) {
                        view.upImageSuccess(imageBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.upImageFail(code,msg);
                    }
                });
    }


    @Override
    public void attachView(CertSeniorContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
