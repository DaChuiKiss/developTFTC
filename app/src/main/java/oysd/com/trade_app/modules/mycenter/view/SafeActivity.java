package oysd.com.trade_app.modules.mycenter.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.SafeContract;
import oysd.com.trade_app.modules.mycenter.presenter.SafePresenter;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.Utils;


public class SafeActivity extends BaseToolActivity implements SafeContract.View{


    @BindView(R.id.openPatternLocker)
    CheckBox openPatternLocker;

    @BindView(R.id.safe_rl_1)
    RelativeLayout safe_rl_1;
    @BindView(R.id.safe_rl_2)
    RelativeLayout safe_rl_2;
    @BindView(R.id.safe_rl_3)
    RelativeLayout safe_rl_3;
    @BindView(R.id.safe_rl_4)
    RelativeLayout safe_rl_4;
    @BindView(R.id.safe_rl_5)
    RelativeLayout safe_rl_5;

    @BindView(R.id.safe_rl_6)
    RelativeLayout safe_rl_6;

    @BindView(R.id.tv_mycenter_safe_phone)
    TextView tv_mycenter_safe_phone;

    @BindView(R.id.tv_mycenter_safe_email)
    TextView tv_mycenter_safe_email;

    @BindView(R.id.tv_mycenter_safe_google)
    TextView tv_mycenter_safe_google;

    @BindView(R.id.tv_mycenter_safe_pwd)
    TextView tv_mycenter_safe_pwd;

    @BindView(R.id.tv_mycenter_safe_accountpwd)
    TextView tv_mycenter_safe_accountpwd;

    SafeContract.Presenter presenter =new SafePresenter(this);
    public static Activity activity;
    @Override
    protected int setView() {
        return R.layout.safe_activity;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        safe_rl_1.setOnClickListener(this);
        safe_rl_2.setOnClickListener(this);
        safe_rl_3.setOnClickListener(this);
        safe_rl_4.setOnClickListener(this);
        safe_rl_5.setOnClickListener(this);
        safe_rl_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.safe_rl_1:
                    quickStartActivityResult(BindPhoneActivity.class, 1);
                    break;
                case R.id.safe_rl_2:
                    quickStartActivityResult(BindEmailActivity.class, 2);
                    break;
                case R.id.safe_rl_3:
                    quickStartActivity(BindGoogleActivity.class);
                    break;
                case R.id.safe_rl_4:
                    quickStartActivity(UpdateLoginPwdActivity.class);
                    break;
                case R.id.safe_rl_5:
                    if(tv_mycenter_safe_accountpwd.getText().equals(getResources().getString(R.string.goto_update))) {
                        quickStartActivity(UpdateAccountPwdActivity.class);
                    }else{

                        quickStartActivity(SettingAccountPwdActivity.class);
                    }
                    break;
                case R.id.safe_rl_6:
                    quickStartActivity(DefaultPatternSettingActivity.class);
                    break;

            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type6);
        activity=this;
    }


    @Override
    protected void onResume() {
        requestSafeInfo();
        super.onResume();
    }

    public void requestSafeInfo(){
        presenter.getSafeInfo();
    }

    @Override
    public void safeInfoSuccess(UserInfoBean safeBean) {

        if(tv_mycenter_safe_email==null)return;


        if(safeBean.getDealPasswordExists()==0){
            tv_mycenter_safe_accountpwd.setText(R.string.goto_settting);
        }else{
            tv_mycenter_safe_accountpwd.setText(getResources().getString(R.string.goto_update));
        }

        if(safeBean.getEmail()!=null&&safeBean.getEmail().length()>0){
            tv_mycenter_safe_email.setText(safeBean.getEmail());
            safe_rl_2.setEnabled(false);

        }else{
            tv_mycenter_safe_email.setText(getResources().getString(R.string.unbind));
            Drawable nav_up = getResources().getDrawable(R.mipmap.goto_next);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv_mycenter_safe_email.setCompoundDrawables(null,null,nav_up,null);
        }
        if(safeBean.getPhone()!=null&&safeBean.getPhone().length()>0){
            tv_mycenter_safe_phone.setText(safeBean.getPhone());
            safe_rl_1.setEnabled(false);
        }else{
            tv_mycenter_safe_phone.setText(getResources().getString(R.string.unbind));
            Drawable nav_up = getResources().getDrawable(R.mipmap.goto_next);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv_mycenter_safe_phone.setCompoundDrawables(null,null,nav_up,null);
        }

        if(safeBean.getGesturePassword()!=null&&safeBean.getGesturePassword().length()>0){
            if(PreferencesUtils.getString("gesture_pwd_key")!=null&&PreferencesUtils.getString("gesture_pwd_key").length()>0)
            {

                if(InfoProvider.getInstance().getGesture()) {
                    openPatternLocker.setChecked(true);
                }else{
                    openPatternLocker.setChecked(false);
                }
            }else{

                openPatternLocker.setChecked(false);
            }
            PreferencesUtils.saveString("gesture_pwd_key",safeBean.getGesturePassword());

        }else{
            openPatternLocker.setChecked(false);
        }

        openPatternLocker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    if(PreferencesUtils.getString("gesture_pwd_key")==null||PreferencesUtils.getString("gesture_pwd_key").equals("")) {
                        //第一次设置手势
                        Intent intent = new Intent(SafeActivity.this, DefaultPatternSettingActivity.class);
                        startActivity(intent);
                    }else{
                        InfoProvider.getInstance().saveGesture(true);
                    }

                }else{
                    InfoProvider.getInstance().saveGesture(false);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(InfoProvider.getInstance().getGesture()){
            openPatternLocker.setChecked(true);
        }else{
            openPatternLocker.setChecked(false);
        }
    }

    @Override
    public void safeInfoFailed() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            if (requestCode == 1) {
                tv_mycenter_safe_phone.setText(data.getStringExtra("phone"));
            } else if (requestCode == 2) {

                tv_mycenter_safe_email.setText(data.getStringExtra("email"));
            }
        }
    }
}
