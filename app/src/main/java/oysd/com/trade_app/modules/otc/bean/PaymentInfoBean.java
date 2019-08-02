package oysd.com.trade_app.modules.otc.bean;

/**
 * 用户支付信息 bean .
 * Created by Liam on 2018/8/21
 */
public class PaymentInfoBean {

    /**
     * id : 12
     * userAccountId : 127
     * receivablesType : 2
     * accountNumber : cmkfkf
     * accountUserName : 额 look
     * receiptCodeImageUrl : http://bourse.oss-cn-shenzhen.aliyuncs.com/image/2018-08-23/895f13e0-4540-4b85-81d2-36be378df25c.jpg
     * remarks : 啃了
     * bankTerritoriality : 1
     * bankName : 4
     * accountOpeningAddress : 小鸡鸡纯牛奶吃
     * bankCardNumber : 123456789011
     * bankCardUserName : 噬金剑仙
     * swiftNo : null
     * bankCardDesc : 超级你从哪拆开看参考参考
     * status : 1
     */

    private int id;
    private int userAccountId;
    private int receivablesType;
    private String accountNumber;
    private String accountUserName;
    private String receiptCodeImageUrl;
    private String remarks;
    private int bankTerritoriality;
    private int bankName;
    private String accountOpeningAddress;
    private String bankCardNumber;
    private String bankCardUserName;
    private String swiftNo;
    private String bankCardDesc;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public int getReceivablesType() {
        return receivablesType;
    }

    public void setReceivablesType(int receivablesType) {
        this.receivablesType = receivablesType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getReceiptCodeImageUrl() {
        return receiptCodeImageUrl;
    }

    public void setReceiptCodeImageUrl(String receiptCodeImageUrl) {
        this.receiptCodeImageUrl = receiptCodeImageUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBankTerritoriality() {
        return bankTerritoriality;
    }

    public void setBankTerritoriality(int bankTerritoriality) {
        this.bankTerritoriality = bankTerritoriality;
    }

    public int getBankName() {
        return bankName;
    }

    public void setBankName(int bankName) {
        this.bankName = bankName;
    }

    public String getAccountOpeningAddress() {
        return accountOpeningAddress;
    }

    public void setAccountOpeningAddress(String accountOpeningAddress) {
        this.accountOpeningAddress = accountOpeningAddress;
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

    public String getSwiftNo() {
        return swiftNo;
    }

    public void setSwiftNo(String swiftNo) {
        this.swiftNo = swiftNo;
    }

    public String getBankCardDesc() {
        return bankCardDesc;
    }

    public void setBankCardDesc(String bankCardDesc) {
        this.bankCardDesc = bankCardDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
