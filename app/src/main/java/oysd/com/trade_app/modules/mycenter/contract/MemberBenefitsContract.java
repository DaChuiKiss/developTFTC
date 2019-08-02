package oysd.com.trade_app.modules.mycenter.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;

/**
 * 会员权益 页面 contract .
 * Created by Liam on 2018/9/21.
 */
public interface MemberBenefitsContract {

    interface View extends BaseView<Presenter> {

        void getUserVipInfoSuccess(UserVipInfoBean response);

        void getUserVipInfoFailed(int code, String msg);

        void changeVipBenefitSuccess();

        void changeVipBenefitFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getUserVipInfo();

        void changeVipBenefit(String code, int userVipId);
    }

}
