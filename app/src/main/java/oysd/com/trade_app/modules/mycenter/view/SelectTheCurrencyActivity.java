package oysd.com.trade_app.modules.mycenter.view;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.modules.mycenter.adapter.SelectTheCurrencyAdapter;
import oysd.com.trade_app.modules.mycenter.bean.SelectTheCurrencyBean;

/**
 * @author houmingkuan
 * @time 2019/8/2
 * @desc 选择币种
 */
public class SelectTheCurrencyActivity extends BaseToolActivity {
    @BindView(R.id.rv_recycler)
    RecyclerView recyclerView;
    private SelectTheCurrencyAdapter adapter;
    @Override
    protected int setView() {
        return R.layout.activity_select_the_currency;
    }
    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.select_the_currency);
        // 实例化一个适配器
        adapter = new SelectTheCurrencyAdapter(this, R.layout.item_my_ads);
        adapter.setOnTradeRevokeListener(new SelectTheCurrencyAdapter.OnTradeRevokeListener() {
            @Override
            public void onRevoke(SelectTheCurrencyBean otcAdBean, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
