package oysd.com.trade_app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * Spannable editor.
 * Created by Liam on 2018/4/26.
 */
public final class SpannableEditor {

    private Context context;
    private String text;
    private SpannableStringBuilder builder;

    public SpannableEditor(Context context, CharSequence text) {
        this.context = context;
        this.text = text.toString();
        builder = new SpannableStringBuilder(text);
    }

    public SpannableEditor(Context context, String text) {
        this.context = context;
        this.text = text;
        builder = new SpannableStringBuilder(text);
    }

    /**
     * @return SpannableStringBuilder after all Spans have been set.
     */
    public SpannableStringBuilder build() {
        return builder;
    }

    /**
     * 更改 text 中某一部分（几部分）字体的颜色。
     */
    public SpannableEditor setTextColor(int textColor, String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new ForegroundColorSpan(textColor), begin, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 更改 text 中部分内容颜色。
     *
     * @param textColor 想要更改的颜色
     * @param begin     开始位置（不包括）
     * @param end       结束位置（不包括）
     */
    public SpannableEditor setTextColor(@ColorInt int textColor, int begin, int end) {
        builder.setSpan(new ForegroundColorSpan(textColor), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 更改 text 中某一部分（几部分）字体的大小。
     */
    public SpannableEditor setTextSize(int textSize, String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new AbsoluteSizeSpan(textSize), begin, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 更改 text 中某一部分（几部分）字体的背景颜色。
     */
    public SpannableEditor setTextBgColor(int textBgColor, String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new BackgroundColorSpan(textBgColor), begin, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置 text 中某一部分（几部分）字体的背景图片。
     */
    public SpannableEditor setTextBgImage(int textBgImage, String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new ImageSpan(context, textBgImage), begin, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置 text 中某一部分（几部分）字体的背景图片，可设置图片的宽高。
     */
    public SpannableEditor setTextBgImage(int textBgImage, int width, int height, String... subTextArray) {
        int begin = 0;
        int end = 0;
        Drawable drawable = this.context.getResources().getDrawable(textBgImage);
        drawable.setBounds(0, 0, width, height);

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), begin, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置 text 中某一部分（几部分）字体为 斜体。
     */
    public SpannableEditor setTextItali(String... subTextArray) {
        return setTextTypeface(Typeface.ITALIC, subTextArray);
    }

    /**
     * 设置 text 中某一部分（几部分）字体 为粗体。
     */
    public SpannableEditor setTextBold(String... subTextArray) {
        return setTextTypeface(Typeface.BOLD, subTextArray);
    }

    /**
     * 更改 text 中某一部分（几部分）字体为对应的字体。
     */
    public SpannableEditor setTextTypeface(int textStyle, String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new StyleSpan(textStyle), begin, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置 text 中某一部分（几部分）字体有删除线效果。
     */
    public SpannableEditor setTextStrikethrough(String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new StrikethroughSpan(), begin, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置 text 中某一部分（几部分）字体有下划线效果。
     */
    public SpannableEditor setTextUnderline(String... subTextArray) {
        int begin = 0;
        int end = 0;

        for (String subText : subTextArray) {
            begin = this.text.indexOf(subText, end);
            end = begin + subText.length();
            builder.setSpan(new UnderlineSpan(), begin, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

}
