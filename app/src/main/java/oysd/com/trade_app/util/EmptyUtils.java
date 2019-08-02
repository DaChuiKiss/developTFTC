package oysd.com.trade_app.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Empty OtcUtils.
 * Created by Liam on 2018/7/10
 */
public final class EmptyUtils {

    private EmptyUtils() {
        // prevent from being instantiating.
    }

    // Use Collections class to retrieve an empty "collection" instance.
    // Collections.EMPTY_MAP
    // Collections.EMPTY_SET
    // Collections.EMPTY_LIST

    /**
     * @return {@code true} if the specified sequence is null or empty,
     * otherwise {@code false}.
     */
    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * @return {@code true} if the specified map is null or empty,
     * otherwise {@code false}.
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * @return {@code true} if the specified set is null or empty,
     * otherwise {@code false}.
     */
    public static boolean isEmpty(Set set) {
        return set == null || set.size() == 0;
    }

    /**
     * @return {@code true} if the specified list is null or empty,
     * otherwise {@code false}.
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * @param array array
     * @param <T>   type parameter
     * @return {@code true} if the specified array is null or empty,
     * otherwise {@code false}.
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] ints) {
        return ints == null || ints.length == 0;
    }

    public static boolean isEmpty(long[] longs) {
        return longs == null || longs.length == 0;
    }

    public static boolean isEmpty(char[] chars) {
        return chars == null || chars.length == 0;
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * @return {@code true} if the specified sequence is not null and not empty,
     * otherwise {@code false}.
     */
    public static boolean isNotEmpty(CharSequence sequence) {
        return sequence != null && sequence.length() != 0;
    }

    /**
     * @return {@code true} if the specified map is not null and not empty,
     * otherwise {@code false}.
     */
    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() != 0;
    }

    /**
     * @return {@code true} if the specified set is not null and not empty,
     * otherwise {@code false}.
     */
    public static boolean isNotEmpty(Set set) {
        return set != null && set.size() != 0;
    }

    /**
     * @return {@code true} if the specified list is not null and not empty,
     * otherwise {@code false}.
     */
    public static boolean isNotEmpty(List list) {
        return list != null && list.size() != 0;
    }

    /**
     * @param array array
     * @param <T>   type parameter
     * @return {@code true} if the specified array is not null and not empty,
     * otherwise {@code false}.
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length != 0;
    }


    /**
     * 对数据为null的时候进行保护
     */
    public static String getBigDecimalValue(BigDecimal bd){
        if(bd==null){
            return "0";
        }else{
            return bd.toPlainString();
        }
    }
}
