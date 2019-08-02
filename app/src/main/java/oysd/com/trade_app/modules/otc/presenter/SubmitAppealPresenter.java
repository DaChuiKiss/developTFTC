package oysd.com.trade_app.modules.otc.presenter;

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
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.otc.contract.SubmitAppealContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/8/28.
 */
public class SubmitAppealPresenter implements SubmitAppealContract.Presenter {

    private SubmitAppealContract.View view;

    public SubmitAppealPresenter(SubmitAppealContract.View view) {
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
                .compose(RxSchedulers.ioMain())
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
    public void submitAppeal(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .submitAppeal(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.submitAppealSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.submitAppealFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(SubmitAppealContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
