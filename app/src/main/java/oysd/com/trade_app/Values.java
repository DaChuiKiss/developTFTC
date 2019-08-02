package oysd.com.trade_app;

/**
 * 定义交易中一些常量值。
 * Created by Liam on 2018/8/15
 */
public interface Values {

    /**
     * App 使用的语言: en / zh
     */
    interface Language {
        String EN = "en";
        String ZH = "zh";
    }

    /**
     * 支付方式：
     * 1、支付宝 2、微信 3、PayPal 4、epay 5、银行卡 6、USD
     */
    interface PaymentMethod {
        int ALIPAY = 1;
        int WECHAT = 2;
        int PAYPAL = 3;
        int EPAY = 4;
        int BANK = 5;
        int USD = 6;
    }
    // TODO: 2019/7/10 货币类型要改成服务器数据，还在理逻辑

    /**
     * 货币类型：
     * 1：人民币; 2：美元; 3:澳元;4:港元;
     */
    interface Currency {
        int CNY = 4;
        int USD = 2;
        int AUD = 3;
        int HKD = 1;

        String CNY_VALUE = "CNY";
        String USD_VALUE = "USD";
        String AUD_VALUE = "AUD";
        String HKD_VALUE = "HKD";

        String CNY_SYMBOL = "¥";
        String USD_SYMBOL = "$";
        String AUD_SYMBOL = "A$";
        String HKD_SYMBOL = "HK$";
    }

    /**
     * 交易类型：
     * 1：买入/买家; 2：卖出/卖家
     */
    interface TransactionType {
        int BUY = 1;
        int SALE = 2;
        String BUY_VALUE = "BUY";
        String SALE_VALUE = "SALE";
    }

    /**
     * 订单状态。
     * 1、待付款 2、待放币 3、已完成 4、已取消 5、申诉中
     */
    interface OrderStatus {
        int UNPAID = 1;
        int PAID = 2;
        int COMPLETED = 3;
        int CANCELED = 4;
        int APPEALING = 5;
    }

    /**
     * Vip 会员的权益.
     * 交易挖矿, 免手续费
     */
    interface VipBenefits {
        String DIG = "deal_extract_bourse_coin";
        String FREE_SERVICE = "deal_cost_discount";
    }

    /**
     * Vip 会员卡日期.
     */
    interface VipCardId {
        int MONTH = 1;
        int SEASON = 2;
        int YEAR = 3;
        int LIFELONG = 4;
    }

    /**
     * Vip 会员卡等级：
     * 白银、黄金……
     */
    interface VipCardLevel {

    }


}
