package oysd.com.trade_app.modules.mycenter.presenter;

import android.support.annotation.NonNull;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.contract.TransferContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 划转到通证账户-主持人
 */
public class TransferPresenter implements TransferContract.Presenter {


    private TransferContract.View view;

    public TransferPresenter(TransferContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(TransferContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void CertificateTransferAccounts(@NonNull Map<String, Object> params) {
        RetrofitHelper.createApi(MycenterApi.class)
                .certificateTransferAccounts(Utils.mapToBody(params))
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.CertificateTransferAccountsSuccess(emptyBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.CertificateTransferAccountsFailed(code, msg);
                    }
                });
    }
}
