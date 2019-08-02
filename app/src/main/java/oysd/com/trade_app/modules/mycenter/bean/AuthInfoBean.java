package oysd.com.trade_app.modules.mycenter.bean;

/**
 * Deal zone info.
 * Created by Liam on 2018/7/23
 */
public class AuthInfoBean {

    private int accountId;
    private int  advancedStatus;
    private String countryName;
    private String email;
    private String failMessage;
    private int googleStatus;
    private String identityNumber;
    private int identityType;
    private String imgBackPath;
    private String imgFrontPath;
    private String nickName;
    private String phone;
    private String realName;
    private String showId;
    private int verifyRealStatus;

    public int getDealPasswordExists() {
        return dealPasswordExists;
    }

    public void setDealPasswordExists(int dealPasswordExists) {
        this.dealPasswordExists = dealPasswordExists;
    }

    private int dealPasswordExists;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAdvancedStatus() {
        return advancedStatus;
    }

    public void setAdvancedStatus(int advancedStatus) {
        this.advancedStatus = advancedStatus;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public int getGoogleStatus() {
        return googleStatus;
    }

    public void setGoogleStatus(int googleStatus) {
        this.googleStatus = googleStatus;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getImgBackPath() {
        return imgBackPath;
    }

    public void setImgBackPath(String imgBackPath) {
        this.imgBackPath = imgBackPath;
    }

    public String getImgFrontPath() {
        return imgFrontPath;
    }

    public void setImgFrontPath(String imgFrontPath) {
        this.imgFrontPath = imgFrontPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public int getVerifyRealStatus() {
        return verifyRealStatus;
    }

    public void setVerifyRealStatus(int verifyRealStatus) {
        this.verifyRealStatus = verifyRealStatus;
    }

    @Override
    public String toString() {
        return "AuthInfoBean{" +
                "accountId=" + accountId +
                ", advancedStatus=" + advancedStatus +
                ", countryName='" + countryName + '\'' +
                ", email='" + email + '\'' +
                ", failMessage='" + failMessage + '\'' +
                ", googleStatus=" + googleStatus +
                ", identityNumber='" + identityNumber + '\'' +
                ", identityType=" + identityType +
                ", imgBackPath='" + imgBackPath + '\'' +
                ", imgFrontPath='" + imgFrontPath + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", showId=" + showId +
                ", verifyRealStatus=" + verifyRealStatus +
                '}';
    }
}
