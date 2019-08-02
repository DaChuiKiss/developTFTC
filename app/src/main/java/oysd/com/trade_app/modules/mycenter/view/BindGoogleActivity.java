package oysd.com.trade_app.modules.mycenter.view;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

public class BindGoogleActivity extends BaseToolActivity {

   @BindView(R.id.bt_mycenter_google_update)
    Button bt_mycenter_google_update;


    @Override
    protected int setView() {
        return R.layout.activity_bind_google;
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_google_update.setOnClickListener(this);
    }
    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.bindGoogle);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_mycenter_google_update:
                quickStartActivity(BindGoogleNextActivity.class);
                break;
        }
    }
}
