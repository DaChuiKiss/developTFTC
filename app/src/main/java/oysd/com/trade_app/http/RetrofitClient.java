package oysd.com.trade_app.http;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit client.
 * Created by Liam on 2018/7/16
 */
public class RetrofitClient {

    private static volatile RetrofitClient instance = null;
    private Retrofit.Builder retrofitBuilder;

    private RetrofitClient() {
        initDefaultBuilder();
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    private void initDefaultBuilder() {
        retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClient.getInstance().getClient());

    }

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }

    public Retrofit getRetrofit(@NonNull String baseUrl) {
        return retrofitBuilder
                .baseUrl(baseUrl)
                .build();
    }

}
