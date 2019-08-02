package oysd.com.trade_app.modules.mycenter.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.OTCConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;

public interface MyAccountOTCContract {

    interface Presenter extends BasePresenter<MyAccountOTCContract.View> {
        void getOnlineTotalAmount();
        void getLegalTenderList(int page, int limit);
    }

    interface View extends BaseView<MyAccountOTCContract.Presenter> {
        void getOnlineTotalAmountSuccess(OTCConvertInfo totalConvertInfo);
        void getOnlineTotalAmountFailed(int code, String msg);

        void getLegalTenderListSuccess(ResponsePaging<LegalOTCBean> totalConvertInfo);
        void getLegalTenderListFailed(int code, String msg);
    }

}
