package oysd.com.trade_app.main.Tools;

/**
 * Created by admin on 2016/7/22.
 */

import android.graphics.drawable.Drawable;

/**
 *
 * 类简要描述
 *
 * <p>
 * 类详细描述
 * </p>
 *
 * @author duanbokan
 *
 */

public class CountryModel
{
    // 国家名称
    public String countryName;

    // 电话号码
    public String countryNumber;

    public String simpleCountryNumber;

    // 国家名称缩写
    public String countrySortKey;

    //电话号码长度
    public int phoneLength;


    //国家代码
    public int countryId;

    public String countryCode;
    // 国家图标
    public Drawable contactPhoto;

    public CountryModel(String countryName, String countryNumber, String countrySortKey,int phoneLength,int countryId,String countryCode)
    {
        super();
        this.countryName = countryName;
        this.countryNumber = countryNumber;
        this.countrySortKey = countrySortKey;
        this.phoneLength =phoneLength;
        this.countryCode =countryCode;
        this.countryId =countryId;
        if (countryNumber != null)
        {
            this.simpleCountryNumber = countryNumber.replaceAll("\\-|\\s", "");
        }
    }

}
