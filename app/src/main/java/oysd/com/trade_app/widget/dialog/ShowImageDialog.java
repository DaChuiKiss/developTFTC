package oysd.com.trade_app.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import oysd.com.trade_app.R;
import oysd.com.trade_app.util.GlideUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 用来展示单个图片.
 * Created by Liam on 2018/8/29.
 */
public class ShowImageDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ShowImageDialog dialog;

    private String imagePath;
    private Bitmap bitmap;

    private ImageView ivShowImage;


    public ShowImageDialog(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context);
        // 未使用
    }

    public ShowImageDialog(@NonNull Context context, @NonNull String imagePath) {
        super(context, R.style.dialog);
        this.context = context;
        this.imagePath = imagePath;

        dialog = this;
        dialog.setContentView(R.layout.dialog_show_image);
        dialog.setCanceledOnTouchOutside(true);
        initView();
        showImage();
        dialog.show();

        try {
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();

            WindowManager windowManager = ((Activity) context).getWindowManager();
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            lp.width = point.x * 4 / 5;     // 宽度为屏幕的 4/5
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            // lp.width = Utils.dip2px(context, 270F);
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(android.R.color.transparent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        ivShowImage = dialog.findViewById(R.id.iv_dialog_image_show);
        ivShowImage.setOnClickListener(this);
    }

    private void showImage() {
        GlideUtils.loadImage(context, ivShowImage, imagePath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dialog_image_show:
                dismiss();
                break;

            default:
                break;
        }
    }

}
