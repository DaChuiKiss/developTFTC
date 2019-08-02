package oysd.com.trade_app.modules.otc.bean;

/**
 * 银行列表中的 item .
 * Created by Liam on 2018/8/21
 */
public class BankItemBean {

    /**
     * backName : string
     * countryName : string
     * key : 0
     */

    private String backName;
    private String countryName;
    private int key;

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

}
