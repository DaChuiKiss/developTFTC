package oysd.com.trade_app.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import oysd.com.trade_app.http.interceptor.HeaderInterceptor;
import oysd.com.trade_app.http.interceptor.HttpLoggingInterceptor;
import oysd.com.trade_app.modules.mycenter.view.SettingAccountPwdActivity;
import oysd.com.trade_app.util.DESUtil;
import oysd.com.trade_app.util.Logger;

/**
 * Http client.
 * Created by Liam on 2018/7/16
 */
public class HttpClient {

    // timeout : 10s
    private static final long HTTP_CONNECT_TIMEOUT = 10;
    private static final long HTTP_READ_TIMEOUT = 10;
    private static final long HTTP_WRITE_TIMEOUT = 10;

    private static volatile HttpClient instance = null;
    private OkHttpClient.Builder builder;

    private HttpClient() {
        initDefaultBuilder();
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }
    //声明响应对象
    private Response response;
    private void initDefaultBuilder() {
        builder = new OkHttpClient.Builder();

        builder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);

        // header interceptor.
        builder.addInterceptor(new HeaderInterceptor());


//        // timestamp interceptor.
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                //获取原始的Requset请求
//                Request originalRequest = chain.request();
//                //获取请求的方法
//                String method = originalRequest.method();
//                //判断是GET请求还是POST请求
//                if("GET".equals(method)){
//                    HttpUrl httpUrl = originalRequest.url()
//                            .newBuilder()
//                            .addQueryParameter("timestamp",System.currentTimeMillis()+"")
//                            .build();
//                    //获取添加公共参数之后的requset对象
//                    Request request = new Request.Builder().url(httpUrl).build();
//                    //发送拼接完成后的请求
//                    response = chain.proceed(request);
//                }else if("POST".equals(method)){
//                    RequestBody requestBody = originalRequest.body();
//                    if(requestBody instanceof FormBody){
//                        FormBody.Builder builder = new FormBody.Builder();
//                        FormBody originalFormBody = (FormBody) originalRequest.body();
//                        for (int i = 0; i < originalFormBody.size(); i++) {
//                            builder.add(originalFormBody.name(i),originalFormBody.value(i));
//                        }
//                        builder.add("timestamp",System.currentTimeMillis()+"");
//                        FormBody formBody = builder.build();
//                        Request request = originalRequest.newBuilder().post(formBody).build();
//                        response = chain.proceed(request);
//                    }else{
//                        response = chain.proceed(originalRequest);
//                    }
//                }else if ("PUT".equals(method)){
//                    RequestBody requestBody = originalRequest.body();
//                    if(requestBody instanceof FormBody){
//                        FormBody.Builder builder = new FormBody.Builder();
//                        FormBody originalFormBody = (FormBody) originalRequest.body();
//                        for (int i = 0; i < originalFormBody.size(); i++) {
//                            builder.add(originalFormBody.name(i),originalFormBody.value(i));
//                        }
//                        builder.add("timestamp",System.currentTimeMillis()+"");
//                        FormBody formBody = builder.build();
//                        Request request = originalRequest.newBuilder().post(formBody).build();
//                        response = chain.proceed(request);
//                    }else{
//                        response = chain.proceed(originalRequest);
//                    }
//                }
//                return response;
//            }
//        });
//        // signature  interceptor
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                //获取原始的Requset请求
//                Request originalRequest = chain.request();
//                //拿到所有Query的Key
//                Set<String> queryKeyName = originalRequest
//                        .url()
//                        .queryParameterNames();
//                TreeMap<String, Object> params = new TreeMap<>();
//                //将query -> body
//                for (String s : queryKeyName) {
//                    params.put(s, originalRequest.url().queryParameterValues(s).get(0));
//                    Logger.d("------------------------->"+s+"--->"+originalRequest.url().queryParameterValues(s).get(0));
//                }
//                //加密之后的数据
//                String i0elHQHLKqllnZeK = DESUtil.sign("I0elHQHLKqllnZeK", params);
//                Logger.d("-------------------------DDDDDDDDDD>"+i0elHQHLKqllnZeK);
//
//                //获取请求的方法
//                String method = originalRequest.method();
//                //判断是GET请求还是POST请求
//                if("GET".equals(method)){
//                    HttpUrl httpUrl = originalRequest.url()
//                            .newBuilder()
//                            .addQueryParameter("sign",i0elHQHLKqllnZeK)
//                            .build();
//                    //获取添加公共参数之后的requset对象
//                    Request request = new Request.Builder().url(httpUrl).build();
//                    //发送拼接完成后的请求
//                    response = chain.proceed(request);
//                }else if("POST".equals(method)){
//                    RequestBody requestBody = originalRequest.body();
//                    if(requestBody instanceof FormBody){
//                        FormBody.Builder builder = new FormBody.Builder();
//                        FormBody originalFormBody = (FormBody) originalRequest.body();
//                        for (int i = 0; i < originalFormBody.size(); i++) {
//                            builder.add(originalFormBody.name(i),originalFormBody.value(i));
//                        }
//                        builder.add("sign",i0elHQHLKqllnZeK);
//                        FormBody formBody = builder.build();
//                        Request request = originalRequest.newBuilder().post(formBody).build();
//                        response = chain.proceed(request);
//                    }else{
//                        Logger.d("bbbbbbbbbbbbbbbbbbbbb");
//                        response = chain.proceed(originalRequest);
//                    }
//                }else if ("PUT".equals(method)){
//                    RequestBody requestBody = originalRequest.body();
//                    if(requestBody instanceof FormBody){
//                        FormBody.Builder builder = new FormBody.Builder();
//                        FormBody originalFormBody = (FormBody) originalRequest.body();
//                        for (int i = 0; i < originalFormBody.size(); i++) {
//                            builder.add(originalFormBody.name(i),originalFormBody.value(i));
//                        }
//                        builder.add("sign",i0elHQHLKqllnZeK);
//                        FormBody formBody = builder.build();
//                        Request request = originalRequest.newBuilder().post(formBody).build();
//                        response = chain.proceed(request);
//                    }else{
//                        response = chain.proceed(originalRequest);
//                    }
//                }
//                return response;
//            }
//        });


        // ssl setting.
        builder.sslSocketFactory(
                TrustManager.getUnsafeSSLSocketFactory(), TrustManager.getX509TrustManager());
        builder.hostnameVerifier(new TrustManager.TrustAllHostnameVerifier());

        // logging interceptor.
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public OkHttpClient getClient() {
        return builder.build();
    }

}

