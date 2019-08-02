package oysd.com.trade_app.modules.trade.presenter;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.DealBean;
import oysd.com.trade_app.modules.trade.contract.BuyAndSaleListContract;
import oysd.com.trade_app.modules.trade.contract.DealReachedListContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;
import oysd.com.trade_app.util.Utils;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public class DealReachedListPresenter implements DealReachedListContract.Presenter {

    private DealReachedListContract.View view;

    public DealReachedListPresenter(DealReachedListContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(DealReachedListContract.View view) {

    }

    @Override
    public void detachView() {
    }

    @Override
    public void getDealReachedList(int limit, int marketId) {
        RetrofitHelper.createApi(TradeApi.class)
                .getUserReachedList(limit,marketId)
                .compose(RxSchedulers.<ResponseList<DealBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<DealBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<DealBean> response) {
                        view.getDealReachedListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getDealReachedListFailed(code, msg);
                    }
                });
    }
}
