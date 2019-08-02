package oysd.com.trade_app.modules.trade.presenter;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.MarketInfoContract;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

public class MarketInfoPresenter implements MarketInfoContract.Presenter {


    private MarketInfoContract.View view;

    public MarketInfoPresenter(MarketInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void getByMarketId(int marketId) {
        RetrofitHelper.createApi(TradeApi.class)
                .getByMarketId(marketId)
                .compose(RxSchedulers.<ResponseEntity<MarketIdBean>>ioMain())
                .subscribe(new DataObserver<MarketIdBean>(App.getContext()) {
                    @Override
                    public void onSuccess(MarketIdBean mb) {
                        view.getByMarketIdSuccess(mb);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getByMarketIdFailed(code, msg);
                    }
                });
    }

    @Override
    public void getTransactionMarketInfo(int marketId) {
        RetrofitHelper.createApi(TradeApi.class)
                .getTransactionMarketInfo(marketId)
                .compose(RxSchedulers.<ResponseEntity<MarketInfoBean>>ioMain())
                .subscribe(new DataObserver<MarketInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(MarketInfoBean response) {
                        view.getTransactionMarketInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getTransactionMarketInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(MarketInfoContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void removeOptional(int marketId) {
        RetrofitHelper.createApi(TradeApi.class)
                .removeOptional(marketId)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.removeOptionalSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.removeOptionalFailed(code,msg);
                    }
                });
    }

    @Override
    public void addOptional(Map<String, Object> params) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.get("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(TradeApi.class)
                .addOptional(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.addOptionalSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.addOptionalFailed(code,msg);
                    }
                });
    }

    @Override
    public void getUseOptionalMarket() {
        RetrofitHelper.createApi(TradeApi.class)
                .getUserOptionalMarket()
                .compose(RxSchedulers.<ResponseList<MarketListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<MarketListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<MarketListBean> response) {
                        view.getUseOptionalMarketSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getUseOptionalMarketFailed(code, msg);
                    }
                });
    }


}
