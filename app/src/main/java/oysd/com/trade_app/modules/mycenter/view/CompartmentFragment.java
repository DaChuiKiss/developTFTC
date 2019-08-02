package oysd.com.trade_app.modules.mycenter.view;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.UserInviteReturnAmountBean;
import oysd.com.trade_app.modules.mycenter.contract.UserInviteReturnAmountContract;
import oysd.com.trade_app.modules.mycenter.presenter.UserInviteReturnAmountPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.PreferencesUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class CompartmentFragment extends LazyLoadBaseFragment implements UserInviteReturnAmountContract.View {

    UserInviteReturnAmountContract.Presenter presenter = new UserInviteReturnAmountPresenter(this);
    @BindView(R.id.tv_mycenter_compartment_totalBtcAmount)
    TextView tv_mycenter_compartment_totalBtcAmount;

    @BindView(R.id.tv_mycenter_compartment_totalTenderAmount)
    TextView tv_mycenter_compartment_totalTenderAmount;

    @BindView(R.id.tv_mycenter_compartment_btcAmount)
    TextView tv_mycenter_compartment_btcAmount;

    @BindView(R.id.tv_mycenter_compartment_tenderAmount)
    TextView tv_mycenter_compartment_tenderAmount;

    @BindView(R.id.tv_mycenter_compartment_date)
    TextView tv_mycenter_compartment_date;


    @Override
    protected int getLayoutRes() {
        return R.layout.compartment;
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        presenter.getUserInviteReturnAmount();
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    public void getUserInviteReturnAmountSuncess(ResponseEntity<UserInviteReturnAmountBean> response) {

        UserInviteReturnAmountBean uirb = response.getData();
        if (tv_mycenter_compartment_totalBtcAmount != null) {
            tv_mycenter_compartment_totalBtcAmount.setText(Utils.myAccountFormat(uirb.getTotalBtcAmount()));
            if (PreferencesUtils.getString("selectCoinType") == null || PreferencesUtils.getString("selectCoinType").equals("1")) {
                tv_mycenter_compartment_totalTenderAmount.setText("≈" + FormatUtils.to2(uirb.getTotalTenderAmount()) + "HKD");
                tv_mycenter_compartment_tenderAmount.setText("≈" + FormatUtils.to2(uirb.getTenderAmount()) + "HKD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("2")) {
                tv_mycenter_compartment_totalTenderAmount.setText("≈" + FormatUtils.to2(uirb.getTotalTenderAmount()) + "USD");
                tv_mycenter_compartment_tenderAmount.setText("≈" + FormatUtils.to2(uirb.getTenderAmount()) + "USD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("3")) {
                tv_mycenter_compartment_totalTenderAmount.setText("≈" + FormatUtils.to2(uirb.getTotalTenderAmount()) + "AUD");
                tv_mycenter_compartment_tenderAmount.setText("≈" + FormatUtils.to2(uirb.getTenderAmount()) + "AUD");
            } else if (PreferencesUtils.getString("selectCoinType").equals("4")) {
                tv_mycenter_compartment_totalTenderAmount.setText("≈" + FormatUtils.to2(uirb.getTotalTenderAmount()) + "CNY");
                tv_mycenter_compartment_tenderAmount.setText("≈" + FormatUtils.to2(uirb.getTenderAmount()) + "CNY");
            }
            tv_mycenter_compartment_btcAmount.setText(Utils.myAccountFormat(uirb.getBtcAmount()));
            tv_mycenter_compartment_date.setText(uirb.getStartDay() + "-" + uirb.getEndDay());
        }
    }

    @Override
    public void getUserInviteReturnAmountFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }
}
