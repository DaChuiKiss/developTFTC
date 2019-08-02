package oysd.com.trade_app.modules.mycenter.view;


import android.text.Editable;
import android.text.TextWatcher;
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
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferCutContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountTransferCutPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;


/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc USDT-ACN互转
 */

public class CutFragment extends LazyLoadBaseFragment implements MyAccountTransferCutContract.View {


    @BindView(R.id.tv_balance)
    TextView tv_balance;

    @BindView(R.id.tv_cut)
    TextView tv_cut;
    @BindView(R.id.tb_all_conversion)
    TextView tb_all_conversion;

    @BindView(R.id.tv_cut_0)
    TextView tv_cut_0;
    @BindView(R.id.tv_cut_1)
    TextView tv_cut_1;

    @BindView(R.id.et_coinToOTC_transferQuantity)
    EditText et_coinToOTC_transferQuantity;

    @BindView(R.id.et_coinToOTC_transferQuantity_error)
    TextView tv_coinToOTC_transferQuantity_error;

    @BindView(R.id.et_coinToOTC_cut)
    EditText et_coinToOTC_cut;

    @BindView(R.id.et_coinToOTC_cut_error)
    TextView tv_coinToOTC_cut_error;

    @BindView(R.id.et_mycenter_otc_accountpwd)
    EditText et_mycenter_otc_accountpwd;

    @BindView(R.id.et_mycenter_otc_accountpwd_error)
    TextView et_mycenter_otc_accountpwd_error;

    @BindView(R.id.bt_coinToOTC_submit)
    Button bt_coinToOTC_submit;

    MyAccountTransferCutContract.Presenter presenter = new MyAccountTransferCutPresenter(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cut;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_coinToOTC_submit.setOnClickListener(this);
        tv_cut.setOnClickListener(this);
        tb_all_conversion.setOnClickListener(this);
    }

    private boolean isTrue = false;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_coinToOTC_submit:
                    if (verificationText()) {
                        String s = et_coinToOTC_transferQuantity.getText().toString();
                        String s1 = tv_balance.getText().toString();
                        if (Double.parseDouble(s) > Double.parseDouble(s1)) {
                            ToastUtils.showShort(getActivity(), R.string.transfer_transfer_prompt);
                        } else {
                            showPasswordDialog();
                        }
                    }
                    break;
                case R.id.tv_cut:
                    //USDT 兑换 ACN时
                    if (!isTrue) {
                        presenter.getCurrentUserLoginAccount("1", "66");
                        presenter.coinExchangeRatio(66, 65);
                        tv_cut_0.setText("ACN");
                        tv_cut_1.setText("USDT");
                        isTrue = true;
                    }
                    //ACN 兑换 USDT时
                    else {
                        presenter.getCurrentUserLoginAccount("1", "65");
                        presenter.coinExchangeRatio(65, 66);
                        tv_cut_0.setText("USDT");
                        tv_cut_1.setText("ACN");
                        isTrue = false;
                    }
                    break;
                case R.id.tb_all_conversion:
                    et_coinToOTC_transferQuantity.setText(tv_balance.getText().toString());
                    break;
            }
        }
    }

    //需要传入id
    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(context, null, getResources().getString(R.string.dialogMsg5), false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                requestTransferCoinToOTC();
            }
        });
        dialog.show();
    }


    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_coinToOTC_transferQuantity:
                    tv_coinToOTC_transferQuantity_error.setText("");
                    break;
                case R.id.et_coinToOTC_cut:
                    tv_coinToOTC_cut_error.setText("");
                    break;
                case R.id.et_mycenter_otc_accountpwd:
                    et_mycenter_otc_accountpwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };


    // 验证输入的内容是否符合要求
    private boolean verificationText() {
        boolean isOk = true;
        //数量
        if (EmptyUtils.isNotEmpty(et_coinToOTC_transferQuantity.getText().toString())) {

        } else {
            isOk = false;
            tv_coinToOTC_transferQuantity_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_coinToOTC_transferQuantity.setOnFocusChangeListener(editTextListener);
        //换算
        if (EmptyUtils.isNotEmpty(et_coinToOTC_cut.getText().toString())) {

        } else {
            isOk = false;
            tv_coinToOTC_cut_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_coinToOTC_cut.setOnFocusChangeListener(editTextListener);
        //密码
        if (!StringUtils.isEmpty(et_mycenter_otc_accountpwd.getText().toString())) {
            if (StringUtils.isMaxLength(et_mycenter_otc_accountpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_mycenter_otc_accountpwd.getText().toString())
                        && StringUtils.isLengthMaxAndMin(et_mycenter_otc_accountpwd.getText().toString(), 3, 16)) {
                } else {
                    isOk = false;
                    et_mycenter_otc_accountpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                et_mycenter_otc_accountpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            et_mycenter_otc_accountpwd_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_mycenter_otc_accountpwd.setOnFocusChangeListener(editTextListener);
        return isOk;
    }


    public void requestTransferCoinToOTC() {
        Map<String, Object> params = new HashMap<>();
        params.put("password", Utils.MD5(et_mycenter_otc_accountpwd.getText().toString()));
        params.put("transferQuantity", et_coinToOTC_transferQuantity.getText().toString());
        params.put("fromCoinId", isTrue ? 66 : 65);
        params.put("toCoinId", isTrue ? 65 : 66);
        presenter.coinExchange(params);
    }

    @Override
    protected void initView(View rootView) {
        et_coinToOTC_transferQuantity.addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 4) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 5);
                    et_coinToOTC_transferQuantity.setText(s);
                    et_coinToOTC_transferQuantity.setSelection(s.length());
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    public void onResume() {
        super.onResume();
        if (!isTrue) {
            //USDT 兑换 ACN时
            presenter.getCurrentUserLoginAccount("1", "65");
            presenter.coinExchangeRatio(65, 66);
        } else {
            //ACN 兑换 USDT时
            presenter.getCurrentUserLoginAccount("1", "66");
            presenter.coinExchangeRatio(66, 65);
        }
    }

    @Override
    public void coinExchangeSuccess(EmptyBean tb) {
        ToastUtils.showLong(App.getContext(), "划转成功");
        if (getActivity() != null) {
            ((CoinChargeActivity) getActivity()).setResult(((CoinChargeActivity) getActivity()).RESULT_OK);
            ((CoinChargeActivity) getActivity()).finish();
        }
    }

    @Override
    public void coinExchangeFailed(int code, String msg) {
        Logger.d("coinExchangeFailed--->" + msg);
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void coinExchangeRatioSuccess(String tb) {
        et_coinToOTC_cut.setText(tb);
    }

    @Override
    public void coinExchangeRatioFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getCurrentUserLoginAccountSuccess(String tb) {
        tv_balance.setText(tb);
    }

    @Override
    public void getCurrentUserLoginAccountFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }


}
