package oysd.com.trade_app.http.bean;

/**
 * Base response for all responses.
 * Created by Liam on 2018/7/10
 */
public abstract class BaseResponse implements IModel {

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    @Override
    public boolean isAuthError() {
        // 具体需要看返回 code 定义。
        return false;
    }

    @Override
    public boolean isBizError() {
        // 具体需要看返回 code 定义。
        return false;
    }

    @Override
    public int getStatusCode() {
        if (status == null) {
            return -1;
        } else {
            return status.getCode();
        }
    }

    @Override
    public String getStatusMsg() {
        if (status == null) {
            return "Status object is null.";
        } else {
            return status.getMsg();
        }
    }

}
