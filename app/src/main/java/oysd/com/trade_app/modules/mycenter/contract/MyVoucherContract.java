package oysd.com.trade_app.modules.mycenter.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.PagingDataBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.OTCConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户-凭证账户-契约
 */
public interface MyVoucherContract {

    interface Presenter extends BasePresenter<MyVoucherContract.View> {
        void getCurrentUserLoginAccount();

    }

    interface View extends BaseView<MyVoucherContract.Presenter> {
        void getCurrentUserLoginAccountSuccess( ResponsePaging<VoucherBean>  voucherBean);

        void getCurrentUserLoginAccountFailed(int code, String msg);


    }

}
