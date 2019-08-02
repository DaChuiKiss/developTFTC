package oysd.com.trade_app.modules.mycenter.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseWebViewActivity;
import oysd.com.trade_app.util.BitmapUtils;
import oysd.com.trade_app.util.ToastUtils;

/**
 * 通用 web 页面。
 */
public class UrlWebActivity extends BaseWebViewActivity {

    // 显示的 web 的类型，如果不需要区分则可以不传递这个参数。
    public static final String EXTRA_TYPE = "type";
    // Web type
    public static final int TYPE_POSTER = 1;    // 宣传海报

    protected int type = 0;

    @BindView(R.id.ll_title_bar)
    protected LinearLayout ll_title_bar;

    @BindView(R.id.tv_title_bar_back_web)
    protected TextView tv_title_bar_back_web;

    @BindView(R.id.tv_title_bar_title_web)
    protected TextView tv_title_bar_title_web;

    @BindView(R.id.ll_title_bar_menu_share)
    LinearLayout ll_title_bar_menu_share;

    @Override
    protected void onRefreshData() {
        // 刷新页面。
        progressBar.setVisibility(View.GONE);
        loadUrlWithHeaders();
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void initData() {
        super.initData();

        type = extras.getInt(EXTRA_TYPE);
        if (type != 0) {
            setWebViewByType();
        }
    }

    // 根据 type 来设置 WebView.
    private void setWebViewByType() {
        switch (type) {
            case TYPE_POSTER:
                setTitleBarGone();
                ll_title_bar.setVisibility(View.VISIBLE);
                tv_title_bar_back_web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UrlWebActivity.this.finish();
                    }
                });

                ll_title_bar_menu_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharePic();
                    }
                });
                tv_title_bar_title_web.setText(title);

                longClickToSave();
                break;

            default:
                break;
        }
    }

    public void sharePic() {
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        Bitmap bitmap = createLongImgFromWebView();
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
        intent2.putExtra(Intent.EXTRA_STREAM, uri);
        intent2.setType("image/*");
        startActivity(Intent.createChooser(intent2, "share"));
    }

    // 长按截屏整个 WebView 页面，并保存图片。
    private void longClickToSave() {
        webView.setLongClickable(true);
        webView.setOnLongClickListener((v) -> {

            Bitmap bitmap = createLongImgFromWebView();
            if (bitmap == null) {
                ToastUtils.showLong(context, "保存图片失败");
                return true;
            }

            Boolean saveResult = BitmapUtils.saveBitmapToGallery(context, bitmap);
            if (saveResult) {
                ToastUtils.showLong(context, "保存图片成功");
            } else {
                ToastUtils.showLong(context, "保存图片失败");
            }
            return true;
        });
    }

    /**
     * 根据WebView 生成一张长图，显示整个 WebView 内容（不只一屏）。
     *
     * @return Bitmap , or null if OutMemoryError occurred.
     */
    private Bitmap createLongImgFromWebView() {
        webView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();

        Bitmap longImage;
        try {
            longImage = Bitmap.createBitmap(webView.getMeasuredWidth(), webView.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError error) {
            return null;
        }

        Canvas canvas = new Canvas(longImage);
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, webView.getMeasuredHeight(), paint);
        webView.draw(canvas);
        return longImage;
    }

}
