package oysd.com.trade_app.http.bean;

/**
 * Model 层根基 interface.
 * Created by Liam on 2018/7/16
 */
public interface IModel {

    /**
     * 返回数据是否为 null。
     */
    boolean isNull();

    /**
     * 是否是验证错误。
     */
    boolean isAuthError();

    /**
     * 是否是业务错误。
     */
    boolean isBizError();

    /**
     * 返回状态码。
     */
    int getStatusCode();

    /**
     * 返回状态消息。
     */
    String getStatusMsg();

}
