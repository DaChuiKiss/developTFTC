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
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PickerUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

/**
 * 设置支付方式 - USD - USD 设置。
 * Created by Liam on 2018/8/8
 */
public class SettingUsdFragment
        extends LazyLoadBaseFragment implements SettingBankContract.View {

    @BindView(R.id.et_usd_name)
    EditText etName;

    @BindView(R.id.tv_usd_bank_name_text)
    TextView tvBankNameText;

    @BindView(R.id.tv_usd_bank_name)
    TextView tvBankName;

    @BindView(R.id.et_usd_card)
    EditText etCard;

    @BindView(R.id.et_usd_card_confirm)
    EditText etCardConfirm;

    @BindView(R.id.et_usd_open_address)
    EditText etOpenAddress;

//    @BindView(R.id.et_usd_swift)
//    EditText etSwift;

    @BindView(R.id.et_usd_password)
    EditText etPassword;

    @BindView(R.id.et_usd_remark)
    EditText etRemark;

    @BindView(R.id.openPatternLocker)
    CheckBox checkbox;

    @BindView(R.id.btn_usd_save)
    Button btnSave;

    @BindView(R.id.tv_usd_goto_trade)
    TextView tvGotoTrade;

    SettingBankContract.Presenter presenter = new SettingBankPresenter(this);
    private String name, bankName, openAddress, card, cardConfirm, swift, password, remark;
    private int status;
    private int payType;

    private boolean hasSaved = false;

    private List<BankItemBean> bankItemBeans = new ArrayList<>();
    private List<String> bankNames = new ArrayList<>();
    private PaymentInfoBean originalPaymentInfo;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting_usd;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected void initData() {
        payType = Values.PaymentMethod.USD;
        presenter.getBankList(null);
        presenter.getUserPaymentInfo(payType);
    }

    @Override
    protected void initClicks() {
        btnSave.setOnClickListener(this);
        tvGotoTrade.setOnClickListener(this);
        tvBankName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_usd_save:
                save();
                break;

            case R.id.tv_usd_goto_trade:
                IntentUtils.startActivity(context, MainActivity.class);

//                if (hasSaved || !isContentExists()) {
//                    IntentUtils.startActivity(context, MainActivity.class);
//                } else {
//                    // show a warning dialog here
//                    // use Toast instead temporary.
//                    ToastUtils.showLong(context, R.string.toast_not_complete);
//                }
                break;

            case R.id.tv_usd_bank_name:
                PickerUtils.showOneOptionPicker(context, bankNames, (opt1, opt2, opt3, view) -> {
                    bankName = bankNames.get(opt1);
                    tvBankName.setText(bankName);
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
        params.put("accountOpeningAddress", openAddress);
        params.put("bankCardDesc", remark);
        params.put("bankCardNumber", card);
        params.put("bankCardUserName", name);
        params.put("bankName", OtcUtils.getBankCodeFromList(bankItemBeans, bankName));
        params.put("bankTerritoriality", 2);
        params.put("password", Utils.MD5(password));
        params.put("status", status);
        params.put("swiftNo", swift);

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
        bankName = tvBankName.getText().toString();
        openAddress = etOpenAddress.getText().toString();
        card = etCard.getText().toString();
        cardConfirm = etCardConfirm.getText().toString();
        //swift = etSwift.getText().toString();
        password = etPassword.getText().toString();
        remark = etRemark.getText().toString();
        status = checkbox.isChecked() ? 1 : 0;
    }

    /**
     * 检查所有 EditText 是否有 输入中 的内容。
     *
     * @return true 有内容；false 无内容。
     */
    //去掉etSwift
    private boolean isContentExists() {
        return ViewHelper.isContentExists(etName, etOpenAddress, etCard, etCardConfirm,
                 etPassword);
    }

    /**
     * 检查所有输入是否有误.
     *
     * @return true 输入有误；false 输入无误。
     */
    //去掉etSwift
    private boolean isErrorInputs() {
        return ViewHelper.isEditEmpty(context, etName, etOpenAddress, etCard,
                etCardConfirm, etPassword)
                || EmptyUtils.isEmpty(tvBankName.getText())
                || !ViewHelper.isTwiceEntrySame(context, etCard, etCardConfirm)
                || !ViewHelper.isRemarkLessThan20(context, etRemark);
    }

    @Override
    public void getBankListSuccess(List<BankItemBean> response) {
        this.bankItemBeans = response;
        this.bankNames = OtcUtils.getBankNames(response);
    }

    @Override
    public void getBankListFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }
    //去掉etSwift
    @Override
    public void getUserPaymentInfoSuccess(PaymentInfoBean response) {
        if (response == null) return;

        originalPaymentInfo = response;

        // update view.
        etName.setText(response.getBankCardUserName());
        tvBankName.setText(OtcUtils.getBankNameFromList(bankItemBeans, response.getBankName()));
        etOpenAddress.setText(response.getAccountOpeningAddress());
        etCard.setText(response.getBankCardNumber());
        etCardConfirm.setText(response.getBankCardNumber());
//        etSwift.setText(response.getSwiftNo());
        etRemark.setText(response.getBankCardDesc());
        checkbox.setChecked(response.getStatus() == 1);
    }

    @Override
    public void getUserPaymentInfoFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void addBankAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_add_usd_suc);

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
        ToastUtils.showLong(context, R.string.toast_update_usd_suc);
    }

    @Override
    public void updateBankAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateBankStatusSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_usd_status_suc);
    }

    @Override
    public void updateBankStatusFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
