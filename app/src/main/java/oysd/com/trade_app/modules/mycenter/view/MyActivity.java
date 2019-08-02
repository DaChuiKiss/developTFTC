package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.GetAuthInfoContract;
import oysd.com.trade_app.modules.mycenter.presenter.GetAuthInfoPresenter;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class MyActivity extends BaseToolActivity implements GetAuthInfoContract.View {


    @BindView(R.id.goto_certification)
    RelativeLayout goto_certification;

    @BindView(R.id.tv_mycenter_auth)
    TextView tv_mycenter_auth;

    @BindView(R.id.tv_mycenter_auth_realName)
     TextView tv_mycenter_auth_realName;

    @BindView(R.id.tv_mycenter_auth_account)
    TextView tv_mycenter_auth_account;

    @BindView(R.id.tv_mycenter_auth_countryName)
    TextView tv_mycenter_auth_countryName;

    @BindView(R.id.tv_mycenter_auth_certNumber)
    TextView tv_mycenter_auth_certNumber;

    @BindView(R.id.rl_mycenter_auth_gotoSenior)
    RelativeLayout rl_mycenter_auth_gotoSenior;

    @BindView(R.id.tv_mycenter_auth_Senior)
    TextView tv_mycenter_auth_Senior;

    GetAuthInfoContract.Presenter presenter =new GetAuthInfoPresenter(this);

    @Override
    protected int setView() {
        return R.layout.my_activity;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        goto_certification.setOnClickListener(this);
        rl_mycenter_auth_gotoSenior.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.goto_certification:
                    quickStartActivityResult(CertificationStep1.class, 1);
                    break;
                case R.id.rl_mycenter_auth_gotoSenior:
                    if (tv_mycenter_auth.getText().equals(getResources().getString(R.string.auth_no))) {
                        ToastUtils.showLong(this, this.getResources().getString(R.string.message11));
                    } else {
                        quickStartActivityResult(CertificationStep2.class, 2);
                    }
                    break;
            }
        }
    }

    @Override
    protected void initData() {
        requestAuthInfo();
    }

    public void requestAuthInfo(){
        presenter.getAuthInfo();
    }

    @Override
    public void getAuthInfoSuccess(AuthInfoBean authInfoBean) {

        Drawable nav_up = getResources().getDrawable(R.mipmap.goto_next);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if(tv_mycenter_auth!=null) {
            if (authInfoBean.getVerifyRealStatus() == 0) {
                tv_mycenter_auth.setTextColor(getResources().getColor(R.color.text_red));
                tv_mycenter_auth.setText(getResources().getString(R.string.auth_no));
                tv_mycenter_auth.setCompoundDrawables(null, null, nav_up, null);
                goto_certification.setEnabled(true);
                if (authInfoBean.getAdvancedStatus() == 0) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_no));
                    tv_mycenter_auth_Senior.setCompoundDrawables(null, null, nav_up, null);
                    rl_mycenter_auth_gotoSenior.setEnabled(true);
                } else if (authInfoBean.getAdvancedStatus() == 1) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_ing));
                    rl_mycenter_auth_gotoSenior.setEnabled(false);
                } else if (authInfoBean.getAdvancedStatus() == 2) {
                    tv_mycenter_auth.setTextColor(getResources().getColor(R.color.text_pink));
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_pink));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_success));
                    rl_mycenter_auth_gotoSenior.setEnabled(false);
                } else if (authInfoBean.getAdvancedStatus() == 3) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_fail));
                    tv_mycenter_auth_Senior.setCompoundDrawables(null, null, getResources().getDrawable(R.mipmap.goto_next), null);
                    rl_mycenter_auth_gotoSenior.setEnabled(true);
                }
            } else if (authInfoBean.getVerifyRealStatus() == 1) {
                tv_mycenter_auth.setTextColor(getResources().getColor(R.color.text_pink));
                tv_mycenter_auth.setText(getResources().getString(R.string.auth_ok));
                tv_mycenter_auth_realName.setText(authInfoBean.getRealName());
                tv_mycenter_auth_account.setText(authInfoBean.getNickName() + "");
                tv_mycenter_auth_countryName.setText(authInfoBean.getCountryName());
//                String s;
//                if(authInfoBean.getIdentityNumber().length()>=10) {
//                    s = authInfoBean.getIdentityNumber().replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*");
//                }else if (authInfoBean.getIdentityNumber().length()>=3){
//                    s = authInfoBean.getIdentityNumber().replaceAll("(?<=[\\d]{1})\\d(?=[\\d]{1})", "*");
//                }else{
//                    s="***";
//                }
                tv_mycenter_auth_certNumber.setText(authInfoBean.getIdentityNumber());

                goto_certification.setEnabled(false);
                InfoProvider.getInstance().setCert(true);
                if (authInfoBean.getAdvancedStatus() == 0) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_no));
                    tv_mycenter_auth_Senior.setCompoundDrawables(null, null, nav_up, null);
                    rl_mycenter_auth_gotoSenior.setEnabled(true);
                } else if (authInfoBean.getAdvancedStatus() == 1) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_ing));
                    rl_mycenter_auth_gotoSenior.setEnabled(false);
                } else if (authInfoBean.getAdvancedStatus() == 2) {
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_pink));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_success));
                    InfoProvider.getInstance().setSeniorCert(true);
                    rl_mycenter_auth_gotoSenior.setEnabled(false);
                } else if (authInfoBean.getAdvancedStatus() == 3) {
                    tv_mycenter_auth.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setTextColor(getResources().getColor(R.color.text_red));
                    tv_mycenter_auth_Senior.setText(getResources().getString(R.string.auth_fail));
                    rl_mycenter_auth_gotoSenior.setEnabled(true);
                }
            }
        }
    }
    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type5);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestAuthInfo();
    }

    @Override
    public void getAuthInfoFailed(int code,String msg) {
            ToastUtils.showLong(App.getContext(),msg);
    }
}
