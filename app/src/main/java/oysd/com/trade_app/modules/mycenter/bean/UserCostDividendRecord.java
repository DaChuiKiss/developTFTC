package oysd.com.trade_app.modules.mycenter.bean;

import java.math.BigDecimal;

public class UserCostDividendRecord {

    /**
     * bourseCoinNumber : 0
     * cost : 0
     * costCoinId : 0
     * costCoinName : string
     * extractDate : 2018-09-20T07:12:19.249Z
     * id : 0
     * userAccountId : 0
     * userName : string
     */

    private BigDecimal bourseCoinNumber;
    private BigDecimal cost;
    private int costCoinId;
    private String costCoinName;
    private String extractDate;
    private int id;
    private int userAccountId;
    private String userName;

    public BigDecimal getBourseCoinNumber() {
        return bourseCoinNumber;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public int getCostCoinId() {
        return costCoinId;
    }

    public String getCostCoinName() {
        return costCoinName;
    }

    public String getExtractDate() {
        return extractDate;
    }

    public int getId() {
        return id;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setBourseCoinNumber(BigDecimal bourseCoinNumber) {
        this.bourseCoinNumber = bourseCoinNumber;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCostCoinId(int costCoinId) {
        this.costCoinId = costCoinId;
    }

    public void setCostCoinName(String costCoinName) {
        this.costCoinName = costCoinName;
    }

    public void setExtractDate(String extractDate) {
        this.extractDate = extractDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
