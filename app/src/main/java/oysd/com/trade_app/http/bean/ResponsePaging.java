package oysd.com.trade_app.http.bean;

/**
 * Created by Liam on 2018/8/2
 */
public class ResponsePaging<T> extends BaseResponse {

    private EmptyBean data;

    private PagingDataBean<T> pagingData;

    public EmptyBean getData() {
        return data;
    }

    public void setData(EmptyBean data) {
        this.data = data;
    }

    public PagingDataBean<T> getPagingData() {
        return pagingData;
    }

    public void setPagingData(PagingDataBean<T> pagingData) {
        this.pagingData = pagingData;
    }

    @Override
    public boolean isNull() {
        return pagingData.getItem() == null;
    }

    @Override
    public String toString() {
        return "ResponsePaging{" +
                "data=" + data +
                ", pagingData=" + pagingData +
                '}';
    }

}
