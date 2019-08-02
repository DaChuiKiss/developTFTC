package oysd.com.trade_app.modules.mycenter.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.modules.mycenter.adapter.VipPopupListAdapter;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.Utils;

/**
 * 会员购买时支付货币选择弹出窗。
 * Created by Liam on 2018/9/15
 */
public class VipPurchasePopupWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private String price;
    private int showType;
    private List<VipCardPriceBean> dataSet;
    private int selectedPosition = 0;
    private OnSubmitClickListener listener;

    private View popupView;
    private ListView listView;
    private VipPopupListAdapter adapter;

    /**
     * 多种价格选择弹窗。
     *
     * @param context  Context
     * @param showType 展示类型：1. 购买时弹出；2. 续费时弹出。
     *                 区别是"购买时"的弹窗是 黄色 样式,"续费时"的弹窗是 蓝色 样式。
     * @param price    基础价格 in USDT
     * @param dataSet  多种价格列表
     */
    public VipPurchasePopupWindow(@NonNull Context context, int showType,
                                  @NonNull String price, @NonNull List<VipCardPriceBean> dataSet) {
        super(context);
        this.context = context;
        this.price = price;
        this.showType = showType;
        this.dataSet = dataSet;

        initPopupWindow();
        setView();
        setListView();
    }

    // 设置返回的回调 listener.
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

        tvTitle.setText(context.getString(R.string.popup_various_payments, price));
        btnSubmit.setText(R.string.popup_confirm_payment);

        // 根据 showType 更改弹窗样式。
        if (showType == 1) {
            // 使用 xml 中定义的值，不作更改。

        } else if (showType == 2) {
            btnSubmit.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        }

        ivCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void setListView() {
        List<String> list = new ArrayList<>();
        for (VipCardPriceBean bean : dataSet) {
            list.add(FormatUtils.double2String(bean.getPrice()) + bean.getCoinName());
        }

        adapter = new VipPopupListAdapter(context, list, selectedPosition, showType);
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
                if (listener != null) {
                    listener.onSubmitClick(dataSet.get(selectedPosition), selectedPosition);
                } else {
                    Logger.e("Listener == null, can not return value back.");
                }
                dismiss();
                break;

            default:
                break;
        }
    }

    public interface OnSubmitClickListener {
        void onSubmitClick(VipCardPriceBean bean, int position);
    }

}
