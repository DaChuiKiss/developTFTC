package oysd.com.trade_app.modules.trade.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;

/**
 * TradeFragment contract.
 * Created by Liam on 2018/7/23
 */
public interface MarketInfoContract {

    interface View extends BaseView<Presenter> {

        void getTransactionMarketInfoSuccess(MarketInfoBean response);

        void getTransactionMarketInfoFailed(int code, String msg);

        void removeOptionalSuccess();

        void removeOptionalFailed(int code, String msg);

        void addOptionalSuccess();

        void addOptionalFailed(int code, String msg);
        void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response);
        void getUseOptionalMarketFailed(int code, String msg);

        void getByMarketIdSuccess(MarketIdBean mb);
        void getByMarketIdFailed(int code,String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void getByMarketId(int marketId);
        void getTransactionMarketInfo(int marketId);
        void removeOptional(int marketId);
        void addOptional(Map<String, Object> params);
        void getUseOptionalMarket();
    }

}
