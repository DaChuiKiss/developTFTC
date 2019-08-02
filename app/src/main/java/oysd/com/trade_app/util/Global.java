package oysd.com.trade_app.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import oysd.com.trade_app.main.bean.UrlBean;

public class Global {

    public static boolean isLogin = false;
    public static boolean isFirst = true;
    public static boolean isVip=false;
    //定时刷新时间间隔
    public static int delayTime=5;
    //如果是买入卖出或者第一次注册跳转到实名认证则不需要跳转到高级认证。
    public static boolean isToSenior=false;
    

    //是子类跳转到LoginActivity
    public static boolean isChildActivity=false;

    public static String url = "http://a.hiphotos.baidu.com/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static List<UrlBean> urlList =new ArrayList<UrlBean>();

}
