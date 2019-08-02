package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.ExtractVirtualCoinParamBean;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;

public interface ExtractVirtualCoinParamContract {

    interface Presenter extends BasePresenter<ExtractVirtualCoinParamContract.View> {
        void getExtractVirtualCoinParam(int coinId);
        void getVerCode(Map<String, Object> params);
        void sponsorTakeOutCoinApply(Map<String, Object> params);
    }

    interface View extends BaseView<ExtractVirtualCoinParamContract.Presenter> {

        void getExtractVirtualCoinParamSuccess(ExtractVirtualCoinParamBean wb);
        void getExtractVirtualCoinParamFailed(int code, String msg);



        void sponsorTakeOutCoinApplySuccess(EmptyBean wb);
        void sponsorTakeOutCoinApplyFailed(int code, String msg);

        void getVerCodeSuccess();
        void getVerCodeFailed(int code, String msg);
    }

}
