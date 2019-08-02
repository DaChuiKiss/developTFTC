package oysd.com.trade_app.http.bean;

import java.util.List;

/**
 * PagingData bean of a response.
 * Created by Liam on 2018/7/12
 */
public class PagingDataBean<T> {

    /**
     * firstPage : true
     * item : [{}]
     * lastPage : true
     * page : 0
     * pageSize : 0
     * total : 0
     * totalPage : 0
     */

    private boolean firstPage;
    private boolean lastPage;
    private int page;
    private int pageSize;
    private int total;
    private int totalPage;
    private List<T> item;

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItem() {
        return item;
    }

    public void setItem(List<T> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "PagingDataBean{" +
                "firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", totalPage=" + totalPage +
                ", item=" + item +
                '}';
    }

}
