package oysd.com.trade_app.modules.trade.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import oysd.com.trade_app.util.EmptyUtils;

public class MarketIdBean implements Parcelable {


    /**
     * buyCoinId : 0
     * buyCoinName : string
     * buyFreezeAmount : 0
     * buyUsableAmount : 0
     * marketId : 0
     * numberDecimals : 0
     * priceDecimals : 0
     * sellCoinId : 0
     * sellCoinName : string
     * sellFreezeAmount : 0
     * sellUsableAmount : 0
     */

    private int buyCoinId;
    private String buyCoinName;
    private BigDecimal buyFreezeAmount;
    private BigDecimal buyUsableAmount;
    private int marketId;
    private int numberDecimals;
    private int priceDecimals;
    private int sellCoinId;
    private String sellCoinName;
    private BigDecimal sellFreezeAmount;
    private BigDecimal sellUsableAmount;

    protected MarketIdBean(Parcel in) {
        buyCoinId = in.readInt();
        buyCoinName = in.readString();
        buyFreezeAmount =new BigDecimal(in.readString());
        buyUsableAmount = new BigDecimal(in.readString());
        marketId = in.readInt();
        numberDecimals = in.readInt();
        priceDecimals = in.readInt();
        sellCoinId = in.readInt();
        sellCoinName = in.readString();
        sellFreezeAmount = new BigDecimal(in.readString());
        sellUsableAmount = new BigDecimal(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(buyCoinId);
        dest.writeString(buyCoinName);
        dest.writeString(EmptyUtils.getBigDecimalValue(buyFreezeAmount));
        dest.writeString(EmptyUtils.getBigDecimalValue(buyUsableAmount));
        dest.writeInt(marketId);
        dest.writeInt(numberDecimals);
        dest.writeInt(priceDecimals);
        dest.writeInt(sellCoinId);
        dest.writeString(sellCoinName);
        dest.writeString(EmptyUtils.getBigDecimalValue(sellFreezeAmount));
        dest.writeString(EmptyUtils.getBigDecimalValue(sellUsableAmount));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MarketIdBean> CREATOR = new Creator<MarketIdBean>() {
        @Override
        public MarketIdBean createFromParcel(Parcel in) {
            return new MarketIdBean(in);
        }

        @Override
        public MarketIdBean[] newArray(int size) {
            return new MarketIdBean[size];
        }
    };

    public int getBuyCoinId() {
        return buyCoinId;
    }

    public void setBuyCoinId(int buyCoinId) {
        this.buyCoinId = buyCoinId;
    }

    public String getBuyCoinName() {
        return buyCoinName;
    }

    public void setBuyCoinName(String buyCoinName) {
        this.buyCoinName = buyCoinName;
    }

    public BigDecimal getBuyFreezeAmount() {
        return buyFreezeAmount;
    }

    public void setBuyFreezeAmount(BigDecimal buyFreezeAmount) {
        this.buyFreezeAmount = buyFreezeAmount;
    }

    public BigDecimal getBuyUsableAmount() {
        return buyUsableAmount;
    }

    public void setBuyUsableAmount(BigDecimal buyUsableAmount) {
        this.buyUsableAmount = buyUsableAmount;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getNumberDecimals() {
        return numberDecimals;
    }

    public void setNumberDecimals(int numberDecimals) {
        this.numberDecimals = numberDecimals;
    }

    public int getPriceDecimals() {
        return priceDecimals;
    }

    public void setPriceDecimals(int priceDecimals) {
        this.priceDecimals = priceDecimals;
    }

    public int getSellCoinId() {
        return sellCoinId;
    }

    public void setSellCoinId(int sellCoinId) {
        this.sellCoinId = sellCoinId;
    }

    public String getSellCoinName() {
        return sellCoinName;
    }

    public void setSellCoinName(String sellCoinName) {
        this.sellCoinName = sellCoinName;
    }

    public BigDecimal getSellFreezeAmount() {
        return sellFreezeAmount;
    }

    public void setSellFreezeAmount(BigDecimal sellFreezeAmount) {
        this.sellFreezeAmount = sellFreezeAmount;
    }

    public BigDecimal getSellUsableAmount() {
        return sellUsableAmount;
    }

    public void setSellUsableAmount(BigDecimal sellUsableAmount) {
        this.sellUsableAmount = sellUsableAmount;
    }

    public static Creator<MarketIdBean> getCREATOR() {
        return CREATOR;
    }
}
