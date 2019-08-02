package oysd.com.trade_app.modules.mycenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import oysd.com.trade_app.util.EmptyUtils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户-凭证账户-实体类
 */
public class VoucherBean implements Parcelable {

    /**
     * id : 56
     * userAccountId : 201350
     * certificateId : 23
     * certificateName : NDT通证
     * address : VTZJJIFPTATKRDF4WY7GCO7GEARSQY7D
     * usableAmount : 0
     * freezeAmount : 0
     * coinName : VC
     */

    private int id;
    private int userAccountId;
    private int certificateId;
    private String certificateName;
    private String address;
    private String usableAmount;
    private String freezeAmount;
    private String coinName;

    protected VoucherBean(Parcel in) {
        id = in.readInt();
        userAccountId = in.readInt();
        certificateId = in.readInt();
        certificateName = in.readString();
        address = in.readString();
        usableAmount = in.readString();
        freezeAmount = in.readString();
        coinName = in.readString();
    }

    public static final Creator<VoucherBean> CREATOR = new Creator<VoucherBean>() {
        @Override
        public VoucherBean createFromParcel(Parcel in) {
            return new VoucherBean(in);
        }

        @Override
        public VoucherBean[] newArray(int size) {
            return new VoucherBean[size];
        }
    };

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

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(String usableAmount) {
        this.usableAmount = usableAmount;
    }

    public String getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userAccountId);
        parcel.writeInt(certificateId);
        parcel.writeString(certificateName);
        parcel.writeString(address);
        parcel.writeString(usableAmount);
        parcel.writeString(freezeAmount);
        parcel.writeString(coinName);
    }
}