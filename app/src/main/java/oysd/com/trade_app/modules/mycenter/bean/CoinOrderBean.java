package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class CoinOrderBean {


    /**
     * billPair : string
     * createDate : 2018-01-01 12:10:10
     * entrustCount : 0
     * entrustPrice : 0
     * entrustStatus : 0
     * id : 0
     * reachedCount : 0
     * type : 0
     * unReachCount : 0
     */

    private String billPair;
    private String createDate;
    private BigDecimal entrustCount;
    private BigDecimal entrustPrice;
    private int entrustStatus;
    private int id;
    private BigDecimal reachedCount;
    private int type;
    private BigDecimal unReachCount;

    public String getBillPair() {
        return billPair;
    }

    public void setBillPair(String billPair) {
        this.billPair = billPair;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getEntrustCount() {
        return entrustCount;
    }

    public void setEntrustCount(BigDecimal entrustCount) {
        this.entrustCount = entrustCount;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public int getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(int entrustStatus) {
        this.entrustStatus = entrustStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getReachedCount() {
        return reachedCount;
    }

    public void setReachedCount(BigDecimal reachedCount) {
        this.reachedCount = reachedCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getUnReachCount() {
        return unReachCount;
    }

    public void setUnReachCount(BigDecimal unReachCount) {
        this.unReachCount = unReachCount;
    }
}
