package oysd.com.trade_app.http;

/**
 * Created by Liam on 2018/7/17
 */
public interface OnFinishListener<T> {

    void onSuccess(T t);

    /**
     * @param code 用于区分网络还是数据请求
     *             0 : 没有网络
     *             1 ：请求成功返回数据失败
     *             2 ：服务器异常了
     * @param msg  Error message
     */
    void onFailure(int code, String msg);

}