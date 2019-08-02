package oysd.com.trade_app.modules.otc.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.main.MainActivity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import oysd.com.trade_app.modules.otc.contract.SettingNetAccountContract;
import oysd.com.trade_app.modules.otc.presenter.SettingNetAccountPresenter;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

/**
 * 设置支付方式 - USD - Paypal/E-pay 设置。
 * Created by Liam on 2018/8/8
 */
public class SettingUsdPeFragment
        extends LazyLoadBaseFragment implements SettingNetAccountContract.View {

    public static final String EXTRA_TYPE = "type";
    private String type;

    @BindView(R.id.et_usd_pe_name)
    EditText etName;

    @BindView(R.id.et_usd_pe_account)
    EditText etAccount;

    @BindView(R.id.et_usd_pe_password)
    EditText etPassword;

    @BindView(R.id.tv_usd_pe_account_text)
    TextView tvAccountText;

    @BindView(R.id.sw_usd_pe_using)
    CheckBox swUsing;

    @BindView(R.id.btn_usd_pe_save)
    Button btnSave;

    @BindView(R.id.tv_usd_pe_goto_trade)
    TextView tvGotoTrade;

    SettingNetAccountContract.Presenter presenter = new SettingNetAccountPresenter(this);
    private String name, account, password;
    private int status;
    private int payType;

    private boolean hasSaved = false;
    private PaymentInfoBean originalPaymentInfo;


    public static SettingUsdPeFragment newInstance(@NonNull String type) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);

        SettingUsdPeFragment fragment = new SettingUsdPeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting_usd_pe;
    }

    @Override
    protected void initView(View rootView) {
        if (bundle == null || bundle.getString(EXTRA_TYPE) == null) {
            throw new IllegalArgumentException("must set type of SettingUsdPeFragment.");
        }

        type = bundle.getString(EXTRA_TYPE);
        payType = type.equals(getString(R.string.paypal)) ?
                Values.PaymentMethod.PAYPAL : Values.PaymentMethod.EPAY;

        tvAccountText.setText(context.getString(R.string.net_account_2, type));
        etAccount.setHint(context.getString(R.string.hint_net_account, type));
        btnSave.setText(context.getString(R.string.save_net_setting, type));
    }

    @Override
    protected void initClicks() {
        btnSave.setOnClickListener(this);
        tvGotoTrade.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter.getUserPaymentInfo(payType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_usd_pe_save:
                save();
                break;

            case R.id.tv_usd_pe_goto_trade:
                IntentUtils.startActivity(context, MainActivity.class);
                /*if (hasSaved || !isContentExists()) {
                    IntentUtils.startActivity(context, MainActivity.class);
                } else {
                    // show a warning dialog here
                    // use Toast instead temporary.
                    ToastUtils.showLong(context, R.string.toast_not_complete);
                }*/
                break;

            default:
                break;
        }
    }

    private void save() {
        if (isErrorInputs()) return;

        getAllInputs();

        if (originalPaymentInfo == null) {
            presenter.addNetAccount(getNetAccountParams());
        } else {
            presenter.updateNetAccount(getNetAccountParams());
            presenter.updateNetAccountStatus(getStatusParams());
        }
    }

    private Map<String, Object> getNetAccountParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("accountNumber", account);
        params.put("accountUserName", name);
        params.put("password", Utils.MD5(password));
        params.put("receivablesType", payType);
        params.put("status", status);

        if (originalPaymentInfo != null) {
            params.put("id", originalPaymentInfo.getId());
        }

        return params;
    }

    private Map<String, Object> getStatusParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", originalPaymentInfo != null ? originalPaymentInfo.getId() : 0);
        params.put("password", Utils.MD5(password));
        params.put("status", swUsing.isChecked() ? 1 : 0);
        return params;
    }

    /**
     * 获得所有的输入内容。
     */
    private void getAllInputs() {
        name = etName.getText().toString();
        account = etAccount.getText().toString();
        password = etPassword.getText().toString();
        status = swUsing.isChecked() ? 1 : 0;
    }

    /**
     * 检查是否还有输入中的内容.
     *
     * @return true 有输入中的内容；false 无输入中的内容。
     */
    private boolean isContentExists() {
        return ViewHelper.isContentExists(etName, etAccount, etPassword);
    }

    /**
     * 检查所有的输入是否有误.
     *
     * @return true 输入有误；false 输入无误。
     */
    private boolean isErrorInputs() {
        return ViewHelper.isEditEmpty(context, etName, etAccount, etPassword);
    }

    @Override
    public void getUserPaymentInfoSuccess(PaymentInfoBean response) {
        if (response == null) return;

        originalPaymentInfo = response;

        etName.setText(response.getAccountUserName());
        etAccount.setText(response.getAccountNumber());
        swUsing.setChecked(response.getStatus() == 1);
    }

    @Override
    public void getUserPaymentInfoFailed(int code, String msg) {
        Logger.e(payType + " code = " + code + " , msg = " + msg);
    }

    @Override
    public void addNetAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_add_account_suc);
    }

    @Override
    public void addNetAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateNetAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_account_suc);
    }

    @Override
    public void updateNetAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateNetAccountStatusSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_account_status_suc);
    }

    @Override
    public void updateNetAccountStatusFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void uploadImageSuccess(ImageBean imageBean) {
    }

    @Override
    public void uploadImageFailed(int code, String msg) {
    }


}
