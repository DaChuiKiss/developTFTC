package oysd.com.trade_app.modules.mycenter.view;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.mycenter.adapter.VipCardAdapter;
import oysd.com.trade_app.modules.mycenter.adapter.VipCardTransformer;
import oysd.com.trade_app.modules.mycenter.bean.VipCardInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.modules.mycenter.bean.VipLevelConfigBean;
import oysd.com.trade_app.modules.mycenter.contract.MemberPurchaseContract;
import oysd.com.trade_app.modules.mycenter.presenter.MemberPurchasePresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.SpannableEditor;
import oysd.com.trade_app.util.StatusBarUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.IosNormalDialog;

/**
 * 会员购买页面。
 * Created by Liam on 19/09/15.
 */
public class MemberPurchaseActivity
        extends BaseToolActivity implements MemberPurchaseContract.View {

    @BindView(R.id.vp_member_purchase_pager)
    ViewPager viewPager;

    @BindView(R.id.tv_member_purchase_benefit1)
    TextView tvBenefit1;

    @BindView(R.id.tv_member_purchase_benefit2)
    TextView tvBenefit2;

    @BindView(R.id.tv_member_purchase_benefit3)
    TextView tvBenefit3;

    @BindView(R.id.tv_member_purchase_benefit4)
    TextView tvBenefit4;

    @BindView(R.id.tv_member_purchase_benefit5)
    TextView tvBenefit5;

    @BindView(R.id.tv_member_purchase_benefit7)
    TextView tvBenefit6;

    @BindView(R.id.btn_member_purchase_buy)
    Button btnBuy;

    @BindView(R.id.cb_member_purchase_agreement)
    CheckBox cbAgreement;

    @BindView(R.id.tv_member_purchase_agreement)
    TextView tvAgreement;

    MemberPurchaseContract.Presenter presenter = new MemberPurchasePresenter(this);

    private List<VipLevelConfigBean> vipLevelConfigBeans;
    private List<String> cardNames = new ArrayList<>();
    // 选中的 card level 的 item position.
    private int selectedCardLevelPos = 0;
    private VipCardInfoBean selectedCardInfo;
    private List<VipCardPriceBean> vipCardPriceBeans;


    @Override
    protected int setView() {
        return R.layout.activity_member_purchase;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText("");
        setTitleBarTextColor(R.color.text_normal);
        setTitleBarColor(R.color.text_white);
        setBackImg(R.mipmap.back_black);
        StatusBarUtils.setStatusBarLightMode(this, true);
        setDrawableRightOfTitle(R.drawable.ic_arrow_down_solid);

        SpannableEditor editor = new SpannableEditor(this, getString(R.string.vip_ensure_agreement));
        editor.setTextColor(ContextCompat.getColor(context, R.color.text_blue),
                getString(R.string.vip_ensure_agreement_sel));
        tvAgreement.setText(editor.build());
    }

    @Override
    protected void initClicks() {
        btnBuy.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        cbAgreement.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                btnBuy.setAlpha(1f);
                btnBuy.setClickable(true);
            } else {
                btnBuy.setAlpha(0.5f);
                btnBuy.setClickable(false);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getVipLevelConfigs();
    }

    @Override
    protected void clickTitle() {
        VipLevelPopupWindow pw = new VipLevelPopupWindow(this, cardNames, selectedCardLevelPos, (name, pos) -> {
            // 更新选择的选项位置。
            selectedCardLevelPos = pos;
            updateView(vipLevelConfigBeans.get(pos));
        });
        pw.showAtLocation(MemberPurchaseActivity.this
                .findViewById(R.id.sv_member_purchase), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (Utils.isFastClick()) return;
        switch (v.getId()) {
            case R.id.tv_member_purchase_agreement:
                String title = getString(R.string.mvTitle);
                if (Utils.isChina()) {
                    IntentUtils.jumpTo(this, title, IntentUtils.URL_USER_AGREEMENT);
                } else {
                    IntentUtils.jumpTo(this, title, IntentUtils.URL_USER_AGREEMENTE);
                }
                break;

            case R.id.btn_member_purchase_buy:
                if (selectedCardInfo == null) {
                    Logger.e("Selected CardInfo bean is null.");
                } else {
                    presenter.getVipCardPrices(selectedCardInfo.getLevelId(), selectedCardInfo.getCardId());
                }
                break;

            default:
                break;
        }
    }

    private void updateView(VipLevelConfigBean vipLevelConfigBean) {
        setTitleText(vipLevelConfigBean.getName());
        List<VipCardInfoBean> cardInfoBeans = vipLevelConfigBean.getCardInfo();

        viewPager.clearOnPageChangeListeners();
        // set ViewPager.
        viewPager.setOffscreenPageLimit(cardInfoBeans.size());
        viewPager.setPageMargin(30);

        VipCardAdapter adapter = new VipCardAdapter(context, cardInfoBeans);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new VipCardTransformer());
        // 界面上默认显示第一个 card.
        selectedCardInfo = cardInfoBeans.get(0);
        refreshCardBenefits(selectedCardInfo);

        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectedCardInfo = cardInfoBeans.get(position);
                refreshCardBenefits(selectedCardInfo);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void refreshCardBenefits(VipCardInfoBean bean) {
        tvBenefit1.setText(getString(R.string.vip_benefit_1, bean.getGiveBourseCoinNumber()));
        tvBenefit2.setText(getString(R.string.vip_benefit_2, bean.getInitGiveRatio()));

        if (bean.getEveryMonthGiveRatio() == 0) {
            tvBenefit3.setText(R.string.vip_benefit_3_without_args);
        } else {
            tvBenefit3.setText(getString(R.string.vip_benefit_3, bean.getEveryMonthGiveRatio()));
        }

        // tvBenefit4.setText(getString(R.string.vip_benefit_4, bean.getGiveDividendNumber()));
        tvBenefit5.setText(getString(R.string.vip_benefit_5, bean.getDealCostDiscount()));
        btnBuy.setText(getString(R.string.vip_submit_price, bean.getPrice()));
    }

    // 展示 输入密码 dialog , 开始进行购买。
    private void startPurchase(VipCardPriceBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("cardId", selectedCardInfo.getCardId());
        params.put("coinId", bean.getCoinId());
        params.put("levelId", selectedCardInfo.getLevelId());
        params.put("price", bean.getPrice());

        new IosNormalDialog(context)
                .setTitleText(R.string.dialog_payment_title)
                .setContent(R.string.dialog_payment_content)
                .hasEdit(true)
                .isPassword(true)
                .setOnDialogClickListener(new IosNormalDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirmClick(IosNormalDialog dialog) {
                        String password = dialog.getInputString();
                        params.put("password", Utils.MD5(password));
                        presenter.userPurchaseVip(params);
                    }

                    @Override
                    public void onCancelClick(IosNormalDialog dialog) {
                    }
                })
                .init();
    }

    @Override
    public void getVipLevelConfigsSuccess(List<VipLevelConfigBean> response) {
        if (EmptyUtils.isEmpty(response)) return;

        this.vipLevelConfigBeans = response;
        for (VipLevelConfigBean bean : response) {
            cardNames.add(bean.getName());
        }

        // 默认显示第一个 VipLevelConfig.
        updateView(response.get(0));
    }

    @Override
    public void getVipLevelConfigsFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void getVipCardPricesSuccess(List<VipCardPriceBean> response) {
        if (EmptyUtils.isEmpty(response)) return;

        vipCardPriceBeans = response;

        // 基本价格 in USDT .
        String price = selectedCardInfo.getPrice();

        VipPurchasePopupWindow popupWindow = new VipPurchasePopupWindow(this, 1, price, vipCardPriceBeans);
        popupWindow.setListener((bean, position) -> startPurchase(bean));
        popupWindow.showAtLocation(MemberPurchaseActivity.this
                .findViewById(R.id.sv_member_purchase), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getVipCardPricesFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void userPurchaseVipSuccess() {
        ToastUtils.showLong(context, R.string.toast_purchase_success);

        quickStartActivity(MemberBenefitsActivity.class);
        ActivitiesManager.getInstance().finishActivity(this);
    }

    @Override
    public void userPurchaseVipFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
