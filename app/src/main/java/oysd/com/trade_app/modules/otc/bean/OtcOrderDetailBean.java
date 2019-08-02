package oysd.com.trade_app.modules.otc.bean;

import java.util.List;

/**
 * Created by Liam on 2018/8/25.
 */
public class OtcOrderDetailBean {

    /**
     * transactionPrice : 1000
     * transationQuantity : 20
     * transationAmount : 20000
     * orderNo : 201808271702370074
     * referenceNumber : 370074
     * drawee : a402826438@163.com
     * sellerUserPaymentMethod : [{"paymentMethod":5,"paymentMethodName":"银行卡"},{"paymentMethod":2,"paymentMethodName":"支付宝"}]
     * transctionCoinAmount : {"accountNumber":null,"accountUserName":null,"receivablesCodeUrl":null,"bankName":"中国银行","bankCardNumber":"123456789","bankCardUserName":"大佬"}
     * payee : null
     * currentLoginUser : 1
     * createDate : 2018-08-27 17:02:37
     * settlementCurrency : CNY
     * coinName : USDT
     */

    private double transactionPrice;
    private double transationQuantity;
    private double transationAmount;
    private String orderNo;
    private String referenceNumber;
    private String drawee;
    private TransactionCoinAccountBean transctionCoinAmount;
    private String payee;
    private int currentLoginUser;
    private String createDate;
    private long createDateTimeStamp;
    private String settlementCurrency;
    private String coinName;
    private List<PaymentMethodBean> sellerUserPaymentMethod;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDrawee() {
        return drawee;
    }

    public void setDrawee(String drawee) {
        this.drawee = drawee;
    }

    public TransactionCoinAccountBean getTransctionCoinAmount() {
        return transctionCoinAmount;
    }

    public void setTransctionCoinAmount(TransactionCoinAccountBean transctionCoinAmount) {
        this.transctionCoinAmount = transctionCoinAmount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public int getCurrentLoginUser() {
        return currentLoginUser;
    }

    public void setCurrentLoginUser(int currentLoginUser) {
        this.currentLoginUser = currentLoginUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getCreateDateTimeStamp() {
        return createDateTimeStamp;
    }

    public void setCreateDateTimeStamp(long createDateTimeStamp) {
        this.createDateTimeStamp = createDateTimeStamp;
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

    public List<PaymentMethodBean> getSellerUserPaymentMethod() {
        return sellerUserPaymentMethod;
    }

    public void setSellerUserPaymentMethod(List<PaymentMethodBean> sellerUserPaymentMethod) {
        this.sellerUserPaymentMethod = sellerUserPaymentMethod;
    }

}
