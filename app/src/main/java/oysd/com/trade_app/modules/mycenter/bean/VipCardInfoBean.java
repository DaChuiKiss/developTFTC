package oysd.com.trade_app.modules.mycenter.bean;

/**
 * 会员卡 info bean .
 * Created by Liam on 2018/9/20.
 */
public class VipCardInfoBean {

    /**
     * levelId : 2
     * cardId : 1
     * cardName : month
     * price : 0
     * cardHoldDay : 30
     * giveBourseCoinNumber : 100
     * initGiveRatio : 10
     * everyMonthGiveRatio : 10
     * giveDividendNumber : 100
     * dealCostDiscount : 10
     */

    private int levelId;
    private int cardId;
    private String cardName;
    private String price;
    private int cardHoldDay;
    private int giveBourseCoinNumber;
    private int initGiveRatio;
    private int everyMonthGiveRatio;
    private int giveDividendNumber;
    private int dealCostDiscount;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCardHoldDay() {
        return cardHoldDay;
    }

    public void setCardHoldDay(int cardHoldDay) {
        this.cardHoldDay = cardHoldDay;
    }

    public int getGiveBourseCoinNumber() {
        return giveBourseCoinNumber;
    }

    public void setGiveBourseCoinNumber(int giveBourseCoinNumber) {
        this.giveBourseCoinNumber = giveBourseCoinNumber;
    }

    public int getInitGiveRatio() {
        return initGiveRatio;
    }

    public void setInitGiveRatio(int initGiveRatio) {
        this.initGiveRatio = initGiveRatio;
    }

    public int getEveryMonthGiveRatio() {
        return everyMonthGiveRatio;
    }

    public void setEveryMonthGiveRatio(int everyMonthGiveRatio) {
        this.everyMonthGiveRatio = everyMonthGiveRatio;
    }

    public int getGiveDividendNumber() {
        return giveDividendNumber;
    }

    public void setGiveDividendNumber(int giveDividendNumber) {
        this.giveDividendNumber = giveDividendNumber;
    }

    public int getDealCostDiscount() {
        return dealCostDiscount;
    }

    public void setDealCostDiscount(int dealCostDiscount) {
        this.dealCostDiscount = dealCostDiscount;
    }
}
