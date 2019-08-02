package oysd.com.trade_app.modules.trade.bean;

import java.math.BigDecimal;

public class BuyAndSaleListBean {


    /**
     * time : 16:22:24
     * price : 1
     * count : 0.1
     * id : 365
     */

    private String time;
    private BigDecimal price;
    private BigDecimal count;
    private int id;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
