package oysd.com.trade_app.modules.trade.bean;

import java.math.BigDecimal;

public class MarketListBean {


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
    private BigDecimal price;
    private BigDecimal priceChangeRatio;
    private String sellCoinName;
    private BigDecimal status;
    private String symbol;
    private BigDecimal topAmount;
    private BigDecimal volume;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect=false;

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

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
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
}
