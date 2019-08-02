package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class OTCOrderBean {


    /**
     * coinId : 0
     * coinName : string
     * createDate : 2018-08-13T12:45:49.344Z
     * id : 0
     * onlineEntrustId : 0
     * orderNo : string
     * orderRefNo : string
     * status : 0
     * transactionPrice : 0
     * transactionType : 0
     * transationAmount : 0
     * transationQuantity : 0
     * userAccountId : 0
     * userAccountName : string
     */

    private int coinId;
    private String coinName;
    private String createDate;
    private int id;
    private int onlineEntrustId;
    private String orderNo;
    private String orderRefNo;
    private int status;
    private BigDecimal transactionPrice;
    private int transactionType;
    private BigDecimal transationAmount;
    private BigDecimal transationQuantity;
    private int userAccountId;
    private String userAccountName;

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOnlineEntrustId() {
        return onlineEntrustId;
    }

    public void setOnlineEntrustId(int onlineEntrustId) {
        this.onlineEntrustId = onlineEntrustId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderRefNo() {
        return orderRefNo;
    }

    public void setOrderRefNo(String orderRefNo) {
        this.orderRefNo = orderRefNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransationAmount() {
        return transationAmount;
    }

    public void setTransationAmount(BigDecimal transationAmount) {
        this.transationAmount = transationAmount;
    }

    public BigDecimal getTransationQuantity() {
        return transationQuantity;
    }

    public void setTransationQuantity(BigDecimal transationQuantity) {
        this.transationQuantity = transationQuantity;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName) {
        this.userAccountName = userAccountName;
    }
}
