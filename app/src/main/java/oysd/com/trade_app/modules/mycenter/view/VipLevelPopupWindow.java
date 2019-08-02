package oysd.com.trade_app.modules.mycenter.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.modules.mycenter.adapter.VipPopupListAdapter;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.Utils;

/**
 * Vip 等级选择弹窗。
 * Created by Liam on 2018/9/20.
 */
public class VipLevelPopupWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private List<String> dataSet;
    private OnSubmitClickListener listener;
    private int selectedPosition;
    private int originalSelectedPosition;

    private View popupView;
    private ListView listView;
    private VipPopupListAdapter adapter;


    /**
     * 初始化 VipLevel list 选择弹窗.
     *
     * @param context          Context
     * @param dataSet          数据源 List
     * @param selectedPosition 已经选中的 item position
     */
    public VipLevelPopupWindow(@NonNull Context context, @NonNull List<String> dataSet,
                               int selectedPosition) {
        this(context, dataSet, selectedPosition, null);
    }

    /**
     * 始化 VipLevel list 选择弹窗.
     *
     * @param context          Context
     * @param dataSet          数据源 List
     * @param selectedPosition 已经选中的 item position
     * @param listener         OnSubmitClickListener 确认选项后,将数值传递回去的 listener
     */
    public VipLevelPopupWindow(@NonNull Context context, @NonNull List<String> dataSet,
                               int selectedPosition, @Nullable OnSubmitClickListener listener) {
        super(context);
        this.context = context;
        this.dataSet = dataSet;
        this.selectedPosition = selectedPosition;
        this.originalSelectedPosition = selectedPosition;
        this.listener = listener;

        initPopupWindow();
        setView();
        setListView();
    }

    public void setListener(OnSubmitClickListener listener) {
        this.listener = listener;
    }

    private void initPopupWindow() {
        popupView = LayoutInflater.from(context).inflate(R.layout.layout_vip_popup, null);

        setContentView(popupView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(0xb0000000));

        // 设置背景半透明
        backgroundAlpha(0.7f);
        setOnDismissListener(() -> backgroundAlpha(1f));
    }

    private void setView() {
        TextView tvTitle = popupView.findViewById(R.id.tv_vip_popup_title);
        ImageView ivCancel = popupView.findViewById(R.id.iv_vip_popup_cancel);
        Button btnSubmit = popupView.findViewById(R.id.btn_vip_popup_submit);
        listView = popupView.findViewById(R.id.lv_vip_popup_list);

        tvTitle.setText(R.string.popup_select_vip_level);
        btnSubmit.setText(R.string.OK);
        ivCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void setListView() {
        adapter = new VipPopupListAdapter(context, dataSet, selectedPosition);
        listView.setAdapter(adapter);
        listView.setDivider(null);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetChanged();
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (Utils.isFastClick()) return;

        switch (v.getId()) {
            case R.id.iv_vip_popup_cancel:
                dismiss();
                break;

            case R.id.btn_vip_popup_submit:
                if (originalSelectedPosition != selectedPosition) {
                    if (listener != null) {
                        listener.onSubmitClick(dataSet.get(selectedPosition), selectedPosition);
                    } else {
                        Logger.e("Listener == null, can not return value back.");
                    }
                }
                dismiss();
                break;

            default:
                break;
        }
    }

    public interface OnSubmitClickListener {
        void onSubmitClick(String cardLevel, int position);
    }


}
