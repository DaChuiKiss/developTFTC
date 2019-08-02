package oysd.com.trade_app.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;
import retrofit2.HttpException;

/**
 * 用于处理 response 返回的 observer。
 * 和 BaseObserver 的区别在于：DataObserver 只能返回单个的 data 。
 *
 * <P>
 *  只有当返回数据为 ResponseEntity 时才能使用 DataObserver 。
 * </P>
 *
 * Created by Liam on 2018/7/20
 */
public abstract class DataObserver<T>
        implements Observer<ResponseEntity<T>>, OnFinishListener<T> {

    private Context context;
    private Disposable disposable;

    public DataObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(ResponseEntity<T> entity) {
        int code = entity.getStatusCode();

        if (code == 0) {
            onSuccess(entity.getData());

        } else if (code == 401) {
            // 用户未登录
            UserBean userBean = InfoProvider.getInstance().getLoginInfo();
            boolean gesture = InfoProvider.getInstance().getGesture();
            InfoProvider.getInstance().saveLogin(false);
            Global.isLogin = false;

            if (userBean != null && gesture) {
                //IntentUtils.startActivity(context, LoginActivity.class);
            } else {
                //ToastUtils.showLong(context, "应该跳到手势密码登录界面，但是现在还没有。");
//                if(!Global.isLogin) {
//                    if (PreferencesUtils.getString("openPatternLocker") != null && PreferencesUtils.getString("openPatternLocker").equals("0")) {
//
//                        IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
//                    }else{
//                        IntentUtils.startActivity(context, LoginActivity.class);
//
//                    }
//                } else {
//                      IntentUtils.startActivity(context, LoginActivity.class);
//                }
                if (InfoProvider.getInstance().getLogin()) {
                    if (PreferencesUtils.getString("openPatternLocker") != null && PreferencesUtils.getString("openPatternLocker").equals("0")) {
                        if (!Global.isLogin) {
                            IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                        } else {
                            //IntentUtils.startActivity(context, LoginActivity.class);
                        }
                    } else {
                        //IntentUtils.startActivity(context, LoginActivity.class);
                    }
                } else {
                    //IntentUtils.startActivity(context, LoginActivity.class);
                }

            }

        } else if (code == 403) {
            // forbidden
            ToastUtils.showLong(App.getContext(), entity.getStatusMsg());

        } else if (code == 404) {
            // not found
            ToastUtils.showLong(App.getContext(), entity.getStatusMsg());

        } else {
            onFailure(entity.getStatusCode(), entity.getStatusMsg());
        }
    }


    @Override
    public void onError(Throwable e) {
        Logger.e(e.toString());

        if (e instanceof HttpException) {
            // 网络错误
            // onFailure(..)
        }
    }

    @Override
    public void onComplete() {
        Logger.d("onComplete");
    }

    @Override
    public void onFailure(int code, String msg) {
    }

}
