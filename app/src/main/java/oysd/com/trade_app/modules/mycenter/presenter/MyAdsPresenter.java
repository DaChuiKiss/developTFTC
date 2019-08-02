package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.contract.MyAdsContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.http.OtcApi;

public class MyAdsPresenter implements MyAdsContract.Presenter {


    private MyAdsContract.View view;

    public MyAdsPresenter(MyAdsContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAdsContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getAds(int page, int limit, int userAccountId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getAds(page, limit, userAccountId)
                .compose(RxSchedulers.<ResponsePaging<OtcAdBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<OtcAdBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<OtcAdBean> responseList) {
                        view.getAdsSuccess(responseList);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getAdsFailed(code, msg);
                    }
                });
    }

    @Override
    public void cancelAd(int adId) {
        RetrofitHelper.createApi(OtcApi.class)
                .cancelEntrustPost(adId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.cancelAdSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.cancelAdFailed(code, msg);
                    }
                });
    }

}
