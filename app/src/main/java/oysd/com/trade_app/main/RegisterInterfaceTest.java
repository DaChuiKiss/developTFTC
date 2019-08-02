package oysd.com.trade_app.main;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import oysd.com.trade_app.App;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.main.http.MainApi;
import oysd.com.trade_app.util.Logger;

/**
 * Created by Liam on 2018/7/17
 */
public class RegisterInterfaceTest {

    public static void test() {
        testRegister();
    }

    public static void testSendVerCode() {
        Gson gson  = new Gson();

        Map<String, Object> params = new HashMap<>();
        params.put("messageEnum", "1");
        params.put("target", "15626584674");
        params.put("type", 1);
        params.put("prefix", "86");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(params));

        RetrofitHelper.createApi(MainApi.class)
                .sendVerCode(body)
                .compose(RxSchedulers.<ResponseEntity<EmptyBean>>composeAll(App.getContext()))
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean emptyBean) {
                        // Logger.d(emptyBean.toString());
                    }
                });
    }

    public static void testRegister() {
        Gson gson  = new Gson();

        Map<String, Object> params = new HashMap<>();
        params.put("password", "test111222");
        params.put("registerName", "15626584674");
        params.put("type", 1);
        params.put("prefix", "86");
        params.put("verifyCode", "537464");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(params));
        RetrofitHelper.createApi(MainApi.class)
                .register(body)
                .compose(RxSchedulers.<ResponseEntity<UserBean>>ioMain())
                .subscribe(new DataObserver<UserBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        Logger.d(userBean.toString());
                    }
                });
    }

    public static void testLogin() {
        Gson gson  = new Gson();

        Map<String, Object> params = new HashMap<>();
        params.put("password", "test111222");
        params.put("loginName", "15626584674");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(params));
        RetrofitHelper.createApi(MainApi.class)
                .login(body)
                .compose(RxSchedulers.<ResponseEntity<UserBean>>ioMain())
                .subscribe(new DataObserver<UserBean>(App.getContext()) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        Logger.d(userBean.toString());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        super.onFailure(code, msg);
                    }
                });
    }

}
