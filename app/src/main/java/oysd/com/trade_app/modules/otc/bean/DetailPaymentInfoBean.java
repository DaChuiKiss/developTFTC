package oysd.com.trade_app.modules.otc.bean;

/**
 * 详细的支付信息.
 * Created by Liam on 2018/8/27.
 */
public class DetailPaymentInfoBean {

    /**
     * accountNumber : string
     * accountUserName : string
     * bankCardNumber : string
     * bankCardUserName : string
     * bankName : string
     * receivablesCodeUrl : string
     */

    private String accountNumber;
    private String accountUserName;
    private String bankCardNumber;
    private String bankCardUserName;
    private String bankName;
    private String receivablesCodeUrl;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReceivablesCodeUrl() {
        return receivablesCodeUrl;
    }

    public void setReceivablesCodeUrl(String receivablesCodeUrl) {
        this.receivablesCodeUrl = receivablesCodeUrl;
    }

}
