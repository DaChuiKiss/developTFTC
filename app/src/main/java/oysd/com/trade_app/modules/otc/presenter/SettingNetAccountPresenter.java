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
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import oysd.com.trade_app.modules.otc.contract.SettingNetAccountContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/8/23.
 */
public class SettingNetAccountPresenter implements SettingNetAccountContract.Presenter {

    private SettingNetAccountContract.View view;

    public SettingNetAccountPresenter(SettingNetAccountContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserPaymentInfo(int payType) {
        RetrofitHelper.createApi(OtcApi.class)
                .getUserPaymentInfo(payType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<PaymentInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(PaymentInfoBean response) {
                        view.getUserPaymentInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserPaymentInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void addNetAccount(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .addNetAccount(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.addNetAccountSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.addNetAccountFailed(code, msg);
                    }
                });
    }

    @Override
    public void updateNetAccount(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .updateNetAccount(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.updateNetAccountSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.updateNetAccountFailed(code, msg);
                    }
                });
    }

    @Override
    public void updateNetAccountStatus(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .updateNetAccountStatus(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.updateNetAccountStatusSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.updateNetAccountStatusFailed(code, msg);
                    }
                });
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

}
