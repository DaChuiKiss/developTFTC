package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.OTCConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountCurrencyContract;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountOTCContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class MyAccountOTCPresenter implements MyAccountOTCContract.Presenter {


    private MyAccountOTCContract.View view;

    public MyAccountOTCPresenter(MyAccountOTCContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAccountOTCContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getOnlineTotalAmount() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getOnlineTotalAmount()
                .compose(RxSchedulers.<ResponseEntity<OTCConvertInfo>>ioMain())
                .subscribe(new DataObserver<OTCConvertInfo>(App.getContext()) {
                    @Override
                    public void onSuccess(OTCConvertInfo totalConvertInfo) {
                        view.getOnlineTotalAmountSuccess(totalConvertInfo);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getOnlineTotalAmountFailed(code,msg);
                    }
                });
    }

    @Override
    public void getLegalTenderList(int page, int limit) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getLegalTenderList(page,limit)
                .compose(RxSchedulers.<ResponsePaging<LegalOTCBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<LegalOTCBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<LegalOTCBean> totalConvertInfo) {
                        view.getLegalTenderListSuccess(totalConvertInfo);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getLegalTenderListFailed(code,msg);
                    }
                });
    }
}
