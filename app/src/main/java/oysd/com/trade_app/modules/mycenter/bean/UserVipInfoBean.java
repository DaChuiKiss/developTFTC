package oysd.com.trade_app.modules.mycenter.bean;

public class UserVipInfoBean {

    /**
     * id : 0
     * loseDate : 2018-09-19T07:20:55.217Z
     * rightsMode : deal_cost_discount
     * startDate : 2018-09-19T07:20:55.218Z
     * vipLevelInfoVo : {"id":0,"name":"string"}
     */

    private int id;
    private int levelId;
    private int cardId;
    private String price;
    private String loseDate;
    private String rightsMode;
    private String startDate;
    private String cardName;
    private int disableBuy;
    // configVo 当前卡的权益数据
    private VipCardInfoBean configVo;
    private VipLevelInfoVoBean vipLevelInfoVo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLoseDate() {
        return loseDate;
    }

    public void setLoseDate(String loseDate) {
        this.loseDate = loseDate;
    }

    public String getRightsMode() {
        return rightsMode;
    }

    public void setRightsMode(String rightsMode) {
        this.rightsMode = rightsMode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getDisableBuy() {
        return disableBuy;
    }

    public void setDisableBuy(int disableBuy) {
        this.disableBuy = disableBuy;
    }

    public VipCardInfoBean getConfigVo() {
        return configVo;
    }

    public void setConfigVo(VipCardInfoBean configVo) {
        this.configVo = configVo;
    }

    public VipLevelInfoVoBean getVipLevelInfoVo() {
        return vipLevelInfoVo;
    }

    public void setVipLevelInfoVo(VipLevelInfoVoBean vipLevelInfoVo) {
        this.vipLevelInfoVo = vipLevelInfoVo;
    }

    public static class VipLevelInfoVoBean {
        /**
         * id : 0
         * name : string
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
