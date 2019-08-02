package oysd.com.trade_app.modules.trade.di;

import dagger.Component;
import oysd.com.trade_app.modules.trade.TradeFragment;

/**
 * Created by Liam on 2018/7/23
 */

@Component(modules = TradeModule.class)
public interface TradeComponent {

    void inject(TradeFragment tradeFragment);

}
