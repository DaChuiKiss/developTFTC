package oysd.com.trade_app.modules.otc.view.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.Values;
import oysd.com.trade_app.base.LazyLoadBaseFragment;
import oysd.com.trade_app.main.MainActivity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.util.Glide4Engine;
import oysd.com.trade_app.modules.otc.bean.PaymentInfoBean;
import oysd.com.trade_app.modules.otc.contract.SettingNetAccountContract;
import oysd.com.trade_app.modules.otc.presenter.SettingNetAccountPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.GlideUtils;
import oysd.com.trade_app.util.IntentUtils;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;
import oysd.com.trade_app.util.ViewHelper;

import static android.app.Activity.RESULT_OK;

/**
 * 设置支付方式 - CNY - 支付宝/WeChat 设置。
 * Created by Liam on 2018/8/8
 */
public class SettingCnyFragment
        extends LazyLoadBaseFragment implements SettingNetAccountContract.View {

    public static final int REQUEST_CODE_IMAGE = 1;
    public static final String EXTRA_TYPE = "type";
    // ZhiFuBao or WeChat
    private String type;

    @BindView(R.id.tv_cny_account_text)
    TextView tvAccountText;

    @BindView(R.id.btn_cny_save)
    Button btnSave;

    @BindView(R.id.et_cny_name)
    EditText etName;

    @BindView(R.id.et_cny_account)
    EditText etAccount;

    @BindView(R.id.et_cny_password)
    EditText etPassword;

    @BindView(R.id.et_cny_remark)
    EditText etRemark;

    @BindView(R.id.iv_cny_show_img)
    ImageView ivShowImg;

    @BindView(R.id.iv_cny_upload)
    ImageView ivUpload;

    @BindView(R.id.tv_cny_upload_text)
    TextView tvUploadText;

    @BindView(R.id.sw_cny_using)
    CheckBox swUsing;

    @BindView(R.id.tv_cny_goto_trade)
    TextView tvGotoTrade;

    private SettingNetAccountContract.Presenter presenter = new SettingNetAccountPresenter(this);
    private String name, account, password, remark;
    private int status;
    private int payType;

    private boolean hasSaved = false;

    private PaymentInfoBean originalPaymentInfo;
    private String imagePath;
    private int imageId = 0;


    public static SettingCnyFragment newInstance(@NonNull String type) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);

        SettingCnyFragment fragment = new SettingCnyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting_cny;
    }

    @Override
    protected void initView(View rootView) {
        if (bundle == null || bundle.getString(EXTRA_TYPE) == null) {
            throw new IllegalArgumentException("must set type of SettingCnyFragment.");
        }

        type = bundle.getString(EXTRA_TYPE);
        payType = getString(R.string.zfb).equals(type) ?
                Values.PaymentMethod.ALIPAY : Values.PaymentMethod.WECHAT;

        String textAccount = String.format(tvAccountText.getText().toString(), type);
        tvAccountText.setText(textAccount);
        String textSave = String.format(btnSave.getText().toString(), type);
        btnSave.setText(textSave);
    }

    @Override
    protected void initClicks() {
        ivUpload.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvGotoTrade.setOnClickListener(this);
        ivShowImg.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter.getUserPaymentInfo(payType);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (Utils.isFastClick()) return;

        switch (v.getId()) {
            case R.id.btn_cny_save:
                save();
                break;

            case R.id.tv_cny_goto_trade:
                IntentUtils.startActivity(context, MainActivity.class);
//                if (hasSaved || !isContentExists()) {
//                    IntentUtils.startActivity(context, MainActivity.class);
//                } else {
//                    // show a warning dialog here
//                    // use Toast instead temporary.
//                    ToastUtils.showLong(context, R.string.toast_not_complete);
//                }
                break;

            case R.id.iv_cny_upload:
                uploadImage();
                break;

            case R.id.iv_cny_show_img:
                uploadImage();
                break;

            default:
                break;
        }
    }

    private void save() {
        if (isErrorInputs()) return;

        getAllInputs();

        if (originalPaymentInfo == null) {
            presenter.addNetAccount(getNetAccountParams());
        } else {
            presenter.updateNetAccount(getNetAccountParams());
            presenter.updateNetAccountStatus(getStatusParams());
        }
    }

    private Map<String, Object> getNetAccountParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("accountNumber", account);
        params.put("accountUserName", name);
        params.put("password", Utils.MD5(password));
        params.put("receivablesType", payType);
        params.put("remarks", remark);
        params.put("status", status);

        if (imageId != 0) {
            params.put("receiptCodeImageId", imageId);
        }
        if (originalPaymentInfo != null) {
            params.put("id", originalPaymentInfo.getId());
        }

        return params;
    }

    private Map<String, Object> getStatusParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", originalPaymentInfo != null ? originalPaymentInfo.getId() : 0);
        params.put("password", Utils.MD5(password));
        params.put("status", swUsing.isChecked() ? 1 : 0);
        return params;
    }

    private void uploadImage() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "oysd.com.trade_app.fileprovider"))
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            List<String> paths = Matisse.obtainPathResult(data);

            if (EmptyUtils.isEmpty(paths)) return;

            imagePath = paths.get(0);
            presenter.uploadImage(new File(imagePath));
        }
    }

    /**
     * 获得所有的输入内容。
     */
    private void getAllInputs() {
        name = etName.getText().toString();
        account = etAccount.getText().toString();
        password = etPassword.getText().toString();
        remark = etRemark.getText().toString();
        status = swUsing.isChecked() ? 1 : 0;
    }

    /**
     * 检查 EditTexts 中是否有内容。
     *
     * @return true 有内容；false 无内容。
     */
    private boolean isContentExists() {
        return ViewHelper.isContentExists(etName, etAccount, etPassword);
    }

    /**
     * 检查输入的内容是否有误.
     *
     * @return true 输入有误；false 输入无误。
     */
    private boolean isErrorInputs() {
        return ViewHelper.isEditEmpty(context, etName, etAccount, etPassword)
                || !ViewHelper.isRemarkLessThan20(context, etRemark);
    }

    /**
     * 展示图片.
     */
    private void showImage(String imagePath) {
        ivUpload.setVisibility(View.GONE);
        tvUploadText.setVisibility(View.GONE);
        ivShowImg.setVisibility(View.VISIBLE);
        GlideUtils.loadImage(context, ivShowImg, imagePath);
    }

    @Override
    public void getUserPaymentInfoSuccess(PaymentInfoBean response) {
        if (response == null) return;

        originalPaymentInfo = response;

        etName.setText(response.getAccountUserName());
        etAccount.setText(response.getAccountNumber());
        etRemark.setText(response.getRemarks());

        if (EmptyUtils.isNotEmpty(response.getReceiptCodeImageUrl())) {
            showImage(response.getReceiptCodeImageUrl());
        }

        swUsing.setChecked(response.getStatus() == 1);
    }

    @Override
    public void getUserPaymentInfoFailed(int code, String msg) {
        Logger.e(payType + " code = " + code + " , msg = " + msg);
    }

    @Override
    public void addNetAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_add_account_suc);

        // presenter.getUserPaymentInfo(payType);
    }

    @Override
    public void addNetAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateNetAccountSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_account_suc);
    }

    @Override
    public void updateNetAccountFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void updateNetAccountStatusSuccess() {
        hasSaved = true;
        etPassword.setText("");
        ToastUtils.showLong(context, R.string.toast_update_account_status_suc);
    }

    @Override
    public void updateNetAccountStatusFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void uploadImageSuccess(ImageBean imageBean) {
        if (imageBean == null) return;

        imageId = imageBean.getImageId();
        showImage(imagePath);
    }

    @Override
    public void uploadImageFailed(int code, String msg) {
    }

}
