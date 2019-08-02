package oysd.com.trade_app.modules.otc.contract;

import java.io.File;
import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;

/**
 * SettingCnyFragment contract.
 * Created by Liam on 2018/8/23.
 */
public interface SettingNetAccountContract {


    interface View extends BaseView<Presenter> {

        void getUserPaymentInfoSuccess(PaymentInfoBean response);

        void getUserPaymentInfoFailed(int code, String msg);

        void addNetAccountSuccess();

        void addNetAccountFailed(int code, String msg);

        void updateNetAccountSuccess();

        void updateNetAccountFailed(int code, String msg);

        void updateNetAccountStatusSuccess();

        void updateNetAccountStatusFailed(int code, String msg);

        void uploadImageSuccess(ImageBean imageBean);

        void uploadImageFailed(int code, String msg);
    }

    interface Presenter extends BaseView<View> {

        void getUserPaymentInfo(int payType);

        void addNetAccount(Map<String, Object> params);

        void updateNetAccount(Map<String, Object> params);

        void updateNetAccountStatus(Map<String, Object> params);

        void uploadImage(File file);
    }

}
