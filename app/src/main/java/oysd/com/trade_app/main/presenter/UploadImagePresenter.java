package oysd.com.trade_app.main.presenter;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.main.contract.UploadImageContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

/**
 * 公共的 Presenter : Upload Image.
 * Created by Liam on 2018/8/23.
 */
public class UploadImagePresenter implements UploadImageContract.Presenter {

    private UploadImageContract.View view;

    public UploadImagePresenter(UploadImageContract.View view) {
        this.view = view;
    }

    @Override
    public void uploadImage(File file) {
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
                        view.uploadImageSuccess(imageBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.uploadImageFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(UploadImageContract.View view) {
    }

    @Override
    public void detachView() {
    }
}
