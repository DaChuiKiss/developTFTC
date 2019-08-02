package oysd.com.trade_app.modules.mycenter.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.MemberBenefitsContract;
import oysd.com.trade_app.modules.mycenter.presenter.MemberBenefitsPresenter;
import oysd.com.trade_app.util.InfoUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.SpannableEditor;
import oysd.com.trade_app.util.StatusBarUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 会员权益选择页面。
 * Created by Liam on 19/09/19.
 */
public class MemberBenefitsActivity
        extends BaseToolActivity implements MemberBenefitsContract.View {

    @BindView(R.id.rl_member_benefits_card)
    RelativeLayout rlCard;

    @BindView(R.id.tv_member_benefits_type)
    TextView tvType;

    @BindView(R.id.rl_member_benefits_dig)
    RelativeLayout rlDig;

    @BindView(R.id.tv_member_benefits_dig)
    TextView tvDig;

    @BindView(R.id.iv_member_benefits_dig)
    ImageView ivDig;

    @BindView(R.id.rl_member_benefits_discount)
    RelativeLayout rlDiscount;

    @BindView(R.id.tv_member_benefits_discount)
    TextView tvDiscount;

    @BindView(R.id.iv_member_benefits_discount)
    ImageView ivDiscount;

    @BindView(R.id.btn_member_benefits_submit)
    Button btnSubmit;


    MemberBenefitsContract.Presenter presenter = new MemberBenefitsPresenter(this);

    private UserVipInfoBean userVipInfoBean;
    // 选中的权益的类型: 1: 交易挖矿 2.免手续费
    private int benefitStatus = 1;
    private int originalBenefitStatus = 1;

    @Override
    protected int setView() {
        return R.layout.activity_member_benefits;
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
    }

    @Override
    protected void initData() {
        presenter.getUserVipInfo();
    }

    @Override
    protected void initClicks() {
        rlDig.setOnClickListener(this);
        rlDiscount.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (Utils.isFastClick()) return;
        switch (v.getId()) {
            case R.id.rl_member_benefits_dig:
                benefitStatus = 1;
                refreshBenefitsView();
                break;

            case R.id.rl_member_benefits_discount:
                benefitStatus = 2;
                refreshBenefitsView();
                break;

            case R.id.btn_member_benefits_submit:
                if (originalBenefitStatus == benefitStatus) {
                    // 权益没有更改，不作提交。
                    ToastUtils.showLong(context, R.string.toast_benefit_not_changed);
                    return;
                }
                String code = benefitStatus == 1 ? Values.VipBenefits.DIG : Values.VipBenefits.FREE_SERVICE;
                presenter.changeVipBenefit(code, userVipInfoBean.getId());
                break;

            default:
                break;
        }
    }

    private void updateView(UserVipInfoBean bean) {
        UserVipInfoBean.VipLevelInfoVoBean levelInfo = bean.getVipLevelInfoVo();

        // 出现过 vipLevelInfoBean 为 null 的情况，因此做判断保护。
        // 如果为 null，则 levelId 默认为 1 ，即是 月卡。
        int levelId;
        String vipName;
        if (levelInfo == null) {
            Logger.e("level == null. Default cardId = 1.");
            levelId = 1;
            vipName = "";
        } else {
            levelId = levelInfo.getId();
            vipName = levelInfo.getName();
        }

        // title 为 vip 的级别。
        setTitleText(vipName);

        int cardBg;
        String cardType;
        int cardId = bean.getCardId();
        switch (cardId) {
            case Values.VipCardId.MONTH:
                cardBg = R.drawable.bg_vip_month;
                cardType = getString(R.string.vip_card_month);
                break;
            case Values.VipCardId.SEASON:
                cardBg = R.drawable.bg_vip_season;
                cardType = getString(R.string.vip_card_season);
                break;
            case Values.VipCardId.YEAR:
                cardBg = R.drawable.bg_vip_year;
                cardType = getString(R.string.vip_card_year);
                break;
            case Values.VipCardId.LIFELONG:
                cardBg = R.drawable.bg_vip_lifelong;
                cardType = getString(R.string.vip_card_lifelong);
                break;
            default:
                cardBg = R.drawable.bg_vip_month;
                cardType = getString(R.string.vip_card_month);
                break;
        }

        rlCard.setBackgroundResource(cardBg);

        // 英文的卡类型和"会员"间有空格分开
        cardType = Values.Language.ZH.equals(InfoUtils.getLanguage())
                ? cardType + getString(R.string.vip_member)
                : cardType + " " + getString(R.string.vip_member);

        String strType = getString(R.string.vip_congratulations, cardType);
        SpannableEditor editor = new SpannableEditor(context, strType);
        editor.setTextColor(ContextCompat.getColor(context, R.color.text_yellow), cardType)
                .setTextSize(Utils.sp2px(context, 17), cardType);
        tvType.setText(editor.build());

        String benefit = bean.getRightsMode();
        if (Values.VipBenefits.DIG.equals(benefit)) {
            benefitStatus = 1;
            originalBenefitStatus = 1;
            refreshBenefitsView();

        } else if (Values.VipBenefits.FREE_SERVICE.equals(benefit)) {
            benefitStatus = 2;
            originalBenefitStatus = 2;
            refreshBenefitsView();
        }
    }

    private void refreshBenefitsView() {
        if (benefitStatus == 1) {
            rlDig.setBackgroundResource(R.drawable.rounded_borders_f2c010_r5);
            tvDig.setTextColor(ContextCompat.getColor(context, R.color.text_light_yellow));
            ivDig.setVisibility(View.VISIBLE);

            rlDiscount.setBackgroundResource(R.drawable.rounded_borders_white_r5);
            tvDiscount.setTextColor(ContextCompat.getColor(context, R.color.text_white));
            ivDiscount.setVisibility(View.GONE);

        } else if (benefitStatus == 2) {
            rlDiscount.setBackgroundResource(R.drawable.rounded_borders_f2c010_r5);
            tvDiscount.setTextColor(ContextCompat.getColor(context, R.color.text_light_yellow));
            ivDiscount.setVisibility(View.VISIBLE);

            rlDig.setBackgroundResource(R.drawable.rounded_borders_white_r5);
            tvDig.setTextColor(ContextCompat.getColor(context, R.color.text_white));
            ivDig.setVisibility(View.GONE);
        }
    }

    @Override
    public void getUserVipInfoSuccess(UserVipInfoBean response) {
        this.userVipInfoBean = response;
        updateView(userVipInfoBean);
    }

    @Override
    public void getUserVipInfoFailed(int code, String msg) {
        Logger.e("code = " + code + " , msg = " + msg);
    }

    @Override
    public void changeVipBenefitSuccess() {
        ToastUtils.showLong(context, R.string.toast_change_benefit_success);
    }

    @Override
    public void changeVipBenefitFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
