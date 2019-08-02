package oysd.com.trade_app.modules.mycenter.util;

import android.text.TextUtils;

import java.util.List;

import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.Utils;

/**
 * Created by hsg on 14/10/2017.
 */

public class PatternHelper {
    public static final int MAX_SIZE = 4;
    public static final int MAX_TIMES =5;
    private static final String GESTURE_PWD_KEY = "gesture_pwd_key";

    private String message;
    private String storagePwd;
    private String tmpPwd;
    private int times;
    private boolean isFinish;
    private boolean isOk;

    public void validateForSetting(List<Integer> hitList) {
        this.isFinish = false;
        this.isOk = false;

        if ((hitList == null) || (hitList.size() < MAX_SIZE)) {
            this.tmpPwd = null;
            this.message = getSizeErrorMsg();
            return;
        }

        //1. draw first time
        if (TextUtils.isEmpty(this.tmpPwd)) {
            this.tmpPwd = convert2String(hitList);
            this.message = getReDrawMsg();
            this.isOk = true;
            return;
        }

        //2. draw second times
        if (this.tmpPwd.equals(convert2String(hitList))) {
            this.message = getSettingSuccessMsg();
            saveToStorage(this.tmpPwd);
            this.isOk = true;
            this.isFinish = true;
        } else {
            this.tmpPwd = null;
            this.message = getDiffPreErrorMsg();
        }
    }

    public void validateForChecking(List<Integer> hitList) {
        this.isOk = false;

        if ((hitList == null) || (hitList.size() < MAX_SIZE)) {
            this.times++;
            this.isFinish = this.times >= MAX_SIZE;
            this.message = getPwdErrorMsg();
            return;
        }

        this.storagePwd = getFromStorage();

        String ss =convert2String(hitList).replace(",","");
        String sss=ss.replace("[","").replace("]","").replace(" ","");

        if (!TextUtils.isEmpty(this.storagePwd) && this.storagePwd.equals(sss)) {
            this.message = getCheckingSuccessMsg();
            this.isOk = true;
            this.isFinish = true;
        } else {
            this.times++;
            this.isFinish = this.times >= MAX_TIMES;
            this.message = getPwdErrorMsg();
        }
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public boolean isOk() {
        return isOk;
    }

    private String getReDrawMsg() {

        return App.getContext().getResources().getString(R.string.gesture_setting);
    }

    private String getSettingSuccessMsg() {
        return App.getContext().getResources().getString(R.string.gesture_setting_2);
    }

    private String getCheckingSuccessMsg() {
        return App.getContext().getResources().getString(R.string.gesture_success);
    }

    private String getSizeErrorMsg() {
        return App.getContext().getResources().getString(R.string.gestrue_setting_4);
    }
    private String getDiffPreErrorMsg() {
        return App.getContext().getResources().getString(R.string.gestrue_setting_3);
    }

    private String getPwdErrorMsg() {
        return App.getContext().getResources().getString(R.string.gestrue_setting_5);
    }

    private String convert2String(List<Integer> hitList) {
        return hitList.toString();
    }

    private void saveToStorage(String gesturePwd) {
        String ss =gesturePwd.replace(",","");
        String sss=ss.replace("[","").replace("]","").replace(" ","");
        PreferencesUtils.saveString(GESTURE_PWD_KEY, sss);
    }

    private String getFromStorage() {
        final String result = PreferencesUtils.getString(GESTURE_PWD_KEY);
        return result;
    }

    public int getRemainTimes() {
        return (times < 5) ? (MAX_TIMES - times) : 0;
    }
}
