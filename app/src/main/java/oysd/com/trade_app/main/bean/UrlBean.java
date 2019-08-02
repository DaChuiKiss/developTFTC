package oysd.com.trade_app.main.bean;

public class UrlBean {

    /**
     * createDate : 2018-01-01 10:10:20
     * id : 0
     * linkType : 0
     * linkUrl : string
     * remark : string
     * status : 0
     * title : string
     * type : 0
     */

    private String createDate;
    private int id;
    private int linkType;
    private String linkUrl;
    private String remark;
    private int status;
    private String title;
    private int type;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
