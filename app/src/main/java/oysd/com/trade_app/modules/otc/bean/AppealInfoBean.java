package oysd.com.trade_app.modules.otc.bean;

import java.util.List;

/**
 * 申诉记录 bean.
 * Created by Liam on 2018/8/29.
 */
public class AppealInfoBean {

    /**
     * id : 15
     * orderNo : 201808251615460069
     * createDate : 2018-08-29 11:28:20
     * coinName : null
     * settlementCurrencyName : null
     * transactionPrice : 1000
     * transationQuantity : null
     * transationAmount : 10000
     * buyerUserName : 15626584674
     * sellUserName : a402826438@163.com
     * statusVal : null
     * receivableType : 5
     * receivableAccount : null
     * appealGrounds : 没给钱洪LOL默默哦
     * appealImageUrls : ["http://bourse.oss-cn-shenzhen.aliyuncs.com/image/2018-08-29/403a5c5d-55fd-43bd-b9d3-34656ed2ad39.png"]
     * appealFeedback : null
     * referenceNumber : 460069
     * currentUser : 1
     * complainant : 1
     */

    private int id;
    private String orderNo;
    private String createDate;
    private String coinName;
    private String settlementCurrencyName;
    private double transactionPrice;
    private double transationQuantity;
    private double transationAmount;
    private String buyerUserName;
    private String sellUserName;
    private String statusVal;
    private int receivableType;
    private String receivableAccount;
    private String appealGrounds;
    private String appealFeedback;
    private String referenceNumber;
    private int currentUser;
    private int complainant;
    private List<String> appealImageUrls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getSettlementCurrencyName() {
        return settlementCurrencyName;
    }

    public void setSettlementCurrencyName(String settlementCurrencyName) {
        this.settlementCurrencyName = settlementCurrencyName;
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public double getTransationQuantity() {
        return transationQuantity;
    }

    public void setTransationQuantity(double transationQuantity) {
        this.transationQuantity = transationQuantity;
    }

    public double getTransationAmount() {
        return transationAmount;
    }

    public void setTransationAmount(double transationAmount) {
        this.transationAmount = transationAmount;
    }

    public String getBuyerUserName() {
        return buyerUserName;
    }

    public void setBuyerUserName(String buyerUserName) {
        this.buyerUserName = buyerUserName;
    }

    public String getSellUserName() {
        return sellUserName;
    }

    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }

    public String getStatusVal() {
        return statusVal;
    }

    public void setStatusVal(String statusVal) {
        this.statusVal = statusVal;
    }

    public int getReceivableType() {
        return receivableType;
    }

    public void setReceivableType(int receivableType) {
        this.receivableType = receivableType;
    }

    public String getReceivableAccount() {
        return receivableAccount;
    }

    public void setReceivableAccount(String receivableAccount) {
        this.receivableAccount = receivableAccount;
    }

    public String getAppealGrounds() {
        return appealGrounds;
    }

    public void setAppealGrounds(String appealGrounds) {
        this.appealGrounds = appealGrounds;
    }

    public String getAppealFeedback() {
        return appealFeedback;
    }

    public void setAppealFeedback(String appealFeedback) {
        this.appealFeedback = appealFeedback;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public int getComplainant() {
        return complainant;
    }

    public void setComplainant(int complainant) {
        this.complainant = complainant;
    }

    public List<String> getAppealImageUrls() {
        return appealImageUrls;
    }

    public void setAppealImageUrls(List<String> appealImageUrls) {
        this.appealImageUrls = appealImageUrls;
    }

}
