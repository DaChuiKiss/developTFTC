package oysd.com.trade_app.modules.trade.bean;

import java.math.BigDecimal;

public class DealBean {

    /**
     * count : 0
     * price : 0
     * time : string
     * type : 0
     */

    private BigDecimal count;
    private BigDecimal price;
    private String time;
    private int type;

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
