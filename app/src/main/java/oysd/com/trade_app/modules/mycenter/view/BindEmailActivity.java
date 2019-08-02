package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.mycenter.contract.BindEmailContract;
import oysd.com.trade_app.modules.mycenter.presenter.BindEmailPresenter;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class BindEmailActivity extends BaseToolActivity implements BindEmailContract.View {


    @BindView(R.id.et_bind_email)
    EditText et_bind_email;
    @BindView(R.id.tv_bind_email_error)
    TextView tv_bind_email_error;

    @BindView(R.id.et_bind_code)
    EditText et_bind_code;
    @BindView(R.id.tv_bind_code_error)
    TextView tv_bind_code_error;

    @BindView(R.id.et_bind_email_pwd)
    EditText et_bind_email_pwd;
    @BindView(R.id.tv_bind_email_pwd_error)
    TextView tv_bind_email_pwd_error;

    @BindView(R.id.bt_bind_submit)
    Button bt_bind_submit;
    @BindView(R.id.getCode)
    Button getCode;
    private CountDownTimer countDownTimer;
    BindEmailContract.Presenter presenter = new BindEmailPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_bind_email;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_bind_submit.setOnClickListener(this);
        getCode.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.bindEmail);
    }

    @Override
    protected void clickBack() {
        super.clickBack();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.bt_bind_submit:
                    if (verificationText()) {
                        requestBindEmail();
                    }
                    break;
                case R.id.getCode:
                    if (TextUtils.isEmpty(et_bind_email.getText().toString())) {
                        tv_bind_email_error.setText(getResources().getString(R.string.error_text_null));
                    } else if (!StringUtils.isEmail(et_bind_email.getText().toString())) {
                        tv_bind_email_error.setText(getResources().getString(R.string.email) + getResources().getString(R.string.error_text_format));
                    } else {
                        requestVerCode();
                    }
                    break;

            }
        }
    }

    public void requestBindEmail() {
        Map<String, Object> params = new HashMap<>();
        params.put("email", et_bind_email.getText().toString());
        params.put("messageEnum", "3");
        params.put("dealPassword", Utils.MD5(et_bind_email_pwd.getText().toString()));
        params.put("verCode", et_bind_code.getText().toString());
        presenter.bindPhone(params);
    }

    private void requestVerCode() {
        Map<String, Object> params = new HashMap<>();
        params.put("messageEnum", "3");
        params.put("type", 2);
        params.put("target", et_bind_email.getText().toString());
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
                if (getCode != null) {
                    getCode.setText(String.valueOf(millisUntilFinished / 1000));

                } else {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                }
            }

            @Override
            public void onFinish() {
                getCode.setClickable(true);
                getCode.setText(getResources().getString(R.string.code));
            }
        }.start();
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_bind_email:
                    tv_bind_email_error.setText("");
                    break;
                case R.id.et_bind_code:
                    tv_bind_code_error.setText("");
                    break;
                case R.id.et_bind_email_pwd:
                    tv_bind_email_pwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //验证输入的内容是否符合要求
    public boolean verificationText() {
        boolean isOK = true;
        //账号
        if (!StringUtils.isEmpty(et_bind_email.getText().toString())) {

            if (StringUtils.isMaxLength(et_bind_email.getText().toString(), 50)) {
                if (StringUtils.isEmail(et_bind_email.getText().toString())) {

                } else {
                    isOK = false;
                    tv_bind_email_error.setText(getResources().getString(R.string.error_text_format));
                }
            } else {
                isOK = false;
                tv_bind_email_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOK = false;
            tv_bind_email_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_bind_email.setOnFocusChangeListener(editTextListener);


        //密码
        if (!StringUtils.isEmpty(et_bind_email_pwd.getText().toString())) {

            if (StringUtils.isMaxLength(et_bind_email_pwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_bind_email_pwd.getText().toString())) {

                } else {
                    isOK = false;
                    tv_bind_email_pwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOK = false;
                tv_bind_email_pwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOK = false;
            tv_bind_email_pwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_bind_email_pwd.setOnFocusChangeListener(editTextListener);

        //验证码
        if (!StringUtils.isEmpty(et_bind_code.getText().toString())) {

        } else {
            isOK = false;
            tv_bind_code_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_bind_code.setOnFocusChangeListener(editTextListener);
        return isOK;
    }

    @Override
    public void bindEmailSuncess() {
        ToastUtils.showLong(App.getContext(), getResources().getString(R.string.message12));
        Intent intet = new Intent();
        intet.putExtra("email", et_bind_email.getText().toString());
        setResult(RESULT_OK, intet);
        ActivitiesManager.getInstance().finishActivity();

    }

    @Override
    public void bindEmailFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }

    @Override
    public void getVerCodeSuccess() {
        ToastUtils.showShort(App.getContext(), getResources().getString(R.string.message5));
    }

    @Override
    public void getVerCodeFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }

}
