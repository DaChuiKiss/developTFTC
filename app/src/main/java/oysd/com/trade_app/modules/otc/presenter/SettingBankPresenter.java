package oysd.com.trade_app.modules.otc.presenter;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.otc.bean.BankAccountBean;
import oysd.com.trade_app.modules.otc.bean.BankItemBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import oysd.com.trade_app.modules.otc.contract.SettingBankContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/8/21
 */
public class SettingBankPresenter implements SettingBankContract.Presenter {

    private SettingBankContract.View view;

    public SettingBankPresenter(SettingBankContract.View view) {
        this.view = view;
    }

    @Override
    public void getBankList(String countryName) {
        RetrofitHelper.createApi(OtcApi.class)
                .getBankList(countryName)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<BankItemBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<BankItemBean> response) {
                        view.getBankListSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getBankListFailed(code, msg);
                    }
                });
    }

    @Override
    public void getUserPaymentInfo(int payType) {
        RetrofitHelper.createApi(OtcApi.class)
                .getUserPaymentInfo(payType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<PaymentInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(PaymentInfoBean response) {
                        view.getUserPaymentInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserPaymentInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void addBankAccount(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .addBankAccount(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.addBankAccountSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.addBankAccountFailed(code, msg);
                    }
                });
    }

    @Override
    public void updateBankAccount(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .updateBankAccount(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.updateBankAccountSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.updateBankAccountFailed(code, msg);
                    }
                });
    }

    @Override
    public void updateBankAccountStatus(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .updateBankAccountStatus(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.updateBankStatusSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.updateBankStatusFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(SettingBankContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
