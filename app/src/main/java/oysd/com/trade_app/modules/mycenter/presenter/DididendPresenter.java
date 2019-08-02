package oysd.com.trade_app.modules.mycenter.presenter;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.bean.DividendBean;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.modules.mycenter.contract.CertSeniorContract;
import oysd.com.trade_app.modules.mycenter.contract.DividendContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class DididendPresenter implements DividendContract.Presenter {


    private DividendContract.View view;

    public DididendPresenter(DividendContract.View view) {
        this.view = view;
    }


    @Override
    public void getDividend() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getDividendValue()
                .compose(RxSchedulers.<ResponseEntity<DividendBean>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<DividendBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<DividendBean> response) {
                        view.getDividendSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getDividendFailed(code,msg);
                    }
                });
    }


    @Override
    public void attachView(DividendContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
