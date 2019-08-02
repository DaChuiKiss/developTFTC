package oysd.com.trade_app.modules.mycenter.contract;


import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 划转到通证账户-契约
 */
public interface TransferContract {

    interface Presenter extends BasePresenter<TransferContract.View> {
        void CertificateTransferAccounts(@NonNull Map<String, Object> params);
    }

    interface View extends BaseView<TransferContract.Presenter> {
        void CertificateTransferAccountsSuccess(EmptyBean tb);

        void CertificateTransferAccountsFailed(int code, String msg);
    }

}
