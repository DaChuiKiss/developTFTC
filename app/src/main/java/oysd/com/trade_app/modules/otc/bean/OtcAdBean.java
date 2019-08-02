package oysd.com.trade_app.modules.otc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * OTC trade bean.
 * Created by Liam on 2018/8/14
 */
public class OtcAdBean implements Parcelable {

    /**
     * id : 3
     * nickName : 18681551243
     * userAccountId : 130
     * onlineOrderNo : 201808151923160003
     * transactionType : 2
     * coinId : 30
     * settlementCurrency : 1
     * onlineOrderPrice : 100
     * onlineOrderQuantity : 100
     * minAmountTransation : 1
     * maxAmountTranscation : 10
     * balance : 100
     * status : 2
     * coinName : btc
     * userPaymentMethod : [{"paymentMethod":5,"paymentMethodName":"银行卡"}]
     */

    private int id;
    private String nickName;
    private int userAccountId;
    private String onlineOrderNo;
    private int transactionType;
    private int coinId;
    private int settlementCurrency;
    private BigDecimal onlineOrderPrice;
    private BigDecimal onlineOrderQuantity;
    private BigDecimal minAmountTransation;
    private BigDecimal maxAmountTranscation;
    private BigDecimal balance;
    private int status;
    private String coinName;
    private List<PaymentMethodBean> userPaymentMethod;

    protected OtcAdBean(Parcel in) {
        id = in.readInt();
        nickName = in.readString();
        userAccountId = in.readInt();
        onlineOrderNo = in.readString();
        transactionType = in.readInt();
        coinId = in.readInt();
        settlementCurrency = in.readInt();
        onlineOrderPrice = (BigDecimal) in.readSerializable();
        onlineOrderQuantity = (BigDecimal) in.readSerializable();
        minAmountTransation = (BigDecimal) in.readSerializable();
        maxAmountTranscation = (BigDecimal) in.readSerializable();
        balance = (BigDecimal) in.readSerializable();
        status = in.readInt();
        coinName = in.readString();
        userPaymentMethod = in.createTypedArrayList(PaymentMethodBean.CREATOR);
    }

    public static final Creator<OtcAdBean> CREATOR = new Creator<OtcAdBean>() {
        @Override
        public OtcAdBean createFromParcel(Parcel in) {
            return new OtcAdBean(in);
        }

        @Override
        public OtcAdBean[] newArray(int size) {
            return new OtcAdBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getOnlineOrderNo() {
        return onlineOrderNo;
    }

    public void setOnlineOrderNo(String onlineOrderNo) {
        this.onlineOrderNo = onlineOrderNo;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public int getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(int settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public BigDecimal getOnlineOrderPrice() {
        return onlineOrderPrice;
    }

    public void setOnlineOrderPrice(BigDecimal onlineOrderPrice) {
        this.onlineOrderPrice = onlineOrderPrice;
    }

    public BigDecimal getOnlineOrderQuantity() {
        return onlineOrderQuantity;
    }

    public void setOnlineOrderQuantity(BigDecimal onlineOrderQuantity) {
        this.onlineOrderQuantity = onlineOrderQuantity;
    }

    public BigDecimal getMinAmountTransation() {
        return minAmountTransation;
    }

    public void setMinAmountTransation(BigDecimal minAmountTransation) {
        this.minAmountTransation = minAmountTransation;
    }

    public BigDecimal getMaxAmountTranscation() {
        return maxAmountTranscation;
    }

    public void setMaxAmountTranscation(BigDecimal maxAmountTranscation) {
        this.maxAmountTranscation = maxAmountTranscation;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public List<PaymentMethodBean> getUserPaymentMethod() {
        return userPaymentMethod;
    }

    public void setUserPaymentMethod(List<PaymentMethodBean> userPaymentMethod) {
        this.userPaymentMethod = userPaymentMethod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickName);
        dest.writeInt(userAccountId);
        dest.writeString(onlineOrderNo);
        dest.writeInt(transactionType);
        dest.writeInt(coinId);
        dest.writeInt(settlementCurrency);
        dest.writeSerializable(onlineOrderPrice);
        dest.writeSerializable(onlineOrderQuantity);
        dest.writeSerializable(minAmountTransation);
        dest.writeSerializable(maxAmountTranscation);
        dest.writeSerializable(balance);
        dest.writeInt(status);
        dest.writeString(coinName);
        dest.writeTypedList(userPaymentMethod);
    }

}
