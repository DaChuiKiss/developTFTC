package oysd.com.trade_app.http;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.util.EmptyUtils;
import retrofit2.Retrofit;

/**
 * Retrofit helper.
 * Created by Liam on 2018/7/12
 */
public class RetrofitHelper {

    // 多 client 模式暂时未使用。
    private static volatile RetrofitHelper instance = null;

    private Map<String, OkHttpClient> httpClientMap = new HashMap<>();
    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    private RetrofitHelper() {
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 对 Api service 进行实现。
     */
    public static <T> T createApi(@NonNull Class<T> clazz) {
        return createApi(clazz, Constants.BASE_URL);
    }

    public static <T> T createApi(@NonNull Class<T> clazz, @NonNull String baseUrl) {
        return createApi(clazz, baseUrl, false);
    }

    public static <T> T createApi(@NonNull Class<T> clazz, @NonNull String baseUrl,
                                  boolean needSignature) {
        if (EmptyUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException("BaseUrl can not be null.");
        }

        Retrofit retrofit = RetrofitClient.getInstance().getRetrofit(baseUrl);
        return retrofit.create(clazz);
    }

}
