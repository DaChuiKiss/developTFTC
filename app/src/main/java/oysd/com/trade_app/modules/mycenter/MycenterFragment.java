package oysd.com.trade_app.modules.mycenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.Constants;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseFragment;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.bean.UrlBean;
import oysd.com.trade_app.modules.mycenter.bean.TotalConvertInfo;
import oysd.com.trade_app.modules.mycenter.bean.UserInfoBean;
import oysd.com.trade_app.modules.mycenter.bean.UserVipInfoBean;
import oysd.com.trade_app.modules.mycenter.contract.MycenterContract;
import oysd.com.trade_app.modules.mycenter.presenter.MycenterPresenter;
import oysd.com.trade_app.modules.mycenter.view.CustomerServiceActivity;
import oysd.com.trade_app.modules.mycenter.view.DefaultPatternCheckingActivity;
import oysd.com.trade_app.modules.mycenter.view.MyAccountActivity;
import oysd.com.trade_app.modules.mycenter.view.MyActivity;
import oysd.com.trade_app.modules.mycenter.view.MyAdsActivity;
import oysd.com.trade_app.modules.mycenter.view.MyIncomeActivity;
import oysd.com.trade_app.modules.mycenter.view.MyInvitationActivity;
import oysd.com.trade_app.modules.mycenter.view.MyMemberActivity;
import oysd.com.trade_app.modules.mycenter.view.OrderManageActivity;
import oysd.com.trade_app.modules.mycenter.view.SafeActivity;
import oysd.com.trade_app.modules.mycenter.view.SystemSettingActivity;
import oysd.com.trade_app.modules.mycenter.view.UrlWebActivity;
import oysd.com.trade_app.util.DateTimeUtils;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.widget.ITabClickListener;

/**
 * Created by yx on 16/4/3.
 */
public class MycenterFragment extends LazyLoadBaseFragment
        implements ITabClickListener, MycenterContract.View {
    @BindView(R.id.tv_mycenter_systemSetting)
    LinearLayout tv_mycenter_systemSetting;
    @BindView(R.id.tv_mycenter_account_btc)
    TextView tv_mycenter_account_btc;
    @BindView(R.id.tv_mycenter_account_rmb)
    TextView tv_mycenter_account_rmb;

    @BindView(R.id.tv_mycenter_phone)
    TextView tv_mycenter_phone;

    @BindView(R.id.tv_mycenter_id)
    TextView tv_mycenter_id;

    @BindView(R.id.rl_1)
    RelativeLayout rl_1;
    @BindView(R.id.rl_2)
    RelativeLayout rl_2;
    @BindView(R.id.rl_3)
    RelativeLayout rl_3;
    @BindView(R.id.rl_4)
    RelativeLayout rl_4;
    @BindView(R.id.rl_5)
    RelativeLayout rl_5;
    @BindView(R.id.rl_6)
    RelativeLayout rl_6;
    @BindView(R.id.rl_7)
    RelativeLayout rl_7;
    @BindView(R.id.rl_8)
    RelativeLayout rl_8;
    @BindView(R.id.rl_9)
    RelativeLayout rl_9;
    @BindView(R.id.rl_10)
    RelativeLayout rl_10;

    @BindView(R.id.tv_mycenter_vipLeve)
    TextView tv_mycenter_vipLeve;

    @BindView(R.id.tv_mycenter_loseDate)
    TextView tv_mycenter_loseDate;

    @BindView(R.id.head)
    ImageView head;

    @BindView(R.id.ll_mycenter_goto_myaccount)
    LinearLayout ll_mycenter_goto_myaccount;

    String TAG = "MycenterFragment";

    MycenterContract.Presenter presenter = new MycenterPresenter(this);


    @Override
    public void onMenuItemClick() {

    }


    public void isLogin() {
        if (InfoProvider.getInstance().getLogin()) {
            Global.isLogin = true;
            rl_1.setOnClickListener(listener);
            rl_2.setOnClickListener(listener);
            rl_3.setOnClickListener(listener);
            rl_4.setOnClickListener(listener);
            rl_5.setOnClickListener(listener);
            rl_6.setOnClickListener(listener);
            rl_7.setOnClickListener(listener);
            rl_8.setOnClickListener(listener);
            rl_9.setOnClickListener(listener);
            rl_10.setOnClickListener(listener);
            ll_mycenter_goto_myaccount.setOnClickListener(listener);
            //初始化内容
            presenter.getUserInfo();
            presenter.getUserVipInfo();
            if (InfoProvider.getInstance().getLoginInfo() != null) {
                tv_mycenter_phone.setText(InfoProvider.getInstance().getLoginInfo().getNickName());
                if (InfoProvider.getInstance().getUserInfo() != null)
                    tv_mycenter_id.setText("ID:" + InfoProvider.getInstance().getUserInfo().getShowId());
            }
            tv_mycenter_systemSetting.setOnClickListener(listener);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {

            int buttonid = v.getId();
            if (!Utils.isFastClick(v)) {
                switch (buttonid) {
                    case R.id.rl_1:
                        Intent intent = new Intent(getActivity(), MyMemberActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.rl_2:
                        intent = new Intent(getActivity(), MyIncomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rl_3:

                        intent = new Intent(getActivity(), MyAdsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.ll_mycenter_goto_myaccount:
                    case R.id.rl_4:
                        intent = new Intent(getActivity(), MyAccountActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rl_5:
                        intent = new Intent(getActivity(), OrderManageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rl_6:
                        // 我的邀请
                        // http://bourse.oss-cn-shenzhen.aliyuncs.com/h5_invite/index.html#/invite?token=''
                        // http://bourse.oss-cn-shenzhen.aliyuncs.com/h5_invite/index.html#/invite_en?token=''

                        // http://bourse.oss-cn-shenzhen.aliyuncs.com/h5_web_release/index.html#/invite_zh?token=
                        // http://bourse.oss-cn-shenzhen.aliyuncs.com/h5_web_release/index.html#/invite_en?token=

                        // String title = getResources().getString(R.string.type4);
                        // String token = InfoProvider.getInstance().getToken();
                        //
                        // if (Utils.isChina()) {
                        //     // jumpToWeb(title, Constants.URL + "/notice/dist/index.html#/invite/?id=" + token);
                        //     jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_zh?token=" + token,
                        //             UrlWebActivity.TYPE_POSTER);
                        // } else {
                        //     jumpToWeb(title, Constants.URL + "/h5_web_release/index.html#/invite_en?token=" + token,
                        //             UrlWebActivity.TYPE_POSTER);
                        // }
                        startActivity(new Intent(getActivity(), MyInvitationActivity.class));
                        break;
                    case R.id.rl_7:
                        intent = new Intent(getActivity(), MyActivity.class);
                        Global.isToSenior = true;
                        startActivity(intent);
                        break;
                    case R.id.rl_8:
                        intent = new Intent(getActivity(), SafeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.rl_9:
                        intent = new Intent(getActivity(), CustomerServiceActivity.class);
                        startActivity(intent);
//                        if (Utils.isChina()) {
//                            jumpToWeb(getResources().getString(R.string.type7), Constants.URL + "/martview/servicecenter.html");
//                        } else {
//                            jumpToWeb(getResources().getString(R.string.type7), Constants.URL + "/martview/E-servicecenter.html");
//                        }
                        break;
                    case R.id.rl_10:
                        if (Utils.isChina()) {
                            jumpToWeb(getResources().getString(R.string.type9), Constants.H5_URL + "/html/guide.html");
                        } else {
                            jumpToWeb(getResources().getString(R.string.type9), "");
                        }

                        break;
                    case R.id.tv_mycenter_systemSetting:
                        intent = new Intent(getActivity(), SystemSettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        }
    };


    public UrlBean selectBean(int type) {
        UrlBean ub = new UrlBean();
        if (Global.urlList.size() > 0) {
            for (int i = 0; i < Global.urlList.size(); i++) {
                if (type == Global.urlList.get(i).getType()) {
                    ub = Global.urlList.get(i);
                }
            }
        }
        return ub;
    }

    // 跳转到指定静态页面
    private void jumpToWeb(String title, String url, int type) {
        Intent intent = new Intent(getActivity(), UrlWebActivity.class);

        intent.putExtra(UrlWebActivity.EXTRA_URL, url);
        intent.putExtra(UrlWebActivity.EXTRA_TITLE, title);
        if (type != 0) intent.putExtra(UrlWebActivity.EXTRA_TYPE, type);

        context.startActivity(intent);

    }

    // 跳转到指定静态页面
    private void
    jumpToWeb(String title, String url) {
        jumpToWeb(title, url, 0);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.mycenter_layout;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    public BaseFragment getFragment() {
        return null;
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

//        if (InfoProvider.getInstance().getLogin() && !Global.isLogin) {
//            if (InfoProvider.getInstance().getGesture()) {
//                presenter.getUserInfo();
//                IntentUtils.startActivity(getActivity(), DefaultPatternCheckingActivity.class);
//            }else{
//                isLogin();
//            }
//        } else if (!InfoProvider.getInstance().getLogin()) {
//
//        }else{
//            isLogin();
//        }

        if (InfoProvider.getInstance().getLogin()) {
            if (InfoProvider.getInstance().getGesture()) {
                if (!Global.isLogin) {
                    presenter.getUserInfo();
                    presenter.getUserVipInfo();
                    IntentUtils.startActivity(getActivity(), DefaultPatternCheckingActivity.class);
                } else {
                    isLogin();
                }
            } else {
                isLogin();
            }
        } else {
            IntentUtils.startActivity(getActivity(), LoginActivity.class);
        }

    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }

    @Override
    public void getUserVipInfoSuccess(UserVipInfoBean ub_vip) {

        if (tv_mycenter_vipLeve == null) return;

        if (ub_vip == null) {
            Global.isVip = false;
            head.setImageResource(R.mipmap.my_head_nonvip);
            tv_mycenter_vipLeve.setVisibility(View.GONE);
            tv_mycenter_loseDate.setText(getResources().getString(R.string.open_vip));
        } else {
            Global.isVip = true;
            head.setImageResource(R.mipmap.my_head_vip);
            tv_mycenter_vipLeve.setVisibility(View.VISIBLE);
            tv_mycenter_vipLeve.setText(ub_vip.getVipLevelInfoVo().getName());
            if (Utils.isChina()) {
                if (ub_vip.getLoseDate() == null) {
                    ub_vip.setLoseDate("2999-12-31");
                }
                tv_mycenter_loseDate.setText(DateTimeUtils.correctSvrTime(ub_vip.getLoseDate(), DateTimeUtils.YYYY_MM_DD, DateTimeUtils.YYYY_MM_DD) + getResources().getString(R.string.loseDate));
            } else {
                if (ub_vip.getLoseDate() == null) {
                    ub_vip.setLoseDate("2999-12-31");
                }
                tv_mycenter_loseDate.setText(getResources().getString(R.string.loseDate) + " " + DateTimeUtils.correctSvrTime(ub_vip.getLoseDate(), DateTimeUtils.YYYY_MM_DD, DateTimeUtils.YYYY_MM_DD));
            }
        }
    }

    @Override
    public void getUserVipInfoFailed(int code, String msg) {
        //ToastUtils.showShort(App.getContext(),msg);
        if (tv_mycenter_vipLeve == null) return;
        if (code == 709) {
            Global.isVip = false;
            head.setImageResource(R.mipmap.my_head_nonvip);
            tv_mycenter_vipLeve.setVisibility(View.GONE);
            tv_mycenter_loseDate.setText(getResources().getString(R.string.open_vip));
        }
    }

    @Override
    public void getUserInfoSuccess(UserInfoBean ub) {
        if (tv_mycenter_id == null) return;
        presenter.getTotalAmount();
        InfoProvider.getInstance().saveUserInfo(ub);
        PreferencesUtils.saveString("gesture_pwd_key", InfoProvider.getInstance().getUserInfo().getGesturePassword());
        if (InfoProvider.getInstance().getUserInfo() != null) {
            tv_mycenter_id.setText("ID:" + InfoProvider.getInstance().getUserInfo().getShowId());
        }
    }

    @Override
    public void getUserInfoFailed(int code, String msg) {
        ToastUtils.showLong(App.getContext(), msg);
    }

    @Override
    public void getTotalAmountSuccess(TotalConvertInfo totalConvertInfo) {
        if (tv_mycenter_account_btc == null) return;
        tv_mycenter_account_btc.setText(Utils.myAccountFormat(totalConvertInfo.getBtcBalance()));
        if (PreferencesUtils.getString("selectCoinType") == null || PreferencesUtils.getString("selectCoinType").equals("1")) {
            tv_mycenter_account_rmb.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "HKD");
        } else if (PreferencesUtils.getString("selectCoinType").equals("2")) {
            tv_mycenter_account_rmb.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "USD");
        } else if (PreferencesUtils.getString("selectCoinType").equals("3")) {
            tv_mycenter_account_rmb.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "AUD");
        } else if (PreferencesUtils.getString("selectCoinType").equals("4")) {
            tv_mycenter_account_rmb.setText("≈" + FormatUtils.to2(EmptyUtils.getBigDecimalValue(totalConvertInfo.getTotalBalance())) + "CNY");
        }

    }

    @Override
    public void getTotalAmountFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), msg);
    }
}
