package oysd.com.trade_app.modules.otc.view.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.otc.bean.AbbreviationBean;
import oysd.com.trade_app.modules.otc.bean.CoinConfigBean;
import oysd.com.trade_app.modules.otc.bean.CoinInfoBean;
import oysd.com.trade_app.modules.otc.contract.PublishAdsContract;
import oysd.com.trade_app.modules.otc.presenter.PublishAdsPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.PickerUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;


/**
 * 广告发布页面
 */
public class PublishAdsActivity
        extends BaseToolActivity implements PublishAdsContract.View {

    @BindView(R.id.rg_publish_type)
    RadioGroup rgTransactionType;

    @BindView(R.id.tv_publish_coin)
    TextView tvCoin;

    @BindView(R.id.tv_publish_currency)
    TextView tvCurrency;

    @BindView(R.id.et_publish_price)
    EditText etPrice;

    @BindView(R.id.et_publish_quantity)
    EditText etQuantity;

    @BindView(R.id.tv_publish_quantity_unit)
    TextView tvQuantityUnit;

    @BindView(R.id.et_publish_min)
    EditText etMinAmount;

    @BindView(R.id.et_publish_max)
    EditText etMaxAmount;

    @BindView(R.id.et_publish_password)
    EditText etPassword;

    @BindView(R.id.btn_publish_publish)
    Button btnPublish;

    // temp use.
    PublishAdsContract.Presenter presenter = new PublishAdsPresenter(this);

    private int page = 1;
    private int limit = 15;

    // 交易类型：在线购买、卖出
    private int transactionType;
    private int coinId;
    private int settlementCurrency;
    private double price;
    private double quantity;
    //    private double minAmount;
//    private double maxAmount;
    private String password;

    private List<String> coinNames = new ArrayList<>();
    private List<String> currencies = new ArrayList<>();
    private List<CoinConfigBean> coinConfigBeans = new ArrayList<>();
    private CoinConfigBean selectedCoinConfig = null;
    private int amountDecimal;


    @Override
    protected int setView() {
        return R.layout.activity_publish_ads;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.publish_ad);
        currencies.add(Values.Currency.HKD_VALUE);
//        currencies.add(Values.Currency.CNY_VALUE);
//        currencies.add(Values.Currency.USD_VALUE);
//        currencies.add(Values.Currency.AUD_VALUE);
        tvCurrency.setText(currencies.get(0));
    }

    @Override
    protected void initClicks() {
        btnPublish.setOnClickListener(this);
        tvCoin.setOnClickListener(this);
        tvCurrency.setOnClickListener(this);

        rgTransactionType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbtn_publish_type_buy) {
                transactionType = Values.TransactionType.BUY;
                presenter.getCoinsConfig(transactionType);

            } else if (checkedId == R.id.rbtn_publish_type_sale) {
                transactionType = Values.TransactionType.SALE;
                presenter.getCoinsConfig(transactionType);
            }
        });
    }

    @Override
    protected void initData() {
        // 给 transactionType 默认值，防止未选择 GroupButton 造成该值为 null。
        transactionType = Values.TransactionType.BUY;

        // presenter.queryAllCoins(page, limit);
        presenter.getCoinsConfig(transactionType);


        etPrice.addTextChangedListener(textWatcher);
        etQuantity.addTextChangedListener(textWatcher1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.btn_publish_publish:
                publishAd();
                break;

            case R.id.tv_publish_coin:
                PickerUtils.showOneOptionPicker(context, coinNames, (opt1, opt2, opt3, view) -> {
                    selectedCoinConfig = coinConfigBeans.get(opt1);
                    setValuesAfterSelectCoin(coinNames.get(opt1), selectedCoinConfig);
                });
                break;

            case R.id.tv_publish_currency:
                PickerUtils.showOneOptionPicker(context, currencies, (opt1, opt2, opt3, view) ->
                        tvCurrency.setText(currencies.get(opt1)));
                break;

            default:
                break;
        }
    }

    private void publishAd() {
        if (isErrorInputs()) return;

        getAllInputs();

        Map<String, Object> map = new HashMap<>();
        map.put("coinId", coinId);
        map.put("maxAmountTranscation", etQuantity.getText().toString());
        map.put("minAmountTransation", "1");
        map.put("onlineOrderPrice", price);
        map.put("onlineOrderQuantity", quantity);
        map.put("password", Utils.MD5(password));
        map.put("settlementCurrency", settlementCurrency);
        map.put("transactionType", transactionType);

        presenter.postEntrustInfo(map);
    }

    private void getAllInputs() {
        coinId = getCoinIdFromList(tvCoin.getText().toString());

        String strCurrency = tvCurrency.getText().toString();
        settlementCurrency = ViewHelper.getCurrencyIdByName(strCurrency);

        price = Double.valueOf(etPrice.getText().toString());
        quantity = Double.valueOf(etQuantity.getText().toString());
//        minAmount = Double.valueOf(etMinAmount.getText().toString());
//        maxAmount = Double.valueOf(etMaxAmount.getText().toString());
        password = etPassword.getText().toString();
    }

    private int getCoinIdFromList(String coinName) {
        for (CoinConfigBean bean : coinConfigBeans) {
            if (bean.getCoinName().equals(coinName)) {
                return bean.getCoinId();
            }
        }

        return 0;
    }

    /**
     * 下单成功后,需要将所有的状态都清除.
     */
    private void clearAllInputs() {
        tvCoin.setText(coinNames.get(0));
        tvQuantityUnit.setText(coinNames.get(0));
        tvCurrency.setText(currencies.get(0));

        //etPrice.setText("");//暂时不清空价格
        etQuantity.setText("");
        etMinAmount.setText("");
        etMaxAmount.setText("");
        etPassword.setText("");

    }

    /**
     * 检查所有的输入是否有误.
     *
     * @return true 输入有误；false 输入无误。
     */
    private boolean isErrorInputs() {
        return ViewHelper.isEditEmpty(context, etPrice, etQuantity, etPassword);
//                || !ViewHelper.isMaxGreaterThanMin(context, etMaxAmount, etMinAmount)
//                || !ViewHelper.isGreaterOrEqual(context, etMinAmount, selectedCoinConfig.getMinTranscationAmount())
//                || !ViewHelper.isLessOrEqual(context, etMaxAmount, selectedCoinConfig.getMaxTranscationAmount());

    }

    @Override
    public void postEntrustInfoSuccess() {
        clearAllInputs();
        ToastUtils.showLong(this, getString(R.string.toast_post_ad_suc));
    }

    @Override
    public void postEntrustInfoFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
        ToastUtils.showLong(context, msg);
    }

    /**
     * 在选择货币类型后，设置所有相关值。
     */
    private void setValuesAfterSelectCoin(String coinName, CoinConfigBean bean) {
        amountDecimal = bean.getAmountDecimal();
        presenter.queryByCoinIdAndAbbreviation(currencies.get(0), bean.getCoinId() + "");
        tvCoin.setText(coinName);
        tvQuantityUnit.setText(coinName);
//        etPrice.setText(bean.getMinTranscationAmount() + "");
        // hint
        etQuantity.setHint(getString(R.string.hint_keep_x_decimal, bean.getAmountDecimal()));
        etMinAmount.setHint(getString(R.string.hint_at_least) + " " +
                BigDecimal.valueOf(bean.getMinTranscationAmount()).toPlainString());
        etMaxAmount.setHint(getString(R.string.hint_at_most) + " " +
                BigDecimal.valueOf(bean.getMaxTranscationAmount()).toPlainString());
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > amountDecimal) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    etPrice.setText(s);
                    etPrice.setSelection(s.length());
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    private TextWatcher textWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    etQuantity.setText(s);
                    etQuantity.setSelection(s.length());
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void queryAllCoinsSuccess(List<CoinInfoBean> response) {

        // 使用 getCoinConfig 来获取 coin 列表。
        /*
        if (EmptyUtils.isEmpty(response)) return;

        coinInfoBeans = response;

        List<String> coinNames = new ArrayList<>();
        for (CoinInfoBean bean : response) {
            coinNames.add(bean.getEnglishShortName());
        }

        this.coinNames = coinNames;
        // 默认显示列表的第一个 coin name.
        String coinName = coinNames.get(0);
        tvCoin.setText(coinName);
        tvQuantityUnit.setText(coinName);
        */
    }

    @Override
    public void queryAllCoinsFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void getCoinsConfigSuccess(List<CoinConfigBean> response) {
        if (EmptyUtils.isEmpty(response) || tvCoin == null) return;

        coinConfigBeans = response;

        List<String> coinNames = new ArrayList<>();
        for (CoinConfigBean bean : response) {
            coinNames.add(bean.getCoinName());
        }

        this.coinNames = coinNames;
        // 默认显示列表的第一个 coin name.
        selectedCoinConfig = response.get(0);
        setValuesAfterSelectCoin(coinNames.get(0), selectedCoinConfig);
    }

    @Override
    public void getCoinsConfigFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void queryByCoinIdAndAbbreviationSuccess(AbbreviationBean response) {
        if (response == null)
            return;
        etPrice.setText(response.getExchangeValue());
    }

    @Override
    public void queryByCoinIdAndAbbreviationFailed(int code, String msg) {

        Logger.e("code = " + code + " , msg = " + msg);
    }

}
