package oysd.com.trade_app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * No scroll WebView.
 * Created by Liam on 2018/8/2
 */
public class    NoScrollWebView extends WebView {

    private OnScrollChangeCallback onScrollChangeCallback;

    public interface OnScrollChangeCallback {
        void onScroll(int l, int t);
    }


    public NoScrollWebView(Context context) {
        super(context);
    }

    public NoScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnScrollChangeCallback getOnScrollChangeCallback() {
        return onScrollChangeCallback;
    }

    public void setOnScrollChangeCallback(OnScrollChangeCallback onScrollChangeCallback) {
        this.onScrollChangeCallback = onScrollChangeCallback;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (onScrollChangeCallback != null) {
            onScrollChangeCallback.onScroll(l, t);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    // 使用 onTouchEvent 方法不能解决 和 SwipeRefreshLayout 的滑动冲突。
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                float y = getScaleY();
//
//                if (this.getScaleY() <= 0) {
//                    this.scrollTo(0, 1);
//                }
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//            default:
//                break;
//        }
//
//        return super.onTouchEvent(event);
//    }

}
