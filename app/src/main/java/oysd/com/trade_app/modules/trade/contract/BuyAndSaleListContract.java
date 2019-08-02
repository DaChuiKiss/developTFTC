package oysd.com.trade_app.modules.trade.contract;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public interface BuyAndSaleListContract {

    interface View extends BaseView<Presenter> {

        void getListSuccess(List<BuyAndSaleListBean> bsList,final int type);

        void getListFailed(int code, String msg);

        void entrustSuccess();

        void entrustFailed(int code, String msg);

        void getAuthInfoSuccess(AuthInfoBean authInfoBean);
        void getAuthInfoFailed(int code,String msg);

        void getByMarketIdSuccess(MarketIdBean mb);
        void getByMarketIdFailed(int code,String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void getBuyAndSaleList(int limit,int marketId,int type);

        void entrust(Map<String, Object> params);

        void getAuthInfo();

        void getByMarketId(int marketId);
    }

}
