package oysd.com.trade_app.modules.mycenter.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Vip card 切换时的效果。
 * Created by Liam on 2018/9/15
 */
public class VipCardTransformer implements ViewPager.PageTransformer {

    private final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        // Logger.d("position : " + position);

        if (position < -1 || position > 1) {
            page.setAlpha(MIN_ALPHA);
        } else {
            // 不透明->半透明
            if (position < 0) {//[0,-1]
                page.setAlpha(MIN_ALPHA + (1 + position) * (1 - MIN_ALPHA));
            } else {//[1,0]
                // 半透明->不透明
                page.setAlpha(MIN_ALPHA + (1 - position) * (1 - MIN_ALPHA));
            }
        }
    }

}
