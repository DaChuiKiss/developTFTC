package oysd.com.trade_app.http.interceptor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import oysd.com.trade_app.App;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.InfoUtils;
import oysd.com.trade_app.util.Logger;

/**
 * Header interceptor.
 * Created by Liam on 2018/7/16
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, Object> headersMap;

    public HeaderInterceptor() {
    }

    public HeaderInterceptor(Map<String, Object> headersMap) {
        this.headersMap = headersMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (EmptyUtils.isNotEmpty(headersMap)) {
            for (Map.Entry<String, Object> entry : headersMap.entrySet()) {
                builder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        // 现在只增加 token 和 language.
        UserBean userBean = InfoProvider.getInstance().getLoginInfo();
        String token = userBean != null ? userBean.getToken() : "1aff4013-0d6a-4632-aaa4-ea5b0250a361";

        String language = InfoUtils.getLanguage();
        String legalTender = InfoUtils.getLegalTender();

        builder.addHeader("accessToken", token);
        builder.addHeader("currLanguage", language);
        builder.addHeader("legalTender", legalTender);
        //在加一个appVersion      标识app的版本，后期有用   值就用那个7
        builder.addHeader("appVersion", "" + packageCode(App.getContext()));
//        builder.addHeader("signTest", "true");
        return chain.proceed(builder.build());
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
}
