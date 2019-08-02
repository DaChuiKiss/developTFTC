package oysd.com.trade_app.modules.otc.contract;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.modules.otc.bean.AbbreviationBean;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.CoinInfoBean;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;

/**
 * Publish ads contract.
 * Created by Liam on 2018/8/15
 */
public interface PublishAdsContract {

    interface View extends BaseView<Presenter> {

        void postEntrustInfoSuccess();

        void postEntrustInfoFailed(int code, String msg);

        void queryAllCoinsSuccess(List<CoinInfoBean> response);

        void queryAllCoinsFailed(int code, String msg);

        void getCoinsConfigSuccess(List<CoinConfigBean> response);

        void getCoinsConfigFailed(int code, String msg);

        void queryByCoinIdAndAbbreviationSuccess(AbbreviationBean response);

        void queryByCoinIdAndAbbreviationFailed(int code, String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void postEntrustInfo(Map<String, Object> params);

        void queryAllCoins(int page, int limit);

        void getCoinsConfig(int transactionType);

        void queryByCoinIdAndAbbreviation(String abbreviation, String exchangeCoinId);
    }

}
