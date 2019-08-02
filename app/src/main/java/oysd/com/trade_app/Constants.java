package oysd.com.trade_app;

/**
 * @author houmingkuan
 * @time 2019/7/19
 * @desc Constants
 */
public final class Constants {

    public final static boolean TEST_MODE = true;//false正式;true测试

    private Constants() {
        // 防止实例化
        // Prevents from being instantiated.
    }

    /**
     * 接口服务器地址
     * ture-新测试环境
     * false-预生产环境
     *
     * @desc BASE_URL 需要以 "/" 结尾，否则 Retrofit 会报错。
     */
    public static final String BASE_URL = TEST_MODE ? "http://118.190.58.226:9021/" : "http://api.opal.vip:9021/";

    /**
     * 分析好友注册链接
     */
    public static final String INVITATION_URL = TEST_MODE ? "http://118.190.59.77:80/my_invitation?ref=" : "www.opal.vip/my_invitation?ref=";

    /**
     * Web Socket 相关
     */
    public static final String BASE_WEB_SOCKET_URL = TEST_MODE ? "ws://118.190.58.226:9021/market/webSocket/websocket" : "ws://api.opal.vip:9021/market/webSocket/websocket";

    /**
     * 中文静态页面URL
     */
    public static final String URL = "http://bourse.oss-cn-shenzhen.aliyuncs.com";//web地址
    public static final String AD_URL = "http://bourse.oss-cn-shenzhen.aliyuncs.com/notice/dist/index.html#/notice/?id=";//公告详情地址
    public static final String H5_URL = "https://tftc-token.oss-ap-southeast-1.aliyuncs.com";//h5

    /**
     * 我的会员所用
     */
    public static final String LIFELONG_CARD_END_TIME_DOT = "2999.12.31";
    public static final String LIFELONG_CARD_END_TIME_LINE = "2999-12-31";

}
