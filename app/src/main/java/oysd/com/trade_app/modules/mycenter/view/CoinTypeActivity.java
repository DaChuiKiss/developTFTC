package oysd.com.trade_app.modules.mycenter.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 货币选择
 */
public class CoinTypeActivity extends BaseToolActivity {


    @BindView(R.id.tv_hkd_id)
    TextView tv_hkd_id;
    @BindView(R.id.tv_cny_id)
    TextView tv_cny_id;
    @BindView(R.id.tv_usdt_id)
    TextView tv_usdt_id;
    @BindView(R.id.tv_aud_id)
    TextView tv_aud_id;

    @Override
    protected int setView() {
        return R.layout.activity_coin_type;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        tv_hkd_id.setOnClickListener(this);
        tv_cny_id.setOnClickListener(this);
        tv_usdt_id.setOnClickListener(this);
        tv_aud_id.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.tv_hkd_id:
                    PreferencesUtils.saveString("selectCoinType", "1");
                    InfoProvider.getInstance().saveLegalTender("HKD");
                    this.finish();
                    break;
                case R.id.tv_cny_id:
                    PreferencesUtils.saveString("selectCoinType", "4");
                    InfoProvider.getInstance().saveLegalTender("CNY");
                    this.finish();
                    break;
                case R.id.tv_usdt_id:
                    PreferencesUtils.saveString("selectCoinType", "2");
                    InfoProvider.getInstance().saveLegalTender("USD");
                    this.finish();
                    break;
                case R.id.tv_aud_id:
                    PreferencesUtils.saveString("selectCoinType", "3");
                    InfoProvider.getInstance().saveLegalTender("AUD");
                    this.finish();
                    break;

            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.selectCoinType);
        Drawable nav_up = getResources().getDrawable(R.mipmap.my_check);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if (PreferencesUtils.getString("selectCoinType", "1").equals("1")) {
            tv_hkd_id.setCompoundDrawables(null, null, nav_up, null);
        } else if (PreferencesUtils.getString("selectCoinType", "1").equals("2")) {
            tv_usdt_id.setCompoundDrawables(null, null, nav_up, null);
        } else if (PreferencesUtils.getString("selectCoinType", "1").equals("3")) {
            tv_aud_id.setCompoundDrawables(null, null, nav_up, null);
        } else if (PreferencesUtils.getString("selectCoinType", "1").equals("4")) {
            tv_cny_id.setCompoundDrawables(null, null, nav_up, null);
        }
    }


    @Override
    protected void initData() {
        super.initData();
    }
}
