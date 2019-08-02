package oysd.com.trade_app.modules.mycenter.view;

import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

/**
 * @author houmingkuan
 * @time 2019/7/2
 * @desc 邀请好友页面
 */
public class InvitationActivity extends BaseToolActivity {

    @Override
    protected int setView() {
        return R.layout.invitation_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type4);
    }


}
