package oysd.com.trade_app.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2018/7/10
 * @desc Base activity for all activities.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {

    protected Context context;

    // 通过 intent 传递的数据。
    protected Bundle extras;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Logger.d(" ---> onCreate.");
        ActivitiesManager.getInstance().addActivity(this);
        // 需要的时候再去调用
        // setTranslucentStatus(R.color.transparent);
        // StatusBarUtil.setStatusBarLightMode(this);
        this.context = this;
        // extras 的获取，需要确定 key。
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.extras = extras;
        }
        initContentView();
        unbinder = ButterKnife.bind(this);
        doBusiness();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        //if(PreferencesUtils.getString("selectLanguage")!=null) {
        //if (PreferencesUtils.getString("selectLanguage").equals("1")) {
        //    config.locale = Locale.CHINA; // 中文
        //} else if (PreferencesUtils.getString("selectLanguage").equals("2")) {
        //    config.locale = Locale.ENGLISH; // 英文
        //}
        //}
        if (Utils.isChina()) {
            config.locale = Locale.CHINA; // 中文
        } else {
            config.locale = Locale.ENGLISH; // 英文
        }

        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 初始化 Content View 相关的内容。SubClass 必须实现的方法。
     */
    protected abstract void initContentView();

    /**
     * 初始化相关的业务流程。SubClass 必须实现的方法。
     */
    protected abstract void doBusiness();


    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d(" ---> onRestart.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(" ---> onStart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("---> onResume.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("---> onStop.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("---> onDestroy.");
        ActivitiesManager.getInstance().finishActivity(this);
        unbinder.unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("---> onPause.");
    }

    /**
     * 处理点击事件。
     * 子类需要重写。
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
    }
}
