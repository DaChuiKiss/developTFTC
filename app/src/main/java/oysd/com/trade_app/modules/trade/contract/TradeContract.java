package oysd.com.trade_app.modules.trade.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;

/**
 * TradeFragment contract.
 * Created by Liam on 2018/7/23
 */
public interface TradeContract {

    interface View extends BaseView<Presenter> {

        void getDealZoneInfoSuccess(ResponseList<DealZoneBean> response);

        void getDealZoneInfoFailed(int code, String msg);


        void getTransactionMarketListSuccess(ResponseList<MarketListBean> response);

        void getTransactionMarketListFailed(int code, String msg);
        void getMarketPriceChangeRatioListSuccess(ResponseList<MarketListBean> response);

        void getMarketPriceChangeRatioListFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getDealZoneInfo(int page, int limit);
        void getTransactionMarketList(int page, int limit,int zoneId);
        void getMarketPriceChangeRatioList(int page, int limit,int zoneId);
    }

}
