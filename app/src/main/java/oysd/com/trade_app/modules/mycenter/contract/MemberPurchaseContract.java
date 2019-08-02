package oysd.com.trade_app.modules.mycenter.contract;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.modules.mycenter.bean.VipLevelConfigBean;

/**
 * Created by Liam on 2018/9/20.
 */
public interface MemberPurchaseContract {

    interface View extends BaseView<Presenter> {

        void getVipLevelConfigsSuccess(List<VipLevelConfigBean> response);

        void getVipLevelConfigsFailed(int code, String msg);

        void getVipCardPricesSuccess(List<VipCardPriceBean> response);

        void getVipCardPricesFailed(int code, String msg);

        void userPurchaseVipSuccess();

        void userPurchaseVipFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getVipLevelConfigs();

        void getVipCardPrices(int levelId, int cardId);

        void userPurchaseVip(Map<String, Object> params);
    }

}
