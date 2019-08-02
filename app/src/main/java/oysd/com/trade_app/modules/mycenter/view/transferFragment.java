package oysd.com.trade_app.modules.mycenter.view;

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
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.contract.MyAccountTransferContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyAccountTransferPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.StringUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

public class transferFragment extends LazyLoadBaseFragment implements MyAccountTransferContract.View{



    @BindView(R.id.et_coinToOTC_transferQuantity)
    EditText et_coinToOTC_transferQuantity;

    @BindView(R.id.bt_coinToOTC_submit)
    Button bt_coinToOTC_submit;

    @BindView(R.id.tv_transfer_total)
    TextView tv_transfer_total;

    @BindView(R.id.tv_transfer_freeze)
    TextView tv_transfer_freeze;

    @BindView(R.id.tv_transfer_useable)
    TextView tv_transfer_useable;

    @BindView(R.id.et_coinToOTC_transferQuantity_error)
     TextView tv_coinToOTC_transferQuantity_error;

    @BindView(R.id.et_mycenter_otc_accountpwd)
    EditText et_mycenter_otc_accountpwd;

    @BindView(R.id.et_mycenter_otc_accountpwd_error)
     TextView et_mycenter_otc_accountpwd_error;

    LegalTenderBean ltb;
    MyAccountTransferContract.Presenter presenter =new MyAccountTransferPresenter(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.transfer_layout;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_coinToOTC_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_coinToOTC_submit:
                    if (verificationText()) {
                        showPasswordDialog();
                    }
                    break;
            }
        }
    }

    //需要传入id
    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(context,null,getResources().getString(R.string.dialogMsg5),false,false,R.style.dialog);
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
        //登录账号
        if (EmptyUtils.isNotEmpty(et_coinToOTC_transferQuantity.getText().toString())) {

        } else {
            isOk = false;
            tv_coinToOTC_transferQuantity_error.setText(getResources().getString(R.string.error_text_null));
        }

        et_coinToOTC_transferQuantity.setOnFocusChangeListener(editTextListener);

        //登录密码
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


   public void  requestTransferCoinToOTC(){
       Map<String, Object> params = new HashMap<>();
       params.put("coinId",ltb.getCoinId());
       params.put("direction", 1);
       params.put("transferQuantity", et_coinToOTC_transferQuantity.getText().toString());
       params.put("password", Utils.MD5(et_mycenter_otc_accountpwd.getText().toString()));
       presenter.c2cTransferAccounts(params);
    }

    @Override
    protected void initView(View rootView) {
         ltb = ((CoinChargeActivity) getActivity()).getLtb();
        tv_transfer_useable.setText(Utils.myAccountFormat(ltb.getBalance()));
        tv_transfer_freeze.setText(Utils.myAccountFormat(ltb.getFreeze()));
        tv_transfer_total.setText(Utils.myAccountFormat(ltb.getBalance().add(ltb.getFreeze())));
    }

    @Override
    public void c2cTransferAccountsSuccess(EmptyBean tb) {
        ToastUtils.showLong(App.getContext(),"划转成功");
        if(getActivity()!=null) {
            ((CoinChargeActivity) getActivity()).setResult( ((CoinChargeActivity) getActivity()).RESULT_OK);
            ((CoinChargeActivity) getActivity()).finish();
        }
    }

    @Override
    public void c2cTransferAccountsFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(),msg);
    }
}
