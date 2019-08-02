package oysd.com.trade_app.modules.mycenter.bean;
/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 活动明细实体类
 */
public class MyInvitationBean  {


    /**
     * id : 1
     * content : <p>活动明细内容</p> <p>1.测试1111111111</p> <p>2.测试2222222222</p> <p>3.测试3333333333</p> <p>4.测试4444444444</p>
     * title : 活动明细
     * type : 1
     * createName : 超级管理员
     * createDate : 2019-07-17 12:02:54
     * status : 1
     */

    private int id;
    private String content;
    private String title;
    private int type;
    private String createName;
    private String createDate;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
