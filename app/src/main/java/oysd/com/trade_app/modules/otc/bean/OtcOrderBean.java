package oysd.com.trade_app.modules.otc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import oysd.com.trade_app.util.EmptyUtils;

/**
 * Otc order bean.
 * Created by Liam on 2018/8/25.
 */
public class OtcOrderBean implements Parcelable {

    /**
     * id : 66
     * transactionType : null
     * createDate : 2018-08-25 15:12:40
     * coinId : 49
     * coinName : null
     * status : 1
     * transactionPrice : 1000
     * transationQuantity : 12.34
     * transationAmount : 12346
     * userAccountId : null
     * userAccountName : null
     * orderNo : 201808251512400066
     * orderRefNo : null
     * onlineEntrustId : 12
     */

    private int id;
    private int transactionType;
    private String createDate;
    private int coinId;
    private String coinName;
    private int status;
    private BigDecimal transactionPrice;
    private BigDecimal transationQuantity;
    private BigDecimal transationAmount;
    private int userAccountId;
    private String userAccountName;
    private String orderNo;
    private String orderRefNo;
    private int onlineEntrustId;

    protected OtcOrderBean(Parcel in) {
        id = in.readInt();
        transactionType = in.readInt();
        createDate = in.readString();
        coinId = in.readInt();
        coinName = in.readString();
        status = in.readInt();
        transactionPrice = new BigDecimal(in.readDouble());
        transationQuantity = new BigDecimal(in.readDouble());
        transationAmount =new BigDecimal(in.readDouble());
        userAccountId = in.readInt();
        userAccountName = in.readString();
        orderNo = in.readString();
        orderRefNo = in.readString();
        onlineEntrustId = in.readInt();
    }

    public static final Creator<OtcOrderBean> CREATOR = new Creator<OtcOrderBean>() {
        @Override
        public OtcOrderBean createFromParcel(Parcel in) {
            return new OtcOrderBean(in);
        }

        @Override
        public OtcOrderBean[] newArray(int size) {
            return new OtcOrderBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

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

    public BigDecimal getTransationQuantity() {
        return transationQuantity;
    }

    public void setTransationQuantity(BigDecimal transationQuantity) {
        this.transationQuantity = transationQuantity;
    }

    public BigDecimal getTransationAmount() {
        return transationAmount;
    }

    public void setTransationAmount(BigDecimal transationAmount) {
        this.transationAmount = transationAmount;
    }

    public static Creator<OtcOrderBean> getCREATOR() {
        return CREATOR;
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

    public int getOnlineEntrustId() {
        return onlineEntrustId;
    }

    public void setOnlineEntrustId(int onlineEntrustId) {
        this.onlineEntrustId = onlineEntrustId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(transactionType);
        dest.writeString(createDate);
        dest.writeInt(coinId);
        dest.writeString(coinName);
        dest.writeInt(status);
        dest.writeString(EmptyUtils.getBigDecimalValue(transactionPrice));
        dest.writeString(EmptyUtils.getBigDecimalValue(transationQuantity));
        dest.writeString(EmptyUtils.getBigDecimalValue(transationAmount));
        dest.writeInt(userAccountId);
        dest.writeString(userAccountName);
        dest.writeString(orderNo);
        dest.writeString(orderRefNo);
        dest.writeInt(onlineEntrustId);
    }

}
