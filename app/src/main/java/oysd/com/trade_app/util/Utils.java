package oysd.com.trade_app.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import oysd.com.trade_app.common.InfoProvider;

/**
 * Created by 亮亮 on 2018/6/25.
 */

public final class Utils {

    private static final int MIN_DELAY_TIME= 400;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    private Utils() {
        // Prevents from being instantiating.
    }

    public static boolean checkLoginStatus(Context context) {

        return false;
    }

    /**
     * Converts values to a list.
     */
    public static <T> List<T> array2List(T... values) {
        return Arrays.asList(values);
    }

    /**
     * Converts a HashMap to RequestBody.
     */
    public static RequestBody mapToBody(@NonNull Map<String, Object> map) {
        Gson gson = new Gson();
        return RequestBody.create(MediaType.parse("application/json"), gson.toJson(map));
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {

        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

        return (int) (spValue * fontScale + 0.5f);

    }

    //获得屏幕的宽度
    public static int getScreenWidth(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    //获得屏幕的高度
    public static int getScreenHeight(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    //获得屏幕的像素密度
    public static float getScreenDensity(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    //获得线性布局的实际高度
    public static int getRealHeight(View child) {
        LinearLayout llayout = (LinearLayout) child;
        LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        int heightSpec;
        if (params.height > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        llayout.measure(widthSpec, heightSpec);
        return llayout.getMeasuredHeight();
    }


    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public static boolean isFastClick(View view){
//        boolean flag = true;
//        long lastTime = view.getTag(-1)==null?0:(long)view.getTag(-1);
//        if (System.currentTimeMillis()-lastTime>400){
//            flag = false;
//        }
//        view.setTag(-1,System.currentTimeMillis());
//        return flag;

        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }


    public static String getKtotal(BigDecimal total){
        if(total==null){
            return "0";
        }
        String s;
        if(total.compareTo(BigDecimal.valueOf((double)1000))<0){
            if(total.compareTo(BigDecimal.valueOf((double)1000))<0&&total.compareTo(BigDecimal.valueOf((double)100))>=0){
               s=FormatUtils.to4NoComma(total);
            }else if (total.compareTo(BigDecimal.valueOf((double)100))<0&&total.compareTo(BigDecimal.valueOf((double)10))>=0){
               s =FormatUtils.to6NoComma(total);
            }else{
                 s =FormatUtils.to8NoComma(total);
            }
            if(s.indexOf(".") > 0){
                //正则表达
                s = s.replaceAll("0+?$", "");//去掉后面无用的零
                s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            return  s;
        }else{
            s=FormatUtils.to2NoComma(total.multiply(BigDecimal.valueOf((double)0.001)));
            if(s.indexOf(".") > 0){
                //正则表达
                s = s.replaceAll("0+?$", "");//去掉后面无用的零
                s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            return s+"k";
        }

    }


    public static String myAccountFormat(BigDecimal total){
        if(total==null){
            return "0";
        }
        String s =FormatUtils.to8NoComma(total);
        if(s.indexOf(".") > 0){
           //正则表达
           s = s.replaceAll("0+?$", "");//去掉后面无用的零
           s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return  s;
    }

    //判断中文还是英文来跳转相应静态页面
    public static boolean isChina(){
        boolean ischina=false;
        if(PreferencesUtils.getString("selectLanguage")!=null){
            if(PreferencesUtils.getString("selectLanguage").equals("1")){
                ischina=true;
            }else{
                ischina=false;
            }
        }else{
            Locale locale = Locale.getDefault();

            if(locale.getLanguage().equals("zh")){
                ischina=true;
            }else{
                ischina=false;
            }
        }
        return ischina;
    }

    public  static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
      //  return s;
    }

    public static void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        String s =mTextView.getText().toString();
                        Log.d("d",s);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
