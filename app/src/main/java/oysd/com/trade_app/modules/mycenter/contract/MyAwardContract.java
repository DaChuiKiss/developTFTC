package oysd.com.trade_app.modules.mycenter.contract;


import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.PagingDataBean;
import oysd.com.trade_app.modules.mycenter.bean.AwardBean;
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的奖励-契约
 */
public interface MyAwardContract {
    interface Presenter extends BasePresenter<MyAwardContract.View> {
        void getUserInvitationRecordList();

        void getUserInvitationReturnRecordList(int page,int limit);
    }

    interface View extends BaseView<MyAwardContract.Presenter> {

        void getUserInvitationRecordListSuncess(EmptyBean emptyBean);

        void getUserInvitationRecordListFailed(int code, String msg);


        void getUserInvitationReturnRecordListSuncess(PagingDataBean<AwardBean> awardBean);

        void getUserInvitationReturnRecordListFailed(int code, String msg);
    }

}
