package oysd.com.trade_app.modules.trade.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.HistoryBean;
import oysd.com.trade_app.modules.trade.contract.CurrentContract;
import oysd.com.trade_app.modules.trade.contract.HistoryContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;

/**
 * BuyOrSell activity presenter.
 * Created by Liam on 2018/7/24
 */
public class HistoryPresenter implements HistoryContract.Presenter {




    private HistoryContract.View view;

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HistoryContract.View view) {

    }

    @Override
    public void detachView() {
    }

    @Override
    public void history(int page,int limit,int marketId,String type) {
        RetrofitHelper.createApi(TradeApi.class)
                .history(page,limit,marketId,type)
                .compose(RxSchedulers.<ResponsePaging<HistoryBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<HistoryBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<HistoryBean> response) {
                        view.historySuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.historyFailed(code, msg);
                    }
                });
    }
}
