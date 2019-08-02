package oysd.com.trade_app.modules.mycenter.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.PagingDataBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.AwardBean;
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAwardContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的奖励-主持人
 */
public class MyAwardPresenter implements MyAwardContract.Presenter {


    private MyAwardContract.View view;

    public MyAwardPresenter(MyAwardContract.View view) {
        this.view = view;
    }


    @Override
    public void attachView(MyAwardContract.View view) {

    }

    @Override
    public void detachView() {

    }


    @Override
    public void getUserInvitationRecordList() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserInvitationRecordList()
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        view.getUserInvitationRecordListSuncess(emptyBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserInvitationRecordListFailed(code, msg);
                    }
                });
    }

    @Override
    public void getUserInvitationReturnRecordList(int page, int limit) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getUserInvitationReturnRecordList(page,limit)
                .compose(RxSchedulers.<ResponseEntity<PagingDataBean<AwardBean>>>ioMain())
                .subscribe(new DataObserver<PagingDataBean<AwardBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(PagingDataBean<AwardBean> awardBean) {
                        view.getUserInvitationReturnRecordListSuncess(awardBean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getUserInvitationReturnRecordListFailed(code, msg);
                    }
                });
    }

}
