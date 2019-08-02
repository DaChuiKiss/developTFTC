package oysd.com.trade_app.modules.mycenter.presenter;

import com.google.gson.Gson;

import java.util.List;
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
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.contract.CoinOrderContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.http.TradeApi;

public class CoinOrderPresenter implements CoinOrderContract.Presenter {


    private CoinOrderContract.View view;

    public CoinOrderPresenter(CoinOrderContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(CoinOrderContract.View view) {

    }

    @Override
    public void detachView() {

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


    @Override
    public void getAll(int page,int limit) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getAll(page,limit)
                .compose(RxSchedulers.<ResponsePaging<CoinOrderBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<CoinOrderBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<CoinOrderBean> response) {
                        view.getAllSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                        view.getAllFailed(code, msg);
                    }
                });
    }
}
