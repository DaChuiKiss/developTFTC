package oysd.com.trade_app.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import io.reactivex.annotations.Nullable;

public final class FormatUtils {

    private FormatUtils() {
        // Prevents from being instantiated.
    }

    /**
     * 将 double 值转为字符串显示，防止科学计数。
     */
    public static String double2String(double value) {
        return BigDecimal.valueOf(value).toPlainString();
    }

    /**
     * 格式化为(最多) 8 位小数。
     * 当小数位大于 8 位时，格式为 8 位小数；小于 8 位时，则显示对应的位数。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to8(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();
        DecimalFormat df;

        if (scale < 2) {
            df = new DecimalFormat("##,###,##0.00");
        } else if (scale <= 8) {
            df = new DecimalFormat("##,###,##0.00######");
        } else {
            df = new DecimalFormat("##,###,##0.00000000");
        }

        // 四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(bigDecimal);
    }

    public static String to8(@Nullable String value) {
        if (EmptyUtils.isEmpty(value)) return "0.00";

        try {
            return to8(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    public static String to8(double value) {
        try {
            return to8(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 格式化为(最多) 8 位小数，<strong>没有</strong>逗号分隔。
     * 当小数位大于 8 位时，格式为 8 位小数；小于 8 位时，则显示对应的位数。
     *
     * @param bigDecimal BigDecimal
     * @return Formatted String
     */
    public static String to8NoComma(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();

        if (scale < 2) {
            return bigDecimal.setScale(2, RoundingMode.HALF_UP).toPlainString();
        } else if (scale <= 8) {
            return bigDecimal.toPlainString();
        } else {
            return bigDecimal.setScale(8, RoundingMode.HALF_UP).toPlainString();
        }
    }

    public static String to8NoComma(String value) {
        if (EmptyUtils.isEmpty(value)) return "0.00";

        try {
            return to8NoComma(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    public static String to8NoComma(double value) {
        try {
            return to8NoComma(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 格式化为最多 6 位小数。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to6(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();
        DecimalFormat df;

        if (scale < 2) {
            df = new DecimalFormat("##,###,##0.00");
        } else if (scale <= 6) {
            df = new DecimalFormat("##,###,##0.00####");
        } else {
            df = new DecimalFormat("##,###,##0.000000");
        }

        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(bigDecimal);
    }

    public static String to6(String value) {
        if (EmptyUtils.isEmpty(value)) return "0.00";

        try {
            return to6(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    public static String to6(double value) {
        try {
            return to6(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 格式化为最多 5 位小数。<strong>没有</strong>逗号分隔。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to5NoComma(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();

        if (scale < 2) {
            return bigDecimal.setScale(2, RoundingMode.HALF_UP).toPlainString();
        } else if (scale <= 5) {
            return bigDecimal.toPlainString();
        } else {
            return bigDecimal.setScale(5, RoundingMode.HALF_UP).toPlainString();
        }
    }  /**
     * 格式化为最多 6 位小数。<strong>没有</strong>逗号分隔。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to6NoComma(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();

        if (scale < 2) {
            return bigDecimal.setScale(2, RoundingMode.HALF_UP).toPlainString();
        } else if (scale <= 6) {
            return bigDecimal.toPlainString();
        } else {
            return bigDecimal.setScale(6, RoundingMode.HALF_UP).toPlainString();
        }
    }

    public static String to6NoComma(String value) {
        if (EmptyUtils.isEmpty(value)) return "0.00";

        try {
            return to6NoComma(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    public static String to6NoComma(double value) {
        try {
            return to6NoComma(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 格式化为(最多) 4 位小数。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to4(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();
        DecimalFormat df;

        if (scale < 2) {
            df = new DecimalFormat("##,###,##0.00");
        } else if (scale <= 4) {
            df = new DecimalFormat("##,###,##0.00##");
        } else {
            df = new DecimalFormat("##,###,##0.0000");
        }

        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(bigDecimal);
    }

    public static String to4(String value) {
        if (EmptyUtils.isEmpty(value)) return "0.00";

        try {
            return to4(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    public static String to4(double value) {
        try {
            return to4(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 格式化为(最多) 4 位小数。<strong>没有</strong>逗号分隔。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to4NoComma(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        int scale = bigDecimal.scale();

        if (scale < 2) {
            return bigDecimal.setScale(2, RoundingMode.HALF_UP).toPlainString();
        } else if (scale <= 4) {
            return bigDecimal.toPlainString();
        } else {
            return bigDecimal.setScale(4, RoundingMode.HALF_UP).toPlainString();
        }
    }

    public static String to4NoComma(String value) {
        if (EmptyUtils.isEmpty(value)) return "0.0000";

        try {
            return to4NoComma(new BigDecimal(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0000";
        }
    }

    public static String to4NoComma(double value) {
        try {
            return to4NoComma(BigDecimal.valueOf(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0000";
        }
    }

    /**
     * 格式化为 2 位小数。
     *
     * @param bigDecimal BigDecimal
     */
    public static String to2(BigDecimal bigDecimal) {
        if (bigDecimal == null) return "0.00";

        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        return df.format(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN));
    }

    public static String to2(String value) {
        if (StringUtils.isEmpty(value)) return "0.00";
        return to2(new BigDecimal(value));
    }

    public static String to2(double value) {
        return to2(BigDecimal.valueOf(value));
    }

    /**
     * 格式化为 2 位小数。
     *
     * @param bd BigDecimal
     */
    public static String to2NoComma(BigDecimal bd) {
        if (bd == null) return "0.00";

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(bd.setScale(8, BigDecimal.ROUND_DOWN));
    }

    public static String to2NoComma(String value) {
        if (StringUtils.isEmpty(value)) return "0.00";
        return to2NoComma(new BigDecimal(value));
    }

    /**
     * 格式化为 指定的 小数位数。
     *
     * @param bigDecimal BigDecimal
     * @param index      指定的位数
     * @return 格式化后的 String
     */
    public static String toX(BigDecimal bigDecimal, int index) {
        if (bigDecimal == null) return "0.00";

        String s = "##,###,##0.";
        for (int i = 0; i < index; i++) {
            s += "0";
        }

        DecimalFormat df = new DecimalFormat(s);
        return df.format(bigDecimal.setScale(8, BigDecimal.ROUND_DOWN));
    }

    public static String toX(String value, int index) {
        if (StringUtils.isEmpty(value)) return "0.00";
        return toX(new BigDecimal(value), index);
    }

    /**
     * 格式化为 指定的 小数位数。没有逗号分隔。
     *
     * @param bd    BigDecimal
     * @param index 指定的位数
     * @return 格式化后的 String
     */
    public static String toXNoComma(BigDecimal bd, int index) {
        if (bd == null) return "0.00";

        String s = "0.";
        for (int i = 0; i < index; i++) {
            s += "0";
        }

        DecimalFormat df = new DecimalFormat(s);
        return df.format(bd.setScale(8, BigDecimal.ROUND_DOWN));
    }

    public static String toXNoComma(String value, int index) {
        if (StringUtils.isEmpty(value)) return "0.00";
        return toXNoComma(new BigDecimal(value), index);
    }


}
