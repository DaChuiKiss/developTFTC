package oysd.com.trade_app.modules.trade.contract;

import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.trade.bean.DealBean;

/**
 * BuyOrSell fragment contract.
 * Created by Liam on 2018/7/24
 */
public interface DealReachedListContract {

    interface View extends BaseView<Presenter> {

        void getDealReachedListSuccess(ResponseList<DealBean> response);

        void getDealReachedListFailed(int code, String msg);

    }

    interface Presenter extends BasePresenter<View> {

        void getDealReachedList(int limit,int marketId);
    }

}
