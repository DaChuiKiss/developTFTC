package oysd.com.trade_app.modules.trade.http;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.CurrentBean;
import oysd.com.trade_app.modules.trade.bean.DealBean;
import oysd.com.trade_app.modules.trade.bean.DealZoneBean;
import oysd.com.trade_app.modules.trade.bean.HistoryBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.MarketListBean;
import oysd.com.trade_app.modules.trade.bean.OrderRecordBean;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Trade APIs.
 * Created by Liam on 2018/7/23
 */
public interface TradeApi {

    // 查询所有启用的分区信息
    @GET("transaction/v1/dealZoneInfo/queryAll")
    Observable<ResponseList<DealZoneBean>> reqDealZoneInfo(@Query("page") int page,
                                                           @Query("limit") int limit);

    // 委托 - 买入&卖出
    @POST("transaction/api/v1/user/entrust")
    Observable<ResponseEntity<EmptyBean>> reqEntrust(@Body RequestBody body);

    // 查询当前委托记录
    @GET("transaction/api/v1/user/entrust/current")
    Observable<ResponseList<OrderRecordBean>> reqCurrentOrders(@Query("page") int page,
                                                               @Query("limit") int limit,
                                                               @Query("marketId") int marketId);

    // 查询历史委托记录
    @GET("transaction/api/v1/user/entrust/history")
    Observable<ResponseList<OrderRecordBean>> reqOrdersHistory(@Query("page") int page,
                                                               @Query("limit") int limit,
                                                               @Query("marketId") int marketId);

    // 批量撤单
    @PUT("transaction/api/v1/user/entrust/rollback")
    Observable<ResponseEntity<EmptyBean>> reqRollback(@Query("marketId") int marketId);

    // 单个撤单
    @PUT("transaction/api/v1/user/entrust/rollbackOne")
    Observable<ResponseEntity<EmptyBean>> reqRollbackOne(@Query("marketId") int marketId);
    //根据交易分区ID查询交易市场列表
    @GET("market/v1/marketList/getTransactionMarketList")
    Observable<ResponseList<MarketListBean>> getTransactionMarketList(@Query("page") int page,
                                                                      @Query("limit") int limit, @Query("zoneId") int zoneId);
    //获取首页涨幅
    @GET("market/v1/marketInfo/getMarketPriceChangeRatioList")
    Observable<ResponseList<MarketListBean>> getMarketPriceChangeRatioList(@Query("page") int page,
                                                                      @Query("limit") int limit, @Query("zoneId") int zoneId);

    //通过字符串搜索制定币
    @GET("market/v1/marketList/getTransactionMarketList")
    Observable<ResponseList<MarketListBean>> getTransactionMarketList(@Query("inputChar") String zoneId);


    @GET("market/v1/marketList/getTransactionMarketInfo")
    Observable<ResponseEntity<MarketInfoBean>> getTransactionMarketInfo(@Query("marketId") int marketId);

    @GET("user/api/v1/optionalMarket/getUserOptionalMarket")
    Observable<ResponseList<MarketListBean>> getUserOptionalMarket();


    @POST("user/api/v1/optionalMarket/addOptional")
    Observable<ResponseEntity<EmptyBean>> addOptional(@Body RequestBody body);


    @DELETE("user/api/v1/optionalMarket/removeOptional")
    Observable<ResponseEntity<EmptyBean>> removeOptional(@Query("marketId") int marketId);

    @GET("transaction/api/v1/user/entrust/current")
    Observable<ResponsePaging<CurrentBean>> current(@Query("page") int page,
                                                    @Query("limit") int limit, @Query("marketId") int marketId, @Query("type") String type);


    @GET("transaction/api/v1/user/entrust/history")
    Observable<ResponsePaging<HistoryBean>> history(@Query("page") int page,
                                                    @Query("limit") int limit, @Query("marketId") int marketId, @Query("entrustStatus") String type);




    //获取买入卖出可用数量
    @GET("financing/api/v1/coinWalletAmount/getByMarketId")
    Observable<ResponseEntity<MarketIdBean>> getByMarketId(@Query("marketId") int marketId);


    //获取买入卖出挂单
    @GET("market/v1/marketList/getUserEntrustRecordList")
    Observable<ResponseList<BuyAndSaleListBean>> getUserEntrustRecordList(@Query("limit") int limit, @Query("marketId") int marketId, @Query("type") int type);


    //撤销全部
    @DELETE("transaction/api/v1/user/entrust/rollback")
    Observable<ResponseEntity<EmptyBean>> rollback(@Query("marketId") int marketId,@Query("type") String type);

    //撤销指定
    @DELETE("transaction/api/v1/user/entrust/rollbackOne")
    Observable<ResponseEntity<EmptyBean>> rollbackOne(@Query("id") int id);


    //获取买入卖出挂单
    @GET("market/v1/marketList/getUserReachedList")
    Observable<ResponseList<DealBean>> getUserReachedList(@Query("limit") int limit, @Query("marketId") int marketId);

    //获取未登录时自选股信息
    @GET(" market/v1/marketList/getOptionalTransactionMarketList")
    Observable<ResponseList<MarketListBean>> getOptionalTransactionMarketList(@Query("marketIds") ArrayList<Integer> marketId);

}
