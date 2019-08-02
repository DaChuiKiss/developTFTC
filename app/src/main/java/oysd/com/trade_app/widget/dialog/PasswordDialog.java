package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.ToastUtils;

/**
 * Password dialog.
 * Created by Liam on 2018/8/4
 */
public class PasswordDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView tvTitle;
    private TextView content;
    private EditText etPassword;
    private Button btnCancel;
    private Button btnSubmit;
    boolean hasTitle;
    boolean hasVisableText;
    String title, contentStr;
    int style;
    private OnSubmitListener onSubmitListener;

    public interface OnSubmitListener {
        void onSubmit(PasswordDialog dialog, String password);
    }


    public PasswordDialog(Context context, String title, String contentStr, boolean hasTitle, boolean hasVisableText, int style) {
        super(context, style);
        setContentView(R.layout.dialog_input_password);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        this.hasTitle = hasTitle;
        this.hasVisableText = hasVisableText;
        this.title = title;
        this.contentStr = contentStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super()
//        try {
//            setContentView(R.layout.dialog_input_password);
//            Window window = getWindow();
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.gravity = Gravity.CENTER;
//            window.setAttributes(params);
//
//  //          WindowManager windowManager = ((Activity) context).getWindowManager();
////            Display display = windowManager.getDefaultDisplay();
////
//////            WindowManager.LayoutParams lp = window.getAttributes();
//////            Point point = new Point();
//////            display.getSize(point);
//////            lp.width = point.x * 7 / 10;
//////            lp.height = point.y * 3 / 10;
//////
//////            window.setAttributes(lp);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_dialog_input_title);
        etPassword = findViewById(R.id.et_dialog_input_password);
        btnCancel = findViewById(R.id.btn_dialog_input_cancel);
        btnSubmit = findViewById(R.id.btn_dialog_input_submit);
        content = findViewById(R.id.tv_dialog_input_content);
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
//        if (context.getString(R.string.dialogMsg7).equals(contentStr)) {
//            btnCancel.setVisibility(View.GONE);
//            btnSubmit.setBackground(context.getResources().getDrawable(R.drawable.bt_dialog_ads_blue));
//        }
        if (hasTitle) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            content.setTextSize(15);
            tvTitle.setVisibility(View.GONE);
        }
        content.setText(contentStr);
        if (hasVisableText) {

            etPassword.setVisibility(View.VISIBLE);

        } else {
            etPassword.setVisibility(View.GONE);
        }
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_input_cancel:
                dismiss();
                break;

            case R.id.btn_dialog_input_submit:
                if (etPassword.getVisibility() == View.VISIBLE) {
                    String password = etPassword.getText().toString();
                    if (EmptyUtils.isEmpty(password)) {
                        ToastUtils.showLong(App.getContext(), "请输入交易密码");
                        dismiss();
                    }
                    if (onSubmitListener != null) {
                        dismiss();
                        onSubmitListener.onSubmit(this, password);
                    }
                } else {
                    if (onSubmitListener != null) {
                        dismiss();
                        onSubmitListener.onSubmit(this, null);
                    }
                }

                break;

            default:
                break;
        }
    }

}
