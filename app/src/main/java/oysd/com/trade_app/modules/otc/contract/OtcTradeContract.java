package oysd.com.trade_app.modules.otc.contract;

import java.util.List;
import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;

/**
 * C2C fragment contract.
 * Created by Liam on 2018/7/23
 */
public interface OtcTradeContract {

    interface View extends BaseView<Presenter> {

        void getEntrustListSuccess(ResponsePaging<OtcAdBean> response);

        void getEntrustListFailed(int code, String msg);

        void addOrderSuccess(OtcOrderBean otcOrderBean);

        void addOrderFailed(int code, String msg);

        void getCoinsConfigSuccess(List<CoinConfigBean> response);

        void getCoinsConfigFailed(int code, String msg);
    }


    interface Presenter extends BasePresenter<View> {

        void getEntrustList(int page, int limit, String userAccountId, String transactionType,
                            String status, String coinId, String settlementCurrency);

        void addOrder(Map<String, Object> params);

        void getCoinsConfig(int transactionType);
    }

}
