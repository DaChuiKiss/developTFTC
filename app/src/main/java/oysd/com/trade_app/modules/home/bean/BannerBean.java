package oysd.com.trade_app.modules.home.bean;

public class BannerBean {

    /**
     * bigImagePath : string
     * id : 0
     * linkUrl : string
     * smallImagePath : string
     * title : string
     */

    private String bigImagePath;
    private int id;
    private String linkUrl;
    private String smallImagePath;
    private String title;

    public String getBigImagePath() {
        return bigImagePath;
    }

    public void setBigImagePath(String bigImagePath) {
        this.bigImagePath = bigImagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getSmallImagePath() {
        return smallImagePath;
    }

    public void setSmallImagePath(String smallImagePath) {
        this.smallImagePath = smallImagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "bigImagePath='" + bigImagePath + '\'' +
                ", id=" + id +
                ", linkUrl='" + linkUrl + '\'' +
                ", smallImagePath='" + smallImagePath + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
