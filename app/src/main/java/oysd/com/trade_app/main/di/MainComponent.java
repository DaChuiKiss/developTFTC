package oysd.com.trade_app.main.di;

import dagger.Component;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.RegisterActivity;

/**
 * Dagger2 main component.
 * Created by Liam on 2018/7/26
 */
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

}
