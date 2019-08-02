package oysd.com.trade_app.modules.mycenter.bean;

import java.util.List;

/**
 * Vip 等级配置信息 bean .
 * Created by Liam on 2018/9/20.
 */
public class VipLevelConfigBean {

    /**
     * name : 铂金会员
     * cardInfo : [{"levelId":2,"cardId":1,"cardName":"month","price":"0","cardHoldDay":30,"giveBourseCoinNumber":100,"initGiveRatio":10,"everyMonthGiveRatio":10,"giveDividendNumber":100,"dealCostDiscount":10},{"levelId":2,"cardId":4,"cardName":"lifetime","price":1,"cardHoldDay":-1,"giveBourseCoinNumber":100,"initGiveRatio":10,"everyMonthGiveRatio":10,"giveDividendNumber":100,"dealCostDiscount":10}]
     */

    private String name;
    private List<VipCardInfoBean> cardInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VipCardInfoBean> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<VipCardInfoBean> cardInfo) {
        this.cardInfo = cardInfo;
    }

}
