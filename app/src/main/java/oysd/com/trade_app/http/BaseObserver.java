package oysd.com.trade_app.http;

import android.content.Context;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import oysd.com.trade_app.App;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.bean.BaseResponse;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.bean.UserBean;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.NetUtils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.LoadingDialog;
import retrofit2.HttpException;

/**
 * Base observer.
 * Created by Liam on 2018/7/20
 */
public abstract class BaseObserver<T> implements Observer<T>, OnFinishListener<T> {

    private Context context;
    private Disposable disposable;
    private LoadingDialog loadingDialog;

    public BaseObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;

        // debugging
        Logger.i("current thread is -- onSubscribe : " + Thread.currentThread().getName());

        if (context == null) {
            Logger.e("context == null.");
            return;
        }

        // Not complete
        // 检测网络连接是否正常
//        if (!NetUtils.isConnected(context)) {
//            ToastUtils.showLong(context, "");
//
//        }
    }

    @Override
    public void onNext(T t) {

        // debugging
        Logger.i("current thread is -- onNext : " + Thread.currentThread().getName());

        if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            int code = response.getStatusCode();

            if (code == 0) {
                onSuccess(t);

            } else if (code == 401) {
                // 用户未登录
                UserBean userBean = InfoProvider.getInstance().getLoginInfo();
                boolean gesture = InfoProvider.getInstance().getGesture();
                InfoProvider.getInstance().saveLogin(false);
                Global.isLogin = false;

                if (userBean != null && gesture) {
                    //IntentUtils.startActivity(context, LoginActivity.class);
                } else {
                    if (InfoProvider.getInstance().getLogin()) {
                        if (PreferencesUtils.getString("openPatternLocker") != null && PreferencesUtils.getString("openPatternLocker").equals("0")) {
                            if (!Global.isLogin) {
                                IntentUtils.startActivity(context, DefaultPatternCheckingActivity.class);
                            } else {
                                // IntentUtils.startActivity(context, LoginActivity.class);
                            }
                        } else {
                            // IntentUtils.startActivity(context, LoginActivity.class);
                        }
                    } else {
                        //  IntentUtils.startActivity(context, LoginActivity.class);
                    }
                }

            } else if (code == 403) {
                // forbidden
                ToastUtils.showLong(App.getContext(), response.getStatusMsg());

            } else if (code == 404) {
                // not found
                ToastUtils.showLong(App.getContext(), response.getStatusMsg());

            } else {
                onFailure(response.getStatusCode(), response.getStatusMsg());
            }

        } else {
            Logger.i("返回数据格式错误");
        }
    }


    @Override
    public void onError(Throwable e) {
        // debugging
        Logger.i("current thread is -- onError : " + Thread.currentThread().getName());

        Logger.d(e.toString());
        if (e instanceof HttpException) {
            // 网络错误
            // onFailure(0, errMsg)

        } else if (e instanceof SocketException) {

        } else if (e instanceof SocketTimeoutException) {

        } else if (e instanceof HttpException) {

        }

    }

    @Override
    public void onComplete() {
        Logger.d("onComplete");

        // debugging
        Logger.i("current thread is -- onComplete : " + Thread.currentThread().getName());
    }

    @Override
    public void onFailure(int code, String msg) {
    }

}
