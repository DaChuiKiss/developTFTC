package oysd.com.trade_app.modules.mycenter.bean;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的奖励-实体类
 */
public class AwardBean {
    /**
     * invitedAccountNumber : 13688800001
     * antiCommission : 2019-07-23
     * commission : 0.003
     * coinName : NYT
     * dealAmount : 1
     * canGetAmount : 0.99
     * orderNo : 1949
     * dealTime : 2019-07-23 16:25:20
     */

    private String invitedAccountNumber;
    private String antiCommission;
    private double commission;
    private String coinName;
    private double dealAmount;
    private double canGetAmount;
    private String orderNo;
    private String dealTime;

    public String getInvitedAccountNumber() {
        return invitedAccountNumber;
    }

    public void setInvitedAccountNumber(String invitedAccountNumber) {
        this.invitedAccountNumber = invitedAccountNumber;
    }

    public String getAntiCommission() {
        return antiCommission;
    }

    public void setAntiCommission(String antiCommission) {
        this.antiCommission = antiCommission;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public double getCanGetAmount() {
        return canGetAmount;
    }

    public void setCanGetAmount(double canGetAmount) {
        this.canGetAmount = canGetAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
