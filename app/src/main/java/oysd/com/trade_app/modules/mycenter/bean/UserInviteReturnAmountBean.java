package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class UserInviteReturnAmountBean {

    /**
     * btcAmount : 0
     * endDay : string
     * startDay : string
     * tenderAmount : 0
     * totalBtcAmount : 0
     * totalTenderAmount : 0
     */

    private BigDecimal btcAmount;
    private String endDay;
    private String startDay;
    private BigDecimal tenderAmount;
    private BigDecimal totalBtcAmount;
    private BigDecimal totalTenderAmount;

    public BigDecimal getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(BigDecimal btcAmount) {
        this.btcAmount = btcAmount;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public BigDecimal getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(BigDecimal tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public BigDecimal getTotalBtcAmount() {
        return totalBtcAmount;
    }

    public void setTotalBtcAmount(BigDecimal totalBtcAmount) {
        this.totalBtcAmount = totalBtcAmount;
    }

    public BigDecimal getTotalTenderAmount() {
        return totalTenderAmount;
    }

    public void setTotalTenderAmount(BigDecimal totalTenderAmount) {
        this.totalTenderAmount = totalTenderAmount;
    }
}
