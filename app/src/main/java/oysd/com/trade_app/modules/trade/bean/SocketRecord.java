package oysd.com.trade_app.modules.trade.bean;

import java.math.BigDecimal;

public class SocketRecord {

    /**
     * date : 1534734879818
     * price : 1
     * type : 1
     * volume : 10
     */

    private String date;
    private BigDecimal price;
    private int type;
    private BigDecimal volume;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
