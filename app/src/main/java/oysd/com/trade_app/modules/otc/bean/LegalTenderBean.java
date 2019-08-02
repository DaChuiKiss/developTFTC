package oysd.com.trade_app.modules.otc.bean;

/**
 * 法定货币 bean .
 * Created by Liam on 2018/9/12
 */
public class LegalTenderBean {

    /**
     * id : 1
     * abbreviation : CNY
     * name : 人民币
     */

    private int id;
    private String abbreviation;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
