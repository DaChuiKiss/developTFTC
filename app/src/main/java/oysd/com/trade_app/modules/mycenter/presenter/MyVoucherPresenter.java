package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountCurrencyContract;
import oysd.com.trade_app.modules.mycenter.contract.MyVoucherContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户-凭证账户-主持人
 */
public class MyVoucherPresenter implements MyVoucherContract.Presenter {


    private MyVoucherContract.View view;

    public MyVoucherPresenter(MyVoucherContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyVoucherContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void getCurrentUserLoginAccount() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getCurrentUserLoginAccount()
                .compose(RxSchedulers.<ResponsePaging<VoucherBean>>ioMain())
                .subscribe(new BaseObserver<ResponsePaging<VoucherBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponsePaging<VoucherBean> voucherBeanResponsePaging) {
                        view.getCurrentUserLoginAccountSuccess(voucherBeanResponsePaging);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getCurrentUserLoginAccountFailed(code, msg);
                    }
                });
    }

}
