package oysd.com.trade_app.modules.home.view.information;

import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

public class MarqueeViewActivity extends BaseToolActivity {
    @BindView(R.id.tv_title)
    TextView mTitle;
    private String content;

    @Override
    protected int setView() {
        return R.layout.activity_marquee_view;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(getIntent().getStringExtra("title"));
        content = getIntent().getStringExtra("content");
        mTitle.setText(Html.fromHtml(content));
    }
}
