package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import oysd.com.trade_app.R;

/**
 * 加载数据、网络请求时的 dialog .
 * <p>
 * android 原生样式，转圆圈的 loading dialog.
 * </P>
 * Created by Liam on 2018/10/16
 */
public class LoadingDialog extends Dialog {

    private Context context;
    private LoadingDialog dialog = null;

    public LoadingDialog(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        dialog = this;

        // Set dialog
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.show();

        try {
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(android.R.color.transparent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
