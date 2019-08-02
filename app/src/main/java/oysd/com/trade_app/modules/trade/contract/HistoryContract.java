package oysd.com.trade_app.modules.trade.contract;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.HistoryBean;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public interface HistoryContract {

    interface View extends BaseView<Presenter> {

        void historySuccess(ResponsePaging<HistoryBean> response);

        void historyFailed(int code, String msg);

    }

    interface Presenter extends BasePresenter<View> {
        void history(int page, int limit, int marketId, String type);
    }

}
