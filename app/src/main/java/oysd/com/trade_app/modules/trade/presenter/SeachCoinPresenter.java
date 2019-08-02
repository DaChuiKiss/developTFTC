package oysd.com.trade_app.modules.trade.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
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
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.SearchCoinContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

public class SeachCoinPresenter implements SearchCoinContract.Presenter {


    private SearchCoinContract.View view;

    public SeachCoinPresenter(SearchCoinContract.View view) {
        this.view = view;
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

    @Override
    public void getTransactionMarketList(String  inputChar) {
        RetrofitHelper.createApi(TradeApi.class)
                .getTransactionMarketList(inputChar)
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
    public void attachView(SearchCoinContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
