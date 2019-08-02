package oysd.com.trade_app.main.di;

import dagger.Module;
import dagger.Provides;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.RegisterActivity;
import oysd.com.trade_app.main.contract.LoginContract;
import oysd.com.trade_app.main.contract.RegisterContract;
import oysd.com.trade_app.main.presenter.LoginPresenter;
import oysd.com.trade_app.main.presenter.RegisterPresenter;

/**
 * Dagger2 main module.
 * Created by Liam on 2018/7/26
 */
@Module
public class MainModule {

    private LoginActivity loginActivity;
    private RegisterActivity registerActivity;

    public MainModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public MainModule(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    // LoginActivity

    @Provides
    public LoginActivity providesLoginActivity() {
        return loginActivity;
    }

    @Provides
    public LoginContract.Presenter providesLoginPresenter(LoginActivity loginActivity) {
        return new LoginPresenter(loginActivity);
    }

    // RegisterActivity

    @Provides
    public RegisterActivity providesRegisterActivity() {
        return registerActivity;
    }

    @Provides
    public RegisterContract.Presenter providesRegisterPresenter(RegisterActivity registerActivity) {
        return new RegisterPresenter(registerActivity);
    }


}
