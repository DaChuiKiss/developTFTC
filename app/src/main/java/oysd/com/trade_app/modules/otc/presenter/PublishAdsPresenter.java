package oysd.com.trade_app.modules.otc.presenter;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.otc.bean.AbbreviationBean;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.CoinInfoBean;
import oysd.com.trade_app.modules.otc.contract.PublishAdsContract;
import oysd.com.trade_app.modules.otc.http.OtcApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/8/15
 */
public class PublishAdsPresenter implements PublishAdsContract.Presenter {

    private PublishAdsContract.View view;

    public PublishAdsPresenter(PublishAdsContract.View view) {
        this.view = view;
    }

    @Override
    public void postEntrustInfo(Map<String, Object> params) {
        RetrofitHelper.createApi(OtcApi.class)
                .postEntrustInfo(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.postEntrustInfoSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.postEntrustInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void queryAllCoins(int page, int limit) {
        RetrofitHelper.createApi(OtcApi.class)
                .queryAllCoins()
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<CoinInfoBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<CoinInfoBean> response) {
                        view.queryAllCoinsSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.queryAllCoinsFailed(code, msg);
                    }
                });
    }

    @Override
    public void getCoinsConfig(int transactionType) {
        RetrofitHelper.createApi(OtcApi.class)
                .getCoinsConfig(transactionType)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<CoinConfigBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<CoinConfigBean> response) {
                        view.getCoinsConfigSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getCoinsConfigFailed(code, msg);
                    }
                });
    }

    @Override
    public void queryByCoinIdAndAbbreviation(String abbreviation, String exchangeCoinId) {
        RetrofitHelper.createApi(OtcApi.class)
                .queryByCoinIdAndAbbreviation(abbreviation, exchangeCoinId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseEntity<AbbreviationBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<AbbreviationBean> response) {
                        view.queryByCoinIdAndAbbreviationSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.queryByCoinIdAndAbbreviationFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(PublishAdsContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
