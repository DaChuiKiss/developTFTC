package oysd.com.trade_app.modules.trade.di;

import dagger.Module;
import dagger.Provides;
import oysd.com.trade_app.modules.trade.TradeFragment;
import oysd.com.trade_app.modules.trade.contract.TradeContract;
import oysd.com.trade_app.modules.trade.presenter.TradePresenter;

/**
 * Created by Liam on 2018/7/23
 */

@Module
public class TradeModule {

    private TradeFragment tradeFragment;

    public TradeModule(TradeFragment tradeFragment) {
        this.tradeFragment = tradeFragment;
    }

    @Provides
    public TradeFragment providesTradeFragment() {
        return tradeFragment;
    }

    @Provides
    public TradeContract.Presenter providesTradePresenter(TradeFragment tradeFragment) {
        return new TradePresenter(tradeFragment);
    }

}
