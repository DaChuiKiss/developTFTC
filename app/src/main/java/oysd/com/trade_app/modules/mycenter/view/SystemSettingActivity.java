package oysd.com.trade_app.modules.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.utils.AppUpdateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.modules.home.view.HomeWebActivity;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.SystemSettingContract;
import oysd.com.trade_app.modules.mycenter.http.UpdateAppHttpUtil;
import oysd.com.trade_app.modules.mycenter.presenter.SystemSettingPresenter;
import oysd.com.trade_app.modules.mycenter.util.FileCacheUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

public class SystemSettingActivity extends BaseToolActivity implements SystemSettingContract.View {


    @BindView(R.id.rl_1)
    RelativeLayout rl_1;

    @BindView(R.id.rl_2)
    RelativeLayout rl_2;

    @BindView(R.id.rl_3)
    RelativeLayout rl_3;


    @BindView(R.id.rl_4)
    RelativeLayout rl_4;

    @BindView(R.id.rl_5)
    RelativeLayout rl_5;

    @BindView(R.id.rl_6)
    RelativeLayout rl_6;

    @BindView(R.id.rl_7)
    RelativeLayout rl_7;

    @BindView(R.id.tv_app_vsersion)
    TextView tv_app_vsersion;

    @BindView(R.id.tv_mycenter_setting_language)
    TextView tv_mycenter_setting_language;
    @BindView(R.id.mycenter_setting_coinType)
    TextView mycenter_setting_coinType;
    private ArrayList<String> options1Items = new ArrayList<>();

    private ArrayList<String> options2Items = new ArrayList<>();
    OptionsPickerView pvOptions;

    @BindView(R.id.bt_mycenter_system_logout)
    TextView bt_mycenter_system_logout;

    @BindView(R.id.tv_cacheSize)
    TextView tv_cacheSize;


    SystemSettingContract.Presenter presenter = new SystemSettingPresenter(this);


    @Override
    protected int setView() {
        return R.layout.setting_activity;
    }


    @Override
    protected void initClicks() {
        super.initClicks();
        rl_1.setOnClickListener(this);
        rl_2.setOnClickListener(this);
        rl_3.setOnClickListener(this);
        rl_4.setOnClickListener(this);
        rl_5.setOnClickListener(this);
        rl_6.setOnClickListener(this);
        rl_7.setOnClickListener(this);
        bt_mycenter_system_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.rl_1:
                    if (Utils.isChina()) {
                        jumpToWeb(getResources().getString(R.string.system1), Constants.H5_URL + "/html/contact.html");
                    } else {
                        jumpToWeb(getResources().getString(R.string.system1), "");
                    }
                    break;
                case R.id.rl_2:
                    if (Utils.isChina()) {
                        jumpToWeb(getResources().getString(R.string.system2), Constants.H5_URL + "/html/privacy.html");
                    } else {
                        jumpToWeb(getResources().getString(R.string.system2), "");
                    }
                    break;
                case R.id.rl_3:
                    if (Utils.isChina()) {
                        jumpToWeb(getResources().getString(R.string.system3), Constants.H5_URL + "/html/agreement.html");
                    } else {
                        jumpToWeb(getResources().getString(R.string.system3), "");
                    }
                    break;
                case R.id.rl_4:
                    quickStartActivity(languageActivity.class);
//                selectLanguageActivity();
//                pvOptions.show(); //弹出条件选择器
                    break;
                case R.id.rl_5:
                    showPasswordDialog();

                    break;
                case R.id.rl_6:
                    quickStartActivity(CoinTypeActivity.class);
//                selectCoinTypeActivity();
//                pvOptions.show(); //弹出条件选择器
                    break;
                case R.id.rl_7:
                    presenter.getNewestAppVersionInfo();
                    //quickStartActivity(JavaActivity.class);
                    break;
                case R.id.bt_mycenter_system_logout:
                    PasswordDialog dialog = new PasswordDialog(this, null, getResources().getString(R.string.logout) + "?", false, false, R.style.dialog);
                    dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
                        @Override
                        public void onSubmit(PasswordDialog dialog, String password) {
                            requestLogout();
                        }
                    });
                    dialog.show();
                    break;
            }
        }
    }


    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(this, null, getResources().getString(R.string.dialogMsg6), false, false, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                FileCacheUtils.clearAllCache(SystemSettingActivity.this);
                try {
                    tv_cacheSize.setText(FileCacheUtils.getTotalCacheSize(SystemSettingActivity.this));
                    if (FileCacheUtils.getTotalCacheSize(SystemSettingActivity.this).equals("0K")) {
                        ToastUtils.showLong(App.getContext(), R.string.clear_cache);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public UrlBean selectBean(int type) {
        UrlBean ub = new UrlBean();
        if (Global.urlList.size() > 0) {
            for (int i = 0; i < Global.urlList.size(); i++) {
                if (type == Global.urlList.get(i).getType()) {
                    ub = Global.urlList.get(i);
                }
            }
        }
        return ub;

    }


    // 跳转到指定静态页面
    private void jumpToWeb(String title, String url) {
        Intent intent = new Intent(this, UrlWebActivity.class);
        intent.putExtra(UrlWebActivity.EXTRA_URL, url);
        intent.putExtra(UrlWebActivity.EXTRA_TITLE, title);
        startActivity(intent);
    }


    public void requestLogout() {
        presenter.logout();
    }

//
//    /**
//     * 切换英文
//     */
//    public void en(){
//        Resources resources = getResources();// 获得res资源对象
//        Configuration config = resources.getConfiguration();// 获得设置对象
//        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
//        config.locale = Locale.ENGLISH; // 英文
//        resources.updateConfiguration(config, dm);
//        finish();//如果不重启当前界面，是不会立马修改的
//        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//
//    }
//    /**
//     * 切换中文
//     */
//    public void cn(){
//        Resources resources = getResources();// 获得res资源对象
//        Configuration config = resources.getConfiguration();// 获得设置对象
//        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
//        config.locale = Locale.CHINA; // 简体中文
//        resources.updateConfiguration(config, dm);
//        finish();////如果不重启当前界面，是不会立马修改的
//        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//
//    }

//    public void selectLanguageActivity() {
//        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//
//            @Override
//
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//
//                //返回的分别是三个级别的选中位置
//
//                String tx = options1Items.get(options1);
//                tv_mycenter_setting_language.setText(tx);
//                if(tv_mycenter_setting_language.getText().toString().equals(getResources().getString(R.string.chinese))){
//                        PreferencesUtils.saveString("selectLanguage","1");
//                        cn();
//
//                }else{
//                        PreferencesUtils.saveString("selectLanguage","2");
//                        en();
//                }
//
//            }
//         })
//                .setTitleText(getResources().getString(R.string.selectLanguage))
//
//                .setContentTextSize(20)//设置滚轮文字大小
//
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
//
//                .setSelectOptions(0, 1)//默认选中项
//
//                .setBgColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.white))
//
//                .setTitleBgColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.white))
//
//                .setTitleColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setCancelColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setSubmitColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setTextColorCenter(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
//
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//
//
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//
//                    @Override
//
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//
//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//
//                        Toast.makeText(SystemSettingActivity.this, str, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                })
//
//                .build();
//        pvOptions.setPicker(options1Items);//一级选择器*/
//    }

//    public void selectCoinTypeActivity() {
//        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//
//            @Override
//
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//
//                //返回的分别是三个级别的选中位置
//
//                String tx = options2Items.get(options1);
//                mycenter_setting_coinType.setText(tx);
//                if(mycenter_setting_coinType.getText().toString().equals("CNY")){
//                    PreferencesUtils.saveString("selectCoinType","1");
//
//                }else{
//                    PreferencesUtils.saveString("selectCoinType","2");
//                }
//
//            }
//        })
//                .setTitleText(getResources().getString(R.string.selectCoinType))
//
//                .setContentTextSize(20)//设置滚轮文字大小
//
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
//
//                .setSelectOptions(0, 1)//默认选中项
//
//                .setBgColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.white))
//
//                .setTitleBgColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.white))
//
//                .setTitleColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setCancelColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setSubmitColor(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .setTextColorCenter(ContextCompat.getColor(SystemSettingActivity.this,R.color.black))
//
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
//
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//
//
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//
//                    @Override
//
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//
//                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//
//                        Toast.makeText(SystemSettingActivity.this, str, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                })
//
//                .build();
//        pvOptions.setPicker(options2Items);//一级选择器*/
//    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type8);
        tv_app_vsersion.setText(packageName(this));

//        options1Items.add(getResources().getString(R.string.chinese));
//        options1Items.add(getResources().getString(R.string.english));
//        options2Items.add("CNY");
//        options2Items.add("USDT");
        try {
            tv_cacheSize.setText(FileCacheUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initData() {

    }


    @Override
    protected void onStart() {
        super.onStart();
        //如果没有选择货币兑换，那么就按照系统语言来决定换算货币。
        if (PreferencesUtils.getString("selectCoinType") == null) {
            Locale locale = Locale.getDefault();
            if (locale.getLanguage().equals("zh")) {
                tv_mycenter_setting_language.setText(getResources().getString(R.string.chinese));
                mycenter_setting_coinType.setText("HKD");
                InfoProvider.getInstance().saveLegalTender("HKD");
                PreferencesUtils.saveString("selectCoinType", "1");
                PreferencesUtils.saveString("selectLanguage", "1");
            } else {
                tv_mycenter_setting_language.setText(getResources().getString(R.string.english));
//                mycenter_setting_coinType.setText("AUD");
//                InfoProvider.getInstance().saveLegalTender("AUD");
//                PreferencesUtils.saveString("selectCoinType", "3");
//                PreferencesUtils.saveString("selectLanguage", "2");
                mycenter_setting_coinType.setText("HKD");
                InfoProvider.getInstance().saveLegalTender("HKD");
                PreferencesUtils.saveString("selectCoinType", "1");
                PreferencesUtils.saveString("selectLanguage", "1");
            }

        } else {
            if (PreferencesUtils.getString("selectLanguage", "1").equals("1")) {
                tv_mycenter_setting_language.setText(getResources().getString(R.string.chinese));

            } else if (PreferencesUtils.getString("selectLanguage", "1").equals("2")) {
                tv_mycenter_setting_language.setText(getResources().getString(R.string.english));

            }
            if (PreferencesUtils.getString("selectCoinType", "1").equals("3")) {
                mycenter_setting_coinType.setText("AUD");
                InfoProvider.getInstance().saveLegalTender("AUD");
                PreferencesUtils.saveString("selectCoinType", "3");
            } else if (PreferencesUtils.getString("selectCoinType", "1").equals("1")) {
                mycenter_setting_coinType.setText("HKD");
                InfoProvider.getInstance().saveLegalTender("HKD");
                PreferencesUtils.saveString("selectCoinType", "1");
            } else if (PreferencesUtils.getString("selectCoinType", "1").equals("2")) {
                mycenter_setting_coinType.setText("USD");
                InfoProvider.getInstance().saveLegalTender("USD");
                PreferencesUtils.saveString("selectCoinType", "2");
            } else if (PreferencesUtils.getString("selectCoinType", "1").equals("4")) {
                mycenter_setting_coinType.setText("CNY");
                InfoProvider.getInstance().saveLegalTender("CNY");
                PreferencesUtils.saveString("selectCoinType", "4");
            }

        }
    }

    @Override
    public void logoutSuccess() {
        ActivitiesManager.getInstance().finishActivity();
        PreferencesUtils.saveBoolean("isLogin", false);
        InfoProvider.getInstance().saveLogin(false);
        InfoProvider.getInstance().saveGesture(false);
        PreferencesUtils.saveString("gesture_pwd_key", "");
        Global.isLogin = false;
    }

    @Override
    public void logoutFailed(int code, String msg) {
        ToastUtils.showLong(this, code + msg);
    }

    @Override
    public void getNewestAppVersionInfoSuccess(AppVersionInfoBean appBean) {

        if (appBean != null) {
            //获取本地应用的版本号
            if (packageCode(this) >= appBean.getVersionRecognition()) {
                ToastUtils.showLong(App.getContext(), getString(R.string.the_new_app));
            } else {
                new UpdateAppManager
                        .Builder()
                        //当前Activity
                        .setActivity(this)
                        //更新参数
                        .handleException(new ExceptionHandler() {
                            @Override
                            public void onException(Exception e) {
                                e.printStackTrace();
                            }
                        })
                        //实现httpManager接口的对象
                        .setHttpManager(new UpdateAppHttpUtil())
                        .build()
                        .update_str(updateStrInfo(appBean));
            }
        } else {
            ToastUtils.showLong(this, R.string.system_error);
        }

    }


    public String updateStrInfo(AppVersionInfoBean ab) {
        JSONObject json = new JSONObject();
        try {
            if (ab.getStatus() == 1) {

                json.put("update", "Yes");

            } else {
                json.put("update", "No");
            }
            json.put("new_version", ab.getVsersionNo());
            json.put("apk_file_url", ab.getDownUrl());
            json.put("update_log", "    ");
            if (ab.getIsCompulsory() == 1) {
                json.put("constraint", "true");
            } else {
                json.put("constraint", "false");
            }
            json.put("new_md5", "b97bea014531123f94c3ba7b7afbaad2");
            json.put("target_size", "17M");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


    //获取版本co
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    //获取版本名
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void getNewestAppVersionInfoFail(int code, String msg) {

    }
}
