package oysd.com.trade_app.modules.mycenter.view;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.util.Utils;

public class CertificationStep3 extends BaseToolActivity{

    @BindView(R.id.bt_mycenter_cert3_back)
    Button bt_mycenter_cert3_back;

    @Override
    protected int setView() {
        return R.layout.activity_certification_step3;
    }


    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_cert3_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_mycenter_cert3_back:
                    setResult(RESULT_OK);
                    ActivitiesManager.getInstance().finishActivity(CertificationStep3.class);
                    break;
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.audit);
    }

}
