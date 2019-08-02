package oysd.com.trade_app.modules.home.view.appstore;

import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.App;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.util.ToastUtils;

public class AppActivity extends BaseToolActivity {

    @BindView(R.id.ll_app_gift)
    LinearLayout ll_app_gift;

    @BindView(R.id.ll_app_wealth_money)
    LinearLayout ll_app_wealth_money;

    @BindView(R.id.ll_app_up_coin)
    LinearLayout ll_app_up_coin;

    @Override
    protected int setView() {
        return R.layout.activity_app;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(getResources().getString(R.string.home_app));
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        ll_app_gift.setOnClickListener(this);
        ll_app_up_coin.setOnClickListener(this);
        ll_app_wealth_money.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_app_gift:
            case R.id.ll_app_wealth_money:
            case R.id.ll_app_up_coin:
                ToastUtils.showShort(App.getContext(), getResources().getString(R.string.wait));
                break;
        }
    }
}
