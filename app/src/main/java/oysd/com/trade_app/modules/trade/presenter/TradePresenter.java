package oysd.com.trade_app.modules.trade.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

/**
 * Trade presenter.
 * Created by Liam on 2018/7/23
 */
public class TradePresenter implements TradeContract.Presenter {

    private TradeContract.View view;

    public TradePresenter(TradeContract.View view) {
        this.view = view;
    }

    @Override
    public void getDealZoneInfo(int page, int limit) {
        RetrofitHelper.createApi(TradeApi.class)
                .reqDealZoneInfo(page, limit)
                .compose(RxSchedulers.<ResponseList<DealZoneBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<DealZoneBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<DealZoneBean> response) {
                        view.getDealZoneInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getDealZoneInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void getTransactionMarketList(int page, int limit, int zoneId) {
        RetrofitHelper.createApi(TradeApi.class)
                .getTransactionMarketList(page, limit,zoneId)
                .compose(RxSchedulers.<ResponseList<MarketListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<MarketListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<MarketListBean> response) {
                        view.getTransactionMarketListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getTransactionMarketListFailed(code, msg);
                    }
                });
    }

    @Override
    public void getMarketPriceChangeRatioList(int page, int limit, int zoneId) {
        RetrofitHelper.createApi(TradeApi.class)
                .getMarketPriceChangeRatioList(page, limit,zoneId)
                .compose(RxSchedulers.<ResponseList<MarketListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<MarketListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<MarketListBean> response) {
                        view.getMarketPriceChangeRatioListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getMarketPriceChangeRatioListFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(TradeContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
