package oysd.com.trade_app.http.bean;

import java.util.List;

/**
 * Response 实体 list。
 * Created by Liam on 2018/7/16
 */
public class ResponseList<T> extends BaseResponse {

    private List<T> data;

    private PagingDataBean<EmptyBean> pagingData;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
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
        return "ResponseList{" +
                "data=" + data +
                ", pagingData=" + pagingData +
                '}';
    }

}
