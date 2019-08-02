package oysd.com.trade_app.modules.mycenter.http;

import java.math.BigDecimal;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.PagingDataBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.bean.AppVersionInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.AwardBean;
import oysd.com.trade_app.modules.mycenter.bean.CoinOrderBean;
import oysd.com.trade_app.modules.mycenter.bean.DividendBean;
import oysd.com.trade_app.modules.mycenter.bean.ExtractVirtualCoinParamBean;
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalOTCBean;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.MyInvitationBean;
import oysd.com.trade_app.modules.mycenter.bean.OTCConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.OTCOrderBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendBean;
import oysd.com.trade_app.modules.mycenter.bean.UserCostDividendRecord;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInviteReturnAmountBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.modules.mycenter.bean.VipLevelConfigBean;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.modules.otc.bean.OtcOrderBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Main Api.
 * Created by Liam on 2018/7/17
 */
public interface MycenterApi {

    @POST("user/api/v1/userinfo/addDealPwd")
    Observable<ResponseEntity<EmptyBean>> addDealPwd(@Body RequestBody body);

    @POST("user/api/v1/auth/authRealInfo")
    Observable<ResponseEntity<EmptyBean>> setCertPrimary(@Body RequestBody body);

    @POST("user/api/v1/auth/authAdvancedInfo ")
    Observable<ResponseEntity<EmptyBean>> setCertSenior(@Body RequestBody body);

    @GET("user/api/v1/auth/getAuthInfo")
    Observable<ResponseEntity<AuthInfoBean>> getgAuthInfo();

    @GET("user/api/v1/userinfo/getUserInfo")
    Observable<ResponseEntity<UserInfoBean>> getUserInfo();

    @PUT("user/api/v1/userinfo/updateDealPwd")
    Observable<ResponseEntity<EmptyBean>> updateDealPwd(@Body RequestBody body);

    @PUT("user/api/v1/userinfo/updateLoginPwd")
    Observable<ResponseEntity<EmptyBean>> updateLoginPwd(@Body RequestBody body);

    @Multipart
    @POST("base/api/v1/image/manager")
    Observable<ResponseEntity<ImageBean>> upImage(@Part MultipartBody.Part file);

    //绑定手机
    @PUT("user/api/v1/userinfo/updatePhone")
    Observable<ResponseEntity<EmptyBean>> bindPhone(@Body RequestBody body);

    //绑定邮箱
    @PUT("user/api/v1/userinfo/updateEmail")
    Observable<ResponseEntity<EmptyBean>> bindEmail(@Body RequestBody body);

    //登出
    @POST("user/v1/user/logout")
    Observable<ResponseEntity<EmptyBean>> logout();

    //获取账户资产
    @GET("financing/api/v1/coinWalletAmount/getTotalConvertInto")
    Observable<ResponseEntity<TotalConvertInfo>> getTotalConvertInfo();

    //获取OTC账户总资产
    @GET("financing/api/v1/user/online/coin/amount/getOnlineTotalAmount")
    Observable<ResponseEntity<OTCConvertInfo>> getOnlineTotalAmount();

    //用户凭证资产信息
    @GET("user/api/v1/userCertificateAmount/getCurrentUserLoginAccount")
    Observable<ResponsePaging<VoucherBean>> getCurrentUserLoginAccount();
    //用户凭证资产信息
    @GET("user/api/v1/UserOnlineTransctionCoinAmount/getCurrentUserLoginAccountByType")
    Observable<ResponseEntity<String>> getCurrentUserLoginAccountByType(@Query("type") String type, @Query("coinId") String coinId);


    //获取OTC账户总资产
    @GET("financing/api/v1/user/total/amount/getUserAccountTotalAmount")
    Observable<ResponseEntity<TotalConvertInfo>> getUserAccountTotalAmount();

    //修改手势密码
    @PUT("user/api/v1/userinfo/updateGesturePwd")
    Observable<ResponseEntity<EmptyBean>> updateGesturePwd(@Body RequestBody body);

    //获取币币账户
    @GET("financing/api/v1/coinWalletAmount/queryLegalTenderList")
    Observable<ResponsePaging<LegalTenderBean>> queryLegalTenderList(@Query("page") int page, @Query("limit") int limit, @Query("coinName") String coinName);

    //获取OTC账户列表
    @GET("financing/api/v1/user/online/coin/amount/getLegalTenderList")
    Observable<ResponsePaging<LegalOTCBean>> getLegalTenderList(@Query("page") int page, @Query("limit") int limit);


    //获取充值地址
    @GET("financing/api/v1/userWallet/getWalletAddress")
    Observable<ResponseEntity<WalletAddressBean>> getWalletAddress(@Query("coinId") int coinId);

    //获取提现参数
    @GET("financing/api/v1/userWallet/getExtractVirtualCoinParam")
    Observable<ResponseEntity<ExtractVirtualCoinParamBean>> getExtractVirtualCoinParam(@Query("coinId") int coinId);

    // 发送验证码
    @POST("user/v1/message/phone")
    Observable<ResponseEntity<EmptyBean>> sendVerCode(@Body RequestBody body);


    // 获取全部委托记录
    @GET("transaction/api/v1/user/entrust/all")
    Observable<ResponsePaging<CoinOrderBean>> getAll(@Query("page") int page,
                                                     @Query("limit") int limit);

    // 获取版本信息
    @GET("base/v1/appVersionInfo/getNewestAppVersionInfo")
    Observable<ResponseEntity<AppVersionInfoBean>> getNewestAppVersionInfo(@Query("type") String type);


    // 提现申请
    @POST("financing/api/v1/userWallet/sponsorTakeOutCoinApply")
    Observable<ResponseEntity<EmptyBean>> sponsorTakeOutCoinApply(@Body RequestBody body);

    //OTC划转接口
    @POST("financing/api/v1/userOnlineTransferAccounts/c2cTransferAccounts")
    Observable<ResponseEntity<EmptyBean>> c2cTransferAccounts(@Body RequestBody body);

    //凭证账户划转接口
    @PUT("financing/api/v1/userCertificateTransferAccounts/certificateTransferAccounts")
    Observable<ResponseEntity<EmptyBean>> certificateTransferAccounts(@Body RequestBody body);

    // 查询用户广告
    @GET("transaction/v1/userOnlineEntrustInfoService/getUserEntruserInfoList")
    Observable<ResponsePaging<OtcAdBean>> getAds(@Query("page") int page, @Query("limit") int limit,
                                                 @Query("userAccountId") int userAccountId);

    // 分页查询OTC订单
    @GET("transaction/api/v1/onlineOrderOperation/getOnlineOrderList")
    Observable<ResponsePaging<OtcOrderBean>> getOnlineOrderList(@Query("page") int page,
                                                                @Query("limit") int limit,
                                                                @Query("transactionType") String transactionType,
                                                                @Query("status") String status);

    // 返回佣金分红
    @GET("financing/api/v1/transaction/invite/return/amount/getUserInviteReturnAmount")
    Observable<ResponseEntity<UserInviteReturnAmountBean>> getUserInviteReturnAmount();

    //获取用户vip信息
    @GET("vip/api/v1/vip/getUserVipInfo")
    Observable<ResponseEntity<UserVipInfoBean>> getUserVipInfo();

    //获取挖矿总数
    @GET("/bourseCoin/api/v1/userExtractBourseCoinRecord/getUserCostDividendRecord")
    Observable<ResponseEntity<String>> getUserCostDividendRecord();

    //获取挖矿记录
    @GET("/bourseCoin/api/v1/userExtractBourseCoinRecord/getUserCostDividendRecordList")
    Observable<ResponsePaging<UserCostDividendRecord>> getUserCostDividendRecordList();

    //获取分红总额
    @GET("/bourseCoin/api/v1/userCostDividendRecord")
    Observable<ResponseEntity<DividendBean>> getDividendValue();

    // 获取 vip card 的配置信息。
    @GET("vip/api/v1/vip/getVipLevelConfig")
    Observable<ResponseList<VipLevelConfigBean>> getVipLevelConfigs();

    // 获取购买 vip card 的价格。
    @GET("vip/api/v1/vip/getCoinLegalTender")
    Observable<ResponseList<VipCardPriceBean>> getVipCardPrices(@Query("levelId") int levelId,
                                                                @Query("cardid") int cardId);

    // 用户购买 vip .
    @POST("vip/api/v1/vip/usersPurchaseVip")
    Observable<ResponseEntity<EmptyBean>> userPurchaseVip(@Body RequestBody body);

    // 更改用户 vip 权益.
    @PUT("vip/api/v1/vip/checkUserVipRigths")
    Observable<ResponseEntity<EmptyBean>> changeUserVipBenefit(@Query("code") String code,
                                                               @Query("userVipId") int userVipId);

    // 获取推荐年卡特惠价(不是会员时)
    @POST("vip/api/v1/vip/getfavorableYearCard")
    Observable<ResponseEntity<VipLevelConfigBean>> getFavorableYearCard();

    // 查询邀请码及邀请人数
    @GET("financing/api/v1/transaction/invite/return/amount/getHomeVo")
    Observable<ResponseEntity<HomeVoBean>> getHomeVo();

    // 查询邀请码及邀请人数
    @GET("base/api/v1/activityFront/getActivityInfo")
    Observable<ResponseEntity<MyInvitationBean>> getActivityInfo(@Query("type") int type);

    // 查询用户邀请列表
    @GET("financing/api/v1/transaction/invite/return/amount/getUserInvitationRecordList")
    Observable<ResponseEntity<EmptyBean>> getUserInvitationRecordList();

    // 查询用户邀请返佣列表
    @GET("financing/api/v1/transaction/invite/return/amount/getUserInvitationReturnRecordList")
    Observable<ResponseEntity<PagingDataBean<AwardBean>>> getUserInvitationReturnRecordList(@Query("page") int page,
                                                                                            @Query("limit") int limit);

    //虚拟币种兑换-提交
    @POST("financing/api/v1/coinExchange/coinExchange")
    Observable<ResponseEntity<EmptyBean>> coinExchange(@Body RequestBody body);

    //获取虚拟币兑换比例
    @GET("financing/api/v1/coinExchange/coinExchangeRatio")
    Observable<ResponseEntity<String>> coinExchangeRatio(@Query("fromCoinId") int fromCoinId,
                                                            @Query("toCoinId") int toCoinId);


}
