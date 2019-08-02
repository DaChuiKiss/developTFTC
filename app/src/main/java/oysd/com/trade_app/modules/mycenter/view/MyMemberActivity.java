package oysd.com.trade_app.modules.mycenter.view;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.VipCardInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.modules.mycenter.bean.VipLevelConfigBean;
import oysd.com.trade_app.modules.mycenter.contract.MyMemberContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyMemberPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.dialog.IosNormalDialog;

/**
 * 我的会员 (会员中心) 页面.
 * Created by Liam on 19/09/15.
 */
public class MyMemberActivity
        extends BaseToolActivity implements MyMemberContract.View {

    @BindView(R.id.tv_my_member_name)
    TextView tvName;

    @BindView(R.id.tv_my_member_change_benefit)
    TextView tvChangeBenefit;

    @BindView(R.id.tv_my_member_status)
    TextView tvStatus;

    @BindView(R.id.tv_my_member_deadline)
    TextView tvDeadline;

    @BindView(R.id.tv_my_member_price)
    TextView tvPrice;

    @BindView(R.id.btn_my_member_purchase)
    Button btnPurchase;

    @BindView(R.id.tv_my_member_benefits)
    TextView tvBenefits;

    @BindView(R.id.rl_my_member_done)
    RelativeLayout rlMemberDone;

    @BindView(R.id.tv_my_member_benefit1)
    TextView tvBenefit1;

    @BindView(R.id.tv_my_member_benefit2)
    TextView tvBenefit2;

    @BindView(R.id.tv_my_member_benefit3)
    TextView tvBenefit3;

    @BindView(R.id.tv_my_member_benefit4)
    TextView tvBenefit4;

    @BindView(R.id.tv_my_member_benefit5)
    TextView tvBenefit5;

    @BindView(R.id.tv_my_member_benefit6)
    TextView tvBenefit6;

    @BindView(R.id.tv_my_member_check_more)
    TextView tvCheckMore;

    @BindView(R.id.rl_my_member_not)
    RelativeLayout rlMemberNot;

    @BindView(R.id.tv_my_member_not_benefit4)
    TextView tvNotBenefit4;

    @BindView(R.id.tv_my_member_not_benefit5)
    TextView tvNotBenefit5;

    @BindView(R.id.tv_my_member_not_benefit6)
    TextView tvNotBenefit6;

    MyMemberContract.Presenter presenter = new MyMemberPresenter(this);

    // 0: 不是会员；1：是会员。
    private int status = 0;
    private UserVipInfoBean userVipInfoBean = null;
    private VipCardInfoBean vipInfo = null;
    private VipCardInfoBean notVipInfo = null;


    @Override
    protected int setView() {
        return R.layout.activity_my_member;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.title_member_center);
    }

    @Override
    protected void initData() {
        presenter.getUserVipInfo();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (Utils.isFastClick()) return;

        switch (v.getId()) {
            case R.id.tv_my_member_change_benefit:
                quickStartActivity(MemberBenefitsActivity.class);
                break;

            case R.id.btn_my_member_purchase:
                if (status == 1) {
                    if (userVipInfoBean.getDisableBuy() == 0) {
                        // 此时 disableBuy == 0 只表示测试会员，“终生会员”已经被禁止点击了。
                        quickStartActivity(MemberPurchaseActivity.class);
                    } else {
                        presenter.getVipCardPrices(userVipInfoBean.getLevelId(), userVipInfoBean.getCardId());
                    }
                } else if (status == 0) {
                    quickStartActivity(MemberPurchaseActivity.class);
                }
                break;

            case R.id.tv_my_member_check_more:
                quickStartActivity(MemberPurchaseActivity.class);
                break;

            default:
                break;
        }
    }

    private void updateView() {
        UserInfoBean userInfoBean = InfoProvider.getInstance().getUserInfo();
        String nickName = userInfoBean == null ? "" : userInfoBean.getNickName();
        tvName.setText(getString(R.string.vip_hi, nickName));

        if (status == 1) {
            rlMemberDone.setVisibility(View.VISIBLE);
            rlMemberNot.setVisibility(View.GONE);

            tvChangeBenefit.setVisibility(View.VISIBLE);
            String date = EmptyUtils.isEmpty(userVipInfoBean.getLoseDate()) ?
                    Constants.LIFELONG_CARD_END_TIME_LINE : userVipInfoBean.getLoseDate();
            tvDeadline.setText(getString(R.string.vip_deadline, date));

            // 不能在 setClickable() 后面，否则无法禁用。
            btnPurchase.setOnClickListener(this);

            // 测试会员，不能进行续费。
//            if (userVipInfoBean.getLevelId() == 5) {
//                btnPurchase.setAlpha(0.3f);
//                btnPurchase.setClickable(false);
//            }
//            if (userVipInfoBean.getDisableBuy() == 0) {
//                btnPurchase.setAlpha(0.3f);
//                btnPurchase.setClickable(false);
//            }

            String cardType = "";
            String priceUnit = "";
            String vipBenefit = "";

            int cardId = userVipInfoBean.getCardId();
            switch (cardId) {
                case Values.VipCardId.MONTH:
                    cardType = getString(R.string.vip_card_month);
                    priceUnit = getString(R.string.vip_card_month_price_unit);
                    vipBenefit = getString(R.string.vip_card_month_benefits);
                    break;
                case Values.VipCardId.SEASON:
                    cardType = getString(R.string.vip_card_season);
                    priceUnit = getString(R.string.vip_card_season_price_unit);
                    vipBenefit = getString(R.string.vip_card_season_benefits);
                    break;
                case Values.VipCardId.YEAR:
                    cardType = getString(R.string.vip_card_year);
                    priceUnit = getString(R.string.vip_card_year_price_unit);
                    vipBenefit = getString(R.string.vip_card_year_benefits);
                    break;
                case Values.VipCardId.LIFELONG:
                    cardType = getString(R.string.vip_card_lifelong);
                    // 终生卡，不能点击。隐藏 "续费"，灰掉 “立即续费” button .
                    tvPrice.setVisibility(View.INVISIBLE);
                    // btnPurchase.setBackgroundResource(R.drawable.rounded_grey_r5);
                    btnPurchase.setAlpha(0.3f);
                    btnPurchase.setClickable(false);
                    vipBenefit = getString(R.string.vip_card_lifelong_benefits);
                    break;
                default:
                    break;
            }

            tvStatus.setText(getString(R.string.vip_had_benefited, cardType));
            String recharge = getString(R.string.vip_recharge_price,
                    (userVipInfoBean.getPrice() + priceUnit));
            tvPrice.setText(recharge);

            if (userVipInfoBean.getDisableBuy() == 0 && userVipInfoBean.getCardId() != 4) {
                // 测试会员
                btnPurchase.setText(R.string.vip_purchase_now);
            } else {
                btnPurchase.setText(R.string.vip_recharge_now);
            }

            tvBenefits.setText(vipBenefit);
            tvChangeBenefit.setOnClickListener(this);
            tvCheckMore.setOnClickListener(this);

        } else if (status == 0) {
            rlMemberDone.setVisibility(View.GONE);
            rlMemberNot.setVisibility(View.VISIBLE);

            tvChangeBenefit.setVisibility(View.GONE);
            tvStatus.setText(R.string.vip_not_benefited);
            tvDeadline.setVisibility(View.INVISIBLE);
            btnPurchase.setText(R.string.vip_purchase_now);
            tvBenefits.setText(R.string.vip_vip_benefits);

            tvNotBenefit4.setText(R.string.vip_benefit_7);
            tvCheckMore.setVisibility(View.GONE);
            tvNotBenefit5.setText(getString(R.string.vip_benefit_5_2));
            btnPurchase.setOnClickListener(this);
        }
    }

    // 更新 vip 用户的各项权益数据.
    private void updateVipValues(VipCardInfoBean bean) {
        if (bean == null) {
            Logger.e("Vip config info is null.");
            return;
        }

        tvBenefit1.setText(getString(R.string.vip_benefit_1, bean.getGiveBourseCoinNumber()));
        tvBenefit2.setText(getString(R.string.vip_benefit_2, bean.getInitGiveRatio()));

        if (bean.getEveryMonthGiveRatio() == 0) {
            tvBenefit3.setText(R.string.vip_benefit_3_without_args);
        } else {
            tvBenefit3.setText(getString(R.string.vip_benefit_3, bean.getEveryMonthGiveRatio()));
        }

        // tvBenefit4.setText(getString(R.string.vip_benefit_4, bean.getGiveDividendNumber()));
        if (bean.getDealCostDiscount() == 100) {
            tvBenefit5.setText(getString(R.string.vip_benefit_5_1));
        } else {
            tvBenefit5.setText(getString(R.string.vip_benefit_5, bean.getDealCostDiscount()));
        }
    }

    // 更新非 vip 用户的数据，如特惠价等。
    private void updateNotVipValues(VipCardInfoBean bean) {
        String price = bean == null ? "" : bean.getPrice();
        String purchasePrice = getString(R.string.vip_purchase_cheap,
                (price + getString(R.string.vip_card_year_price_unit)));
        tvPrice.setText(purchasePrice);
    }

    // 展示 输入密码 dialog , 开始进行购买。
    private void startPurchase(VipCardPriceBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("cardId", userVipInfoBean.getCardId());
        params.put("coinId", bean.getCoinId());
        params.put("levelId", userVipInfoBean.getLevelId());
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
    public void getUserVipInfoSuccess(UserVipInfoBean response) {
        this.userVipInfoBean = response;

        status = 1;
        updateView();

        // Liam 20181010 :
        // getUserVipInfo 接口已经返回了会员对应的权益数据，因此不用再去请求 vip 对应 card 的
        // config .

        // 获取会员卡各项权益数据.
        // presenter.getVipLevelConfigs();
        updateVipValues(response.getConfigVo());
    }

    @Override
    public void getUserVipInfoFailed(int code, String msg) {
        if (code == 709) {
            // 不是会员
            status = 0;
            updateView();
            // 获取推荐年卡特惠价。
            presenter.getFavorableYearCard();

        } else {
            ToastUtils.showLong(context, msg);
        }
    }

    @Override
    public void getFavorableYearCardSuccess(VipLevelConfigBean response) {
        if (response != null) {
            List<VipCardInfoBean> list = response.getCardInfo();
            notVipInfo = EmptyUtils.isEmpty(list) ? null : list.get(0);
            updateNotVipValues(notVipInfo);
        } else {
            notVipInfo = null;
            updateNotVipValues(notVipInfo);
        }
    }

    @Override
    public void getFavorableYearCardFailed(int code, String msg) {
        Logger.e("Get favorable year card failed. Code = " + code + " , msg =" + msg);
        updateNotVipValues(null);
    }

    @Override
    public void getVipLevelConfigsSuccess(List<VipLevelConfigBean> response) {
        int vipLevelId = userVipInfoBean.getLevelId();
        int vipCardId = userVipInfoBean.getCardId();

        for (VipLevelConfigBean bean : response) {
            List<VipCardInfoBean> list = bean.getCardInfo();
            if (vipLevelId == list.get(0).getLevelId()) {
                for (VipCardInfoBean cardInfoBean : list) {
                    if (vipCardId == cardInfoBean.getCardId()) {
                        // 和当前用户 vip 一致的 VipCardInfoBean .
                        vipInfo = cardInfoBean;
                    }
                }
            }
        }

        updateVipValues(vipInfo);
    }

    @Override
    public void getVipLevelConfigsFailed(int code, String msg) {
        Logger.e("Get level configs failed. Code = " + code + " , msg =" + msg);
        updateVipValues(null);
    }

    @Override
    public void getVipCardPricesSuccess(List<VipCardPriceBean> response) {
        String price = userVipInfoBean.getPrice();
        VipPurchasePopupWindow pw = new VipPurchasePopupWindow(this, 2, price, response);
        pw.setListener((bean, position) -> startPurchase(bean));
        pw.showAtLocation(MyMemberActivity.this.findViewById(R.id.sv_my_member), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getVipCardPricesFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void userPurchaseVipSuccess() {
        ToastUtils.showLong(context, R.string.toast_recharge_success);
    }

    @Override
    public void userPurchaseVipFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }


}
