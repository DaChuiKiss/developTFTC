package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import oysd.com.trade_app.R;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 普通的 dialog (iOS 风格).
 * <p>
 * 带有以下几个元素：title / content / EditText / Positive Button / Negative Button .
 * </p>
 * Created by Liam on 2018/8/28.
 */
public class IosNormalDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private OnDialogClickListener onDialogClickListener;

    private IosNormalDialog dialog = null;

    private TextView tvTitle;
    private TextView tvContent;
    private EditText etInput;
    private TextView tvCancel;
    private TextView tvConfirm;

    // data
    private String title;
    private String content;
    private String input;
    private String inputHint;
    private String cancelText;
    private String confirmText;

    private boolean edit = false;
    private boolean password = false;


    public IosNormalDialog(@NonNull Context context) {
        this(context, null);
    }

    public IosNormalDialog(@NonNull Context context, OnDialogClickListener onDialogClickListener) {
        super(context, 0);
        this.context = context;
        this.onDialogClickListener = onDialogClickListener;

        dialog = this;
        dialog.setContentView(R.layout.dialog_normal_ios);
        dialog.setCanceledOnTouchOutside(false);
        initView();
        dialog.show();

        try {
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();

            // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.width = Utils.dip2px(context, 270F);
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(android.R.color.transparent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tvTitle = dialog.findViewById(R.id.tv_dialog_normal_title);
        tvContent = dialog.findViewById(R.id.tv_dialog_normal_content);
        etInput = dialog.findViewById(R.id.et_dialog_normal_input);
        tvCancel = dialog.findViewById(R.id.tv_dialog_normal_cancel);
        tvConfirm = dialog.findViewById(R.id.tv_dialog_normal_confirm);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    public void init() {
        if (EmptyUtils.isEmpty(title)) tvTitle.setVisibility(View.GONE);
        if (EmptyUtils.isEmpty(content)) tvContent.setVisibility(View.GONE);
        if (!edit) etInput.setVisibility(View.GONE);
        // 如果是密码，则将密码使用 * 号处理。
        if (password) etInput.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // 底部还是两个 button ，暂不作兼容。
    }

    /**
     * 返回 EditText 输入的内容。
     */
    public String getInputString() {
        return edit ? etInput.getText().toString() : "";
    }

    /**
     * 设置 title 显示的内容。
     * 如果 dialog 包含 title ，需要先设置 title 的内容再进行初始化。
     */
    public IosNormalDialog setTitleText(@NonNull String title) {
        this.title = title;
        tvTitle.setText(title);
        return this;
    }

    /**
     * 设置 title 显示的内容。
     * 如果 dialog 包含 title ，需要先设置 title 的内容再进行初始化。
     */
    public IosNormalDialog setTitleText(@StringRes int resId) {
        this.title = context.getString(resId);
        tvTitle.setText(resId);
        return this;
    }

    /**
     * 设置 title 字体显示颜色。
     */
    public IosNormalDialog setTitleColor(@ColorRes int color) {
        tvTitle.setTextColor(ContextCompat.getColor(context, color));
        return this;
    }

    /**
     * 设置 content 显示的内容。
     * 如果 dialog 包含 content ，需要先设置 content 的内容再进行初始化。
     */
    public IosNormalDialog setContent(@NonNull String content) {
        this.content = content;
        tvContent.setText(content);
        return this;
    }

    /**
     * 设置 content 显示的内容。
     * 如果 dialog 包含 content ，需要先设置 content 的内容再进行初始化。
     */
    public IosNormalDialog setContent(@StringRes int resId) {
        this.content = context.getString(resId);
        tvContent.setText(resId);
        return this;
    }

    /**
     * 设置 content 字体的显示颜色。
     */
    public IosNormalDialog setContentColor(@ColorRes int color) {
        tvContent.setTextColor(ContextCompat.getColor(context, color));
        return this;
    }

    /**
     * Dialog 是否包含 EditText 。
     * 需要先设置此项后再进行 dialog 的初始化。
     */
    public IosNormalDialog hasEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    /**
     * Dialog 中的 EditText 是否是密码（如果包含 EditText）。
     * 需要先设置此项后再进行 dialog 的初始化。
     */
    public IosNormalDialog isPassword(boolean password) {
        this.password = password;
        return this;
    }

    /**
     * 设置 EditText 的 hint 提示内容（如果包含 EditText）。
     */
    public IosNormalDialog setEditHint(@NonNull String inputHint) {
        this.inputHint = inputHint;
        etInput.setHint(inputHint);
        return this;
    }

    /**
     * 设置 EditText 的 hint 提示内容（如果包含 EditText）。
     */
    public IosNormalDialog setEditHint(@StringRes int resId) {
        this.inputHint = context.getString(resId);
        etInput.setHint(resId);
        return this;
    }

    /**
     * 设置 Cancel Button 显示的文字内容。
     */
    public IosNormalDialog setCancelText(@NonNull String cancelText) {
        this.cancelText = cancelText;
        tvCancel.setText(cancelText);
        return this;
    }

    /**
     * 设置 Cancel Button 显示的文字内容。
     */
    public IosNormalDialog setCancelText(@StringRes int resId) {
        this.cancelText = context.getString(resId);
        tvCancel.setText(resId);
        return this;
    }

    /**
     * 设置 Cancel Button 背景颜色。
     */
    public IosNormalDialog setCancelBgColor(int color) {
        tvCancel.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置 Confirm Button 显示的文字内容。
     */
    public IosNormalDialog setConfirmText(@NonNull String confirmText) {
        this.confirmText = confirmText;
        tvConfirm.setText(confirmText);
        return this;
    }

    /**
     * 设置 Confirm Button 显示的文字内容。
     */
    public IosNormalDialog setConfirmText(@StringRes int resId) {
        this.confirmText = context.getString(resId);
        tvConfirm.setText(resId);
        return this;
    }

    /**
     * 设置 Confirm Button 背景颜色。
     */
    public IosNormalDialog setConfirmBgColor(int color) {
        tvConfirm.setBackgroundColor(color);
        return this;
    }

    public IosNormalDialog setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_normal_cancel:
                if (onDialogClickListener != null) {
                    onDialogClickListener.onCancelClick(dialog);
                }
                dismissDialog();
                break;

            case R.id.tv_dialog_normal_confirm:
                if (TextUtils.isEmpty(getInputString())) {
                    ToastUtils.showShort(context, R.string.err_content_not_empty);
                } else {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onConfirmClick(dialog);
                    }
                    dismissDialog();
                }
                break;

            default:
                break;
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public interface OnDialogClickListener {

        void onConfirmClick(IosNormalDialog dialog);

        void onCancelClick(IosNormalDialog dialog);

    }

}
