package oysd.com.trade_app.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.contract.RestPwdContract;
import oysd.com.trade_app.main.presenter.ResetPwdPresenter;
import oysd.com.trade_app.main.view.UserView;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class ResetActivity extends BaseToolActivity implements RestPwdContract.View {

    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.et_reset_newpwd)
    EditText et_reset_newpwd;

    @BindView(R.id.tv_reset_nation_error)
    TextView tv_reset_nation_error;

    //国家
    @BindView(R.id.tv_reset_nation)
    TextView tv_reset_nation;

    @BindView(R.id.tv_reset_newpwd_error)
    TextView tv_reset_newpwd_error;

    @BindView(R.id.et_reset_cofigpwd)
    EditText et_reset_cofigpwd;

    @BindView(R.id.tv_reset_cofigpwd_error)
    TextView tv_reset_cofigpwd_error;

    @BindView(R.id.et_retrieve_name)
    EditText et_retrieve_name;
    @BindView(R.id.tv_retrieve_name_error)
    TextView tv_retrieve_name_error;

    @BindView(R.id.et_retrieve_code)
    EditText et_retrieve_code;
    @BindView(R.id.tv_retrieve_code_error)
    TextView tv_retrieve_code_error;

    @BindView(R.id.getCode)
    Button getCode;

    boolean isGetCode = false;

    private CountDownTimer countDownTimer;
    int phoneLength = 11;
    String countryCode;
    RestPwdContract.Presenter presenter = new ResetPwdPresenter(this);

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.resetPwd);
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        reset.setOnClickListener(this);
        tv_reset_nation.setOnClickListener(this);
        getCode.setOnClickListener(this);

    }

    @Override
    protected int setView() {
        return R.layout.activity_reset;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.reset:
                    if (verificationText()) {
                        requestResetPwd();
                    }
                    break;
                case R.id.tv_reset_nation:
                    Intent intent = new Intent();
                    intent.setClass(ResetActivity.this, CountryActivity.class);
                    startActivityForResult(intent, 12);
                    break;
                case R.id.getCode:
                    isGetCode = true;
                    //判断手机号
                    if (!StringUtils.isEmpty(et_retrieve_name.getText().toString())) {
                        requestExists();
                    } else {
                        tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_null));
                        et_retrieve_name.setOnFocusChangeListener(editTextListener);
                    }
                    break;

            }
        }
    }

    //获取验证码之前需要先判断账号是否符合
    public boolean getCodeVerification() {
        boolean isOk = true;

        //判断国家
        if (!StringUtils.isEmpty(tv_reset_nation.getText().toString())) {

        } else {
            isOk = false;
            tv_reset_nation_error.setText(getResources().getString(R.string.error_text_null));
        }
        tv_reset_nation.setOnFocusChangeListener(editTextListener);

        //账号
        if (!StringUtils.isEmpty(et_retrieve_name.getText().toString())) {

            if (StringUtils.isMaxLength(et_retrieve_name.getText().toString(), 50)) {

                if (StringUtils.isEmail(et_retrieve_name.getText().toString())) {

                } else if (et_retrieve_name.getText().toString().length() == phoneLength) {

                } else {
                    isOk = false;
                    tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_format));
                }


            } else {
                isOk = false;
                tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_retrieve_name.setOnFocusChangeListener(editTextListener);
        return isOk;
    }


    private void requestVerCode() {
        Gson gson = new Gson();

        Map<String, Object> params = new HashMap<>();
        params.put("messageEnum", "5");


        if (StringUtils.isEmail(et_retrieve_name.getText().toString())) {
            params.put("type", 2);
            params.put("target", et_retrieve_name.getText().toString());
        } else {
            params.put("type", 1);
            params.put("countryCode", countryCode);
            params.put("target", et_retrieve_name.getText().toString());
            params.put("prefix", getNationCode(tv_reset_nation.getText().toString()).substring(2));
        }

        presenter.getVerCode(params);
        duringRequestingVerCode();
    }

    /**
     * 验证码发送后处理。
     */
    private void duringRequestingVerCode() {
        getCode.setClickable(false);

        // 开启倒计时。
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCode.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                getCode.setClickable(true);
                getCode.setText(getResources().getString(R.string.code));
            }
        }.start();
    }

    @Override
    protected void initData() {
        super.initData();
        isGetCode = false;
        if (Utils.isChina()) {
            tv_reset_nation.setText(getResources().getString(R.string.nation_default));
            countryCode = "CN";
        } else {
            tv_reset_nation.setText(getResources().getString(R.string.nation_default));
            countryCode = "AU";
        }
        //判断账户是不是存在
        et_retrieve_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    Log.d("12", "22222222222222");
                    tv_retrieve_name_error.setText("");
                } else {
                    Log.d("12", "3333333333333");
                    requestExists();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 12:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");
                    phoneLength = bundle.getInt("phoneLength");
                    countryCode = bundle.getString("countryCode");
                    tv_reset_nation.setText(countryName + "(" + countryNumber + ")");

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void requestResetPwd() {

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isEmail(et_retrieve_name.getText().toString())) {
            params.put("resetName", et_retrieve_name.getText().toString());
            params.put("type", 2);
            params.put("verifyCode", et_retrieve_code.getText().toString());
            params.put("password", Utils.MD5(et_reset_cofigpwd.getText().toString()));
        } else {
            params.put("resetName", et_retrieve_name.getText().toString());
            params.put("type", 1);
            params.put("countryCode", countryCode);
            params.put("verifyCode", et_retrieve_code.getText().toString());
            params.put("prefix", getNationCode(tv_reset_nation.getText().toString()).substring(2));
            params.put("password", Utils.MD5(et_reset_cofigpwd.getText().toString()));
        }

        presenter.resetPwd(params);
    }


    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.tv_reset_nation:
                    tv_reset_nation_error.setText("");
                    break;
                case R.id.et_reset_newpwd:
                    tv_reset_newpwd_error.setText("");
                    break;
                case R.id.et_reset_cofigpwd:
                    tv_reset_cofigpwd_error.setText("");
                    break;
                case R.id.et_retrieve_code:
                    tv_retrieve_code_error.setText("");
                    break;
                default:
                    break;
            }

        }
    };


    public void requestExists() {

        presenter.isExists(et_retrieve_name.getText().toString());
    }

    //验证输入的内容是否符合要求
    public Boolean verificationText() {
        boolean isOk = true;
        //判断国家
        if (!StringUtils.isEmpty(tv_reset_nation.getText().toString())) {

        } else {
            isOk = false;
            tv_reset_nation_error.setText(getResources().getString(R.string.error_text_null));
        }
        tv_reset_nation.setOnFocusChangeListener(editTextListener);

        //账号
        if (!StringUtils.isEmpty(et_retrieve_name.getText().toString())) {

            if (StringUtils.isMaxLength(et_retrieve_name.getText().toString(), 50)) {

                if (StringUtils.isEmail(et_retrieve_name.getText().toString())) {

                } else if (et_retrieve_name.getText().toString().length() == phoneLength) {

                } else {
                    isOk = false;
                    tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_format));
                }


            } else {
                isOk = false;
                tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_retrieve_name_error.setText(getResources().getString(R.string.error_text_null));
        }


        //验证码
        if (!StringUtils.isEmpty(et_retrieve_code.getText().toString())) {

        } else {
            isOk = false;
            tv_retrieve_code_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_retrieve_code.setOnFocusChangeListener(editTextListener);

        //重置新密码
        if (!StringUtils.isEmpty(et_reset_newpwd.getText().toString())) {

            if (StringUtils.isMaxLength(et_reset_newpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_reset_cofigpwd.getText().toString()) && StringUtils.isLengthMaxAndMin(et_reset_cofigpwd.getText().toString(), 3, 16)) {

                } else {
                    isOk = false;
                    tv_reset_newpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                tv_reset_newpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_reset_newpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_reset_newpwd.setOnFocusChangeListener(editTextListener);

        //重置确认密码
        if (!StringUtils.isEmpty(et_reset_cofigpwd.getText().toString())) {
            if (StringUtils.isMaxLength(et_reset_cofigpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_reset_cofigpwd.getText().toString()) && StringUtils.isLengthMaxAndMin(et_reset_cofigpwd.getText().toString(), 3, 16)) {
                    if (StringUtils.isEquals(et_reset_cofigpwd.getText().toString(), et_reset_newpwd.getText().toString())) {

                    } else {
                        isOk = false;
                        tv_reset_cofigpwd_error.setText(getResources().getString(R.string.error_text_inconsistent_password));
                    }
                } else {
                    isOk = false;
                    tv_reset_cofigpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                tv_reset_cofigpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_reset_cofigpwd_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_reset_cofigpwd.setOnFocusChangeListener(editTextListener);
        return isOk;
    }

    @Override
    public void resetPwdSuccess() {
        ActivitiesManager.getInstance().finishActivity();
//        quickStartActivity(LoginActivity.class);
    }

    @Override
    public void resetPwdFail(int code, String msg) {

        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getVerCodeSuccess() {
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message5));
        // 开启倒计时。
    }

    @Override
    public void getVerCodeFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void isExistsSuccess(Boolean flag) {
        if (!flag) {
            tv_retrieve_name_error.setText(getResources().getString(R.string.isExist_no));
        } else {
            if (isGetCode) {
                isGetCode = false;
                if (getCodeVerification()) {
                    requestVerCode();
                }
            }
        }
    }

    @Override
    public void isExistsFailed(int code, String msg) {

        ToastUtils.showLong(App.getContext(), msg);
    }

    private static String getNationCode(@NonNull String text) {
        return text.substring(text.indexOf("("), text.indexOf(")"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
