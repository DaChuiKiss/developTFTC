package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;

public interface MyAccountCurrencyContract {

    interface Presenter extends BasePresenter<MyAccountCurrencyContract.View> {
        void getTotalConvertInto();
        void queryLegalTenderList(int page,int limit,String coinName);
    }

    interface View extends BaseView<MyAccountCurrencyContract.Presenter> {
        void getTotalConvertIntoSuccess(TotalConvertInfo totalConvertInfo);
        void getTotalConvertIntoFailed(int code, String msg);

        void queryLegalTenderListSuccess(ResponsePaging<LegalTenderBean> totalConvertInfo);
        void queryLegalTenderListFailed(int code, String msg);
    }

}
