package oysd.com.trade_app.modules.trade.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

import oysd.com.trade_app.util.EmptyUtils;

public class MarketInfoBean implements Parcelable{


    /**
     * legalTenderVal : 0
     * lowAmount : 0
     * marketId : 0
     * marketName : string
     * newestPrice : 0
     * priceChangeRatio : 0
     * sellCoinName : string
     * status : 0
     * symbol : string
     * topAmount : 0
     * volume : 0
     */

    private BigDecimal legalTenderVal;
    private BigDecimal lowAmount;
    private int marketId;
    private String marketName;
    private BigDecimal newestPrice;
    private BigDecimal priceChangeRatio;
    private String sellCoinName;
    private int status;
    private String symbol;

    public String getBuyCoinName() {
        return buyCoinName;
    }

    public void setBuyCoinName(String buyCoinName) {
        this.buyCoinName = buyCoinName;
    }

    private String buyCoinName;
    private BigDecimal topAmount;
    private BigDecimal volume;

    public BigDecimal getLegalTenderVal() {
        return legalTenderVal;
    }

    public void setLegalTenderVal(BigDecimal legalTenderVal) {
        this.legalTenderVal = legalTenderVal;
    }

    public BigDecimal getLowAmount() {
        return lowAmount;
    }

    public void setLowAmount(BigDecimal lowAmount) {
        this.lowAmount = lowAmount;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public BigDecimal getNewestPrice() {
        return newestPrice;
    }

    public void setNewestPrice(BigDecimal newestPrice) {
        this.newestPrice = newestPrice;
    }

    public BigDecimal getPriceChangeRatio() {
        return priceChangeRatio;
    }

    public void setPriceChangeRatio(BigDecimal priceChangeRatio) {
        this.priceChangeRatio = priceChangeRatio;
    }

    public String getSellCoinName() {
        return sellCoinName;
    }

    public void setSellCoinName(String sellCoinName) {
        this.sellCoinName = sellCoinName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getTopAmount() {
        return topAmount;
    }

    public void setTopAmount(BigDecimal topAmount) {
        this.topAmount = topAmount;
    }
    
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public static Creator<MarketInfoBean> getCREATOR() {
        return CREATOR;
    }

    protected MarketInfoBean(Parcel in) {
        legalTenderVal=new BigDecimal(in.readString());
        lowAmount=new BigDecimal(in.readString());
        marketId = in.readInt();
        marketName = in.readString();
        newestPrice=new BigDecimal(in.readString());
        priceChangeRatio=new BigDecimal(in.readString());
        sellCoinName = in.readString();
        status = in.readInt();
        symbol = in.readString();
        topAmount=new BigDecimal(in.readString());
        volume=new BigDecimal(in.readString());
    }

    public static final Creator<MarketInfoBean> CREATOR = new Creator<MarketInfoBean>() {
        @Override
        public MarketInfoBean createFromParcel(Parcel in) {
            return new MarketInfoBean(in);
        }

        @Override
        public MarketInfoBean[] newArray(int size) {
            return new MarketInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(legalTenderVal.toString());
        dest.writeString(lowAmount.toString());
        dest.writeInt(marketId);
        dest.writeString(marketName);
        dest.writeString(newestPrice.toString());
        dest.writeString(priceChangeRatio.toString());
        dest.writeString(sellCoinName);
        dest.writeInt(status);
        dest.writeString(symbol);
        dest.writeString(EmptyUtils.getBigDecimalValue(topAmount));
        dest.writeString(EmptyUtils.getBigDecimalValue(volume));
    }

    @Override
    public String toString() {
        return "MarketInfoBean{" +
                "legalTenderVal=" + legalTenderVal +
                ", lowAmount=" + lowAmount +
                ", marketId=" + marketId +
                ", marketName='" + marketName + '\'' +
                ", newestPrice=" + newestPrice +
                ", priceChangeRatio=" + priceChangeRatio +
                ", sellCoinName='" + sellCoinName + '\'' +
                ", status=" + status +
                ", symbol='" + symbol + '\'' +
                ", buyCoinName='" + buyCoinName + '\'' +
                ", topAmount=" + topAmount +
                ", volume=" + volume +
                '}';
    }
}
