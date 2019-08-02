package oysd.com.trade_app.modules.otc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Otc 交易时 账户 bean .
 * Created by Liam on 2018/8/25.
 */
public class TransactionCoinAccountBean implements Parcelable {

    /**
     * accountNumber : null
     * accountUserName : null
     * receivablesCodeUrl : null
     * bankName : 中国银行
     * bankCardNumber : 123456789
     * bankCardUserName : 大佬
     */

    private String accountNumber;
    private String accountUserName;
    private String receivablesCodeUrl;
    private String bankName;
    private String bankCardNumber;
    private String bankCardUserName;

    protected TransactionCoinAccountBean(Parcel in) {
        accountNumber = in.readString();
        accountUserName = in.readString();
        receivablesCodeUrl = in.readString();
        bankName = in.readString();
        bankCardNumber = in.readString();
        bankCardUserName = in.readString();
    }

    public static final Creator<TransactionCoinAccountBean> CREATOR = new Creator<TransactionCoinAccountBean>() {
        @Override
        public TransactionCoinAccountBean createFromParcel(Parcel in) {
            return new TransactionCoinAccountBean(in);
        }

        @Override
        public TransactionCoinAccountBean[] newArray(int size) {
            return new TransactionCoinAccountBean[size];
        }
    };

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

    public String getReceivablesCodeUrl() {
        return receivablesCodeUrl;
    }

    public void setReceivablesCodeUrl(String receivablesCodeUrl) {
        this.receivablesCodeUrl = receivablesCodeUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountNumber);
        dest.writeString(accountUserName);
        dest.writeString(receivablesCodeUrl);
        dest.writeString(bankName);
        dest.writeString(bankCardNumber);
        dest.writeString(bankCardUserName);
    }

}
