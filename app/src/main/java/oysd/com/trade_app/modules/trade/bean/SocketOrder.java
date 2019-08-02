package oysd.com.trade_app.modules.trade.bean;

import java.math.BigDecimal;
import java.util.List;

public class SocketOrder {

    private List<BuyEntrustListBean> buyEntrustList;
    private List<SellEntrustListBean> sellEntrustList;

    public List<BuyEntrustListBean> getBuyEntrustList() {
        return buyEntrustList;
    }

    public void setBuyEntrustList(List<BuyEntrustListBean> buyEntrustList) {
        this.buyEntrustList = buyEntrustList;
    }

    public List<SellEntrustListBean> getSellEntrustList() {
        return sellEntrustList;
    }

    public void setSellEntrustList(List<SellEntrustListBean> sellEntrustList) {
        this.sellEntrustList = sellEntrustList;
    }

    public static class BuyEntrustListBean {
        /**
         * price : 43
         * volume : 72
         */

        private BigDecimal price;
        private BigDecimal volume;

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public void setVolume(BigDecimal volume) {
            this.volume = volume;
        }
    }

    public static class SellEntrustListBean {
        /**
         * price : 67
         * volume : 9
         */

        private BigDecimal price;
        private BigDecimal volume;

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public void setVolume(BigDecimal volume) {
            this.volume = volume;
        }
    }
}
