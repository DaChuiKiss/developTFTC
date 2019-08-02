package oysd.com.trade_app.main.contract;

import java.io.File;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.ImageBean;

/**
 * 公共 contract : Upload Image.
 * Created by Liam on 2018/8/23.
 */
public interface UploadImageContract {

    interface View extends BaseView<Presenter> {

        void uploadImageSuccess(ImageBean imageBean);

        void uploadImageFailed(int code, String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void uploadImage(File file);
    }

}
