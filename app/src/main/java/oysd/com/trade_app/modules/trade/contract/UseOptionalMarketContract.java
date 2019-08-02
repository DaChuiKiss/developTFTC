package oysd.com.trade_app.modules.trade.contract;

import java.util.ArrayList;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;

/**
 * TradeFragment contract.
 * Created by Liam on 2018/7/23
 */
public interface UseOptionalMarketContract {

    interface View extends BaseView<Presenter> {

        void getUseOptionalMarketSuccess(ResponseList<MarketListBean> response);
        void getUseOptionalMarketFailed(int code, String msg);

        void getOptionalTransactionMarketListSuccess(ResponseList<MarketListBean> response);
        void getOptionalTransactionMarketListFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void getUseOptionalMarket();
        void getOptionalTransactionMarketList(ArrayList<Integer> marketIds);

    }

}
