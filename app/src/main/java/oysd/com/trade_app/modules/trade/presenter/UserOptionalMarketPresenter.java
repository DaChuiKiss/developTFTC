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
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.MarketInfoContract;
import oysd.com.trade_app.modules.trade.contract.UseOptionalMarketContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

public class UserOptionalMarketPresenter implements UseOptionalMarketContract.Presenter {


    private UseOptionalMarketContract.View view;

    public UserOptionalMarketPresenter(UseOptionalMarketContract.View view) {
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
    public void getOptionalTransactionMarketList(ArrayList<Integer> marketIds) {
        RetrofitHelper.createApi(TradeApi.class)
                .getOptionalTransactionMarketList(marketIds)
                .compose(RxSchedulers.<ResponseList<MarketListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<MarketListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<MarketListBean> response) {
                        view.getOptionalTransactionMarketListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getOptionalTransactionMarketListFailed(code, msg);
                    }
                });
    }


    @Override
    public void attachView(UseOptionalMarketContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
