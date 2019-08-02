package oysd.com.trade_app.modules.home.http;

import io.reactivex.Observable;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.home.bean.AnnouncementBean;
import oysd.com.trade_app.modules.home.bean.BannerBean;
import oysd.com.trade_app.modules.home.bean.TimeZoneInfoBean;
import oysd.com.trade_app.modules.home.bean.UpListBean;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Main Api.
 * Created by Liam on 2018/7/17
 */
public interface HomeApi {

    @GET("base/v1/image/getAppBanner")
    Observable<ResponseList<BannerBean>> getBanner();

    @GET("market/v1/marketInfo/getMarketPriceChangeRatioList")
    Observable<ResponseList<UpListBean>> getMarketPriceChangeRatioList(@Query("type") long account);

    //获取otc公告
    @GET("base/v1/announFront/otcShowAnnouncementList")
    Observable<ResponseList<AnnouncementBean>> otcShowAnnouncementList();

    //获取首页轮播公告
    @GET("base/v1/announFront/queryShowList")
    Observable<ResponseList<AnnouncementBean>> queryShowList();

    // 获取 server 使用的 TimeZone
    @GET("base/v1/timeZone")
    Observable<ResponseEntity<TimeZoneInfoBean>> getTimeZone();
}
