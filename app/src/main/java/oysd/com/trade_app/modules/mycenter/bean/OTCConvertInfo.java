package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class OTCConvertInfo {


    /**
     * btcAmount : 0
     * legalTenderAmount : 0
     */

    private BigDecimal btcAmount;
    private BigDecimal legalTenderAmount;

    public BigDecimal getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(BigDecimal btcAmount) {
        this.btcAmount = btcAmount;
    }

    public BigDecimal getLegalTenderAmount() {
        return legalTenderAmount;
    }

    public void setLegalTenderAmount(BigDecimal legalTenderAmount) {
        this.legalTenderAmount = legalTenderAmount;
    }
}
