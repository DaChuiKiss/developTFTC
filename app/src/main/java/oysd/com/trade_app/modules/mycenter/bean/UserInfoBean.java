package oysd.com.trade_app.modules.mycenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User info.
 * Created by Liam on 2018/7/24
 */
public class UserInfoBean implements Parcelable {

    public String getGesturePassword() {
        return gesturePassword;
    }

    public void setGesturePassword(String gesturePassword) {
        this.gesturePassword = gesturePassword;
    }

    public int getDealPasswordExists() {
        return dealPasswordExists;
    }

    public void setDealPasswordExists(int dealPasswordExists) {
        this.dealPasswordExists = dealPasswordExists;
    }

    /**
     * email : string
     * id : 0
     * nickName : string
     * phone : string
     * prefix : string
     * showId : string
     * userAccountId : 0
     */
    private int dealPasswordExists;
    private String gesturePassword;
    private String email;
    private int id;
    private String nickName;
    private String phone;
    private String prefix;
    private String showId;
    private int userAccountId;

    protected UserInfoBean(Parcel in) {
        email = in.readString();
        id = in.readInt();
        nickName = in.readString();
        phone = in.readString();
        prefix = in.readString();
        showId = in.readString();
        userAccountId = in.readInt();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", prefix='" + prefix + '\'' +
                ", showId='" + showId + '\'' +
                ", userAccountId=" + userAccountId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeInt(id);
        dest.writeString(nickName);
        dest.writeString(phone);
        dest.writeString(prefix);
        dest.writeString(showId);
        dest.writeInt(userAccountId);
    }

}
