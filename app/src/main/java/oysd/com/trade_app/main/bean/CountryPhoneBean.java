package oysd.com.trade_app.main.bean;

public class CountryPhoneBean {


    /**
     * countryCode : string
     * id : 0
     * name : string
     * phoneLength : 0
     * plusPrefix : string
     * prefix : string
     */

    private String countryCode;
    private int id;
    private String name;
    private int phoneLength;
    private String plusPrefix;
    private String prefix;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

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

    public int getPhoneLength() {
        return phoneLength;
    }

    public void setPhoneLength(int phoneLength) {
        this.phoneLength = phoneLength;
    }

    public String getPlusPrefix() {
        return plusPrefix;
    }

    public void setPlusPrefix(String plusPrefix) {
        this.plusPrefix = plusPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "CountryPhoneBean{" +
                "countryCode='" + countryCode + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phoneLength=" + phoneLength +
                ", plusPrefix='" + plusPrefix + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }
}
