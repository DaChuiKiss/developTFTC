package oysd.com.trade_app.modules.home.bean;

public class AnnouncementBean {


    /**
     * id : 44
     * title : test1111
     * type : 1
     * content :
     * viewCount : 0
     * issuerName : 小漫
     * issuerDate : 2018-06-30 17:30:47
     * status : 1
     */

    private int id;
    private String title;
    private int type;
    private String content;
    private int viewCount;
    private String issuerName;
    private String issuerDate;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuerDate() {
        return issuerDate;
    }

    public void setIssuerDate(String issuerDate) {
        this.issuerDate = issuerDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
