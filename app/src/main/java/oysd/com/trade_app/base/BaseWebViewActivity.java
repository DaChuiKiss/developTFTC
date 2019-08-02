package oysd.com.trade_app.base;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.common.SerializableMap;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.widget.NoScrollWebView;
/**
 * @author houmingkuan
 * @time 2018/7/11
 * @desc Base WebView activity.
 */
public abstract class BaseWebViewActivity extends BaseToolActivity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_HEADERS = "headers";

    @BindView(R.id.pb_base_progress)
    protected ProgressBar progressBar;

    @BindView(R.id.wv_base_web)
    protected NoScrollWebView webView;

    protected String url;
    protected String title;
    protected Map<String, String> headers;

    @Override
    protected void initContentView() {
        // 必须在 wbView 实例化前设置。
        enableSlowWholeWeb();
        super.initContentView();
    }

    // 为了让截屏时能够截全屏，允许 web 页面的所有内容都缓存在内存中。
    // 注意如果以后某些页面内容过多，可能会造成内存占用过多，出现 OOM 的情况。
    private void enableSlowWholeWeb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
    }

    @Override
    protected int setView() {
        return R.layout.activity_base_webview;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void onRefreshData() {
        // 刷新页面。
        progressBar.setVisibility(View.VISIBLE);
        loadUrlWithHeaders();
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void initView() {
        // progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initData() {
        url = extras.getString(EXTRA_URL);
        title = extras.getString(EXTRA_TITLE);
        Serializable serializable = extras.getSerializable(EXTRA_HEADERS);
        // 传递数据的时候要使用 SerializableMap 传递 map 对象
        headers = serializable == null ? null : ((SerializableMap) serializable).getMap();

        if (EmptyUtils.isEmpty(url)) {
            // throw new IllegalArgumentException("Url cannot be null or empty.");
        }

        setTitleText(EmptyUtils.isEmpty(title) ? "默认页面" : title);
        setWebView();
    }

    private void setWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        loadUrlWithHeaders();

        webView.setOnScrollChangeCallback((l, t) -> {
            if (t == 0) {
                setEnableRefresh(true);
            } else {
                setEnableRefresh(false);
            }
        });
    }

    /**
     * 子类需要重新加载页面时，使用本方法，支持请求添加 headers .
     */
    protected void loadUrlWithHeaders() {
        if (EmptyUtils.isEmpty(headers)) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl(url, headers);
        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                onRefreshFinish();
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }

        super.onDestroy();
    }

}
