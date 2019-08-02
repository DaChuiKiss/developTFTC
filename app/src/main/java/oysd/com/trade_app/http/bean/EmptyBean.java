package oysd.com.trade_app.http.bean;

/**
 * Empty bean, no data in response.
 * Created by Liam on 2018/7/16
 */
public class EmptyBean {
    // EmptyBean 表示 返回的的 data 没有数据 。
    // 因此 data == null, 不能调用 emptyBean.toString()，会报 NullPointerException.
}
