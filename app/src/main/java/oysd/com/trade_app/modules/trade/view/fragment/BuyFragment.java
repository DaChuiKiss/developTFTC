package oysd.com.trade_app.modules.trade.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.SocketClient;
import oysd.com.trade_app.modules.mycenter.bean.AuthInfoBean;
import oysd.com.trade_app.modules.mycenter.view.CertificationStep1;
import oysd.com.trade_app.modules.mycenter.view.MyActivity;
import oysd.com.trade_app.modules.mycenter.view.SettingAccountPwdActivity;
import oysd.com.trade_app.modules.trade.adapter.TradeBuyAdapter;
import oysd.com.trade_app.modules.trade.adapter.TradeSaleAdapter;
import oysd.com.trade_app.modules.trade.bean.BuyAndSaleListBean;
import oysd.com.trade_app.modules.trade.bean.MarketIdBean;
import oysd.com.trade_app.modules.trade.bean.MarketInfoBean;
import oysd.com.trade_app.modules.trade.bean.SocketOrder;
import oysd.com.trade_app.modules.trade.contract.BuyAndSaleListContract;
import oysd.com.trade_app.modules.trade.presenter.BuyAndSaleListPresenter;
import oysd.com.trade_app.modules.trade.view.activity.KLineTradeActivity;
import oysd.com.trade_app.modules.trade.view.activity.saleAndBuyActivity;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.PasswordDialog;

/**
 * BuyOrSell - 买入 fragment.
 */
public class BuyFragment extends LazyLoadBaseFragment implements BuyAndSaleListContract.View {


    @BindView(R.id.bt_trade_buy)
    Button bt_trade_buy;

    @BindView(R.id.tv_trade_available)
    TextView tv_trade_available;

    @BindView(R.id.tv_trade_turnover)
    TextView tv_trade_turnover;

    @BindView(R.id.tv_trade_locked)
    TextView tv_trade_locked;

    @BindView(R.id.lv_entrust_buy)
    RecyclerView lv_entrust_buy;

    @BindView(R.id.lv_entrust_sale)
    RecyclerView lv_entrust_sale;

    @BindView(R.id.et_trade_buy_entrust_price)
    EditText etEntrustPrice;

    @BindView(R.id.et_trade_buy_entrust_count)
    EditText etEntrustCount;

    @BindView(R.id.tv_buyAndSale_25)
    TextView tv_buyAndSale_25;
    @BindView(R.id.tv_buyAndSale_50)
    TextView tv_buyAndSale_50;
    @BindView(R.id.tv_buyAndSale_75)
    TextView tv_buyAndSale_75;
    @BindView(R.id.tv_buyAndSale_100)
    TextView tv_buyAndSale_100;

    @BindView(R.id.tv_trade_last_price)
    TextView tv_trade_last_price;

    @BindView(R.id.tv_trade_last_price_lg)
    TextView tv_trade_last_price_lg;

    @BindView(R.id.tv_legalTenderVal)
    TextView tv_legalTenderVal;

    private MarketIdBean marketIdBean;
    private MarketInfoBean minfo;

    TradeBuyAdapter tradeBuyAdapter;
    TradeSaleAdapter tradeSaleAdapter;

    BuyAndSaleListContract.Presenter presenter = new BuyAndSaleListPresenter(this);

    //    List<BuyAndSaleListBean> bList=new ArrayList<>(),sList=new ArrayList<>();
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_buy;
    }

    @Override
    protected void initView(View rootView) {
        init();
        tradeBuyAdapter = new TradeBuyAdapter(getContext(), R.layout.activity_buy_sale_item_2);
        lv_entrust_buy.setAdapter(tradeBuyAdapter);
        lv_entrust_buy.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_entrust_buy.setItemAnimator(new DefaultItemAnimator());
        tradeBuyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                etEntrustPrice.setFocusable(true);
                etEntrustPrice.setText(((saleAndBuyActivity) getActivity()).getbList().get(position).getPrice().toPlainString());
                etEntrustPrice.setSelection(etEntrustPrice.getText().toString().length());

            }
        });


        tradeSaleAdapter = new TradeSaleAdapter(getContext(), R.layout.activity_buy_sale_item_2);
        lv_entrust_sale.setAdapter(tradeSaleAdapter);
        lv_entrust_sale.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_entrust_sale.setItemAnimator(new DefaultItemAnimator());

        tradeSaleAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                etEntrustPrice.setFocusable(true);
                etEntrustPrice.setText(((saleAndBuyActivity) getActivity()).getsList().get(position).getPrice().toPlainString());
                etEntrustPrice.setSelection(etEntrustPrice.getText().toString().length());
            }
        });

        etEntrustPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etEntrustPrice.getText().toString().length() > 0 && s.length() > 0) {
                    if (minfo.getNewestPrice().compareTo(BigDecimal.ZERO) == 1) {
                        tv_legalTenderVal.setText("≈" + minfo.getSymbol() + FormatUtils.to4(((minfo.getLegalTenderVal().multiply(BigDecimal.valueOf(Double.parseDouble(etEntrustPrice.getText().toString().replace(",", ""))))).divide(minfo.getNewestPrice())) + ""));
                    }
                }

                if (etEntrustPrice.getText().toString().length() > 0 && s.length() > 0 && etEntrustCount.getText().toString().length() > 0) {
                    tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(s.toString().replace(",", "")) * Double.parseDouble(etEntrustCount.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                }
            }
        });


        etEntrustCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etEntrustPrice.getText().toString().length() > 0 && s.length() > 0) {
                    tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(s.toString().replace(",", "")) * Double.parseDouble(etEntrustPrice.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                }
            }
        });
        etEntrustPrice.addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 4) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 5
                    );
                    etEntrustPrice.setText(s);
                    etEntrustPrice.setSelection(s.length());
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected void init() {
        marketIdBean = ((saleAndBuyActivity) getActivity()).getMarketIdBean();
        minfo = ((saleAndBuyActivity) getActivity()).getMinfo();
        if (minfo != null && marketIdBean != null) {
            bt_trade_buy.setText(getResources().getString(R.string.buy) + " " + marketIdBean.getSellCoinName());
            tv_trade_available.setText(EmptyUtils.getBigDecimalValue(marketIdBean.getBuyUsableAmount()) + marketIdBean.getBuyCoinName());
            tv_trade_locked.setText(EmptyUtils.getBigDecimalValue(marketIdBean.getBuyFreezeAmount()) + marketIdBean.getBuyCoinName());

            tv_trade_last_price.setText(EmptyUtils.getBigDecimalValue(minfo.getNewestPrice()));
            tv_trade_last_price_lg.setText("≈" + minfo.getSymbol() + FormatUtils.to4(EmptyUtils.getBigDecimalValue(minfo.getLegalTenderVal())));
            tv_legalTenderVal.setText("≈" + minfo.getSymbol() + FormatUtils.to4(EmptyUtils.getBigDecimalValue(minfo.getLegalTenderVal())));
            Logger.d("dddddddddddddddddddddd" + minfo.getNewestPrice());
            etEntrustPrice.setText(EmptyUtils.getBigDecimalValue(minfo.getNewestPrice()));
            etEntrustPrice.setSelection(etEntrustPrice.getText().toString().length());
            bt_trade_buy.setOnClickListener(this);

            tv_buyAndSale_25.setOnClickListener(this);
            tv_buyAndSale_50.setOnClickListener(this);
            tv_buyAndSale_75.setOnClickListener(this);
            tv_buyAndSale_100.setOnClickListener(this);
        }
//
//        presenter.getBuyAndSaleList(5, marketIdBean.getMarketId(), 1);
//        presenter.getBuyAndSaleList(5, marketIdBean.getMarketId(), 2);
    }

    @Override
    public void getListSuccess(List<BuyAndSaleListBean> bsList, int type) {
    }

    @Override
    public void getListFailed(int code, String msg) {
    }

    @Override
    public void entrustSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.entry_orders_success);
        presenter.getByMarketId(marketIdBean.getMarketId());
        hideSoftInput(etEntrustCount);
    }


    // 隐藏键盘
    protected void hideSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void entrustFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getAuthInfoSuccess(AuthInfoBean authInfoBean) {
        if (authInfoBean.getDealPasswordExists() == 1) {
            InfoProvider.getInstance().setAccount(true);
        } else {
            InfoProvider.getInstance().setAccount(false);
        }

        if (authInfoBean.getVerifyRealStatus() == 1) {
            InfoProvider.getInstance().setCert(true);
        } else {
            InfoProvider.getInstance().setCert(false);
        }

        if (authInfoBean.getAdvancedStatus() == 2) {
            InfoProvider.getInstance().setSeniorCert(true);
        } else {
            InfoProvider.getInstance().setSeniorCert(false);
        }


        if (InfoProvider.getInstance().getAccount()) {
            if (InfoProvider.getInstance().getCert()) {
                if (marketIdBean.getBuyUsableAmount().compareTo(BigDecimal.valueOf(Double.parseDouble(tv_trade_turnover.getText().toString().replace(",", "")))) >= 0) {
                    if (etEntrustCount.getText().length() > 0 && BigDecimal.valueOf(Double.parseDouble(etEntrustCount.getText().toString().replace(",", ""))).compareTo(BigDecimal.ZERO) == 1) {
                        showPasswordDialog();
                    } else {
                        ToastUtils.showLong(getActivity(), "数量不能为空");
                    }
                } else {
                    ToastUtils.showLong(getActivity(), "资金不足");
                }
            } else {
                //设置资金成功，未实名
                Intent intent = new Intent(getActivity(), CertificationStep1.class);
                Global.isToSenior = false;
                startActivity(intent);
            }
        } else {
            //未设置资金
            Intent intent = new Intent(getActivity(), SettingAccountPwdActivity.class);
            Global.isToSenior = false;
            startActivity(intent);
        }
    }

    @Override
    public void getAuthInfoFailed(int code, String msg) {

    }

    @Override
    public void getByMarketIdSuccess(MarketIdBean mb) {
        init();
        tv_trade_available.setText(EmptyUtils.getBigDecimalValue(mb.getBuyUsableAmount()) + mb.getBuyCoinName());
        tv_trade_locked.setText(EmptyUtils.getBigDecimalValue(mb.getBuyFreezeAmount()) + mb.getBuyCoinName());
    }

    @Override
    public void getByMarketIdFailed(int code, String msg) {

    }

    @Override
    public void onClick(View v) {
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_trade_buy:
                    // todo : 没有做 entrustPrice / entrustCount 的输入校验。
                    presenter.getAuthInfo();
                    break;

                case R.id.tv_buyAndSale_25:
                    if (getCount((BigDecimal.valueOf(0.25))) != null) {
                        tv_buyAndSale_25.setTextColor(getResources().getColor(R.color.text_blue));
                        tv_buyAndSale_25.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        tv_buyAndSale_50.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_50.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_75.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_75.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_100.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_100.setTextColor(getResources().getColor(R.color.text_light));
                        etEntrustCount.setText(getCount((BigDecimal.valueOf(0.25))));
                        tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(etEntrustCount.getText().toString().replace(",", "")) * Double.parseDouble(etEntrustPrice.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                    }
                    break;
                case R.id.tv_buyAndSale_50:
                    if (getCount((BigDecimal.valueOf(0.50))) != null) {
                        tv_buyAndSale_25.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_25.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_50.setTextColor(getResources().getColor(R.color.text_blue));
                        tv_buyAndSale_50.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        tv_buyAndSale_75.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_75.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_100.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_100.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        etEntrustCount.setText(getCount((BigDecimal.valueOf(0.50))));
                        tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(etEntrustCount.getText().toString().replace(",", "")) * Double.parseDouble(etEntrustPrice.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                    }
                    break;
                case R.id.tv_buyAndSale_75:
                    if (getCount((BigDecimal.valueOf(0.75))) != null) {
                        tv_buyAndSale_25.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_25.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_75.setTextColor(getResources().getColor(R.color.text_blue));
                        tv_buyAndSale_75.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        tv_buyAndSale_50.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_50.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_100.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_100.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        etEntrustCount.setText(getCount((BigDecimal.valueOf(0.75))));
                        tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(etEntrustCount.getText().toString().replace(",", "")) * Double.parseDouble(etEntrustPrice.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                    }
                    break;
                case R.id.tv_buyAndSale_100:
                    if (getCount((BigDecimal.valueOf(1.0))) != null) {
                        tv_buyAndSale_25.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_25.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_100.setTextColor(getResources().getColor(R.color.text_blue));
                        tv_buyAndSale_100.setBackground(getResources().getDrawable(R.drawable.rectangle_blue));
                        tv_buyAndSale_50.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_50.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        tv_buyAndSale_75.setTextColor(getResources().getColor(R.color.text_light));
                        tv_buyAndSale_75.setBackground(getResources().getDrawable(R.drawable.rectangle_grey));
                        etEntrustCount.setText(getCount((BigDecimal.valueOf(1.0))));
                        tv_trade_turnover.setText(FormatUtils.toX(Double.parseDouble(etEntrustCount.getText().toString().replace(",", "")) * Double.parseDouble(etEntrustPrice.getText().toString().replace(",", "")) + "", marketIdBean.getNumberDecimals()));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String getCount(BigDecimal m) {
        BigDecimal a = BigDecimal.ZERO;
        if (etEntrustPrice.getText().toString().length() > 0 && BigDecimal.valueOf(Double.parseDouble(etEntrustPrice.getText().toString())).compareTo(BigDecimal.ZERO) == 1) {
            a = marketIdBean.getBuyUsableAmount().divide(BigDecimal.valueOf(Double.parseDouble(etEntrustPrice.getText().toString())), 10, BigDecimal.ROUND_DOWN).multiply(m);
        } else {
            ToastUtils.showLong(App.getContext(), "委托价格不能为空");
            return null;
        }
        return FormatUtils.toX(EmptyUtils.getBigDecimalValue(a), marketIdBean.getNumberDecimals());
    }


    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(context, null, getResources().getString(R.string.dialogMsg1), false, true, R.style.dialog);
        dialog.setOnSubmitListener(new PasswordDialog.OnSubmitListener() {
            @Override
            public void onSubmit(PasswordDialog dialog, String password) {
                Map<String, Object> params = new HashMap<>();
                params.put("dealPassword", Utils.MD5(password));
                params.put("type", 1);
                params.put("entrustType", 0);//0普通订单，1时价订单
                params.put("entrustPrice", FormatUtils.toXNoComma(etEntrustPrice.getText().toString().replace(",", ""), marketIdBean.getPriceDecimals()));
                params.put("entrustCount", FormatUtils.toXNoComma(etEntrustCount.getText().toString().replace(",", ""), marketIdBean.getNumberDecimals()));
                params.put("marketId", marketIdBean.getMarketId());
                presenter.entrust(params);
            }
        });
        dialog.show();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        ((saleAndBuyActivity) getActivity()).setFagmentIndex(1);
        reflash(((saleAndBuyActivity) getActivity()).getsList(), ((saleAndBuyActivity) getActivity()).getbList());
        presenter.getByMarketId(marketIdBean.getMarketId());
    }

    public void reflash(List<SocketOrder.SellEntrustListBean> sList, List<SocketOrder.BuyEntrustListBean> bList) {
        tradeSaleAdapter.setDataSet(sList);
        tradeSaleAdapter.notifyDataSetChanged();
        tradeBuyAdapter.setDataSet(bList);
        tradeBuyAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        ((saleAndBuyActivity) getActivity()).setFagmentIndex(0);
    }

}
