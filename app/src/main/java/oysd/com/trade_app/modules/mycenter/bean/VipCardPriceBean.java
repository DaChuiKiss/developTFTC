package oysd.com.trade_app.modules.mycenter.bean;

/**
 * vip card 价格 bean
 * Created by Liam on 2018/9/21.
 */
public class VipCardPriceBean {

    /**
     * coinId : 48
     * coinName : BTC
     * price : 0.5
     */

    private int coinId;
    private String coinName;
    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
