package oysd.com.trade_app.modules.mycenter.bean;
/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 邀请码/邀请链接
 */
public class HomeVoBean {

    /**
     * id : 17
     * bigImagePath : https://tftc-token.oss-ap-southeast-1.aliyuncs.com/image/2019-07-06/ef084305-308a-400f-844a-293f8f0cf452.jpg
     * smallImagePath : https://tftc-token.oss-ap-southeast-1.aliyuncs.com/image/2019-07-06/ef084305-308a-400f-844a-293f8f0cf452_small.jpg
     * inviteCode : z5vwat
     * levelProportion : 0
     * inviteCount : 0
     * btcReturnAmount : null
     */

    private int id;
    private String bigImagePath;
    private String smallImagePath;
    private String inviteCode;
    private String levelProportion;
    private int inviteCount;
    private Object btcReturnAmount;


    private String nytReturnAmount;
    private String vcReturnAmount;

    public String getNytReturnAmount() {
        return nytReturnAmount;
    }

    public void setNytReturnAmount(String nytReturnAmount) {
        this.nytReturnAmount = nytReturnAmount;
    }

    public String getVcReturnAmount() {
        return vcReturnAmount;
    }

    public void setVcReturnAmount(String vcReturnAmount) {
        this.vcReturnAmount = vcReturnAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBigImagePath() {
        return bigImagePath;
    }

    public void setBigImagePath(String bigImagePath) {
        this.bigImagePath = bigImagePath;
    }

    public String getSmallImagePath() {
        return smallImagePath;
    }

    public void setSmallImagePath(String smallImagePath) {
        this.smallImagePath = smallImagePath;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getLevelProportion() {
        return levelProportion;
    }

    public void setLevelProportion(String levelProportion) {
        this.levelProportion = levelProportion;
    }

    public int getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(int inviteCount) {
        this.inviteCount = inviteCount;
    }

    public Object getBtcReturnAmount() {
        return btcReturnAmount;
    }

    public void setBtcReturnAmount(Object btcReturnAmount) {
        this.btcReturnAmount = btcReturnAmount;
    }
}
