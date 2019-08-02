package oysd.com.trade_app.modules.otc.bean;

/**
 * Coin config bean.
 * Created by Liam on 2018/8/24.
 */
public class CoinConfigBean {

    /**
     * id : 7
     * coinId : 49
     * coinName : null
     * amountDecimal : 8
     * minTranscationAmount : 10
     * maxTranscationAmount : 100000
     * fee : 0.5
     * status : 0
     * stopSelling : 0
     * prohibitionPurchase : 0
     */

    private int id;
    private int coinId;
    private String coinName;
    private int amountDecimal;
    private double minTranscationAmount;
    private double maxTranscationAmount;
    private double fee;
    private int status;
    private int stopSelling;
    private int prohibitionPurchase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAmountDecimal() {
        return amountDecimal;
    }

    public void setAmountDecimal(int amountDecimal) {
        this.amountDecimal = amountDecimal;
    }

    public double getMinTranscationAmount() {
        return minTranscationAmount;
    }

    public void setMinTranscationAmount(double minTranscationAmount) {
        this.minTranscationAmount = minTranscationAmount;
    }

    public double getMaxTranscationAmount() {
        return maxTranscationAmount;
    }

    public void setMaxTranscationAmount(double maxTranscationAmount) {
        this.maxTranscationAmount = maxTranscationAmount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStopSelling() {
        return stopSelling;
    }

    public void setStopSelling(int stopSelling) {
        this.stopSelling = stopSelling;
    }

    public int getProhibitionPurchase() {
        return prohibitionPurchase;
    }

    public void setProhibitionPurchase(int prohibitionPurchase) {
        this.prohibitionPurchase = prohibitionPurchase;
    }

}
