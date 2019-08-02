package oysd.com.trade_app.modules.mycenter.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseFragment;
import oysd.com.trade_app.modules.mycenter.bean.LegalTenderBean;
import oysd.com.trade_app.modules.mycenter.bean.WalletAddressBean;
import oysd.com.trade_app.modules.mycenter.contract.WalletAddressContract;
import oysd.com.trade_app.modules.mycenter.presenter.WalletAddressPresenter;
import oysd.com.trade_app.util.FormatUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class depositFragment extends BaseFragment implements WalletAddressContract.View {

    @BindView(R.id.tv_deposit_balance)
    TextView tv_deposit_balance;
    @BindView(R.id.tv_deposit_freeze)
    TextView tv_deposit_freeze;
    @BindView(R.id.tv_deposit_hashAddress)
    TextView tv_deposit_hashAddress;
    @BindView(R.id.tv_deposit_warm)
    TextView tv_deposit_warm;

    @BindView(R.id.iv_deposit_qr_code)
    ImageView iv_deposit_qr_code;

    @BindView(R.id.tv_deposit_total)
    TextView tv_deposit_total;

    @BindView(R.id.bt_deposit_copy)
    Button bt_deposit_copy;

    WalletAddressContract.Presenter presenter = new WalletAddressPresenter(this);


    @Override
    protected int getLayoutRes() {
        return R.layout.deposit_layout;
    }

    @Override
    protected void initView(View rootView) {
        LegalTenderBean ltb = ((CoinChargeActivity) getActivity()).getLtb();
        tv_deposit_balance.setText(Utils.myAccountFormat(ltb.getBalance()));
        tv_deposit_freeze.setText(Utils.myAccountFormat(ltb.getFreeze()));
        tv_deposit_total.setText(Utils.myAccountFormat(ltb.getBalance().add(ltb.getFreeze())));
        presenter.getWalletAddress(ltb.getCoinId());
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        bt_deposit_copy.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_deposit_copy:
                    ClipboardManager clipboard = (ClipboardManager)
                            getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text",
                            tv_deposit_hashAddress.getText());

                    // Set the clipboard's primary clip.
                    clipboard.setPrimaryClip(clip);
                    ToastUtils.showLong(App.getContext(), getResources().getString(R.string.copySuccess));

                    break;
            }
        }
    }

    @Override
    public void getWalletAddressSuccess(WalletAddressBean wb) {
        if (tv_deposit_hashAddress == null) return;
        tv_deposit_hashAddress.setText(wb.getWalletAddress());
        tv_deposit_warm.setText(wb.getTakeInReminder());
        Bitmap bitmap = stringToBitmap(wb.getImageData());
        Bitmap zoomBitmap = zoomBitmap(bitmap, Utils.dip2px(getActivity(), 151), Utils.dip2px(getActivity(), 151));
        iv_deposit_qr_code.setImageBitmap(zoomBitmap);

    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void getWalletAddressFailed(int code, String msg) {
        //ToastUtils.showLong(App.getContext(), code + msg);
    }

}
