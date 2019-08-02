package oysd.com.trade_app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import oysd.com.trade_app.App;

/**
 * Created by hsg on 14/10/2017.
 */

public final class PreferencesUtils {

    private static final String SP_NAME = "HCConfig";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private PreferencesUtils() {
        // Prevent from being instantiating.
    }

    private static void init() {
        if (sp == null) {
            synchronized (PreferencesUtils.class) {
                if (sp == null) {
                    sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                    editor = sp.edit();
                }
            }
        }
    }

    private static void checkInit() {
        if (sp == null) {
            init();
        }
    }

    /**
     * Returns SharePreferences instance for chain operations.
     */
    public static SharedPreferences getSp() {
        checkInit();
        return sp;
    }

    /**
     * Returns Editor instance for chain operations.
     */
    public static SharedPreferences.Editor getEditor() {
        checkInit();
        return editor;
    }

    /**
     * Save string value.
     */
    public static void saveString(String key, String value) {
        checkInit();
        editor.putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        checkInit();
        return sp.getString(key, defValue);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * Save int value.
     */
    public static void saveInt(String key, int value) {
        checkInit();
        editor.putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        checkInit();
        return sp.getInt(key, defValue);
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * Save boolean value.
     */
    public static void saveBoolean(String key, boolean value) {
        checkInit();
        editor.putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        checkInit();
        return sp.getBoolean(key, defValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Save long value.
     */
    public static void saveLong(String key, long value) {
        checkInit();
        editor.putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        checkInit();
        return sp.getLong(key, defValue);
    }

    public static long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * 保存对象
     */
    public static boolean saveObject(String key, Object obj) {
        checkInit();
        try {
            // 先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            // 将对象序列化写入byte缓存
            os.writeObject(obj);
            // 将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            bos.close();
            os.close();
            //保存该16进制数组
            editor.putString(key, bytesToHexString);
            editor.commit();
            Log.i("保存16进制", key + "保存obj成功");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("保存16进制", key + "保存obj失败");
            return false;
        }
    }

    /**
     * desc:获取保存的Object对象
     */
    public static Object readObject(String key) {
        checkInit();
        try {
            if (sp.contains(key)) {
                String string = sp.getString(key, "");
                if (EmptyUtils.isEmpty(string)) {
                    return null;
                } else {
                    // 将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    // 返回反序列化得到的对象
                    Object readObject = is.readObject();
                    is.close();
                    bis.close();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 所有异常返回null
        return null;
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray byte array
     * @return modified:
     */
    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     *
     * @param data String
     * @return modified:
     */
    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * Remove value corresponding to the specified key.
     */
    public static boolean remove(String key) {
        checkInit();
        return editor.remove(key).commit();
    }

}
