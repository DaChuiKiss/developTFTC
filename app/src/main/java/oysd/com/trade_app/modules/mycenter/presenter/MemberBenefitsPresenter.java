package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.MemberBenefitsContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

/**
 * Created by Liam on 2018/9/21.
 */
public class MemberBenefitsPresenter implements MemberBenefitsContract.Presenter {

    private MemberBenefitsContract.View view;

    public MemberBenefitsPresenter(MemberBenefitsContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserVipInfo() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserVipInfo()
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<UserVipInfoBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserVipInfoBean response) {
                        view.getUserVipInfoSuccess(response);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserVipInfoFailed(code, msg);
                    }
                });
    }

    @Override
    public void changeVipBenefit(String code, int userVipId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .changeUserVipBenefit(code, userVipId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean response) {
                        view.changeVipBenefitSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.changeVipBenefitFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(MemberBenefitsContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
