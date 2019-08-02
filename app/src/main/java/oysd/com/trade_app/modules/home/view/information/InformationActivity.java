package oysd.com.trade_app.modules.home.view.information;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

public class InformationActivity extends BaseToolActivity{

    @BindView(R.id.bt_back_home)
    Button bt_back_home;

    @Override
    protected int setView() {
        return R.layout.activity_information;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(getResources().getString(R.string.home_message));
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_back_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_back_home:
                finish();
                break;
        }
    }
}
