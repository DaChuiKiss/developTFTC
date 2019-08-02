package oysd.com.trade_app.modules.otc;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import oysd.com.trade_app.R;
import oysd.com.trade_app.modules.otc.bean.OtcAdBean;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

/**
 * OTC 购买或出售时的弹窗。
 * Created by Liam on 2018/7/18
 */
public class OtcPopupWindow extends PopupWindow implements View.OnClickListener {

    private TextView tvType;
    private TextView tvPrice;

    private TextView tvMoneyText;
    private EditText etMoney;
    private TextView tvMoneyUnit;
    private TextView tvMoneyAll;

    private TextView tvQuantityText;
    private EditText etQuantity;
    private TextView tvQuantityUnit;
    private TextView tvQuantityAll;

    private TextView tvLimit;
    private TextView tvActual;
    private TextView tvTotal;

    private TextView tvConfirm;
    private TextView tvCancel;

    private Context context;
    private String type;
    private OtcAdBean otcAdBean;
    private String currencySymbol;
    private String coinName;

    private OnSubmitClickListener listener;
    private View popupWindowView;

    public OtcPopupWindow(Context context, String type, OtcAdBean otcAdBean,
                          OnSubmitClickListener listener) {
        super(context);
        this.context = context;
        this.type = type;
        this.otcAdBean = otcAdBean;
        this.listener = listener;
        initPopupWindow();

        initView();
        initClicks();
        initData();
    }

    private void initPopupWindow() {
        /*
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.otc_popup_confirm, null);
        */
        popupWindowView = LayoutInflater.from(context).inflate(R.layout.otc_popup_confirm, null);
        this.setContentView(popupWindowView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        // 设置背景半透明
        backgroundAlpha(0.7f);
        setOnDismissListener(() -> backgroundAlpha(1f));
    }

    private void initView() {
        tvType = popupWindowView.findViewById(R.id.tv_otc_popup_type);
        tvPrice = popupWindowView.findViewById(R.id.tv_otc_popup_price);

        tvMoneyText = popupWindowView.findViewById(R.id.tv_otc_popup_money_text);
        etMoney = popupWindowView.findViewById(R.id.et_otc_popup_money);
        tvMoneyUnit = popupWindowView.findViewById(R.id.tv_otc_popup_money_unit);
        tvMoneyAll = popupWindowView.findViewById(R.id.tv_otc_popup_money_all);

        tvQuantityText = popupWindowView.findViewById(R.id.tv_otc_popup_quantity_text);
        etQuantity = popupWindowView.findViewById(R.id.et_otc_popup_quantity);
        tvQuantityUnit = popupWindowView.findViewById(R.id.tv_otc_popup_quantity_unit);
        tvQuantityAll = popupWindowView.findViewById(R.id.tv_otc_popup_quantity_all);

        tvLimit = popupWindowView.findViewById(R.id.tv_otc_popup_limit);
        tvActual = popupWindowView.findViewById(R.id.tv_otc_popup_actual);
        tvTotal = popupWindowView.findViewById(R.id.tv_otc_popup_total);

        tvConfirm = popupWindowView.findViewById(R.id.tv_otc_popup_confirm);
        tvCancel = popupWindowView.findViewById(R.id.tv_otc_popup_cancel);

        setValues();
    }

    private void setValues() {


        currencySymbol = ViewHelper.getCurrencySymbolById(otcAdBean.getSettlementCurrency());


        coinName = otcAdBean.getCoinName();

        String strType = type + " " + coinName;
        tvType.setText(strType);
        String strPrice = currencySymbol + FormatUtils.to2(otcAdBean.getOnlineOrderPrice());
        tvPrice.setText(strPrice);

        String strEnterHint = context.getString(R.string.pls_enter_buy_or_sell_amount, type);
        String strAll = context.getString(R.string.all) + " " + type;

        String strMoneyText = context.getString(R.string.money_amount);
        tvMoneyText.setText(strMoneyText);
        etMoney.setHint(strEnterHint);
        tvMoneyUnit.setText(currencySymbol);
        tvMoneyAll.setText(strAll);

        String strQuantityText = context.getString(R.string.quantity);
        tvQuantityText.setText(strQuantityText);
        etQuantity.setHint(strEnterHint);
        tvQuantityUnit.setText(coinName);
        tvQuantityAll.setText(strAll);

        String textLimit = FormatUtils.to2(otcAdBean.getMinAmountTransation().multiply(otcAdBean.getOnlineOrderPrice()))
                + "~" + FormatUtils.to8NoComma(otcAdBean.getMaxAmountTranscation().multiply(otcAdBean.getOnlineOrderPrice()));

        tvLimit.setText(context.getString(R.string.limit_amount_2_args, currencySymbol, textLimit));

        String textActual = context.getString(R.string.actually_buy_or_sell, "", (FormatUtils.to8("") + coinName));
        tvActual.setText(textActual);

        String textTotal = context.getString(R.string.exchange_total_amount, currencySymbol, FormatUtils.to2(""));
        tvTotal.setText(textTotal);
    }

    private void initClicks() {
        tvMoneyAll.setOnClickListener(this);
        tvQuantityAll.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMoney.isFocused()) {
                    String str = s.toString();

                    if (EmptyUtils.isEmpty(str)) {
                        etQuantity.setText(FormatUtils.to8NoComma(str));
                    } else {
                        BigDecimal bd = null;
                        try {
                            bd = new BigDecimal(str);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        if (bd == null) {
                            etQuantity.setText(FormatUtils.to8NoComma(str));
                        } else {
                            BigDecimal price = otcAdBean.getOnlineOrderPrice();
                            if (price.compareTo(BigDecimal.ZERO) == 0) {
                                // 被除数为 0
                                Logger.e("被除数为 0.");
                                etQuantity.setText(FormatUtils.to8NoComma("0"));
                            } else {
                                etQuantity.setText(FormatUtils.to8NoComma(bd.divide(price, 8, RoundingMode.HALF_UP)));
                            }

                        }
                    }
                }
                refreshTotalValue();
            }
        });
        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etQuantity.isFocused()) {
                    String str = s.toString();

                    if (EmptyUtils.isEmpty(str)) {
                        etMoney.setText(FormatUtils.to2NoComma(str));
                    } else {
                        BigDecimal bd = new BigDecimal(str);
                        etMoney.setText(FormatUtils.to2NoComma(bd.multiply(otcAdBean.getOnlineOrderPrice())));
                    }
                }
                refreshActualValue();
            }
        });
    }

    private void initData() {
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.tv_otc_popup_money_all:
                    setBuyAll();
                    break;

                case R.id.tv_otc_popup_quantity_all:
                    setBuyAll();
                    break;

                case R.id.tv_otc_popup_cancel:
                    dismiss();
                    break;

                case R.id.tv_otc_popup_confirm:
                    if (listener != null) {
                        String money = etMoney.getText().toString();
                        String quantity = etQuantity.getText().toString();
                        if (TextUtils.isEmpty(money)) {
                            ToastUtils.showShort(context, R.string.err_content_not_empty);
                        } else if (TextUtils.isEmpty(quantity)) {
                            ToastUtils.showShort(context, R.string.err_content_not_empty);
                        } else {
                            listener.onSubmitClick(money, quantity);
                            dismiss();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setBuyAll() {
        String allQuantity = Utils.myAccountFormat(otcAdBean.getBalance());
        String allMoney = FormatUtils.to2NoComma(otcAdBean.getBalance().multiply(otcAdBean.getOnlineOrderPrice()));

        etMoney.setText(allMoney);
        etQuantity.setText(allQuantity);
    }
    //一根香蕉+鸡胸肉+西蓝花+秋葵
    private void refreshActualValue() {
        String quantity = FormatUtils.to8(etQuantity.getText().toString());
        String textActual = context.getString(R.string.actually_buy_or_sell, type, (quantity + coinName));
        tvActual.setText(textActual);
    }

    private void refreshTotalValue() {
        String money = FormatUtils.to2(etMoney.getText().toString());
        String textTotal = context.getString(R.string.exchange_total_amount, currencySymbol, money);
        tvTotal.setText(textTotal);
    }

    public interface OnSubmitClickListener {
        void onSubmitClick(String money, String quantity);
    }

}
