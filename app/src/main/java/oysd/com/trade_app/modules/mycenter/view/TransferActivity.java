package oysd.com.trade_app.modules.mycenter.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferContract;
import oysd.com.trade_app.modules.mycenter.contract.TransferContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountTransferPresenter;
import oysd.com.trade_app.modules.mycenter.presenter.TransferPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 划转到通证账户
 */
public class TransferActivity extends BaseToolActivity implements TransferContract.View {

    @BindView(R.id.bt_OTCtoCoin_submit)
    Button bt_OTCtoCoin_submit;
    @BindView(R.id.et_OTCToCoin_transferQuantity)
    EditText et_OTCToCoin_transferQuantity;
    @BindView(R.id.et_mycenter_otcToCoin_accountpwd)
    EditText et_mycenter_otcToCoin_accountpwd;
    @BindView(R.id.tv_transferOTC_total)
    TextView tv_transferOTC_total;
    @BindView(R.id.tv_transferOTC_freeze)
    TextView tv_transferOTC_freeze;
    @BindView(R.id.tv_transferOTC_useable)
    TextView tv_transferOTC_useable;
    @BindView(R.id.tv_OTCTOCoin_accountpwd_error)
    TextView tv_OTCTOCoin_accountpwd_error;
    @BindView(R.id.tv_OTCTOCoin_transferQuantity_error)
    TextView tv_OTCTOCoin_transferQuantity_error;


    VoucherBean voucherBean;
    TransferContract.Presenter presenter = new TransferPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.transfer_to_pass_card_account);
        voucherBean = this.getIntent().getParcelableExtra("VoucherBean");
        tv_transferOTC_useable.setText(FormatUtils.to8NoComma(voucherBean.getUsableAmount()));
        tv_transferOTC_freeze.setText(FormatUtils.to8NoComma(voucherBean.getFreezeAmount()));
        tv_transferOTC_total.setText(FormatUtils.to8NoComma(Double.parseDouble(voucherBean.getUsableAmount()) + Double.parseDouble(voucherBean.getFreezeAmount())));
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_OTCtoCoin_submit.setOnClickListener(this);
    }

    // 验证输入的内容是否符合要求
    private boolean verificationText() {
        boolean isOk = true;

        if (EmptyUtils.isNotEmpty(et_OTCToCoin_transferQuantity.getText().toString())) {
        } else {
            isOk = false;
            tv_OTCTOCoin_transferQuantity_error.setText(getResources().getString(R.string.error_text_null));
        }
        double v = Double.parseDouble(FormatUtils.to8NoComma(et_OTCToCoin_transferQuantity.getText().toString()));
        double b = Double.parseDouble(FormatUtils.to8NoComma(voucherBean.getUsableAmount()));
        if (b>=v) {
        } else {
            isOk = false;
            tv_OTCTOCoin_transferQuantity_error.setText(getResources().getString(R.string.error_text_exceed));
        }


        et_OTCToCoin_transferQuantity.setOnFocusChangeListener(editTextListener);

        //登录密码
        if (!StringUtils.isEmpty(et_mycenter_otcToCoin_accountpwd.getText().toString())) {
            if (StringUtils.isMaxLength(et_mycenter_otcToCoin_accountpwd.getText().toString(), 50)) {
                if (StringUtils.isContainNumAndABC(et_mycenter_otcToCoin_accountpwd.getText().toString())
                        && StringUtils.isLengthMaxAndMin(et_mycenter_otcToCoin_accountpwd.getText().toString(), 3, 16)) {

                } else {
                    isOk = false;
                    tv_OTCTOCoin_accountpwd_error.setText(getResources().getString(R.string.error_text_limit6_16));
                }
            } else {
                isOk = false;
                tv_OTCTOCoin_accountpwd_error.setText(getResources().getString(R.string.error_text_limit50));
            }
        } else {
            isOk = false;
            tv_OTCTOCoin_accountpwd_error.setText(getResources().getString(R.string.error_text_null));
        }
        et_mycenter_otcToCoin_accountpwd.setOnFocusChangeListener(editTextListener);
        return isOk;
    }

    public View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) return;
            switch (view.getId()) {
                case R.id.et_coinToOTC_transferQuantity:
                    tv_OTCTOCoin_transferQuantity_error.setText("");
                    break;
                case R.id.et_mycenter_otc_accountpwd:
                    tv_OTCTOCoin_accountpwd_error.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick(v)) {
            switch (v.getId()) {
                case R.id.bt_OTCtoCoin_submit:
                    if (verificationText()) {
                        showPasswordDialog();
                    }
                    break;
            }
        }
    }

    //需要传入id
    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(this, null, getResources().getString(R.string.dialogMsg5), false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                requestTransferCoinToOTC();
            }
        });
        dialog.show();
    }

    public void requestTransferCoinToOTC() {
        Map<String, Object> params = new HashMap<>();
        params.put("certificateId", voucherBean.getCertificateId());
//        params.put("direction", 2);
        params.put("transferQuantity", et_OTCToCoin_transferQuantity.getText().toString());
        params.put("password", Utils.MD5(et_mycenter_otcToCoin_accountpwd.getText().toString()));
        presenter.CertificateTransferAccounts(params);
    }

    @Override
    public void CertificateTransferAccountsSuccess(EmptyBean tb) {
        ToastUtils.showLong(this, "划转成功");
        this.finish();
    }

    @Override
    public void CertificateTransferAccountsFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }
}
