package oysd.com.trade_app.modules.mycenter.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.contract.SetAccountContract;
import oysd.com.trade_app.modules.mycenter.presenter.SetAccountPresenter;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;


public class SettingAccountPwdActivity extends BaseToolActivity implements SetAccountContract.View{

    @BindView(R.id.bt_mycenter_setting_accountpwd)
    Button bt_mycenter_setting_accountpwd;
    @BindView(R.id.et_mycenter_setting_accountpwd)
    EditText et_mycenter_setting_accountpwd;
    @BindView(R.id.et_mycenter_setting_accountconfigpwd)
    EditText et_mycenter_setting_accountconfigpwd;


    SetAccountContract.Presenter presenter = new SetAccountPresenter(this);
    @Override
    protected int setView() {
        return R.layout.activity_setting_account_pwd;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_setting_accountpwd.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.transfer_pwd);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void clickBack() {
        super.clickBack();
        if(!InfoProvider.getInstance().getLogin()){
            quickStartActivity(LoginActivity.class);
        }else {
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_mycenter_setting_accountpwd:
                    requestSetAccount();
                    break;
            }
        }
    }

    //请求设置资金密码接口
    public void requestSetAccount(){
        Map<String, Object> params = new HashMap<>();
        params.put("newDealPassword", Utils.MD5(et_mycenter_setting_accountpwd.getText().toString()));
        params.put("confirmDealPassword",Utils.MD5( et_mycenter_setting_accountconfigpwd.getText().toString()));
        presenter.addDealPwd(params);
    }

    @Override
    public void addDealPwdSuccess() {
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message7));
        InfoProvider.getInstance().setAccount(true);
        if(!InfoProvider.getInstance().getLogin()) {
            quickStartActivity(CertificationStep1.class);
        }
        ActivitiesManager.getInstance().finishActivity(SettingAccountPwdActivity.class);
    }

    @Override
    public void addDealPwdFailed(int code,String msg) {
        ToastUtils.showLong(this, code+msg);
    }
}
