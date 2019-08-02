package oysd.com.trade_app.http.bean;

/**
 * Status of a response.
 * Created by Liam on 2018/7/12
 */
public class StatusBean {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
