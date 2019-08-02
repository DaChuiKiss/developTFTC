package oysd.com.trade_app.modules.mycenter.bean;

/**
 * Deal zone info.
 * Created by Liam on 2018/7/23
 */
public class SafeBean {


    /**
     * email : string
     * gesturePassword : string
     * id : 0
     * nickName : string
     * phone : string
     * prefix : string
     * showId : string
     * userAccountId : 0
     */

    private String email;
    private String gesturePassword;
    private int id;
    private String nickName;
    private String phone;
    private String prefix;
    private String showId;
    private int userAccountId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGesturePassword() {
        return gesturePassword;
    }

    public void setGesturePassword(String gesturePassword) {
        this.gesturePassword = gesturePassword;
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
}
