package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;
import oysd.com.trade_app.modules.mycenter.contract.WalletAddressContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class WalletAddressPresenter implements WalletAddressContract.Presenter {


    private WalletAddressContract.View view;

    public WalletAddressPresenter(WalletAddressContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(WalletAddressContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getWalletAddress(int coinId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getWalletAddress(coinId)
                .compose(RxSchedulers.<ResponseEntity<WalletAddressBean>>ioMain())
                .subscribe(new DataObserver<WalletAddressBean>(App.getContext()) {
                    @Override
                    public void onSuccess(WalletAddressBean walletAddressBean) {
                        view.getWalletAddressSuccess(walletAddressBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getWalletAddressFailed(code,msg);
                    }
                });
    }
}
