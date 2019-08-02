package oysd.com.trade_app.modules.mycenter.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;
import oysd.com.trade_app.modules.mycenter.bean.MyInvitationBean;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的邀请-契约
 */
public interface MyInvitationContract {

    interface Presenter extends BasePresenter<MyInvitationContract.View> {
        void getHomeVo();
        void getActivityInfo(int type);
    }

    interface View extends BaseView<MyInvitationContract.Presenter> {
        void getHomeVoSuncess(HomeVoBean homeVoBean);

        void getHomeVoFailed(int code, String msg);

        void getActivityInfoSuncess(MyInvitationBean homeVoBean);

        void getActivityInfoFailed(int code, String msg);
    }

}
