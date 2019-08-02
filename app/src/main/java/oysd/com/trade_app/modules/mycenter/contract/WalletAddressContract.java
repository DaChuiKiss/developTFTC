package oysd.com.trade_app.modules.mycenter.contract;


import java.io.File;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;

public interface WalletAddressContract {

    interface Presenter extends BasePresenter<WalletAddressContract.View> {
        void getWalletAddress(int coinId);
    }

    interface View extends BaseView<WalletAddressContract.Presenter> {

        void getWalletAddressSuccess(WalletAddressBean wb);
        void getWalletAddressFailed(int code,String msg);
    }

}
