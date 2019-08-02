package oysd.com.trade_app.modules.mycenter.view;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;


public class HelpActivity extends BaseToolActivity {


    @Override
    protected int setView() {
        return R.layout.help_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type9);
    }
}
