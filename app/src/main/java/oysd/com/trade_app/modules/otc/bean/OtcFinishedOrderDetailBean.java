package oysd.com.trade_app.modules.otc.bean;

/**
 * Otc 已完成订单详情。
 * Created by Liam on 2018/8/27.
 */
public class OtcFinishedOrderDetailBean {

    /**
     * buyerName : string
     * buyerOrSeller : 0
     * createDate : 2018-08-24T13:20:21.687Z
     * onlineOrderNo : string
     * paymentMethod : string
     * refOrderNo : string
     * sellerName : string
     * transactionPrice : 0
     * transationAmount : 0
     * transationQuantity : 0
     */

    private String buyerName;
    private int buyerOrSeller;
    private String createDate;
    private String onlineOrderNo;
    private String paymentMethod;
    private String refOrderNo;
    private String sellerName;
    private double transactionPrice;
    private double transationAmount;
    private double transationQuantity;
    private String settlementCurrency;
    private String coinName;


    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getBuyerOrSeller() {
        return buyerOrSeller;
    }

    public void setBuyerOrSeller(int buyerOrSeller) {
        this.buyerOrSeller = buyerOrSeller;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOnlineOrderNo() {
        return onlineOrderNo;
    }

    public void setOnlineOrderNo(String onlineOrderNo) {
        this.onlineOrderNo = onlineOrderNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRefOrderNo() {
        return refOrderNo;
    }

    public void setRefOrderNo(String refOrderNo) {
        this.refOrderNo = refOrderNo;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public double getTransationAmount() {
        return transationAmount;
    }

    public void setTransationAmount(double transationAmount) {
        this.transationAmount = transationAmount;
    }

    public double getTransationQuantity() {
        return transationQuantity;
    }

    public void setTransationQuantity(double transationQuantity) {
        this.transationQuantity = transationQuantity;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

}
