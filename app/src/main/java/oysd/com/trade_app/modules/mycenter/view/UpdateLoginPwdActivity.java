package oysd.com.trade_app.modules.mycenter.view;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.contract.UpdateLoginPwdContract;
import oysd.com.trade_app.modules.mycenter.presenter.UpdateLoginPwdPresenter;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class UpdateLoginPwdActivity extends BaseToolActivity implements UpdateLoginPwdContract.View {

    @BindView(R.id.et_update_loginpwd_oldpwd)
    EditText et_update_loginpwd_oldpwd;
    @BindView(R.id.tv_update_loginpwd_oldpwd_error)
    TextView tv_update_loginpwd_oldpwd_error;

    @BindView(R.id.et_update_loginpwd_newpwd)
    EditText et_update_loginpwd_newpwd;
    @BindView(R.id.tv_update_loginpwd_newpwd_error)
    TextView tv_update_loginpwd_newpwd_error;

    @BindView(R.id.et_update_loginpwd_againpwd)
    EditText et_update_loginpwd_againpwd;
    @BindView(R.id.tv_update_loginpwd_againpwd_error)
    TextView tv_update_loginpwd_againpwd_error;

    @BindView(R.id.et_update_loginpwd_code)
    EditText et_update_loginpwd_code;
    @BindView(R.id.tv_update_loginpwd_code_error)
    TextView tv_update_loginpwd_code_error;

    @BindView(R.id.bt_update_loginpwd_submit)
    Button bt_update_loginpwd_submit;

    @BindView(R.id.getCode)
    Button getCode;


    CountDownTimer countDownTimer;
    UpdateLoginPwdContract.Presenter presenter = new UpdateLoginPwdPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_update_loginpwd;
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_update_loginpwd_oldpwd:
                    tv_update_loginpwd_oldpwd_error.setText("");
                    break;

                case R.id.et_update_loginpwd_newpwd:
                    tv_update_loginpwd_newpwd_error.setText("");
                    break;

                case R.id.et_update_loginpwd_againpwd:
                    tv_update_loginpwd_againpwd_error.setText("");
                    break;
                case R.id.et_update_loginpwd_code:
                    tv_update_loginpwd_code_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_update_loginpwd_submit.setOnClickListener(this);
        getCode.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.updateLoginPwd);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_update_loginpwd_submit:
                    if (verificationText()) {
                        requestUpdateLoginPwd();
                    }
                    break;
                case R.id.getCode:
                    requestGetVerCode();
                    break;
            }
        }
    }


    //请求修改登录接口

    protected void requestUpdateLoginPwd() {

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())) {

            params.put("dealPassword", Utils.MD5(et_update_loginpwd_oldpwd.getText().toString()));
            params.put("messageEnum", "7");
            params.put("newLoginPassword", Utils.MD5(et_update_loginpwd_newpwd.getText().toString()));
            params.put("confirmLoginPassword", Utils.MD5(et_update_loginpwd_againpwd.getText().toString()));
            params.put("email", InfoProvider.getInstance().getLoginInfo().getNickName());
            params.put("verCode", et_update_loginpwd_code.getText().toString());
        } else {
            params.put("dealPassword", Utils.MD5(et_update_loginpwd_oldpwd.getText().toString()));
            params.put("messageEnum", "7");
            params.put("newLoginPassword", Utils.MD5(et_update_loginpwd_newpwd.getText().toString()));
            params.put("confirmLoginPassword", Utils.MD5(et_update_loginpwd_againpwd.getText().toString()));
            params.put("phone", InfoProvider.getInstance().getLoginInfo().getNickName());
            params.put("prefix", InfoProvider.getInstance().getLoginInfo().getPrefix());
            params.put("verCode", et_update_loginpwd_code.getText().toString());
        }
        presenter.updateLoginPwd(params);
    }


    //验证输入的内容是否符合要求
    public boolean verificationText() {

        boolean isOK = true;
        //旧密码
        if (!StringUtils.isEmpty(et_update_loginpwd_oldpwd.getText().toString())) {

            if (StringUtils.isMaxLength(et_update_loginpwd_oldpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_update_loginpwd_oldpwd.getText().toString()) && StringUtils.isLengthMaxAndMin(et_update_loginpwd_oldpwd.getText().toString(), 6, 16)) {

                } else {
                    isOK = false;
                    tv_update_loginpwd_oldpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOK = false;
                tv_update_loginpwd_oldpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOK = false;
            tv_update_loginpwd_oldpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_update_loginpwd_oldpwd.setOnFocusChangeListener(editTextListener);


        //新密码
        if (!StringUtils.isEmpty(et_update_loginpwd_newpwd.getText().toString())) {

            if (StringUtils.isMaxLength(et_update_loginpwd_newpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_update_loginpwd_newpwd.getText().toString()) && StringUtils.isLengthMaxAndMin(et_update_loginpwd_newpwd.getText().toString(), 6, 16)) {

                } else {
                    isOK = false;
                    tv_update_loginpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOK = false;
                tv_update_loginpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOK = false;
            tv_update_loginpwd_newpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_update_loginpwd_newpwd.setOnFocusChangeListener(editTextListener);


        //确认密码
        if (!StringUtils.isEmpty(et_update_loginpwd_againpwd.getText().toString())) {

            if (StringUtils.isMaxLength(et_update_loginpwd_againpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_update_loginpwd_againpwd.getText().toString()) && StringUtils.isLengthMaxAndMin(et_update_loginpwd_againpwd.getText().toString(), 6, 16)) {
                    if (StringUtils.isEquals(et_update_loginpwd_againpwd.getText().toString(), et_update_loginpwd_newpwd.getText().toString())) {

                    } else {
                        isOK = false;
                        tv_update_loginpwd_againpwd_error.setText(getResources().getString(R.string.error_text_inconsistent_password));
                    }
                } else {
                    isOK = false;
                    tv_update_loginpwd_againpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOK = false;
                tv_update_loginpwd_againpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOK = false;
            tv_update_loginpwd_againpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_update_loginpwd_againpwd.setOnFocusChangeListener(editTextListener);


        //验证码
        if (!StringUtils.isEmpty(et_update_loginpwd_code.getText().toString())) {

        } else {
            isOK = false;
            tv_update_loginpwd_code_error.setText(getResources().getString(R.string.error_text_null));
        }
        tv_update_loginpwd_code_error.setOnFocusChangeListener(editTextListener);
        return isOK;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public void requestGetVerCode() {
        if (StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())) {
            Map<String, Object> params = new HashMap<>();
            params.put("messageEnum", "5");
            params.put("type", 2);
            params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());
            presenter.getVerCode(params);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("messageEnum", "5");
            params.put("type", 1);
            //params.put("countryCode",InfoProvider.getInstance().getLoginInfo().getCountryCode());
            params.put("prefix", InfoProvider.getInstance().getLoginInfo().getPrefix());
            params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());
            presenter.getVerCode(params);
        }
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
    public void updateLoginPwdSuccess() {
//        ActivitiesManager.getInstance().finishActivity(SafeActivity.class);
        SafeActivity.activity.finish();
        finish();
        Global.isLogin = false;
        InfoProvider.getInstance().saveLogin(false);
        quickStartActivity(LoginActivity.class);


    }

    @Override
    public void updateLoginPwdFailed(int code, String msg) {

        ToastUtils.showLong(this, msg);

    }

    @Override
    public void getVerCodeSuccess() {

    }

    @Override
    public void getVerCodeFailed(int code, String msg) {

    }
}
