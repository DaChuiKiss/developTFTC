package oysd.com.trade_app.modules.mycenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import oysd.com.trade_app.util.EmptyUtils;

public class LegalOTCBean implements Parcelable{


    /**
     * availableAmount : 0
     * coinId : 0
     * coinName : string
     * frozenAmount : 0
     */

    private BigDecimal availableAmount;
    private int coinId;
    private String coinName;
    private BigDecimal frozenAmount;

    protected LegalOTCBean(Parcel in) {
        availableAmount = new BigDecimal(in.readString());
        coinId = in.readInt();
        coinName = in.readString();
        frozenAmount = new BigDecimal(in.readString());
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public static Creator<LegalOTCBean> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<LegalOTCBean> CREATOR = new Creator<LegalOTCBean>() {
        @Override
        public LegalOTCBean createFromParcel(Parcel in) {
            return new LegalOTCBean(in);
        }

        @Override
        public LegalOTCBean[] newArray(int size) {
            return new LegalOTCBean[size];
        }
    };



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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(EmptyUtils.getBigDecimalValue(availableAmount));
        dest.writeInt(coinId);
        dest.writeString(coinName);
        dest.writeString(EmptyUtils.getBigDecimalValue(frozenAmount));
    }
}
