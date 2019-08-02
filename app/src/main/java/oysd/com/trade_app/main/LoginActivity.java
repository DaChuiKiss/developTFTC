package oysd.com.trade_app.main;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.contract.LoginContract;
import oysd.com.trade_app.main.di.DaggerMainComponent;
import oysd.com.trade_app.main.di.MainModule;
import oysd.com.trade_app.util.DESUtil;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.MD5Utils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 登录页面 activity.
 */
public class LoginActivity extends BaseToolActivity implements LoginContract.View {

    @BindView(R.id.main_login)
    Button main_login;

    @BindView(R.id.goto_register)
    TextView goto_register;

    @BindView(R.id.goto_retrieve)
    TextView goto_retrieve;

    @BindView(R.id.et_login_name)
    EditText et_login_name;

    @BindView(R.id.tv_login_name_error)
    TextView tv_login_name_error;

    @BindView(R.id.et_login_pwd)
    EditText et_login_pwd;

    @BindView(R.id.tv_login_pwd_error)
    TextView tv_login_pwd_error;

    @Inject
    LoginContract.Presenter presenter;

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.login);
//
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        main_login.setOnClickListener(this);
        goto_register.setOnClickListener(this);
        goto_retrieve.setOnClickListener(this);
        //监听手机号输入框禁止输入空格
        et_login_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_login_name.setText(str1);
                    et_login_name.setSelection(start);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.main_login:
                    if (verificationText()) {
                        requestLogin();
                    }

                    break;
                case R.id.goto_register:
                    quickStartActivity(RegisterActivity.class);
                    this.finish();
                    break;
                case R.id.goto_retrieve:
                    quickStartActivity(ResetActivity.class);
                    break;
            }
        }
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_login_name:
                    tv_login_name_error.setText("");
                    break;
                case R.id.et_login_pwd:
                    tv_login_pwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    // 验证输入的内容是否符合要求
    private boolean verificationText() {
        boolean isOk = true;
        //登录账号
        if (EmptyUtils.isNotEmpty(et_login_name.getText().toString())) {

            if (StringUtils.isMaxLength(et_login_name.getText().toString(), 50)) {

            } else {
                isOk = false;
                tv_login_name_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_login_name_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_login_name.setOnFocusChangeListener(editTextListener);

        //登录密码
        if (!StringUtils.isEmpty(et_login_pwd.getText().toString())) {
            if (StringUtils.isMaxLength(et_login_pwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_login_pwd.getText().toString())
                        && StringUtils.isLengthMaxAndMin(et_login_pwd.getText().toString(), 3, 16)) {

                } else {
                    isOk = false;
                    tv_login_pwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                tv_login_pwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_login_pwd_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_login_pwd.setOnFocusChangeListener(editTextListener);
        return isOk;
    }


    private void requestLogin() {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("password", Utils.MD5(et_login_pwd.getText().toString()));
        params.put("loginName", et_login_name.getText().toString());
        params.put("clientType", 2);
        //params.put("timestamp", System.currentTimeMillis());
        //String i0elHQHLKqllnZeK1 = sign("I0elHQHLKqllnZeK", params);
//        //所有参数包括时间戳
//        String s = Utils.MD5(et_login_pwd.getText().toString()) + et_login_name.getText().toString() + "2" + l;
//        //所有参数+进行DES加密
//        String i0elHQHLKqllnZeK = null;
//        try {
//            i0elHQHLKqllnZeK = DESUtil.encrypt(s, "I0elHQHLKqllnZeK");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String encode = MD5Utils.encodeNotSalt(i0elHQHLKqllnZeK);
        //params.put("sign", i0elHQHLKqllnZeK1);
        //网络请求
        presenter.login(params);
    }


    /**
     * md5签名
     * <p>
     * 按参数名称升序，将参数值进行连接 签名
     *
     * @param authKey 加密密钥
     * @param params  参数
     * @return
     */
    public static String sign(String authKey, TreeMap<String, Object> params) {
        StringBuilder paramValues = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramValues.append(entry.getValue());
        }
        Logger.d("ddddddddddddd+" + paramValues);
        String md5Str = null;
        try {
            md5Str = MD5Utils.encodeNotSalt(DESUtil.encrypt(paramValues.toString(), authKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        // save data.
        if (main_login == null) return;
        InfoProvider.getInstance().saveToken(userBean.getToken());
        InfoProvider.getInstance().saveLoginInfo(userBean);
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message1));
        //隐藏键盘
        hideSoftInput(main_login);
        Global.isFirst = false;
        Global.isLogin = true;
        PreferencesUtils.saveBoolean("isFirst", Global.isFirst);
        InfoProvider.getInstance().saveLogin(true);
        setResult(RESULT_OK);
        LoginActivity.this.finish();
    }

    @Override
    protected void clickBack() {
        super.clickBack();
        Global.isLogin = false;
        InfoProvider.getInstance().saveLogin(false);
        if (!Global.isChildActivity) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Global.isChildActivity = false;
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Global.isLogin = false;
            InfoProvider.getInstance().saveLogin(false);
            Intent intetn = new Intent(LoginActivity.this, MainActivity.class);
            intetn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intetn);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void loginFailed(int code, String msg) {
        // ToastUtils.showLong(this, this.getResources().getString(R.string.message2));
        ToastUtils.showLong(this, msg);
        InfoProvider.getInstance().saveLogin(false);
    }


    @Override
    public void onBackPressed() {
        //TODO something
        super.onBackPressed();
//        InfoProvider.getInstance().saveLogin(false);
//        Global.isLogin = false;
    }


}
