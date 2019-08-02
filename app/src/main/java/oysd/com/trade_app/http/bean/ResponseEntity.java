package oysd.com.trade_app.http.bean;

/**
 * Response 实体类。
 * Created by Liam on 2018/7/16
 */
public class ResponseEntity<T> extends BaseResponse {

    private T data;
    private PagingDataBean<EmptyBean> pagingData;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PagingDataBean<EmptyBean> getPagingData() {
        return pagingData;
    }

    public void setPagingData(PagingDataBean<EmptyBean> pagingData) {
        this.pagingData = pagingData;
    }

    @Override
    public boolean isNull() {
        return data == null;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "data=" + data +
                ", pagingData=" + pagingData +
                '}';
    }

}
