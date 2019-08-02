package oysd.com.trade_app.modules.mycenter.contract;


import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.ImageBean;
import retrofit2.http.Multipart;

public interface CertSeniorContract {

    interface Presenter extends BasePresenter<CertSeniorContract.View> {
        void setCertSenior(Map<String, Object> params);
        void upImage(File file);
    }

    interface View extends BaseView<CertSeniorContract.Presenter> {

        void certSeniorSuccess();
        void certSeniorFailed();
        void upImageSuccess(ImageBean img);
        void upImageFail(int code, String msg);
    }

}
