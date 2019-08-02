package oysd.com.trade_app.modules.home.bean;

import java.math.BigDecimal;
import java.util.List;

public class UpListBean {


    /**
     * btcVolume : 0
     * dealKLineAmountList : [0]
     * legarTenderVal : 0
     * marketName : string
     * price : 0
     * priceChangeRatio : 0
     * symbol : string
     */

    private int marketId;
    private BigDecimal btcVolume;
    private BigDecimal legarTenderVal;
    private String marketName;
    private BigDecimal price;
    private BigDecimal priceChangeRatio;
    private String symbol;
    private List<Float> dealKLineAmountList;

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public BigDecimal getBtcVolume() {
        return btcVolume;
    }

    public void setBtcVolume(BigDecimal btcVolume) {
        this.btcVolume = btcVolume;
    }

    public BigDecimal getLegarTenderVal() {
        return legarTenderVal;
    }

    public void setLegarTenderVal(BigDecimal legarTenderVal) {
        this.legarTenderVal = legarTenderVal;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceChangeRatio() {
        return priceChangeRatio;
    }

    public void setPriceChangeRatio(BigDecimal priceChangeRatio) {
        this.priceChangeRatio = priceChangeRatio;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Float> getDealKLineAmountList() {
        return dealKLineAmountList;
    }

    public void setDealKLineAmountList(List<Float> dealKLineAmountList) {
        this.dealKLineAmountList = dealKLineAmountList;
    }
}
