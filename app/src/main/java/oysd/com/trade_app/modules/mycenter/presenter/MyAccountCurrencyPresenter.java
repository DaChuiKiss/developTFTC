package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountCurrencyContract;
import oysd.com.trade_app.modules.mycenter.contract.MycenterContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class MyAccountCurrencyPresenter implements MyAccountCurrencyContract.Presenter {


    private MyAccountCurrencyContract.View view;

    public MyAccountCurrencyPresenter(MyAccountCurrencyContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAccountCurrencyContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void queryLegalTenderList(int page,int limit,String coinName) {
        RetrofitHelper.createApi(MycenterApi.class)
                .queryLegalTenderList(page,limit,coinName)
                .compose(RxSchedulers.<ResponsePaging<LegalTenderBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<LegalTenderBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<LegalTenderBean> totalConvertInfo) {
                        view.queryLegalTenderListSuccess(totalConvertInfo);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.queryLegalTenderListFailed(code,msg);
                    }
                });
    }

    @Override
    public void getTotalConvertInto() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getTotalConvertInfo()
                .compose(RxSchedulers.<ResponseEntity<TotalConvertInfo>>ioMain())
                .subscribe(new DataObserver<TotalConvertInfo>(App.getContext()) {
                    @Override
                    public void onSuccess(TotalConvertInfo totalConvertInfo) {
                        view.getTotalConvertIntoSuccess(totalConvertInfo);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getTotalConvertIntoFailed(code,msg);
                    }
                });
    }

}
