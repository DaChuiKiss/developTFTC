package oysd.com.trade_app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.util.Locale;

import oysd.com.trade_app.util.Utils;

/**
 * Created by hsg on 14/10/2017.
 */
public class App extends MultiDexApplication {

    private static Context context;

    private static volatile App instance = null;

    private Gson gson;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker("UA-128409462-1");
            // Provide unhandled exceptions reports. Do that first after creating the tracker
            sTracker.enableExceptionReporting(true);

            // Enable Remarketing, Demographics & Interests reports
            // https://developers.google.com/analytics/devguides/collection/android/display-features
            sTracker.enableAdvertisingIdCollection(true);

            // Enable automatic activity tracking for your app
            sTracker.enableAutoActivityTracking(true);
        }

        return sTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        gson = new Gson();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();//这是获取当前APP已经可以申请到的最大运行内存
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memorySize = activityManager.getMemoryClass();//这是获取当前APP默认情况下的最大运行内存
        int newmaxmemorySize = activityManager.getLargeMemoryClass();//这是获取当前APP可以申请到的最大运行内存
        Log.d("sunlei", "maxMemory------" + maxMemory / 1024 / 1024 + "M");
        Log.d("sunlei", "memorySize------" + memorySize + "M");
        Log.d("sunlei", "newmaxmemorySize------" + newmaxmemorySize + "M");

        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
//        if(PreferencesUtils.getString("selectLanguage")!=null) {
//            if (PreferencesUtils.getString("selectLanguage").equals("1")) {
//                config.locale = Locale.CHINA; // 中文
//            } else if (PreferencesUtils.getString("selectLanguage").equals("2")) {
//                config.locale = Locale.ENGLISH; // 英文
//            }
//        }
        if (Utils.isChina()) {
            config.locale = Locale.CHINA; // 中文
        } else {
            config.locale = Locale.ENGLISH; // 英文
        }

        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public static Context getContext() {
        return context;

    }

    public Gson getGson() {
        return gson;
    }

}
