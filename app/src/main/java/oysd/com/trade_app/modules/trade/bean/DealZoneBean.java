package oysd.com.trade_app.modules.trade.bean;

/**
 * Deal zone info.
 * Created by Liam on 2018/7/23
 */
public class DealZoneBean {

    /**
     * id : 15
     * coinId : null
     * coinName : null
     * name : ASCH
     * description : null
     * status : null
     * createDate : null
     */

    private int id;
    private Object coinId;
    private Object coinName;
    private String name;
    private Object description;
    private Object status;
    private Object createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getCoinId() {
        return coinId;
    }

    public void setCoinId(Object coinId) {
        this.coinId = coinId;
    }

    public Object getCoinName() {
        return coinName;
    }

    public void setCoinName(Object coinName) {
        this.coinName = coinName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DealZoneBean{" +
                "id=" + id +
                ", coinId=" + coinId +
                ", coinName=" + coinName +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", status=" + status +
                ", createDate=" + createDate +
                '}';
    }

}
