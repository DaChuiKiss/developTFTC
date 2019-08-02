package oysd.com.trade_app.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oysd.com.trade_app.Values;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.PreferencesUtils;

/**
 * @author houmingkuan
 * @time 2018/7/24
 * @desc 基础信息保存
 */
public class InfoProvider {

    public static final String TOKEN = "token";
    // 是否是登录状态（accessToken 有效时间内 login 为 true）
    public static final String LOGIN = "login";
    // 是否设置手势密码。
    public static final String GESTURE = "gesture";

    public static final String LANGUAGE = "language";
    public static final String LEGAL_TENDER = "legalTender";

    public static final String LOGIN_INFO = "login_info";
    public static final String USER_INFO = "user_info";

    public static final String USER_MARKET = "user_market";

    public static final String SET_ACCOUNT = "set_account";

    public static final String SET_CERT = "set_cert";
    public static final String SET_SENIOR_CERT = "set_senior_cert";
    public static final String THE_FIRST = "the_first";

    public static final String TIME_ZONE = "timeZone";

    private Map<String, Object> infoMap = new HashMap<>();
    private static volatile InfoProvider instance = null;

    private InfoProvider() {
    }

    public static InfoProvider getInstance() {
        if (instance == null) {
            synchronized (InfoProvider.class) {
                if (instance == null) {
                    instance = new InfoProvider();
                }
            }
        }
        return instance;
    }

    /**
     * 保存 token.
     */
    public void saveToken(@NonNull String token) {
        synchronized (this) {
            infoMap.put(TOKEN, token);
            PreferencesUtils.saveString(TOKEN, token);
        }
    }

    /**
     * 获取 token.
     */
    @Nullable
    public String getToken() {
        Object objToken = infoMap.get(TOKEN);
        if (objToken != null) {
            return (String) objToken;
        }

        String token = PreferencesUtils.getString(TOKEN);
        if (EmptyUtils.isNotEmpty(token)) {
            infoMap.put(TOKEN, token);
            return token;
        }
        return null;
    }

    /**
     * 保存 Login status.
     */
    public void saveLogin(boolean status) {
        synchronized (this) {
            infoMap.put(LOGIN, status);
            PreferencesUtils.saveBoolean(LOGIN, status);
        }
    }

    /**
     * 获取 Login status.
     */
    public boolean getLogin() {
        Object objLoginStatus = infoMap.get(LOGIN);
        if (objLoginStatus != null) {
            return (boolean) objLoginStatus;
        }

        boolean loginStatus = PreferencesUtils.getBoolean(LOGIN);
        infoMap.put(LOGIN, loginStatus);
        return loginStatus;
    }

    /**
     * 保存是否设置手势密码状态。
     */
    public void saveGesture(boolean gesture) {
        synchronized (this) {
            infoMap.put(GESTURE, gesture);
            PreferencesUtils.saveBoolean(GESTURE, gesture);
        }
    }

    /**
     * 获取是否设置手势密码状态。
     */
    public boolean getGesture() {
        Object objGesture = infoMap.get(GESTURE);
        if (objGesture != null) {
            return (boolean) objGesture;
        }

        boolean gesture = PreferencesUtils.getBoolean(GESTURE);
        infoMap.put(GESTURE, gesture);
        return gesture;
    }

    /**
     * 保存语言。
     */
    public void saveLanguage(@NonNull String language) {
        synchronized (this) {
            infoMap.put(LANGUAGE, language);
            PreferencesUtils.saveString(LANGUAGE, language);
        }
    }

    /**
     * 获取语言。
     */
    @Nullable
    public String getLanguage() {
        Object objLanguage = infoMap.get(LANGUAGE);
        if (objLanguage != null) {
            return (String) objLanguage;
        }

        String language = PreferencesUtils.getString(LANGUAGE);
        if (EmptyUtils.isNotEmpty(language)) {
            infoMap.put(LANGUAGE, language);
            return language;
        }
        return null;
    }

    /**
     * 保存法定货币类型。CNY or USD.
     *
     * @param legalTender legal tender
     */
    public void saveLegalTender(@NonNull String legalTender) {
        synchronized (this) {
            infoMap.put(LEGAL_TENDER, legalTender);
            PreferencesUtils.saveString(LEGAL_TENDER, legalTender);
        }
    }

    /**
     * 获取法定货币类型。
     *
     * @return String
     */
    @Nullable
    public String getLegalTender() {
        Object objLegalTender = infoMap.get(LEGAL_TENDER);
        if (objLegalTender != null) {
            return (String) objLegalTender;
        }

        String legalTender = PreferencesUtils.getString(LEGAL_TENDER);
        if (EmptyUtils.isNotEmpty(legalTender)) {
            infoMap.put(LEGAL_TENDER, legalTender);
            return legalTender;
        }
        Locale locale = Locale.getDefault();

        if (locale.getLanguage().equals("zh")) {
            //默认HKD
            return Values.Currency.HKD_VALUE;
        } else {
            return Values.Currency.AUD_VALUE;
        }

    }

    /**
     * 保存自选
     */
    public void saveMarketId(@NonNull Integer marketId) {
        synchronized (this) {
            String strList = PreferencesUtils.getString(USER_MARKET);
            List<Integer> list = null;
            Gson gson = new Gson();
            if (EmptyUtils.isNotEmpty(strList)) {
                list = gson.fromJson(strList, new TypeToken<List<Integer>>() {
                }.getType());
                list.add(marketId);
                PreferencesUtils.saveString(USER_MARKET, list.toString());
            } else {
                list = new ArrayList<Integer>();
                list.add(marketId);
                PreferencesUtils.saveString(USER_MARKET, list.toString());
            }
        }
    }

    /**
     * 是否在自选
     */
    public boolean isUserMarketId(int marketId) {
        synchronized (this) {
            boolean flag = false;
            String strList = PreferencesUtils.getString(USER_MARKET);
            List<Integer> list = null;
            Gson gson = new Gson();
            if (EmptyUtils.isNotEmpty(strList)) {
                list = gson.fromJson(strList, new TypeToken<List<Integer>>() {
                }.getType());

                for (int i = 0; i < list.size(); i++) {
                    if (marketId == list.get(i)) {
                        flag = true;
                    }
                }
            }

            return flag;
        }
    }

    /**
     * 删除自选
     */
    public void deleteMarketId(@NonNull Integer marketId) {
        synchronized (this) {
            String strList = PreferencesUtils.getString(USER_MARKET);
            List<Integer> list = null;
            Gson gson = new Gson();
            if (EmptyUtils.isNotEmpty(strList)) {
                list = gson.fromJson(strList, new TypeToken<List<Integer>>() {
                }.getType());
                List<Integer> tempList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    Integer in = list.get(i);
                    if (in != marketId) {
                        tempList.add(in);
                    }
                }
                list = tempList;
                PreferencesUtils.saveString(USER_MARKET, list.toString());
            }
        }
    }

    /**
     * 得到自选
     */

    public ArrayList<Integer> getMarketIdList() {
        synchronized (this) {
            ArrayList<Integer> list = null;
            Gson gson = new Gson();
            String strList = PreferencesUtils.getString(USER_MARKET);
            if (EmptyUtils.isNotEmpty(strList)) {
                list = gson.fromJson(strList, new TypeToken<List<Integer>>() {
                }.getType());
            }

            return list;
        }
    }

    /**
     * 保存 login info.
     */
    public void saveLoginInfo(@NonNull UserBean userBean) {
        synchronized (this) {
            infoMap.put(LOGIN_INFO, userBean);
            PreferencesUtils.saveString(LOGIN_INFO, new Gson().toJson(userBean));
        }
    }

    /**
     * 获取 login info.
     */
    @Nullable
    public UserBean getLoginInfo() {
        Object objLoginInfo = infoMap.get(LOGIN_INFO);
        if (objLoginInfo != null) {
            return (UserBean) objLoginInfo;
        }

        String strLoginInfo = PreferencesUtils.getString(LOGIN_INFO);
        if (EmptyUtils.isNotEmpty(strLoginInfo)) {
            UserBean userBean = new Gson().fromJson(strLoginInfo, UserBean.class);
            infoMap.put(LOGIN_INFO, userBean);
            return userBean;
        }
        return null;
    }

    /**
     * 保存用户信息。
     */
    public void saveUserInfo(@NonNull UserInfoBean userInfoBean) {
        synchronized (this) {
            infoMap.put(USER_INFO, userInfoBean);
            PreferencesUtils.saveString(InfoProvider.USER_INFO, new Gson().toJson(userInfoBean));
        }
    }

    /**
     * 获取用户信息。
     */
    @Nullable
    public UserInfoBean getUserInfo() {
        Object objUserInfo = infoMap.get(USER_INFO);
        if (objUserInfo != null) {
            return (UserInfoBean) objUserInfo;
        }

        String strUserInfo = PreferencesUtils.getString(USER_INFO);
        if (EmptyUtils.isNotEmpty(strUserInfo)) {
            UserInfoBean userInfoBean = new Gson().fromJson(strUserInfo, UserInfoBean.class);
            infoMap.put(USER_INFO, userInfoBean);
            return userInfoBean;
        }
        return null;
    }

    /**
     * 清除 Login Info.
     */
    public boolean clearLoginInfo() {
        synchronized (this) {
            infoMap.remove(LOGIN_INFO);
            return PreferencesUtils.remove(LOGIN_INFO);
        }
    }

    /**
     * 清除 UserInfo.
     */
    public boolean clearUserInfo() {
        synchronized (this) {
            infoMap.remove(USER_INFO);
            return PreferencesUtils.remove(USER_INFO);
        }
    }

    /**
     * 设置资金成功
     */
    public void setAccount(boolean flag) {
        PreferencesUtils.saveBoolean(SET_ACCOUNT, flag);

    }


    /**
     * 获取资金密码设置状态
     */
    public boolean getAccount() {

        return PreferencesUtils.getBoolean(SET_ACCOUNT, false);

    }


    /**
     * 设置实名认证
     */
    public void setCert(boolean flag) {
        PreferencesUtils.saveBoolean(SET_CERT, flag);

    }


    /**
     * 获取实名认证状态
     */
    public boolean getSeniorCert() {

        return PreferencesUtils.getBoolean(SET_SENIOR_CERT, false);

    }

    /**
     * 设置实名认证
     */
    public void setSeniorCert(boolean flag) {
        PreferencesUtils.saveBoolean(SET_SENIOR_CERT, flag);

    }


    /**
     * 获取实名认证状态
     */
    public boolean getCert() {

        return PreferencesUtils.getBoolean(SET_CERT, false);

    }


    /**
     * 第一次安装应用，启动页存在
     */
    public void setWelcome(boolean flag) {
        PreferencesUtils.saveBoolean(THE_FIRST, flag);

    }


    /**
     * 获取实名认证状态
     */
    public boolean getWelcome() {

        return PreferencesUtils.getBoolean(THE_FIRST, true);

    }

    /**
     * 保存 TimeZone
     */
    public void saveTimeZone(String timeZone) {
        PreferencesUtils.saveString(TIME_ZONE, timeZone);
    }

    /**
     * 获取保存的 TimeZone info.
     * 如果 TimeZone 不为空，则返回 TimeZone, 否则返回标准 "GMT" 。
     */
    public String getTimeZone() {
        String timeZone = PreferencesUtils.getString(TIME_ZONE);
        return EmptyUtils.isEmpty(timeZone) ? "GMT" : timeZone;
    }

}
