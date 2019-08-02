package oysd.com.trade_app.modules.mycenter.presenter;

import java.math.BigDecimal;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendBean;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.modules.mycenter.bean.UserInviteReturnAmountBean;
import oysd.com.trade_app.modules.mycenter.contract.UserCostDividendContract;
import oysd.com.trade_app.modules.mycenter.contract.UserInviteReturnAmountContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

public class UserCostDividendPresenter implements UserCostDividendContract.Presenter {


    private UserCostDividendContract.View view;

    public UserCostDividendPresenter(UserCostDividendContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(UserCostDividendContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getUserCostDividend() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserCostDividendRecord()
                .compose(RxSchedulers.<ResponseEntity<String>>ioMain())
                .subscribe(new BaseObserver<ResponseEntity<String>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseEntity<String> response) {
                        view.getUserCostDividendSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserCostDividendFailed(code,msg);
                    }
                });
    }

    @Override
    public void getUserCostDividendRecordList() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserCostDividendRecordList()
                .compose(RxSchedulers.<ResponsePaging<UserCostDividendRecord>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<UserCostDividendRecord>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<UserCostDividendRecord> response) {
                        view.getUserCostDividendRecordListSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserCostDividendRecordListFailed(code,msg);
                    }
                });
    }
}
