package oysd.com.trade_app.modules.trade.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

public class DrawableEditText extends android.support.v7.widget.AppCompatEditText {

    public DrawableEditText(Context context) {

        super(context);

    }



    public DrawableEditText(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

    }



    public DrawableEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }



    @Override

    protected void onDraw(Canvas canvas) {

        // getCompoundDrawables() : Returns drawables for the left, top, right, and bottom borders.

        Drawable[] drawable = getCompoundDrawables();

        //得到drawableLeft设置的drawable对象

        Drawable leftDrawable = drawable[0];

        if (leftDrawable != null) {

            //得到leftdrawable 的宽度

            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();

            //得到drawable与text之间的间距

            int drawablePadding = getCompoundDrawablePadding();

            //得到文本的宽度

            int textWidth = (int) getPaint().measureText(getHint().toString().trim());



            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;

            canvas.save();

            //将内容在X轴方向平移

            canvas.translate((getWidth()/2 - bodyWidth/2), 0);

        }



        super.onDraw(canvas);

    }
}
