package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.CountryActivity;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.contract.CertPrimaryContract;
import oysd.com.trade_app.modules.mycenter.presenter.CertPrimaryPresenter;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;


public class CertificationStep1 extends BaseToolActivity implements CertPrimaryContract.View {

    @BindView(R.id.bt_mycenter_cert1_submit)
    Button bt_mycenter_cert1_submit;
    @BindView(R.id.et_mycenter_cert1_number)
    EditText et_mycenter_cert1_number;
    @BindView(R.id.et_mycenter_cert1_name)
    EditText et_mycenter_cert1_name;
    @BindView(R.id.tv_mycenter_cert_nation)
    TextView tv_mycenter_cert_nation;
    @BindView(R.id.tv_mycenter_cert_type)
    TextView tv_mycenter_cert_type;

    @BindView(R.id.ll_mycenter_cert1_selectCountry)
    LinearLayout ll_mycenter_cert1_selectCountry;

    @BindView(R.id.ll_mycenter_cert1_selectCertType)
    LinearLayout ll_mycenter_cert1_selectCertType;
    int countryId;
    int certType;

    private ArrayList<String> options1Items = new ArrayList<>();
    OptionsPickerView pvOptions;

    CertPrimaryContract.Presenter presenter =new CertPrimaryPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_certification_step1;
    }

    @Override
    protected void initData() {
        super.initData();
        //默认身份证、中国国家
        certType=1;
        countryId=34;
    }


    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.my1);
        options1Items.add(getResources().getString(R.string.auth_cert_type1));
        options1Items.add(getResources().getString(R.string.auth_cert_type2));
        options1Items.add(getResources().getString(R.string.auth_cert_type3));
        options1Items.add(getResources().getString(R.string.auth_cert_type4));
    }



    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_cert1_submit.setOnClickListener(this);
        ll_mycenter_cert1_selectCertType.setOnClickListener(this);
        ll_mycenter_cert1_selectCountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_mycenter_cert1_submit:
                    requestCertPrimary();
                    break;
                case R.id.ll_mycenter_cert1_selectCertType:
                    //隐藏键盘
                    hideSoftInput(ll_mycenter_cert1_selectCertType);

                    selectLanguageActivity();
                    pvOptions.show(); //弹出条件选择器
                    break;
                case R.id.ll_mycenter_cert1_selectCountry:
                    Intent intent = new Intent();
                    intent.setClass(CertificationStep1.this, CountryActivity.class);
                    startActivityForResult(intent, 12);
                    break;

            }
        }
    }

    public void requestCertPrimary(){
        Map<String, Object> params = new HashMap<>();
        params.put("realName", et_mycenter_cert1_name.getText().toString());
        params.put("identityNumber", et_mycenter_cert1_number.getText().toString());
        params.put("identityType", certType);
        params.put("countryId", countryId);
        presenter.setCertPrimary(params);
    }


    public void selectLanguageActivity() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override

            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1);
                tv_mycenter_cert_type.setText(tx);
                if(tv_mycenter_cert_type.getText().toString().equals(getResources().getString(R.string.auth_cert_type1))){
                    certType=1;

                }else if(tv_mycenter_cert_type.getText().toString().equals(getResources().getString(R.string.auth_cert_type2))){
                    certType=2;
                }else if(tv_mycenter_cert_type.getText().toString().equals(getResources().getString(R.string.auth_cert_type3))){
                    certType=3;
                }else if(tv_mycenter_cert_type.getText().toString().equals(getResources().getString(R.string.auth_cert_type4))){
                    certType=4;
                }

            }
        })
                .setTitleText("")

                .setContentTextSize(20)//设置滚轮文字大小

                .setDividerColor(Color.LTGRAY)//设置分割线的颜色

                .setSelectOptions(0, 1)//默认选中项

                .setBgColor(ContextCompat.getColor(CertificationStep1.this,R.color.text_white))

                .setTitleBgColor(ContextCompat.getColor(CertificationStep1.this,R.color.text_white))

                .setTitleColor(ContextCompat.getColor(CertificationStep1.this,R.color.text_white))

                .setCancelColor(ContextCompat.getColor(CertificationStep1.this,R.color.text_grey))

                .setSubmitColor(ContextCompat.getColor(CertificationStep1.this,R.color.text_blue))

                .setTextColorCenter(ContextCompat.getColor(CertificationStep1.this,R.color.text_normal))

                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。

                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。


                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {

                    @Override

                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//
//                        Toast.makeText(CertificationStep1.this, str, Toast.LENGTH_SHORT).show();

                    }

                })

                .build();
        pvOptions.setPicker(options1Items);//一级选择器*/
    }

    @Override
    public void certPrimarySuccess() {
        ActivitiesManager.getInstance().finishActivity();
        ToastUtils.showLong(this, this.getResources().getString(R.string.message9));
        if(InfoProvider.getInstance().getLogin()) {
            if(Global.isToSenior) {
                quickStartActivity(CertificationStep2.class);
            }
        }else{
            quickStartActivity(LoginActivity.class);
        }
    }

    @Override
    public void certPrimaryFailed(int code,String msg) {
        ToastUtils.showLong(App.getContext(),msg);
    }

    @Override
    protected void clickBack() {
        super.clickBack();
        setResult(RESULT_OK);
        if(!InfoProvider.getInstance().getLogin()){
            quickStartActivity(LoginActivity.class);
        }else {
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        switch (requestCode)
        {
            case 12:
                if (resultCode == RESULT_OK)
                {
                    Bundle bundle = data.getExtras();
                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");
                    countryId =bundle.getInt("countryId");
                    tv_mycenter_cert_nation.setText(countryName+"("+countryNumber+")");

                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
