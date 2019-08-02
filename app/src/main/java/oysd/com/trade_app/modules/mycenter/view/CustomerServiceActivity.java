package oysd.com.trade_app.modules.mycenter.view;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

/**
 * 客服中心
 */
public class CustomerServiceActivity extends BaseToolActivity {


    @Override
    protected int setView() {
        return R.layout.customer_service_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type7);
    }

}
