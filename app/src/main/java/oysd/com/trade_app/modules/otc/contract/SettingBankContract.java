package oysd.com.trade_app.modules.otc.contract;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.BankItemBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;

/**
 * SettingBankFragment contract.
 * Created by Liam on 2018/8/21
 */
public interface SettingBankContract {

    interface View extends BaseView<Presenter> {

        void getBankListSuccess(List<BankItemBean> response);

        void getBankListFailed(int code, String msg);

        void getUserPaymentInfoSuccess(PaymentInfoBean response);

        void getUserPaymentInfoFailed(int code, String msg);

        void addBankAccountSuccess();

        void addBankAccountFailed(int code, String msg);

        void updateBankAccountSuccess();

        void updateBankAccountFailed(int code, String msg);

        void updateBankStatusSuccess();

        void updateBankStatusFailed(int code, String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void getBankList(String countryName);

        void getUserPaymentInfo(int payType);

        void addBankAccount(Map<String, Object> params);

        void updateBankAccount(Map<String, Object> params);

        void updateBankAccountStatus(Map<String, Object> params);
    }

}
