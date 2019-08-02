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
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.ExtractVirtualCoinParamBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.contract.ExtractVirtualCoinParamContract;
import oysd.com.trade_app.modules.mycenter.presenter.ExtractVirtualCoinParamPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

public class withdrawFragment extends LazyLoadBaseFragment implements ExtractVirtualCoinParamContract.View {

    @BindView(R.id.tv_withdraw_balance)
    TextView tv_withdraw_balance;

    @BindView(R.id.tv_withdraw_freeze)
    TextView tv_withdraw_freeze;

    @BindView(R.id.tv_withdraw_takeOutCost)
    TextView tv_withdraw_takeOutCost;

    @BindView(R.id.tv_withdraw_takeOutLeastNumberOrtakeOutMaxNumber)
    TextView tv_withdraw_takeOutLeastNumberOrtakeOutMaxNumber;

    @BindView(R.id.tv_withdraw_total)
    TextView tv_withdraw_total;

    @BindView(R.id.tv_withdraw_usableAmount)
    TextView tv_withdraw_usableAmount;

    @BindView(R.id.et_withdraw_takeInMinValue)
    EditText et_withdraw_takeInMinValue;

    @BindView(R.id.bt_withdraw_getcode)
    Button bt_withdraw_getcode;
    @BindView(R.id.bt_withdraw_submit)
    Button bt_withdraw_submit;


    @BindView(R.id.et_withdraw_code)
    EditText et_withdraw_code;

    @BindView(R.id.et_withdraw_address)
    EditText et_withdraw_address;

    @BindView(R.id.et_withdraw_dealPassword)
    EditText et_withdraw_dealPassword;

    @BindView(R.id.tv_withdraw_address_error)
    TextView tv_withdraw_address_error;

    @BindView(R.id.tv_withdraw_code_error)
    TextView tv_withdraw_code_error;

    @BindView(R.id.tv_withdraw_dealPassword_error)
    TextView tv_withdraw_dealPassword_error;

    @BindView(R.id.mycenter_withdraw_id)
    TextView mycenter_withdraw_id;


    LegalTenderBean ltb;
    CountDownTimer countDownTimer;
    ExtractVirtualCoinParamContract.Presenter presenter = new ExtractVirtualCoinParamPresenter(this);


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Logger.d(" ---> onDestroyView");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.withdraw_layout;
    }

    @Override
    protected void initView(View rootView) {
        ltb = ((CoinChargeActivity) getActivity()).getLtb();
        tv_withdraw_usableAmount.setText(Utils.myAccountFormat(ltb.getBalance()));
        tv_withdraw_balance.setText(Utils.myAccountFormat(ltb.getBalance()));
        tv_withdraw_freeze.setText(Utils.myAccountFormat(ltb.getFreeze()));
        tv_withdraw_total.setText(Utils.myAccountFormat(ltb.getBalance()));
        presenter.getExtractVirtualCoinParam(ltb.getCoinId());
        //获取验证码
        bt_withdraw_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastClick()) {
                    requestVerCode();
                }
            }
        });
        //提交
        bt_withdraw_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificationText()) {
                    showPasswordDialog();
                }
            }
        });

    }

    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(context, null, getResources().getString(R.string.dialogMsg2), false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                Map<String, Object> params = new HashMap<>();
                params.put("address", et_withdraw_address.getText().toString());
                params.put("amount", et_withdraw_takeInMinValue.getText().toString());
                params.put("coinId", ltb.getCoinId());
                params.put("dealPassword", Utils.MD5(et_withdraw_dealPassword.getText().toString()));
//                params.put("messageEnum", "4");
//                if (StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())) {
//                    params.put("type", 2);
//                } else {
//                    params.put("prefix", InfoProvider.getInstance().getLoginInfo().getPrefix());
//                    params.put("type", 1);
//                }
//                params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());
                params.put("verifyCode", et_withdraw_code.getText().toString());
                presenter.sponsorTakeOutCoinApply(params);
            }
        });
        dialog.show();
    }


    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_withdraw_address:
                    tv_withdraw_address_error.setText("");
                    break;
                case R.id.et_withdraw_code:
                    tv_withdraw_code_error.setText("");
                    break;
                case R.id.et_withdraw_dealPassword:
                    tv_withdraw_dealPassword_error.setText("");
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
        if (EmptyUtils.isNotEmpty(et_withdraw_address.getText().toString())) {
        } else {
            isOk = false;
            tv_withdraw_address_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_withdraw_address.setOnFocusChangeListener(editTextListener);
        //验证码
        if (EmptyUtils.isNotEmpty(et_withdraw_code.getText().toString())) {
        } else {
            isOk = false;
            tv_withdraw_code_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_withdraw_code.setOnFocusChangeListener(editTextListener);

        //登录密码
        if (!StringUtils.isEmpty(et_withdraw_dealPassword.getText().toString())) {
            if (StringUtils.isMaxLength(et_withdraw_dealPassword.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_withdraw_dealPassword.getText().toString())
                        && StringUtils.isLengthMaxAndMin(et_withdraw_dealPassword.getText().toString(), 3, 16)) {

                } else {
                    isOk = false;
                    tv_withdraw_dealPassword_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                tv_withdraw_dealPassword_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_withdraw_dealPassword_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_withdraw_dealPassword.setOnFocusChangeListener(editTextListener);


        return isOk;
    }


    /**
     * 开始请求验证码。
     */
    private void requestVerCode() {
        Map<String, Object> params = new HashMap<>();

        params.put("messageEnum", "4");
        if (StringUtils.isEmail(InfoProvider.getInstance().getLoginInfo().getNickName())) {
            params.put("type", 2);
            params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());

        } else {
            params.put("type", 1);
            params.put("target", InfoProvider.getInstance().getLoginInfo().getNickName());
            params.put("prefix", InfoProvider.getInstance().getLoginInfo().getPrefix());
        }

        presenter.getVerCode(params);
        duringRequestingVerCode();
    }


    /**
     * 验证码发送后处理。
     */
    private void duringRequestingVerCode() {
        bt_withdraw_getcode.setClickable(false);

        // 开启倒计时。
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bt_withdraw_getcode.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                bt_withdraw_getcode.setClickable(true);
                bt_withdraw_getcode.setText(withdrawFragment.this.getResources().getString(R.string.code));
            }
        }.start();
    }

    @Override
    public void getExtractVirtualCoinParamSuccess(ExtractVirtualCoinParamBean eb) {
        tv_withdraw_takeOutCost.setText(EmptyUtils.getBigDecimalValue(eb.getTakeOutCost()));
        tv_withdraw_takeOutLeastNumberOrtakeOutMaxNumber.setText(getString(R.string.limit) + EmptyUtils.getBigDecimalValue(eb.getTakeOutLeastNumber()) + "-" + EmptyUtils.getBigDecimalValue(eb.getTakeOutMaxNumber()));
        et_withdraw_takeInMinValue.setHint(getActivity().getResources().getString(R.string.takeInMinValue) + FormatUtils.toX(EmptyUtils.getBigDecimalValue(eb.getTakeOutLeastNumber()), eb.getTakeOutDecimals()));
        mycenter_withdraw_id.setText(eb.getTakeOutReminder());
    }

    @Override
    public void getExtractVirtualCoinParamFailed(int code, String msg) {
    }

    @Override
    public void sponsorTakeOutCoinApplySuccess(EmptyBean wb) {

        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message15));
        if (getActivity() != null) {
            ((CoinChargeActivity) getActivity()).finish();
        }
    }

    @Override
    public void sponsorTakeOutCoinApplyFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getVerCodeSuccess() {
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message5));
    }

    @Override
    public void getVerCodeFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), this.getResources().getString(R.string.message6));
    }

}
