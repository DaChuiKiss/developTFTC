package oysd.com.trade_app.modules.mycenter.view;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseAdapter;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.http.bean.ResponsePaging;
import oysd.com.trade_app.modules.mycenter.adapter.MyVoucherAdapter;
import oysd.com.trade_app.modules.mycenter.bean.MessageEvent;
import oysd.com.trade_app.modules.mycenter.bean.VoucherBean;
import oysd.com.trade_app.modules.mycenter.contract.MyVoucherContract;
import oysd.com.trade_app.modules.mycenter.presenter.MyVoucherPresenter;
import oysd.com.trade_app.util.EventBusUtil;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2019/7/15
 * @desc 我的账户-凭证账户
 */
public class VoucherFragment extends LazyLoadBaseFragment implements MyVoucherContract.View {
    @BindView(R.id.rv_voucher_recycler)
    RecyclerView recyclerView;
    MyVoucherAdapter myVoucherAdapter;
    MyVoucherContract.Presenter presenter = new MyVoucherPresenter(this);
    private List<VoucherBean> voucherBeans = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_voucher;
    }

    @Override
    protected void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onResume() {
        super.onResume();
        //因为返回数据重复问题，暂时这么写
        if (myVoucherAdapter != null) {
            myVoucherAdapter = null;
        }
        myVoucherAdapter = new MyVoucherAdapter(getActivity(), R.layout.my_voucher_item);
        myVoucherAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isFastClick(view)) {
                    Intent intent = new Intent(getActivity(), TransferActivity.class);
                    intent.putExtra("VoucherBean", voucherBeans.get(position));
                    startActivity(intent);
                }
            }
        });
        myVoucherAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", voucherBeans.get(position).getAddress());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                ToastUtils.showLong(App.getContext(), getResources().getString(R.string.copySuccess));
            }
        });
        recyclerView.setAdapter(myVoucherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.getCurrentUserLoginAccount();
    }

    @Override
    public void getCurrentUserLoginAccountSuccess(ResponsePaging<VoucherBean> response) {
        if (response == null) return;
        voucherBeans.clear();
        voucherBeans = response.getPagingData().getItem();
        myVoucherAdapter.addDataSet(voucherBeans);
        myVoucherAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBusUtil.post(new MessageEvent("VoucherFragment"));
    }

    @Override
    public void getCurrentUserLoginAccountFailed(int code, String msg) {
        ToastUtils.showLong(getActivity(), code + msg);
    }

}
