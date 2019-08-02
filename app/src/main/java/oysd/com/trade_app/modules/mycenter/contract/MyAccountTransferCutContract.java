package oysd.com.trade_app.modules.mycenter.contract;


import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;

public interface MyAccountTransferCutContract {

    interface Presenter extends BasePresenter<MyAccountTransferCutContract.View> {
        void coinExchange(@NonNull Map<String, Object> params);
        void coinExchangeRatio(int fromCoinId,int toCoinId);
        void getCurrentUserLoginAccount( String type,String coinId);
    }

    interface View extends BaseView<MyAccountTransferCutContract.Presenter> {
        void coinExchangeSuccess(EmptyBean tb);
        void coinExchangeFailed(int code, String msg);

        void coinExchangeRatioSuccess(String tb);
        void coinExchangeRatioFailed(int code, String msg);


        void  getCurrentUserLoginAccountSuccess(String tb);
        void getCurrentUserLoginAccountFailed(int code, String msg);
    }

}
