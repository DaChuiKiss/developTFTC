package oysd.com.trade_app.modules.otc;

import java.util.ArrayList;
import java.util.List;

import oysd.com.trade_app.modules.otc.bean.BankItemBean;

/**
 * OtcUtils for Module Otc.
 * Created by Liam on 2018/8/23.
 */
public final class OtcUtils {

    private OtcUtils() {
        // Prevents from being instantiated.
    }

    /**
     * 根据返回的 BankItem list, 获取 Bank name list .
     */
    public static List<String> getBankNames(List<BankItemBean> list) {
        List<String> bankNames = new ArrayList<>();
        for (BankItemBean bean : list) {
            bankNames.add(bean.getBackName());
        }

        return bankNames;
    }

    /**
     * 返回 Bank code 对应的 Bank name, 如果没有对应的 Bank name, 则返回 null.
     */
    public static String getBankNameFromList(List<BankItemBean> list, int bankCode) {
        for (BankItemBean bean : list) {
            if (bankCode == bean.getKey()) return bean.getBackName();
        }
        return null;
    }

    /**
     * 返回 Bank name 对应的 Bank Code, 如果没有对应的 Bank code, 则返回 -1.
     */
    public static int getBankCodeFromList(List<BankItemBean> list, String bank) {
        for (BankItemBean bean : list) {
            if (bank.equals(bean.getBackName())) return bean.getKey();
        }
        return -1;
    }

}
