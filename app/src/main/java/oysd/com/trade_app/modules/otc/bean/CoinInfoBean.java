package oysd.com.trade_app.modules.otc.bean;

/**
 * Coin info.
 * Created by Liam on 2018/8/24.
 */
public class CoinInfoBean {

    /**
     * id : 52
     * name : 小蚁
     * englishName : NEO
     * englishShortName : NEO
     * coinTypeId : null
     * coinTypeName : null
     * type : null
     */

    private int id;
    private String name;
    private String englishName;
    private String englishShortName;
    private int coinTypeId;
    private String coinTypeName;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishShortName() {
        return englishShortName;
    }

    public void setEnglishShortName(String englishShortName) {
        this.englishShortName = englishShortName;
    }

    public int getCoinTypeId() {
        return coinTypeId;
    }

    public void setCoinTypeId(int coinTypeId) {
        this.coinTypeId = coinTypeId;
    }

    public String getCoinTypeName() {
        return coinTypeName;
    }

    public void setCoinTypeName(String coinTypeName) {
        this.coinTypeName = coinTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
