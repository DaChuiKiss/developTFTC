package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class TotalConvertInfo {


    /**
     * btcBalance : 0
     * totalBalance : 0
     */

    private BigDecimal btcBalance;
    private BigDecimal totalBalance;

    public BigDecimal getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(BigDecimal btcBalance) {
        this.btcBalance = btcBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }
}
