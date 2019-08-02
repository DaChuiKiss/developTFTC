package oysd.com.trade_app.modules.trade.presenter;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.contract.BuyAndSaleListContract;
import oysd.com.trade_app.modules.trade.contract.CurrentContract;
import oysd.com.trade_app.modules.trade.http.TradeApi;
import oysd.com.trade_app.util.Utils;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public class BuyAndSaleListPresenter implements BuyAndSaleListContract.Presenter {

    private BuyAndSaleListContract.View view;

    public BuyAndSaleListPresenter(BuyAndSaleListContract.View view) {
        this.view = view;
    }

    @Override
    public void getBuyAndSaleList(int limit, int marketId, final int type) {
        RetrofitHelper.createApi(TradeApi.class)
                .getUserEntrustRecordList(limit, marketId, type)
                .compose(RxSchedulers.<ResponseList<BuyAndSaleListBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<BuyAndSaleListBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<BuyAndSaleListBean> repose) {
                        List<BuyAndSaleListBean> lsbsb = repose.getData();
                        view.getListSuccess(lsbsb, type);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getListFailed(code, msg);
                    }
                });
    }

    @Override
    public void entrust(Map<String, Object> params) {
        RetrofitHelper.createApi(TradeApi.class)
                .reqEntrust(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.entrustSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.entrustFailed(code, msg);
                    }
                });
    }


    @Override
    public void getAuthInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getgAuthInfo()
                .compose(RxSchedulers.<ResponseEntity<AuthInfoBean>>ioMain())
                .subscribe(new DataObserver<AuthInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(AuthInfoBean authInfoBean) {
                        view.getAuthInfoSuccess(authInfoBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getAuthInfoFailed(code,msg);
                    }
                });
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
    public void attachView(BuyAndSaleListContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
