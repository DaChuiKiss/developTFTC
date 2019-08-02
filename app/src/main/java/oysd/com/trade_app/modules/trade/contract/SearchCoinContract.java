package oysd.com.trade_app.modules.trade.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;

/**
 * TradeFragment contract.
 * Created by Liam on 2018/7/23
 */
public interface SearchCoinContract {

    interface View extends BaseView<Presenter> {
        void getTransactionMarketListSuccess(ResponseList<MarketListBean> response);

        void getTransactionMarketListFailed(int code, String msg);

        void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response);
        void getUseOptionalMarketFailed(int code, String msg);

        void removeOptionalSuccess();

        void removeOptionalFailed(int code, String msg);

        void addOptionalSuccess();

        void addOptionalFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getUseOptionalMarket();
        void getTransactionMarketList(String inputChar);
        void removeOptional(int marketId);
        void addOptional(Map<String, Object> params);
    }

}
