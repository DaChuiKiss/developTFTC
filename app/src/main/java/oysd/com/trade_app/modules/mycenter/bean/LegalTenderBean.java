package oysd.com.trade_app.modules.mycenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import oysd.com.trade_app.util.EmptyUtils;

public class LegalTenderBean implements Parcelable{

    /**
     * balance : 0
     * btcConvertInto : 0
     * coinId : 0
     * coinName : string
     * freeze : 0
     * id : 0
     * lteConvertInto : 0
     * takeInStatus : 0
     * takeOutCost : 0
     * takeOutLeastNumber : 0
     * takeOutMaxNumber : 0
     * takeOutReminder : string
     * takeOutStatus : 0
     * totalAmount : 0
     */

    private BigDecimal balance;
    private BigDecimal btcConvertInto;
    private int coinId;
    private String coinName;
    private BigDecimal freeze;
    private int id;
    private BigDecimal lteConvertInto;
    private BigDecimal takeInStatus;
    private BigDecimal takeOutCost;
    private BigDecimal takeOutLeastNumber;
    private BigDecimal takeOutMaxNumber;
    private String takeOutReminder;
    private BigDecimal takeOutStatus;
    private BigDecimal totalAmount;

    protected LegalTenderBean(Parcel in) {
        balance = new BigDecimal(in.readString());
        btcConvertInto =new BigDecimal(in.readString());
        coinId = in.readInt();
        coinName = in.readString();
        freeze = new BigDecimal(in.readString());
        id = in.readInt();
        lteConvertInto =new BigDecimal(in.readString());
        takeInStatus = new BigDecimal(in.readString());
        takeOutCost = new BigDecimal(in.readString());
        takeOutLeastNumber = new BigDecimal(in.readString());
        takeOutMaxNumber = new BigDecimal(in.readString());
        takeOutReminder = in.readString();
        takeOutStatus = new BigDecimal(in.readString());
        totalAmount = new BigDecimal(in.readString());
    }

    public static final Creator<LegalTenderBean> CREATOR = new Creator<LegalTenderBean>() {
        @Override
        public LegalTenderBean createFromParcel(Parcel in) {
            return new LegalTenderBean(in);
        }

        @Override
        public LegalTenderBean[] newArray(int size) {
            return new LegalTenderBean[size];
        }
    };

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBtcConvertInto() {
        return btcConvertInto;
    }

    public void setBtcConvertInto(BigDecimal btcConvertInto) {
        this.btcConvertInto = btcConvertInto;
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

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getLteConvertInto() {
        return lteConvertInto;
    }

    public void setLteConvertInto(BigDecimal lteConvertInto) {
        this.lteConvertInto = lteConvertInto;
    }

    public BigDecimal getTakeInStatus() {
        return takeInStatus;
    }

    public void setTakeInStatus(BigDecimal takeInStatus) {
        this.takeInStatus = takeInStatus;
    }

    public BigDecimal getTakeOutCost() {
        return takeOutCost;
    }

    public void setTakeOutCost(BigDecimal takeOutCost) {
        this.takeOutCost = takeOutCost;
    }

    public BigDecimal getTakeOutLeastNumber() {
        return takeOutLeastNumber;
    }

    public void setTakeOutLeastNumber(BigDecimal takeOutLeastNumber) {
        this.takeOutLeastNumber = takeOutLeastNumber;
    }

    public BigDecimal getTakeOutMaxNumber() {
        return takeOutMaxNumber;
    }

    public void setTakeOutMaxNumber(BigDecimal takeOutMaxNumber) {
        this.takeOutMaxNumber = takeOutMaxNumber;
    }

    public String getTakeOutReminder() {
        return takeOutReminder;
    }

    public void setTakeOutReminder(String takeOutReminder) {
        this.takeOutReminder = takeOutReminder;
    }

    public BigDecimal getTakeOutStatus() {
        return takeOutStatus;
    }

    public void setTakeOutStatus(BigDecimal takeOutStatus) {
        this.takeOutStatus = takeOutStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static Creator<LegalTenderBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(EmptyUtils.getBigDecimalValue(balance));
        dest.writeString(EmptyUtils.getBigDecimalValue(btcConvertInto));
        dest.writeInt(coinId);
        dest.writeString(coinName);
        dest.writeString(EmptyUtils.getBigDecimalValue(freeze));
        dest.writeInt(id);
        dest.writeString(EmptyUtils.getBigDecimalValue(lteConvertInto));
        dest.writeString(EmptyUtils.getBigDecimalValue(takeInStatus));
        dest.writeString(EmptyUtils.getBigDecimalValue(takeOutCost));
        dest.writeString(EmptyUtils.getBigDecimalValue(takeOutLeastNumber));
        dest.writeString(EmptyUtils.getBigDecimalValue(takeOutMaxNumber));
        dest.writeString(takeOutReminder);
        dest.writeString(EmptyUtils.getBigDecimalValue(takeOutStatus));
        dest.writeString(EmptyUtils.getBigDecimalValue(totalAmount));
    }
}
