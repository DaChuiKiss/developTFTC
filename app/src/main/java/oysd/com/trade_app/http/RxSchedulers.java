package oysd.com.trade_app.http;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import oysd.com.trade_app.App;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.NetUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.widget.dialog.LoadingDialog;

/**
 * 封装 RxJava 中常用的操作符。
 * Created by Liam on 2018/7/19
 */
public class RxSchedulers {

    /**
     * RxJava 切换线程，For Observable.
     */
    public static <T> ObservableTransformer<T, T> ioMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * RxJava 切换线程，For Flowable .
     */
    public static <T> FlowableTransformer<T, T> flowableIoMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static LoadingDialog loadingDialog;

    public static <T> ObservableTransformer<T, T> composeAll(@NonNull final Context context) {
        return composeAll(context, true);
    }

    /**
     * 将 RxJava 常用操作封装在一起，包括 Toast / Dialog 的显示。
     */
    public static <T> ObservableTransformer<T, T> composeAll(@NonNull final Context context,
                                                             final boolean showDialog) {
        // 使用 WeakReference 防止 activity 已经关闭，但是网络请求 hold 引用，
        // 造成内在泄露。
        final WeakReference<Context> weakReference = new WeakReference<>(context);

        return upstream -> {
            if (showDialog && loadingDialog == null) {
                // init ProgressDialog.
                loadingDialog = new LoadingDialog(weakReference.get());
            }

            return upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                        if (!NetUtils.isConnected(weakReference.get())) {
                            disposable.dispose();
                            ToastUtils.showLong(App.getContext(), "网络异常，请检查网络连接！");
                            return;
                        }

                        if (showDialog && loadingDialog != null && !loadingDialog.isShowing()) {
                            loadingDialog.showDialog();
                            loadingDialog.setOnCancelListener(dialogInterface -> {
                                Logger.d("Dialog cancelled and interrupt network request.");
                                disposable.dispose();
                            });
                        }
                    })
                    .doFinally(() -> {
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismissDialog();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

}
