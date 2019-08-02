package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

/**
 * 分红bean
 */
public class DividendBean {


    /**
     * bonusAmount : 0
     * currencyUnit : string
     * frenchCurrencyAmount : 0
     */

    private BigDecimal bonusAmount;
    private String currencyUnit;
    private BigDecimal frenchCurrencyAmount;

    public BigDecimal getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(BigDecimal bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public BigDecimal getFrenchCurrencyAmount() {
        return frenchCurrencyAmount;
    }

    public void setFrenchCurrencyAmount(BigDecimal frenchCurrencyAmount) {
        this.frenchCurrencyAmount = frenchCurrencyAmount;
    }
}
