package oysd.com.trade_app.modules.mycenter.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.dynamic.IFragmentWrapper;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import oysd.com.trade_app.App;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.modules.mycenter.bean.HomeVoBean;
import oysd.com.trade_app.modules.mycenter.bean.MyInvitationBean;
import oysd.com.trade_app.modules.mycenter.contract.MyInvitationContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyInvitationPresenter;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;

/**
 * @author houmingkuan
 * @time 2019/7/16
 * @desc 我的邀请
 */
public class MyInvitationActivity extends BaseToolActivity implements MyInvitationContract.View {

    @BindView(R.id.tv_person)
    TextView mPerson;
    @BindView(R.id.tv_invitation_code)
    TextView mInvitationCode;
    @BindView(R.id.tv_recommended_links)
    TextView mRecommendedLinks;
    @BindView(R.id.tv_html)
    TextView webView;
    MyInvitationContract.Presenter presenter = new MyInvitationPresenter(this);
    HomeVoBean homeVoBean;
    private String nytReturnAmount;
    private String vcReturnAmount;

    @Override
    protected int setView() {
        return R.layout.activity_my_invitation;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.type4);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHomeVo();
        presenter.getActivityInfo(1);
    }

    @Override
    protected void initClicks() {
        //我的奖励
        rxClickById(R.id.tv_my_award).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(MyInvitationActivity.this, MyAwardActivity.class);
                intent.putExtra("nytReturnAmount", nytReturnAmount);
                intent.putExtra("vcReturnAmount", vcReturnAmount);
                quickStartActivity(intent);
            }
        });

        //邀请码复制
        rxClickById(R.id.ll_copy_invitation_code).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ClipboardManager clipboard = (ClipboardManager) MyInvitationActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", mInvitationCode.getText());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                ToastUtils.showLong(App.getContext(), getResources().getString(R.string.copySuccess));
            }
        });

        //邀请链接复制
        rxClickById(R.id.ll_copy_recommended_links).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                ClipboardManager clipboard = (ClipboardManager) MyInvitationActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", mRecommendedLinks.getText());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                ToastUtils.showLong(App.getContext(), getResources().getString(R.string.copySuccess));
            }
        });

    }

    @Override
    public void getHomeVoSuncess(HomeVoBean homeVoBean) {
        if (homeVoBean == null) return;
        nytReturnAmount = homeVoBean.getNytReturnAmount();
        vcReturnAmount = homeVoBean.getVcReturnAmount();
        if (!TextUtils.isEmpty(homeVoBean.getInviteCode())) {
            mInvitationCode.setText(homeVoBean.getInviteCode());
            mRecommendedLinks.setText(Constants.INVITATION_URL + homeVoBean.getInviteCode());
        }

        mPerson.setText(homeVoBean.getInviteCount() + "");
    }

    @Override
    public void getHomeVoFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

    @Override
    public void getActivityInfoSuncess(MyInvitationBean homeVoBean) {
        if (homeVoBean == null)
            return;
        webView.setText(Html.fromHtml(homeVoBean.getContent()));
    }

    @Override
    public void getActivityInfoFailed(int code, String msg) {
        ToastUtils.showLong(this, msg);
    }

}