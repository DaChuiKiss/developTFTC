package oysd.com.trade_app.modules.mycenter.view;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.BuildConfig;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.contract.UpdateAccountPwdContract;
import oysd.com.trade_app.modules.mycenter.presenter.UpdateAccountPwdPresenter;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class UpdateAccountPwdActivity extends BaseToolActivity implements UpdateAccountPwdContract.View{

    @BindView(R.id.et_update_accountpwd_old_pwd)
    EditText et_update_accountpwd_cert_old_pwd;
    @BindView(R.id.tv_update_accountpwd_cert_error)
    TextView tv_update_accountpwd_cert_error;

    @BindView(R.id.et_update_accountpwd_code)
    EditText et_update_accountpwd_code;
    @BindView(R.id.tv_update_accountpwd_code_error)
    TextView tv_update_accountpwd_code_error;

    @BindView(R.id.et_update_accountpwd_newpwd)
    EditText et_update_accountpwd_newpwd;
    @BindView(R.id.tv_update_accountpwd_newpwd_error)
    TextView tv_update_accountpwd_newpwd_error;

    @BindView(R.id.et_update_accountpwd_cofigpwd)
    EditText et_update_accountpwd_cofigpwd;
    @BindView(R.id.tv_update_accountpwd_cofigpwd_error)
    TextView tv_update_accountpwd_cofigpwd_error;

    @BindView(R.id.bt_update_accountpwd_submit)
    Button bt_update_accountpwd_submit;

    @BindView(R.id.getCode)
    Button getCode;

    CountDownTimer countDownTimer;
    UpdateAccountPwdContract.Presenter presenter =new UpdateAccountPwdPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_update_accountpwd;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_update_accountpwd_submit.setOnClickListener(this);
        getCode.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.updateAccountPwd);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_update_accountpwd_submit:
                    if (verificationText()) {
                        requestUpdateDealPwd();
                    }

                    break;

                case R.id.getCode:
                    requestGetVerCode();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
    }

    public void requestGetVerCode(){

        if(StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())){
            Map<String, Object> params = new HashMap<>();
            params.put("messageEnum", "7");
            params.put("type", 2);
            params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());
            presenter.getVerCode(params);
        }else{
            Map<String, Object> params = new HashMap<>();
            params.put("messageEnum", "7");
            params.put("type", 1);
//            params.put("countryCode",InfoProvider.getInstance().getLoginInfo().getCountryCode());
            params.put("prefix",InfoProvider.getInstance().getLoginInfo().getPrefix());
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
        countDownTimer = new CountDownTimer(60000, 1000){
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



    public void requestUpdateDealPwd(){

        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())){
            params.put("type", 2);

        }else{
//            params.put("prefix", InfoProvider.getInstance().getLoginInfo().getPrefix());
            params.put("type", 1);
        }
        params.put("confirmDealPassword", Utils.MD5(et_update_accountpwd_cofigpwd.getText().toString()));
        params.put("newDealPassword", Utils.MD5(et_update_accountpwd_newpwd.getText().toString()));
        params.put("verifyCode", et_update_accountpwd_code.getText().toString());
        params.put("resetName", InfoProvider.getInstance().getLoginInfo().getNickName());
        params.put("orgDealPassword",Utils.MD5(et_update_accountpwd_cert_old_pwd.getText().toString()));
        presenter.updateDealPwd(params);
    }


    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_update_accountpwd_old_pwd:
                    tv_update_accountpwd_cert_error.setText("");
                    break;

                case R.id.et_update_accountpwd_code:
                    tv_update_accountpwd_code_error.setText("");
                    break;

                case R.id.et_update_accountpwd_newpwd:
                    tv_update_accountpwd_newpwd_error.setText("");
                    break;
                case R.id.et_update_accountpwd_cofigpwd:
                    tv_update_accountpwd_cofigpwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    //验证输入的内容是否符合要求
    public boolean verificationText(){
        boolean isOk=true;

        //身份证
        if(!StringUtils.isEmpty(et_update_accountpwd_cert_old_pwd.getText().toString())){
            if(StringUtils.isMaxLength(et_update_accountpwd_newpwd.getText().toString(),50)){
                if(StringUtils.isContainNumAndABC(et_update_accountpwd_newpwd.getText().toString())&&StringUtils.isLengthMaxAndMin(et_update_accountpwd_newpwd.getText().toString(),6,16)){

                }else{
                    isOk=false;
                    tv_update_accountpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            }else{
                isOk=false;
                tv_update_accountpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }

        }else{
            isOk=false;
            tv_update_accountpwd_cert_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_update_accountpwd_cert_old_pwd.setOnFocusChangeListener(editTextListener);


        //验证码
        if(!StringUtils.isEmpty(et_update_accountpwd_code.getText().toString())){

        }else{
            isOk=false;
            tv_update_accountpwd_code_error.setText(getResources().getString(R.string.error_text_null));
        }

        tv_update_accountpwd_code_error.setOnFocusChangeListener(editTextListener);


        //新密码
        if(!StringUtils.isEmpty(et_update_accountpwd_newpwd.getText().toString())){

            if(StringUtils.isMaxLength(et_update_accountpwd_newpwd.getText().toString(),50)){
                if(StringUtils.isContainNumAndABC(et_update_accountpwd_newpwd.getText().toString())&&StringUtils.isLengthMaxAndMin(et_update_accountpwd_newpwd.getText().toString(),6,16)){

                }else{
                    isOk=false;
                    tv_update_accountpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            }else{
                isOk=false;
                tv_update_accountpwd_newpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        }else{
            isOk=false;
            tv_update_accountpwd_newpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_update_accountpwd_newpwd.setOnFocusChangeListener(editTextListener);


        //确认密码
        if(!StringUtils.isEmpty(et_update_accountpwd_cofigpwd.getText().toString())){

            if(StringUtils.isMaxLength(et_update_accountpwd_cofigpwd.getText().toString(),50)){
                if(StringUtils.isContainNumAndABC(et_update_accountpwd_cofigpwd.getText().toString())&&StringUtils.isLengthMaxAndMin(et_update_accountpwd_newpwd.getText().toString(),6,16)){
                    if(StringUtils.isEquals(et_update_accountpwd_cofigpwd.getText().toString(),et_update_accountpwd_newpwd.getText().toString())){

                    }else{
                        isOk=false;
                        tv_update_accountpwd_cofigpwd_error.setText(getResources().getString(R.string.error_text_inconsistent_password));
                    }
                }else{
                    isOk=false;
                    tv_update_accountpwd_cofigpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            }else{
                isOk=false;
                tv_update_accountpwd_cofigpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        }else{
            isOk=false;
            tv_update_accountpwd_cofigpwd_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_update_accountpwd_newpwd.setOnFocusChangeListener(editTextListener);
        return isOk;

    }

    @Override
    public void updateDealPwdSuccess() {
        finish();
        ToastUtils.showLong(App.getContext(),getResources().getString(R.string.message13));

    }

    @Override
    public void updateDealPwdFailed(int code ,String msg) {
        ToastUtils.showLong(App.getContext(),msg);
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
