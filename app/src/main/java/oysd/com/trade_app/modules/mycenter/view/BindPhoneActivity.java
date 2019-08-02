package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.CountryActivity;
import oysd.com.trade_app.main.RegisterActivity;
import oysd.com.trade_app.modules.mycenter.contract.BindPhoneContract;
import oysd.com.trade_app.modules.mycenter.presenter.BindPhonePresenter;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class BindPhoneActivity extends BaseToolActivity implements BindPhoneContract.View {

    @BindView(R.id.tv_bind_nation)
    TextView tv_bind_nation;
    @BindView(R.id.tv_bind_nation_error)
    TextView tv_bind_nation_error;

    @BindView(R.id.et_bind_phone)
    EditText et_bind_phone;
    @BindView(R.id.tv_bind_phone_error)
    TextView tv_bind_phone_error;

   @BindView(R.id.et_bind_phone_code)
    EditText et_bind_phone_code;
   @BindView(R.id.tv_bind_phone_code_error)
    TextView tv_bind_phone_code_error;


    @BindView(R.id.et_bind_phone_pwd)
    EditText et_bind_phone_pwd;
    @BindView(R.id.tv_bind_phone_pwd_error)
    TextView tv_bind_phone_pwd_error;

   @BindView(R.id.bind_phone_submit)
    Button bind_phone_submit;

   @BindView(R.id.nationLL)
    LinearLayout nationLL;

   @BindView(R.id.getCode)
    Button getCode;

    private  CountDownTimer countDownTimer;

    BindPhoneContract.Presenter  presenter =new BindPhonePresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bind_phone_submit.setOnClickListener(this);
        getCode.setOnClickListener(this);
        nationLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.bind_phone_submit:
                    if (verificationText()) {
                        requestBindPhone();
                    }
                    break;
                case R.id.getCode:
                    requestVerCode();
                    duringRequestingVerCode();
                    break;

                case R.id.nationLL:
                    Intent intent = new Intent();
                    intent.setClass(BindPhoneActivity.this, CountryActivity.class);
                    startActivityForResult(intent, 12);
                    break;
            }
        }
    }


    private void requestVerCode() {
        Gson gson  = new Gson();

        Map<String, Object> params = new HashMap<>();
        params.put("messageEnum", "3");
        params.put("type", 1);
        params.put("countryCode",countryCode);
        params.put("target", et_bind_phone.getText().toString());
        params.put("prefix", getNationCode(tv_bind_nation.getText().toString()).substring(2));

        presenter.getVerCode(params);
        duringRequestingVerCode();
    }


    @Override
    protected void onDestroy() {
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
        super.onDestroy();

    }

    @Override
    protected  void clickBack(){
        super.clickBack();
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
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
                    countryCode=bundle.getString("countryCode");
                    tv_bind_nation.setText(countryName + "(" + countryNumber + ")");
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 验证码发送后处理。
     */
    private void duringRequestingVerCode() {
        getCode.setClickable(false);

        // 开启倒计时。
        countDownTimer = new CountDownTimer(60000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                if(getCode!=null) {
                    getCode.setText(String.valueOf(millisUntilFinished / 1000));
                }else{
                    if(countDownTimer!=null){
                        countDownTimer.cancel();
                        countDownTimer=null;
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

    private static String getNationCode(@NonNull String text) {
        return text.substring(text.indexOf("("), text.indexOf(")"));
    }

    public void requestBindPhone(){
        Map<String, Object> params = new HashMap<>();
        params.put("phone", et_bind_phone.getText().toString());
        params.put("prefix", getNationCode(tv_bind_nation.getText().toString()).substring(2));
        params.put("messageEnum", "3");
        params.put("countryCode",countryCode);
        params.put("dealPassword", Utils.MD5(et_bind_phone_pwd.getText().toString()));
        params.put("verCode", et_bind_phone_code.getText().toString());
        presenter.bindPhone(params);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.bindPhone);
        countryCode="CN";
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.tv_bind_nation:
                    tv_bind_nation_error.setText("");
                    break;

                case R.id.et_bind_phone:
                    tv_bind_phone_error.setText("");
                    break;
                case R.id.et_bind_phone_code:
                    tv_bind_phone_code_error.setText("");
                    break;
                case R.id.et_bind_phone_pwd:
                    tv_bind_phone_pwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    //验证输入的内容是否符合要求
    public boolean  verificationText(){

        boolean isOK=true;
        //国家
        if(!StringUtils.isEmpty(tv_bind_nation.getText().toString())){

        }else{
            isOK=false;
            tv_bind_nation_error.setText(getResources().getString(R.string.error_text_null));
        }
        tv_bind_nation.setOnFocusChangeListener(editTextListener);


        //账号
        if(!StringUtils.isEmpty(et_bind_phone.getText().toString())){

            if(StringUtils.isMaxLength(et_bind_phone.getText().toString(),50)){
                if(StringUtils.isPhoneNum(et_bind_phone.getText().toString(),11)){

                }else{
                    isOK=false;
                    tv_bind_phone_error.setText(getResources().getString(R.string.error_text_format));
                }
            }else{
                isOK=false;
                tv_bind_phone_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        }else{
            isOK=false;
            tv_bind_phone_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_bind_phone.setOnFocusChangeListener(editTextListener);

        //验证码
        if(!StringUtils.isEmpty(et_bind_phone_code.getText().toString())){

        }else{
            isOK=false;
            tv_bind_phone_code_error.setText(getResources().getString(R.string.error_text_null));
        }
        tv_bind_phone_code_error.setOnFocusChangeListener(editTextListener);


        //密码
        if(!StringUtils.isEmpty(et_bind_phone_pwd.getText().toString())){

            if(StringUtils.isMaxLength(et_bind_phone_pwd.getText().toString(),50)){
                if(StringUtils.isContainNumAndABC(et_bind_phone_pwd.getText().toString())){

                }else{
                    isOK=false;
                    tv_bind_phone_pwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            }else{
                isOK=false;
                tv_bind_phone_pwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        }else{
            isOK=false;
            tv_bind_phone_pwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_bind_phone_pwd.setOnFocusChangeListener(editTextListener);

        return isOK;
    }

    @Override
    public void bindPhoneSuncess() {
        ToastUtils.showLong(this,getResources().getString(R.string.message12));
        Intent intet =new Intent();
        intet.putExtra("phone",et_bind_phone.getText().toString());
        setResult(RESULT_OK,intet);
        ActivitiesManager.getInstance().finishActivity();
    }

    @Override
    public void bindPhoneFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(),msg);
    }

    @Override
    public void getVerCodeSuccess() {
        ToastUtils.showShort(App.getContext(),getResources().getString(R.string.message5));
    }

    @Override
    public void getVerCodeFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(),msg);
    }
}
