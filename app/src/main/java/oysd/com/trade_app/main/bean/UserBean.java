package oysd.com.trade_app.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a user that in a response.
 * Created by Liam on 2018/7/17
 */
public class UserBean implements Parcelable {

    /**
     * id : 0
     * nickName : string
     * token : string
     */

    private int id;
    private String nickName;
    private String token;
    private String prefix;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    private String countryCode;

    protected UserBean(Parcel in) {
        id = in.readInt();
        nickName = in.readString();
        token = in.readString();
        countryCode=in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", token='" + token + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickName);
        dest.writeString(token);
        dest.writeString(prefix);
        dest.writeString(countryCode);
    }

}
