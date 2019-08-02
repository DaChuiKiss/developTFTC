package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.MainActivity;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.Utils;

public class languageActivity extends BaseToolActivity {


    @BindView(R.id.tv_english_id)
    TextView english;
    @BindView(R.id.tv_chinese_id)
    TextView chinese;

    @Override
    protected int setView() {
        return R.layout.activity_language;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        english.setOnClickListener(this);
        chinese.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.tv_english_id:
                    PreferencesUtils.saveString("selectLanguage", "2");
                    InfoProvider.getInstance().saveLanguage("en");
                    //InfoProvider.getInstance().saveLegalTender("AUD");
                    InfoProvider.getInstance().saveLegalTender("HKD");
                    PreferencesUtils.saveString("selectCoinType", "1");
                    en();
                    break;
                case R.id.tv_chinese_id:
                    PreferencesUtils.saveString("selectLanguage", "1");
                    InfoProvider.getInstance().saveLanguage("zh");
                    InfoProvider.getInstance().saveLegalTender("HKD");
                    PreferencesUtils.saveString("selectCoinType", "1");
                    cn();
                    break;
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.language);
        Drawable nav_up = getResources().getDrawable(R.mipmap.my_check);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if (PreferencesUtils.getString("selectLanguage", "1").equals("1")) {
            chinese.setCompoundDrawables(null, null, nav_up, null);
        } else {
            english.setCompoundDrawables(null, null, nav_up, null);
        }
    }


    /**
     * 切换英文
     */
    public void en() {
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.ENGLISH; // 英文
        resources.updateConfiguration(config, dm);
        finish();//如果不重启当前界面，是不会立马修改的
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    /**
     * 切换中文
     */
    public void cn() {
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.CHINA; // 简体中文
        resources.updateConfiguration(config, dm);
        finish();////如果不重启当前界面，是不会立马修改的
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    @Override
    protected void initData() {
        super.initData();
    }
}
