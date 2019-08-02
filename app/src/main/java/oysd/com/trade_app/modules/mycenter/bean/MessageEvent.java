package oysd.com.trade_app.modules.mycenter.bean;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MessageEvent {
    private String message;
    private String data;
    public MessageEvent(String message){
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
