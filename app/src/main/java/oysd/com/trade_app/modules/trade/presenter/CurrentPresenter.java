package oysd.com.trade_app.modules.trade.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.contract.BuyOrSellContract;
import oysd.com.trade_app.modules.trade.contract.CurrentContract;
import oysd.com.trade_app.modules.trade.contract.MarketInfoContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

/**
 * BuyOrSell activity presenter.
 * Created by Liam on 2018/7/24
 */
public class CurrentPresenter implements CurrentContract.Presenter {




    private CurrentContract.View view;

    public CurrentPresenter(CurrentContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(CurrentContract.View view) {

    }

    @Override
    public void detachView() {
    }

    @Override
    public void current(int page,int limit,int marketId,String type) {
        RetrofitHelper.createApi(TradeApi.class)
                .current(page,limit,marketId,type)
                .compose(RxSchedulers.<ResponsePaging<CurrentBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<CurrentBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<CurrentBean> response) {
                        view.CurrentSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.CurrentFailed(code, msg);
                    }
                });
    }

    @Override
    public void rollback(int marketId, String type) {
        RetrofitHelper.createApi(TradeApi.class)
                .rollback(marketId,type)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<EmptyBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<EmptyBean> response) {
                        view.rollbackSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.rollbackFailed(code, msg);
                    }
                });
    }

    @Override
    public void rollbackOne(int id) {
        RetrofitHelper.createApi(TradeApi.class)
                .rollbackOne(id)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<EmptyBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<EmptyBean> response) {
                        view.rollbackOneSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.rollbackOneFailed(code, msg);
                    }
                });
    }
}
