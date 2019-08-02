package oysd.com.trade_app.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.contract.RegisterContract;
import oysd.com.trade_app.main.di.DaggerMainComponent;
import oysd.com.trade_app.main.di.MainModule;
import oysd.com.trade_app.modules.mycenter.view.SettingAccountPwdActivity;
import oysd.com.trade_app.modules.mycenter.view.UrlWebActivity;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class RegisterActivity extends BaseToolActivity implements RegisterContract.View {

    String json;
    private ProgressDialog mProgressDialog;

    //国家
    @BindView(R.id.tv_register_nation)
    TextView tv_register_nation;

    @BindView(R.id.emailOrPhoneId)
    TextView emailOrPhoneId;


    @BindView(R.id.ll_register_email)
    LinearLayout ll_register_email;

    //邮箱
    @BindView(R.id.et_register_email)
    EditText et_register_email;

    @BindView(R.id.tv_register_email_error)
    TextView tv_register_email_error;


    @BindView(R.id.ll_register_phone)
    LinearLayout ll_register_phone;

    //手机号码
    @BindView(R.id.et_register_phone)
    EditText et_register_phone;

    @BindView(R.id.tv_register_phone_error)
    TextView tv_register_phone_error;

    @BindView(R.id.ll_register_nation)
    LinearLayout ll_register_nation;

    @BindView(R.id.tv_register_nation_error)
    TextView tv_register_nation_error;

    //验证码
    @BindView(R.id.et_register_code)
    EditText et_register_code;

    @BindView(R.id.tv_register_code_error)
    TextView tv_register_code_error;

    //登录密码
    @BindView(R.id.et_register_pwd)
    EditText et_register_pwd;

    @BindView(R.id.tv_register_pwd_error)
    TextView tv_register_pwd_error;

    @BindView(R.id.main_register)
    Button main_register;

    @BindView(R.id.et_register_invite)
    EditText et_register_invite;

    @BindView(R.id.bt_register_getCode)
    Button bt_register_getCode;

    @BindView(R.id.tv_goto_mv)
    TextView tv_goto_mv;

    @BindView(R.id.cb_register_mv)
    CheckBox cb_register_mv;

    @Inject
    RegisterContract.Presenter presenter;
    private CountDownTimer countDownTimer;

    boolean isOnclickG3 = false;
    private GT3GeetestUtilsBind gt3GeetestUtils;
    int phoneLength;

    //    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    private static final String captchaURL = Constants.BASE_URL + "base/v1/captcha/start";//BASE_URL = "http://192.168.0.64:9121/";//本地电脑访问地址
    // 设置二次验证的URL，需替换成自己的服务器URL
//    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
    private static final String validateURL = Constants.BASE_URL + "base/v1/captcha";

    @Override
    protected void initClicks() {
        super.initClicks();
        main_register.setOnClickListener(this);
        tv_goto_mv.setOnClickListener(this);
        cb_register_mv.setChecked(true);
        // getCode 点击后开始滑块验证。
        // bt_register_getCode.setOnClickListener(this);

        //监听手机号输入框禁止输入空格
        et_register_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_register_phone.setText(str1);
                    et_register_phone.setSelection(start);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //监听邮箱输入框禁止输入空格
        et_register_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    et_register_email.setText(str1);
                    et_register_email.setSelection(start);
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
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.main_register:
                    et_register_invite.requestFocus();
                    if (verificationText()) {
                        requestRegister();
                    }
                    break;
                case R.id.tv_goto_mv:
                    jumpToWeb(getResources().getString(R.string.mvTitle), Constants.H5_URL + "/html/agreement.html");
                    break;
                default:
                    break;
            }
        }
    }

    // 跳转到指定静态页面
    private void jumpToWeb(String title, String url) {
        Intent intent = new Intent(this, UrlWebActivity.class);
        intent.putExtra(UrlWebActivity.EXTRA_URL, url);
        intent.putExtra(UrlWebActivity.EXTRA_TITLE, title);
        startActivity(intent);
    }

    private void requestRegister() {
        Map<String, Object> params = new HashMap<>();

        params.put("password", Utils.MD5(et_register_pwd.getText().toString()));
        params.put("verifyCode", et_register_code.getText().toString());
        if (ll_register_email.getVisibility() == View.VISIBLE) {
            params.put("type", 2);
            params.put("registerName", et_register_email.getText().toString());

        } else {
            params.put("type", 1);
            params.put("countryCode", countryCode);
            params.put("registerName", et_register_phone.getText().toString());
            params.put("prefix", getNationCode(tv_register_nation.getText().toString()).substring(2));
        }

        String inviteCode = et_register_invite.getText().toString();
        if (EmptyUtils.isNotEmpty(inviteCode)) {
            params.put("inviteCode", inviteCode);
        }

        presenter.register(params);
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.tv_register_nation:
                    tv_register_nation_error.setText("");
                    break;
                case R.id.et_register_email:
                    tv_register_email_error.setText("");
                    break;
                case R.id.et_register_phone:
                    tv_register_phone_error.setText("");
                    break;
                case R.id.et_register_code:
                    tv_register_code_error.setText("");
                    break;
                case R.id.et_register_pwd:
                    tv_register_pwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    //验证输入的内容是否符合要求
    public boolean verificationText() {
        boolean isOk = true;
        if (cb_register_mv.isChecked()) {
            if (ll_register_email.getVisibility() == View.VISIBLE) {
                //判断邮箱
                if (!StringUtils.isEmpty(et_register_email.getText().toString())) {
                    if (StringUtils.isMaxLength(et_register_email.getText().toString(), 50)) {
                        if (StringUtils.isEmail(et_register_email.getText().toString())) {
                            tv_register_email_error.setText("");
                        } else {
                            isOk = false;
                            tv_register_email_error.setText(getResources().getString(R.string.error_text_format));
                        }
                    } else {
                        isOk = false;
                        tv_register_email_error.setText(getResources().getString(R.string.error_text_limit50));
                    }
                } else {
                    isOk = false;
                    tv_register_email_error.setText(getResources().getString(R.string.error_text_null));
                }

                et_register_email.setOnFocusChangeListener(editTextListener);
            } else {
                //判断国家
                if (!StringUtils.isEmpty(tv_register_nation.getText().toString())) {

                } else {
                    isOk = false;
                    tv_register_email_error.setText(getResources().getString(R.string.error_text_null));
                }
                tv_register_nation.setOnFocusChangeListener(editTextListener);

                //判断手机号码
                if (!StringUtils.isEmpty(et_register_phone.getText().toString())) {
                    if (StringUtils.isMaxLength(et_register_phone.getText().toString(), 50)) {
                        if (StringUtils.isPhoneNum(et_register_phone.getText().toString(), phoneLength)) {

                        } else {
                            isOk = false;
                            tv_register_phone_error.setText(getResources().getString(R.string.error_text_format));
                        }
                    } else {
                        isOk = false;
                        tv_register_phone_error.setText(getResources().getString(R.string.error_text_limit50));
                    }
                } else {
                    isOk = false;
                    tv_register_phone_error.setText(getResources().getString(R.string.error_text_null));
                }
                et_register_phone.setOnFocusChangeListener(editTextListener);
            }
            //判断验证码
            if (!StringUtils.isEmpty(et_register_code.getText().toString())) {

            } else {
                isOk = false;
                tv_register_code_error.setText(getResources().getString(R.string.error_text_null));
            }
            if (et_register_code.getText().toString().length() >= 6) {

            } else {
                isOk = false;
                tv_register_code_error.setText(getResources().getString(R.string.error_text_length));
            }
            et_register_code.setOnFocusChangeListener(editTextListener);

            //判断登录密码
            if (!StringUtils.isEmpty(et_register_pwd.getText().toString())) {
                if (StringUtils.isMaxLength(et_register_email.getText().toString(), 50)) {
                    if (StringUtils.isContainNumAndABC(et_register_pwd.getText().toString())
                            && StringUtils.isLengthMaxAndMin(et_register_pwd.getText().toString(), 3, 16)) {

                    } else {
                        isOk = false;
                        tv_register_pwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                    }
                } else {
                    isOk = false;
                    tv_register_pwd_error.setText(getResources().getString(R.string.error_text_limit50));
                }
            } else {
                isOk = false;
                tv_register_pwd_error.setText(getResources().getString(R.string.error_text_null));
            }
            et_register_pwd.setOnFocusChangeListener(editTextListener);
        } else {
            ToastUtils.showLong(this, getResources().getString(R.string.mvIsOk));
            isOk = false;
        }
        return isOk;
    }


    @Override
    protected void initView() {
        super.initView();
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        mProgressDialog = new ProgressDialog(this);
        gt3GeetestUtils = new GT3GeetestUtilsBind(RegisterActivity.this);
    }

    @Override
    protected void initData() {
        if (Utils.isChina()) {
            tv_register_nation.setText(getResources().getString(R.string.nation_default));
            countryCode = "CN";
            phoneLength = 11;
        } else {
            tv_register_nation.setText(getResources().getString(R.string.nation_default));
            countryCode = "AU";
            phoneLength = 8;
        }

        //判断账户是不是存在
        et_register_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Log.d("12", "22222222222222");
                    tv_register_email_error.setText("");
                } else {
                    Log.d("12", "3333333333333");
                    requestExists(et_register_email.getText().toString());
                }
            }
        });


        //判断账户是不是存在
        et_register_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Log.d("12", "22222222222222");
                    tv_register_phone_error.setText("");
                } else {
                    Log.d("12", "3333333333333");
                    requestExists(et_register_phone.getText().toString());
                }
            }
        });


        bt_register_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOnclickG3 = true;
                if (ll_register_email.getVisibility() == View.VISIBLE) {
                    if (et_register_email.getText().length() <= 0) {
                        tv_register_email_error.setText(getResources().getString(R.string.email) + getResources().getString(R.string.error_text_null));
                        isOnclickG3 = false;
                        return;
                    }
                    if (!StringUtils.isEmail(et_register_email.getText().toString())) {
                        tv_register_email_error.setText(getResources().getString(R.string.email) + getResources().getString(R.string.error_text_format));
                        isOnclickG3 = false;
                        return;
                    }
                    requestExists(et_register_email.getText().toString());
                } else {
                    if (et_register_phone.getText().length() <= 0) {
                        tv_register_phone_error.setText(getResources().getString(R.string.phoneNo) + getResources().getString(R.string.error_text_null));
                        isOnclickG3 = false;
                        return;
                    }
                    requestExists(et_register_phone.getText().toString());
                }
            }

        });

        if (emailOrPhoneId.getText().toString().contains(RegisterActivity.this.getString(R.string.emailRegiest))) {
            ll_register_email.setVisibility(View.VISIBLE);
            tv_register_email_error.setVisibility(View.VISIBLE);

            ll_register_nation.setVisibility(View.GONE);
            tv_register_nation_error.setVisibility(View.GONE);

            ll_register_phone.setVisibility(View.GONE);
            tv_register_phone_error.setVisibility(View.GONE);
            emailOrPhoneId.setText(RegisterActivity.this.getString(R.string.phoneRegiest));
            setTitleText(R.string.emailRegiest);
        } else {
            ll_register_phone.setVisibility(View.GONE);
            tv_register_phone_error.setVisibility(View.GONE);
            ll_register_nation.setVisibility(View.GONE);
            tv_register_nation_error.setVisibility(View.GONE);
            ll_register_email.setVisibility(View.VISIBLE);
            tv_register_email_error.setVisibility(View.VISIBLE);
            emailOrPhoneId.setText(RegisterActivity.this.getString(R.string.emailRegiest));
            setTitleText(R.string.phoneRegiest);
        }

        tv_register_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, CountryActivity.class);
                startActivityForResult(intent, 12);
            }
        });
    }


    public void requestExists(String s) {
        presenter.isExists(s);
    }

    public void EmailOrPhone(View v) {
        if (emailOrPhoneId.getText().toString().contains(RegisterActivity.this.getString(R.string.emailRegiest))) {
            emailOrPhoneId.setText(RegisterActivity.this.getString(R.string.phoneRegiest));
            ll_register_email.setVisibility(View.VISIBLE);
            tv_register_email_error.setVisibility(View.VISIBLE);
            ll_register_phone.setVisibility(View.GONE);
            tv_register_phone_error.setVisibility(View.GONE);
            ll_register_nation.setVisibility(View.GONE);
            tv_register_nation_error.setVisibility(View.GONE);
            setTitleText(R.string.emailRegiest);

        } else {
            ll_register_nation.setVisibility(View.VISIBLE);
            tv_register_nation_error.setVisibility(View.VISIBLE);
            emailOrPhoneId.setText(RegisterActivity.this.getString(R.string.emailRegiest));
            ll_register_phone.setVisibility(View.VISIBLE);
            tv_register_phone_error.setVisibility(View.VISIBLE);
            ll_register_email.setVisibility(View.GONE);
            tv_register_email_error.setVisibility(View.GONE);
            setTitleText(R.string.phoneRegiest);

        }
    }

    public void goto_login(View v) {
        quickStartActivity(LoginActivity.class);
        this.finish();
    }

    String countryCode;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 12:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");
                    countryCode = bundle.getString("countryCode");
                    phoneLength = bundle.getInt("phoneLength");
                    tv_register_nation.setText(countryName + "(" + countryNumber + ")");
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 页面关闭时释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gt3GeetestUtils.cancelUtils();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    /**
     * 设置后，界面横竖屏不会关闭验证码，推荐设置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gt3GeetestUtils.changeDialogLayout();
    }

    @Override
    protected int setView() {
        return R.layout.activity_register;
    }

    /**
     * 开始请求验证码。
     */
    private void requestVerCode() {
        Map<String, Object> params = new HashMap<>();

        params.put("messageEnum", "1");
        if (ll_register_email.getVisibility() == View.VISIBLE) {
            params.put("type", 2);
            params.put("target", et_register_email.getText().toString());

        } else {
            params.put("type", 1);
            params.put("countryCode", countryCode);
            params.put("target", et_register_phone.getText().toString());
            params.put("prefix", getNationCode(tv_register_nation.getText().toString()).substring(2));
        }

        presenter.getVerCode(params);
        duringRequestingVerCode();
    }

    /**
     * 验证码发送后处理。
     */
    private void duringRequestingVerCode() {
        bt_register_getCode.setClickable(false);

        // 开启倒计时。
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bt_register_getCode.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                bt_register_getCode.setClickable(true);
                bt_register_getCode.setText(RegisterActivity.this.getResources().getString(R.string.code));
            }
        }.start();
    }

    // 截取国家代码。
    private static String getNationCode(@NonNull String text) {
        return text.substring(text.indexOf("("), text.indexOf(")"));
    }

    @Override
    public void getCaptchaSuccess() {
        gt3GeetestUtils.gt3TestFinish();
        requestVerCode();
    }

    @Override
    public void getCaptchaFail(int code, String msg) {
        gt3GeetestUtils.gt3TestClose();
    }

    @Override
    public void getVerCodeSuccess() {
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message5));
    }

    @Override
    public void getVerCodeFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        InfoProvider.getInstance().saveLoginInfo(userBean);
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message3));
        ActivitiesManager.getInstance().finishActivity(RegisterActivity.class);
        quickStartActivity(SettingAccountPwdActivity.class);
    }

    @Override
    public void registerFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void isExistsSuccess(Boolean flag) {
        if (flag) {
            if (ll_register_email.getVisibility() == View.VISIBLE) {
                tv_register_email_error.setText(getResources().getString(R.string.isExist_yes));
            } else if (ll_register_phone.getVisibility() == View.VISIBLE) {
                if (et_register_phone.getText().toString().length() > 0) {
                    tv_register_phone_error.setText(getResources().getString(R.string.isExist_yes));
                }
            }
        } else {
            if (isOnclickG3) {
                isOnclickG3 = false;
                gotoValidation();
            }
        }
    }


    public void gotoValidation() {
        gt3GeetestUtils.getGeetest(RegisterActivity.this, captchaURL, validateURL, null, new GT3GeetestBindListener() {
            /**
             * num 1 点击验证码的关闭按钮来关闭验证码
             * num 2 点击屏幕关闭验证码
             * num 3 点击返回键关闭验证码
             */
            @Override
            public void gt3CloseDialog(int num) {
            }


            /**
             * 验证码加载准备完成
             * 此时弹出验证码
             */
            @Override
            public void gt3DialogReady() {
            }


            /**
             * 拿到第一个url（API1）返回的数据
             * 该方法只适用于不使用自定义api1时使用
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
                // Logger.d(jsonObject.toString());
            }


            /**
             * 往API1请求中添加参数
             * 该方法只适用于不使用自定义api1时使用
             * 添加数据为Map集合
             * 添加的数据以get形式提交
             */
            @Override
            public Map<String, String> gt3CaptchaApi1() {
                Map<String, String> map = new HashMap<String, String>();
                return map;
            }

            /**
             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
             * 如果为false后续会走gt3GetDialogResult(String result)拿到api2需要的参数
             * 如果为true后续会走gt3GetDialogResult(boolean a, String result)拿到api2需要的参数
             * result为二次验证所需要的数据
             */
            @Override
            public boolean gt3SetIsCustom() {
                return true;
            }

            /**
             * 拿到第二个url（API2）需要的数据
             * 该方法只适用于不使用自定义api2时使用
             */
            @Override
            public void gt3GetDialogResult(String result) {
            }


            /**
             * 自定义二次验证，也就是当gtSetIsCustom为ture时才执行
             * 拿到第二个url（API2）需要的数据
             * 在该回调里面自行请求api2
             * 对api2的结果进行处理
             */
            @Override
            public void gt3GetDialogResult(boolean status, String result) {

                if (status) {
                    JSONObject res_json = null;
                    try {
                        res_json = new JSONObject(result);
                        Map<String, Object> validateParams = new HashMap<>();
                        validateParams.put("geetestChallenge", res_json.getString("geetest_challenge"));
                        validateParams.put("geetestValidate", res_json.getString("geetest_validate"));
                        validateParams.put("geetestSeccode", res_json.getString("geetest_seccode"));
                        presenter.captcha(validateParams);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    /**
                     * 基本使用方法：
                     *
                     * 1.取出该接口返回的三个参数用于自定义二次验证
                     * JSONObject res_json = new JSONObject(result);
                     *
                     * Map<String, String> validateParams = new HashMap<>();
                     *
                     * validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));
                     *
                     * validateParams.put("geetest_validate", res_json.getString("geetest_validate"));
                     *
                     * validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));
                     *
                     * 新加参数可以继续比如
                     *
                     * validateParams.put("user_key1", "value1");
                     *
                     * validateParams.put("user_key2", "value2");
                     *
                     * 2.自行做网络请求，请求时用上前面取出来的参数
                     *
                     * 3.拿到网络请求后的结果，判断是否成功
                     *
                     * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
                     * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
                     */
                }
            }


            /**
             * 需要做验证统计的可以打印此处的JSON数据
             * JSON数据包含了极验每一步的运行状态和结果
             */
            @Override
            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
            }

            /**
             * 往二次验证里面put数据
             * put类型是map类型
             * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             * 该方法只适用于不使用自定义api2时使用
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("testkey", "12315");
                return map;

            }

            /**
             * 二次验证完成的回调
             * 该方法只适用于不使用自定义api2时使用
             * result为俄二次验证后的数据
             * 根据二次验证返回的数据判断此次验证是否成功
             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
             */
            @Override
            public void gt3DialogSuccessResult(String result) {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String sta = jobj.getString("status");
                        if ("success".equals(sta)) {
                            gt3GeetestUtils.gt3TestFinish();
                            Log.d("234243234234", "1111111111111");
                            requestVerCode();
                        } else {
                            gt3GeetestUtils.gt3TestClose();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    gt3GeetestUtils.gt3TestClose();
                }
            }

            /**
             * 验证过程错误
             * 返回的错误码为判断错误类型的依据
             */

            @Override
            public void gt3DialogOnError(String error) {
                Log.i("dsd", "gt3DialogOnError");

            }
        });
        //设置是否可以点击屏幕边缘关闭验证码
        gt3GeetestUtils.setDialogTouch(true);
    }

    @Override
    public void isExistsFailed(int code, String msg) {

    }

}
