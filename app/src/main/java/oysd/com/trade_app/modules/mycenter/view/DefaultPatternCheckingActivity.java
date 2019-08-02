package oysd.com.trade_app.modules.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.modules.mycenter.util.PatternHelper;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Utils;


public class DefaultPatternCheckingActivity extends BaseToolActivity {
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockerView;
    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;
    @BindView(R.id.text_msg)
    TextView textMsg;

    @BindView(R.id.tv_gotoLogin)
    TextView tv_gotoLogin;

    private ActionBar actionBar;
    private PatternHelper patternHelper;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DefaultPatternCheckingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int setView() {
        return R.layout.activity_default_pattern_checking;
    }

    @Override
    protected void initView() {
        super.initView();
        Global.isLogin=false;
        setTitleText(R.string.safe_patternLocker);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.tv_gotoLogin:
                    quickStartActivityResult(LoginActivity.class, 2);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==RESULT_OK){
            DefaultPatternCheckingActivity.this.finish();

        }
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        tv_gotoLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

        this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isError = !isPatternOk(hitList);
                view.updateStatus(isError);
                patternIndicatorView.updateState(hitList, isError);
                updateMsg();
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });

        this.textMsg.setText(getResources().getString(R.string.handPwdMessage));
        this.patternHelper = new PatternHelper();
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ?
                getResources().getColor(R.color.text_blue) :
                getResources().getColor(R.color.text_blue));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish()) {
            Global.isLogin=true;
            finish();
        }else{
            if(this.patternHelper.getRemainTimes()==1) {
                Global.isLogin = false;
                InfoProvider.getInstance().saveGesture(false);
                InfoProvider.getInstance().saveLogin(false);
                Intent intent = new Intent(DefaultPatternCheckingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
