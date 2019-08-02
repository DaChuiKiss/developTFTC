package oysd.com.trade_app.main.http;

import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import oysd.com.trade_app.http.bean.CurrencyTypeBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Main Api.
 * Created by Liam on 2018/7/17
 */
public interface MainApi {

    // 发送验证码
    @POST("user/v1/message/phone")
    Observable<ResponseEntity<EmptyBean>> sendVerCode(@Body RequestBody body);

    // 注册
    @POST("user/v1/user/register")
    Observable<ResponseEntity<UserBean>> register(@Body RequestBody body);

    // 登录
    @POST("user/v1/user/login")
    Observable<ResponseEntity<UserBean>> login(@Body RequestBody body);

    @GET("base/v1/phoneCell/queryPhoneAll")
    Observable<ResponseList<CountryPhoneBean>> getCountryPhoneAll();

    @POST("user/v1/user/resetPwd")
    Observable<ResponseEntity<EmptyBean>> resetPwd(@Body RequestBody body);

    @GET("user/v1/user/user/exists")
    Observable<ResponseEntity<Boolean>> isExists(@Query("account") String account);

    @GET("base/v1/appConfig/urlList")
    Observable<ResponseList<UrlBean>> urlList();


    @POST("base/v1/captcha")
    Observable<ResponseEntity<EmptyBean>> captcha(@Body RequestBody body);

    //获取货币类型
    @GET("financing/v1/legalTender/queryAll")
    Observable<CurrencyTypeBean> queryAll();

    //提现-获取验证码
    @POST("financing/api/v1/userWallet/sendTakeOutMsg")
    Observable<ResponseEntity<EmptyBean>> sendTakeOutMsg();



}
