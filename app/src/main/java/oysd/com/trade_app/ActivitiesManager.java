package oysd.com.trade_app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Activity 管理。
 * Created by Liam on 2018/7/11
 */
public class ActivitiesManager {

    private static Stack<Activity> activityStack;
    private static volatile ActivitiesManager instance = null;

    private ActivitiesManager() {
    }

    /**
     * 单例
     */
    public static ActivitiesManager getInstance() {
        if (instance == null) {
            synchronized (ActivitiesManager.class) {
                if (instance == null) {
                    instance = new ActivitiesManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加 Activity 到堆栈。
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前 Activity（堆栈中最后一个压入的）。
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前 Activity（堆栈中最后一个压入的）。
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的 Activity。
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的 Activity。
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有 Activity。
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序。
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序是否已经退出。
     * @return {@code true} 表示已经退出。
     */
    public boolean isAppExit() {
        return activityStack == null || activityStack.isEmpty();
    }

}
