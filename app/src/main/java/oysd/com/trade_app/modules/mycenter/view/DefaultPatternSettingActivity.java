package oysd.com.trade_app.modules.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.contract.UpdateGesturePwdContract;
import oysd.com.trade_app.modules.mycenter.presenter.UpdateGesturePwdlPresenter;
import oysd.com.trade_app.modules.mycenter.util.PatternHelper;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;


public class DefaultPatternSettingActivity extends BaseToolActivity implements UpdateGesturePwdContract.View{

    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockerView;
    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;

    @BindView(R.id.text_msg)
    TextView textMsg;

    private ActionBar actionBar;
    private PatternHelper patternHelper;

    UpdateGesturePwdContract.Presenter presenter =new UpdateGesturePwdlPresenter(this);

    public static void startAction(Context context) {
        if (!Utils.isFastClick()) {
            Intent intent = new Intent(context, DefaultPatternSettingActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.safe_patternLocker);
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
                boolean isOk = isPatternOk(hitList);
                view.updateStatus(!isOk);
                patternIndicatorView.updateState(hitList, !isOk);
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
        this.patternHelper.validateForSetting(hitList);
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
            InfoProvider.getInstance().saveGesture(true);
            String s =PreferencesUtils.getString("gesture_pwd_key");
            Logger.d(s);
            Map<String, Object> params = new HashMap<>();
            params.put("confirmGesturePassword",s);
            params.put("newGesturePassword",s);
            presenter.updateGesturePwd(params);
            finish();
        }
    }

    @Override
    protected int setView() {
        return R.layout.activity_default_pattern_setting;
    }

    @Override
    public void updateGesturePwdSuccess() {
        ToastUtils.showLong(App.getContext(),getResources().getString(R.string.message14));
    }

    @Override
    public void updateGesturePwdFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(),msg);

    }
}