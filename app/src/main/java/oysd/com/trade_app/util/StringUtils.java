package oysd.com.trade_app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String OtcUtils.
 * Created by Liam on 2018/7/10
 */
public final class StringUtils {

    private static String[] EMPTY_STRING_ARRAY = new String[]{};
    private static List<String> EMPTY_STRING_LIST = new ArrayList<>();

    private StringUtils() {
        // prevent from being instantiating.
    }


    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    public static int length(String s) {
        return s == null ? 0 : s.length();
    }

    /**
     * Same as method {@code TextUtils.equals(CharSequence a, CharSequence b)}.
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串反转。
     */
    public static String reverse(String s) {
        if (isBlank(s)) return s;

        int len = s.length();
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;

        for (int i = 0; i < mid; i++) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 首字母大写。
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写。
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 去掉所有特殊字符。
     */
    public static String removeALLSpecialChars(String s) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }

    public static List<String> stringToList(String s, String separator) {
        if (isEmpty(s)) return EMPTY_STRING_LIST;
        String[] strings = s.split(separator);
        return new ArrayList<>(Arrays.asList(strings));
    }

    public static String listToString(List<String> list) {
        if (EmptyUtils.isEmpty(list)) return "";
        StringBuilder sb = new StringBuilder();

        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }

    //判断输入的字符不能超过某个值
    public static boolean isMaxLength(String text,int max){
        return isLengthMaxAndMin(text,0,max);
    }

    //判断输入的字符是数字和字母的组合
    public static boolean  isLengthMaxAndMin(String text,int min,int max){
        if(text.length()>=min&&text.length()<=max){
            return true;
        }else{
            return false;
        }
    }


    //判断输入的字符长度在某个区间
    public static boolean isContainNumAndABC(String text){
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{0,50}$";
        return text.matches(regex);
    }

    //判断邮箱是否符合规则
    public static boolean isEmail(String text){
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return text.matches(regex);
    }

    //判断手机号码长度是否符合规则
    public static boolean isPhoneNum(String text,int length){
        return text.length()==length;
    }

    //判断两次输入的值是否一致
    public static boolean isEquals(String a,String b){
        return a.equals(b);
    }

}
