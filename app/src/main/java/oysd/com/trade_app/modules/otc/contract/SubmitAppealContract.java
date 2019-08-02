package oysd.com.trade_app.modules.otc.contract;

import java.io.File;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.ImageBean;

/**
 * Created by Liam on 2018/8/28.
 */
public interface SubmitAppealContract {

    interface View extends BaseView<Presenter> {

        void uploadImageSuccess(ImageBean imageBean);

        void uploadImageFailed(int code, String msg);

        void submitAppealSuccess();

        void submitAppealFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void uploadImage(File file);

        void submitAppeal(Map<String, Object> params);
    }

}
