package oysd.com.trade_app.modules.otc.http;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.otc.bean.AbbreviationBean;
import oysd.com.trade_app.modules.otc.bean.AppealInfoBean;
import oysd.com.trade_app.modules.otc.bean.BankItemBean;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.CoinInfoBean;
import oysd.com.trade_app.modules.otc.bean.DetailPaymentInfoBean;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.OtcFinishedOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderDetailBean;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * C2C Api.
 * Created by Liam on 2018/7/23
 */
public interface OtcApi {

    // 获取挂单列表信息
    @GET("transaction/v1/userOnlineEntrustInfoService/getUserEntruserInfoList")
    Observable<ResponsePaging<OtcAdBean>> getEntrustList(@Query("page") int page,
                                                         @Query("limit") int limit,
                                                         @Query("userAccountId") String userAccountId,
                                                         @Query("transactionType") String transactionType,
                                                         @Query("status") String status,
                                                         @Query("coinId") String coinId,
                                                         @Query("settlementCurrency") String settlementCurrency);

    // 用户发布广告
    @POST("transaction/v1/userOnlineEntrustInfoService/userOnlineEntrustInfo")
    Observable<ResponseEntity<EmptyBean>> postEntrustInfo(@Body RequestBody body);

    // 取消广告发布
    @PUT("transaction/v1/userOnlineEntrustInfoService/updateUserEntrusOrderStatus")
    Observable<ResponseEntity<EmptyBean>> cancelEntrustPost(@Query("id") int adId);


    // 用户下单
    @POST("transaction/api/v1/onlineOrderOperation/addUserOnlineOrderInfo")
    Observable<ResponseEntity<OtcOrderBean>> addOrder(@Body RequestBody body);

    // 取消订单
    @PUT("transaction/api/v1/onlineOrderOperation/userCancelOrder")
    Observable<ResponseEntity<EmptyBean>> cancelOrder(@Query("orderId") int orderId);

    // 订单已付款
    @PUT("transaction/api/v1/onlineOrderOperation/userOrderAlreadyPaid")
    Observable<ResponseEntity<EmptyBean>> userAlreadyPaid(@Query("orderId") int orderId,
                                                          @Query("receivableType") int receivableType);

    // 查询用户详细的付款信息
    @GET("transaction/api/v1/onlineOrderOperation/getUserAccountMsg")
    Observable<ResponseEntity<DetailPaymentInfoBean>> getDetailPaymentInfo(@Query("orderId") int orderId,
                                                                           @Query("paymentMethodType") int paymentMethodType);

    // 买家或卖家查询 otc 订单详情 （未完成时）
    @GET("transaction/api/v1/onlineOrderOperation/getSellerOrBuyerOnlineOrderMsg")
    Observable<ResponseEntity<OtcOrderDetailBean>> getOrderDetail(@Query("orderId") int orderId);

    // 放币
    @PUT("transaction/api/v1/onlineOrderOperation/userOrderPaymentMoney")
    Observable<ResponseEntity<EmptyBean>> releaseCoins(@Body RequestBody body);

    // 订单交易完成买卖双方查看订单信息
    @GET("transaction/api/v1/onlineOrderOperation/OnlineOrderTransactionShow")
    Observable<ResponseEntity<OtcFinishedOrderDetailBean>> getFinishedOrderDetail(@Query("orderId") int orderId);

    // 提起申诉
    @POST("transaction/api/v1/orderAppealOper/addUserOrderAppealInfo")
    Observable<ResponseEntity<EmptyBean>> submitAppeal(@Body RequestBody body);

    // 取消申诉
    @PUT("transaction/api/v1/orderAppealOper/userCancelOrderAppealInfo")
    Observable<ResponseEntity<EmptyBean>> cancelAppeal(@Query("appealId") int appealId);

    // 买卖双方查看申诉信息
    @GET("transaction/api/v1/orderAppealOper/getAppealInfo")
    Observable<ResponseEntity<AppealInfoBean>> getAppealInfo(@Query("orderId") int orderId);


    // 获取 银行列表 信息
    @GET("user/api/v1/userPaymentInfo/getBackName")
    Observable<ResponseList<BankItemBean>> getBankList(@Query("countryName") String countryName);

    // 获取用户支付信息
    @GET("user/api/v1/userPaymentInfo/getUserPaymentInfo")
    Observable<ResponseEntity<PaymentInfoBean>> getUserPaymentInfo(@Query("payType") int payType);


    // 新增用户 银行卡
    @POST("user/api/v1/userPaymentInfo/addUserBankCardPayConf")
    Observable<ResponseEntity<EmptyBean>> addBankAccount(@Body RequestBody body);

    // 修改用户 银行卡 信息
    @PUT("user/api/v1/userPaymentInfo/updateUserBankCardPayConf")
    Observable<ResponseEntity<EmptyBean>> updateBankAccount(@Body RequestBody body);

    // 修改用户 银行卡 状态
    @PUT("user/api/v1/userPaymentInfo/updateUserBankCardPayConfStatus")
    Observable<ResponseEntity<EmptyBean>> updateBankAccountStatus(@Body RequestBody body);

    // 新增 网络账号
    @POST("user/api/v1/userPaymentInfo/addUserNetworkAccount")
    Observable<ResponseEntity<EmptyBean>> addNetAccount(@Body RequestBody body);

    // 修改 网络账号 信息
    @PUT("user/api/v1/userPaymentInfo/updateUserNetworkAccountConf")
    Observable<ResponseEntity<EmptyBean>> updateNetAccount(@Body RequestBody body);

    // 修改 网络账号 状态
    @PUT("user/api/v1/userPaymentInfo/updateUserNetworkAccountStatus")
    Observable<ResponseEntity<EmptyBean>> updateNetAccountStatus(@Body RequestBody body);

    // 查询所有可用 coin 信息
    @GET("transaction/api/v1/coinInfo/queryAll")
    Observable<ResponseList<CoinInfoBean>> queryAllCoins();

    // 查询所有 coin 挂单配置信息
    @GET("market/v1/transctionOTCCoinCoinfig/getAllOTCTransctionConfig")
    Observable<ResponseList<CoinConfigBean>> getCoinsConfig(@Query("transcationType") int transactionType);

    // 通过币种id和法币名称获取法币兑换值
    @GET("financing/v1/legalTender/queryByCoinIdAndAbbreviation")
    Observable<ResponseEntity<AbbreviationBean>> queryByCoinIdAndAbbreviation(@Query("abbreviation") String abbreviation, @Query("exchangeCoinId") String exchangeCoinId);


}
