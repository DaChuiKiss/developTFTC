package oysd.com.trade_app.main;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Locale;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.main.contract.GetUrlContract;
import oysd.com.trade_app.main.presenter.GetUrlListPresenter;
import oysd.com.trade_app.main.view.CustomVideoView;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.SafeBean;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.StatusBarUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class SplashActivity extends BaseToolActivity implements GetUrlContract.View {


    @BindView(R.id.video_view)
    CustomVideoView video_view;
    MediaController mMediaController;

    GetUrlContract.Presenter presenter = new GetUrlListPresenter(this);
    static SplashActivity activity = new SplashActivity();

    @Override
    protected int setView() {
        return R.layout.init_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setStatusBarLightMode(SplashActivity.this, true);

        setRefreshLayoutTexts();
        mMediaController = new MediaController(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.launcher;
        video_view.setVideoURI(Uri.parse(uri));

        video_view.start();

        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video 视屏播放的时候把背景设置为透明
                            video_view.setBackgroundColor(Color.TRANSPARENT);
                            return true;
                        }
                        return false;
                    }

                });
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                presenter.accessTokenIsOk();
            }
        }).start();
    }

    private void setRefreshLayoutTexts() {
        // set header
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.refresh_header_pull_down);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.refresh_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.refresh_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.refresh_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.refresh_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.refresh_header_failed);
        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.refresh_header_last_time);
        ClassicsHeader.REFRESH_HEADER_SECOND_FLOOR = getString(R.string.refresh_header_second_floor);

        // set footer
        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.refresh_footer_pull_up);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.refresh_footer_release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.refresh_footer_loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.refresh_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.refresh_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.refresh_footer_failed);
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.refresh_footer_all_loaded);
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean ub) {
        InfoProvider.getInstance().saveUserInfo(ub);
        PreferencesUtils.saveString("gesture_pwd_key", InfoProvider.getInstance().getUserInfo().getGesturePassword());
        if (InfoProvider.getInstance().getWelcome()) {
            InfoProvider.getInstance().setWelcome(false);
            quickStartActivity(WelcomeActivity.class);
        } else {
            quickStartActivity(MainActivity.class);
        }

        this.finish();
    }

    @Override
    public void getUserInfoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), R.string.web_error);
        if (InfoProvider.getInstance().getWelcome()) {
            InfoProvider.getInstance().setWelcome(false);
            quickStartActivity(WelcomeActivity.class);
        } else {
            quickStartActivity(MainActivity.class);
        }
        this.finish();
    }

    @Override
    public void getUrlListSuccess(ResponseList<UrlBean> response) {
        Global.urlList.clear();
        Global.urlList = response.getData();
        if (InfoProvider.getInstance().getWelcome()) {
            InfoProvider.getInstance().setWelcome(false);
            quickStartActivity(WelcomeActivity.class);
        } else {
            quickStartActivity(MainActivity.class);
        }
        this.finish();
    }

    @Override
    public void getUrlListFail(int code, String msg) {
    }


    @Override
    public void accessTokenIsOk(int code, AuthInfoBean authInfoBean) {
        if (code == 401) {
            InfoProvider.getInstance().saveLogin(false);
            Global.isLogin = false;
            if (InfoProvider.getInstance().getWelcome()) {
                InfoProvider.getInstance().setWelcome(false);
                quickStartActivity(WelcomeActivity.class);
            } else {
                quickStartActivity(MainActivity.class);
            }
            this.finish();
        } else {
            if (authInfoBean != null) {
                presenter.getUserInfo();
                if (authInfoBean.getDealPasswordExists() == 1) {
                    InfoProvider.getInstance().setAccount(true);
                } else {
                    InfoProvider.getInstance().setAccount(false);
                }

                if (authInfoBean.getVerifyRealStatus() == 1) {
                    InfoProvider.getInstance().setCert(true);
                } else {
                    InfoProvider.getInstance().setCert(false);
                }

                if (authInfoBean.getAdvancedStatus() == 2) {
                    InfoProvider.getInstance().setSeniorCert(true);
                } else {
                    InfoProvider.getInstance().setSeniorCert(false);
                }
            } else {
                if (InfoProvider.getInstance().getWelcome()) {
                    InfoProvider.getInstance().setWelcome(false);
                    quickStartActivity(WelcomeActivity.class);
                } else {
                    quickStartActivity(MainActivity.class);
                }
                this.finish();
            }
        }
    }

    @Override
    public void netIsError() {
        ToastUtils.showLong(App.getContext(), "网络异常");
        if (InfoProvider.getInstance().getWelcome()) {
            InfoProvider.getInstance().setWelcome(false);
            quickStartActivity(WelcomeActivity.class);
        } else {
            quickStartActivity(MainActivity.class);
        }
        this.finish();
    }


    public static SplashActivity getActivity() {

        return activity;
    }


}
