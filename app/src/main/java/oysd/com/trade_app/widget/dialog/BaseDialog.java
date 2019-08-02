package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Base dialog.
 * Created by Liam on 2018/7/11
 */
public class BaseDialog extends Dialog implements View.OnClickListener {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onClick(View v) {

    }
}
