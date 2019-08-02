package oysd.com.trade_app.modules.mycenter.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.modules.mycenter.bean.DividendBean;
import oysd.com.trade_app.modules.mycenter.contract.DividendContract;
import oysd.com.trade_app.modules.mycenter.presenter.DididendPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.Global;
import oysd.com.trade_app.util.ToastUtils;

public class TradingDividendsFragment extends LazyLoadBaseFragment implements DividendContract.View {

    @BindView(R.id.bt_mycenter_mining_tovip)
    Button bt_mycenter_mining_tovip;
    @BindView(R.id.tv_mycenter_dividend_btc_value)
    TextView tv_mycenter_dividend_btc_value;
    @BindView(R.id.tv_mycenter_dividend_fb_value)
    TextView tv_mycenter_dividend_fb_value;

    DividendContract.Presenter presenter = new DididendPresenter(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.trading_dividends;
    }

    @Override
    protected void initView(View rootView) {

//        if (Global.isVip) {
            bt_mycenter_mining_tovip.setVisibility(View.GONE);
            presenter.getDividend();
//        } else {
//            bt_mycenter_mining_tovip.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_mycenter_mining_tovip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_mycenter_mining_tovip:
                Intent intent = new Intent(getActivity(), MyMemberActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
       // if (Global.isVip) {
            bt_mycenter_mining_tovip.setVisibility(View.GONE);
            presenter.getDividend();
//        } else {
//            bt_mycenter_mining_tovip.setVisibility(View.VISIBLE);
//
//        }
    }

    @Override
    public void getDividendSuccess(ResponseEntity<DividendBean> response) {
        if (tv_mycenter_dividend_btc_value == null) return;
        DividendBean db = response.getData();
        if (db.getBonusAmount() == null) {
            tv_mycenter_dividend_btc_value.setText("0");
        } else {
            tv_mycenter_dividend_btc_value.setText(EmptyUtils.getBigDecimalValue(db.getBonusAmount()));
        }

        if (db.getFrenchCurrencyAmount() == null) {
            tv_mycenter_dividend_fb_value.setText("0" + db.getCurrencyUnit());
        } else {
            tv_mycenter_dividend_fb_value.setText(db.getFrenchCurrencyAmount().toPlainString() + db.getCurrencyUnit());
        }
    }

    @Override
    public void getDividendFailed(int code, String msg) {
        ToastUtils.showShort(App.getContext(), msg);
    }
}
