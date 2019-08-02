package oysd.com.trade_app.modules.mycenter.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountOTCContract;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

public class MyAccountTransferPresenter implements MyAccountTransferContract.Presenter {


    private MyAccountTransferContract.View view;

    public MyAccountTransferPresenter(MyAccountTransferContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAccountTransferContract.View view) {

    }

    @Override
    public void detachView() {

    }



    @Override
    public void c2cTransferAccounts(@NonNull Map<String, Object> params) {
        RetrofitHelper.createApi(MycenterApi.class)
                .c2cTransferAccounts(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.c2cTransferAccountsSuccess(emptyBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.c2cTransferAccountsFailed(code, msg);
                    }
                });
    }
}
