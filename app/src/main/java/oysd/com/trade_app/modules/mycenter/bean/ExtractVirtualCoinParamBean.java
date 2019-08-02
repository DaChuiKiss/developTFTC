package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class ExtractVirtualCoinParamBean {


    /**
     * takeInMinValue : 0
     * takeOutCost : 0
     * takeOutDecimals : 0
     * takeOutLeastNumber : 0
     * takeOutMaxNumber : 0
     * takeOutReminder : string
     * usableAmount : 0
     */

    private BigDecimal takeInMinValue;
    private BigDecimal takeOutCost;
    private int takeOutDecimals;
    private BigDecimal takeOutLeastNumber;
    private BigDecimal takeOutMaxNumber;
    private String takeOutReminder;
    private BigDecimal usableAmount;

    public BigDecimal getTakeInMinValue() {
        return takeInMinValue;
    }

    public void setTakeInMinValue(BigDecimal takeInMinValue) {
        this.takeInMinValue = takeInMinValue;
    }

    public BigDecimal getTakeOutCost() {
        return takeOutCost;
    }

    public void setTakeOutCost(BigDecimal takeOutCost) {
        this.takeOutCost = takeOutCost;
    }

    public int getTakeOutDecimals() {
        return takeOutDecimals;
    }

    public void setTakeOutDecimals(int takeOutDecimals) {
        this.takeOutDecimals = takeOutDecimals;
    }

    public BigDecimal getTakeOutLeastNumber() {
        return takeOutLeastNumber;
    }

    public void setTakeOutLeastNumber(BigDecimal takeOutLeastNumber) {
        this.takeOutLeastNumber = takeOutLeastNumber;
    }

    public BigDecimal getTakeOutMaxNumber() {
        return takeOutMaxNumber;
    }

    public void setTakeOutMaxNumber(BigDecimal takeOutMaxNumber) {
        this.takeOutMaxNumber = takeOutMaxNumber;
    }

    public String getTakeOutReminder() {
        return takeOutReminder;
    }

    public void setTakeOutReminder(String takeOutReminder) {
        this.takeOutReminder = takeOutReminder;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }
}
