package oysd.com.trade_app.modules.otc.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.main.MainActivity;
import oysd.com.trade_app.modules.otc.OtcUtils;
import oysd.com.trade_app.modules.otc.bean.BankItemBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import oysd.com.trade_app.modules.otc.contract.SettingBankContract;
import oysd.com.trade_app.modules.otc.presenter.SettingBankPresenter;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PickerUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

/**
 * 设置支付方式 - CNY - E-Bank 设置。
 * Created by Liam on 2018/8/8
 */
public class SettingBankFragment
        extends LazyLoadBaseFragment implements SettingBankContract.View {

    @BindView(R.id.et_bank_name)
    EditText etName;

    // 银行列表只能进行选择,不能输入
    @BindView(R.id.tv_bank_bank)
    TextView tvBank;

    @BindView(R.id.et_bank_address)
    EditText etAddress;

    @BindView(R.id.et_bank_card)
    EditText etCard;

    @BindView(R.id.et_bank_card_confirm)
    EditText etCardConfirm;

    @BindView(R.id.et_bank_password)
    EditText etPassword;

    @BindView(R.id.et_bank_remark)
    EditText etRemark;

    @BindView(R.id.tv_bank_bank_text)
    TextView tvBankText;

    @BindView(R.id.sw_bank_using)
    CheckBox swUsing;

    @BindView(R.id.btn_bank_save)
    Button btnSave;

    @BindView(R.id.tv_bank_goto_trade)
    TextView tvGotoTrade;

    private SettingBankContract.Presenter presenter = new SettingBankPresenter(this);
    private String name, bank, address, card, cardConfirm, password, remark;
    private int status;
    private int payType;

    // 是否已经保存了设置。
    private boolean hasSaved = false;

    private List<BankItemBean> bankItemBeans = new ArrayList<>();
    private List<String> bankNames = new ArrayList<>();
    private PaymentInfoBean originalPaymentInfo;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting_bank;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected void initClicks() {
        btnSave.setOnClickListener(this);
        tvGotoTrade.setOnClickListener(this);
        tvBank.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        payType = Values.PaymentMethod.BANK;

        presenter.getBankList(null);
        presenter.getUserPaymentInfo(payType);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (Utils.isFastClick()) return;

        switch (v.getId()) {
            case R.id.btn_bank_save:
                save();
                break;

            case R.id.tv_bank_goto_trade:
                IntentUtils.startActivity(context, MainActivity.class);
//                if (hasSaved || !isContentExists()) {
//                    IntentUtils.startActivity(context, MainActivity.class);
//                } else {
//                    // show a warning dialog here
//                    // use Toast instead temporary.
//                    ToastUtils.showLong(context, R.string.toast_not_complete);
//                }
                break;

            case R.id.tv_bank_bank:
                PickerUtils.showOneOptionPicker(context, bankNames, (opt1, opt2, opt3, view) -> {
                    bank = bankNames.get(opt1);
                    tvBank.setText(bank);
                });
                break;

            default:
                break;
        }
    }

    private void save() {
        if (isErrorInputs()) return;

        getAllInputs();

        if (originalPaymentInfo == null) {
            presenter.addBankAccount(getBankParams());
        } else {
            presenter.updateBankAccount(getBankParams());
            presenter.updateBankAccountStatus(getStatusParams());
        }
    }

    private Map<String, Object> getBankParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("accountOpeningAddress", address);
        params.put("bankCardDesc", remark);
        params.put("bankCardNumber", card);
        params.put("bankCardUserName", name);
        params.put("bankName", OtcUtils.getBankCodeFromList(bankItemBeans, bank));
        params.put("bankTerritoriality", 1);
        params.put("password", Utils.MD5(password));
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
        params.put("status", status);
        return params;
    }

    /**
     * 获得所有的输入内容。
     */
    private void getAllInputs() {
        name = etName.getText().toString();
        bank = tvBank.getText().toString();
        address = etAddress.getText().toString();
        card = etCard.getText().toString();
        cardConfirm = etCardConfirm.getText().toString();
        password = etPassword.getText().toString();
        remark = etRemark.getText().toString();
        status = swUsing.isChecked() ? 1 : 0;
    }

    /**
     * 检查所有 EditText ，是否还有内容存在。
     *
     * @return true 表示有内容存在；false 没有。
     */
    private boolean isContentExists() {
        return ViewHelper.isContentExists(etName, etAddress, etCard, etCardConfirm,
                etPassword, etRemark);
    }

    /**
     * 输入是否有误.
     *
     * @return true 表示输入有误；false 输入全部正常.
     */
    private boolean isErrorInputs() {
        return ViewHelper.isEditEmpty(context, etName, etAddress, etCard, etCardConfirm,
                etPassword)
                || !ViewHelper.isRemarkLessThan20(context, etRemark)
                || !ViewHelper.isTwiceEntrySame(context, etCard, etCardConfirm);
    }

    @Override
    public void getBankListSuccess(List<BankItemBean> response) {
        this.bankItemBeans = response;
        this.bankNames = OtcUtils.getBankNames(response);
    }

    @Override
    public void getBankListFailed(int code, String msg) {
        Logger.e(msg);
    }

    @Override
    public void getUserPaymentInfoSuccess(PaymentInfoBean response) {
        if (response == null) return;

        originalPaymentInfo = response;

        // update view.
        etName.setText(response.getBankCardUserName());
        tvBank.setText(OtcUtils.getBankNameFromList(bankItemBeans, response.getBankName()));
        etAddress.setText(response.getAccountOpeningAddress());
        etCard.setText(response.getBankCardNumber());
        etCardConfirm.setText(response.getBankCardNumber());
        etRemark.setText(response.getBankCardDesc());
        swUsing.setChecked(response.getStatus() == 1);
    }

    @Override
    public void getUserPaymentInfoFailed(int code, String msg) {
        Logger.e(msg);
    }

    @Override
    public void addBankAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_add_bank_suc);

        // 新增成功后重新取一次数据
        presenter.getUserPaymentInfo(payType);
    }

    @Override
    public void addBankAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateBankAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_bank_suc);
    }

    @Override
    public void updateBankAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateBankStatusSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_bank_status_suc);
    }

    @Override
    public void updateBankStatusFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
