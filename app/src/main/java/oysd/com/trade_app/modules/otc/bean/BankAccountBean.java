package oysd.com.trade_app.modules.otc.bean;

/**
 * 用户银行账号信息 bean .
 * Created by Liam on 2018/8/21
 */
public class BankAccountBean {

    /**
     * accountOpeningAddress : string
     * bankCardDesc : string
     * bankCardNumber : string
     * bankCardUserName : string
     * bankName : 0
     * bankTerritoriality : 0
     * id : 0
     * status : 0
     * swiftNo : string
     * userAccountId : 0
     */

    private String accountOpeningAddress;
    private String bankCardDesc;
    private String bankCardNumber;
    private String bankCardUserName;
    private int bankName;
    private int bankTerritoriality;
    private int id;
    private int status;
    private String swiftNo;
    private int userAccountId;

    public String getAccountOpeningAddress() {
        return accountOpeningAddress;
    }

    public void setAccountOpeningAddress(String accountOpeningAddress) {
        this.accountOpeningAddress = accountOpeningAddress;
    }

    public String getBankCardDesc() {
        return bankCardDesc;
    }

    public void setBankCardDesc(String bankCardDesc) {
        this.bankCardDesc = bankCardDesc;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankCardUserName() {
        return bankCardUserName;
    }

    public void setBankCardUserName(String bankCardUserName) {
        this.bankCardUserName = bankCardUserName;
    }

    public int getBankName() {
        return bankName;
    }

    public void setBankName(int bankName) {
        this.bankName = bankName;
    }

    public int getBankTerritoriality() {
        return bankTerritoriality;
    }

    public void setBankTerritoriality(int bankTerritoriality) {
        this.bankTerritoriality = bankTerritoriality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSwiftNo() {
        return swiftNo;
    }

    public void setSwiftNo(String swiftNo) {
        this.swiftNo = swiftNo;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

}
