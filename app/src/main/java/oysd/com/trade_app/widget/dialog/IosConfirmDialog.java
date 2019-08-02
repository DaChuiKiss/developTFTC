package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import oysd.com.trade_app.R;
import oysd.com.trade_app.util.Utils;

/**
 * 确认 dialog (iOS 风格).
 * <p>
 * 包含的元素：content / confirm button / cancel button .
 * </P>
 * Created by Liam on 2018/8/28.
 */
public class IosConfirmDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private OnDialogClickListener onDialogClickListener;

    private IosConfirmDialog dialog = null;

    private TextView tvConfirm;
    private TextView tvCancel;
    private TextView tvContent;

    public IosConfirmDialog(Context context) {
        this(context, null);
    }

    public IosConfirmDialog(Context context, OnDialogClickListener onDialogClickListener) {
        super(context);
        this.context = context;
        this.onDialogClickListener = onDialogClickListener;

        dialog = this;
        dialog.setContentView(R.layout.dialog_confirm_ios);
        dialog.setCanceledOnTouchOutside(false);
        initView(dialog);
        dialog.show();

        try {
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();

            // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.width = Utils.dip2px(context, 270F);
            // lp.width = Utils.dip2px(context, 110);
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(android.R.color.transparent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(IosConfirmDialog dialog) {
        tvCancel = dialog.findViewById(R.id.tv_dialog_confirm_ios_cancel);
        tvConfirm = dialog.findViewById(R.id.tv_dialog_confirm_ios_confirm);
        tvContent = dialog.findViewById(R.id.tv_dialog_confirm_ios_content);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_confirm_ios_cancel:
                if (onDialogClickListener != null) {
                    onDialogClickListener.onCancelClick(dialog);
                }
                dialog.dismissDialog();
                break;
            case R.id.tv_dialog_confirm_ios_confirm:
                if (onDialogClickListener != null) {
                    onDialogClickListener.onConfirmClick(dialog);
                }
                dialog.dismissDialog();
                break;
        }
    }

    /**
     * 设置 dialog 显示的内容。
     */
    public IosConfirmDialog setContent(@NonNull String text) {
        tvContent.setText(text);
        return this;
    }

    /**
     * 设置 dialog 显示的内容。
     */
    public IosConfirmDialog setContent(@StringRes int resId) {
        tvContent.setText(context.getString(resId));
        return this;
    }

    /**
     * 设置 confirm button 上的文字。 默认为“确认”。
     */
    public IosConfirmDialog setConfirmText(@NonNull String text) {
        tvConfirm.setText(text);
        return this;
    }

    /**
     * 设置 confirm button 上的文字。 默认为“确认”。
     */
    public IosConfirmDialog setConfirmText(@StringRes int resId) {
        tvConfirm.setText(context.getString(resId));
        return this;
    }

    /**
     * 设置 cancel button 上的文字。默认“取消”。
     */
    public IosConfirmDialog setCancelText(@NonNull String text) {
        tvCancel.setText(text);
        return this;
    }

    /**
     * 设置 cancel button 上的文字。默认“取消”。
     */
    public IosConfirmDialog setCancelText(@StringRes int resId) {
        tvCancel.setText(context.getString(resId));
        return this;
    }

    public IosConfirmDialog setOnClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
        return this;
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public interface OnDialogClickListener {

        void onConfirmClick(IosConfirmDialog dialog);

        void onCancelClick(IosConfirmDialog dialog);

    }

}
