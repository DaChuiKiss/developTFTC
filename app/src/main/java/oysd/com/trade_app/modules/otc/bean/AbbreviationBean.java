package oysd.com.trade_app.modules.otc.bean;

public class AbbreviationBean {

    /**
     * abbreviation : string
     * exchangeCoinId : 0
     * exchangeCoinName : string
     * exchangeValue : 0
     * id : 0
     * name : string
     * remark : string
     * updateDate : 2019-07-24T06:01:09.501Z
     */

    private String abbreviation;
    private int exchangeCoinId;
    private String exchangeCoinName;
    private String exchangeValue;
    private int id;
    private String name;
    private String remark;
    private String updateDate;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getExchangeCoinId() {
        return exchangeCoinId;
    }

    public void setExchangeCoinId(int exchangeCoinId) {
        this.exchangeCoinId = exchangeCoinId;
    }

    public String getExchangeCoinName() {
        return exchangeCoinName;
    }

    public void setExchangeCoinName(String exchangeCoinName) {
        this.exchangeCoinName = exchangeCoinName;
    }

    public String getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(String exchangeValue) {
        this.exchangeValue = exchangeValue;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
