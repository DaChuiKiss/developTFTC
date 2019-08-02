package oysd.com.trade_app.modules.otc.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.util.Glide4Engine;
import oysd.com.trade_app.modules.otc.contract.SubmitAppealContract;
import oysd.com.trade_app.modules.otc.presenter.SubmitAppealPresenter;
import oysd.com.trade_app.util.EmptyUtils;
import oysd.com.trade_app.util.GlideUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

/**
 * 提出申诉页面。
 * Created by Liam on 2018/7/18
 */
public class SubmitAppealActivity
        extends BaseToolActivity implements SubmitAppealContract.View {

    public static final int REQUEST_CODE_IMAGE = 1;
    public static final String EXTRA_ORDER_ID = "order_id";

    @BindView(R.id.et_appeal_reason)
    EditText etInputReason;

    @BindView(R.id.tv_appeal_click1)
    TextView tvAddImg01;
    @BindView(R.id.tv_appeal_click2)
    TextView tvAddImg02;
    @BindView(R.id.tv_appeal_click3)
    TextView tvAddImg03;

    @BindView(R.id.iv_appeal_img1)
    ImageView ivShowImg01;
    @BindView(R.id.iv_appeal_img2)
    ImageView ivShowImg02;
    @BindView(R.id.iv_appeal_img3)
    ImageView ivShowImg03;

    @BindView(R.id.btn_appeal_submit)
    Button btnSubmitAppeal;

    SubmitAppealContract.Presenter presenter = new SubmitAppealPresenter(this);

    private int orderId;
    private String reason;
    private List<Integer> imageIds = new ArrayList<>();


    @Override
    protected int setView() {
        return R.layout.activity_submit_appeal;
    }

    @Override
    protected boolean setViewInRefresh() {
        return true;
    }

    @Override
    protected void initView() {
        setEnableRefresh(false);
        setTitleText(R.string.title_submit_appeal);

        if (extras == null) {
            throw new IllegalArgumentException("must pass orderId value.");
        }
        orderId = extras.getInt(EXTRA_ORDER_ID);
        if (orderId == 0) {
            throw new IllegalArgumentException("must pass orderId value.");
        }
    }

    @Override
    protected void initClicks() {
        tvAddImg01.setOnClickListener(this);
        tvAddImg02.setOnClickListener(this);
        tvAddImg03.setOnClickListener(this);
        btnSubmitAppeal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.tv_appeal_click1:

                    int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //权限判断是否有访问外部存储空间权限
                    if (flag != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                            Toast.makeText(this, R.string.permission_request_denied, Toast.LENGTH_LONG).show();
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        } else {
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        }
                    } else {
                        startUploadImage();
                    }
                    break;

                case R.id.tv_appeal_click2:
                    //权限判断是否有访问外部存储空间权限
                    int flag2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (flag2 != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                            Toast.makeText(this, R.string.permission_request_denied, Toast.LENGTH_LONG).show();
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        } else {
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        }
                    } else {
                        startUploadImage();
                    }
                    break;

                case R.id.tv_appeal_click3:
                    int flag3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //权限判断是否有访问外部存储空间权限
                    if (flag3 != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                            Toast.makeText(this, R.string.permission_request_denied, Toast.LENGTH_LONG).show();
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        } else {
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        }
                    } else {
                        startUploadImage();
                    }
                    break;
                case R.id.btn_appeal_submit:
                    int flag4 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //权限判断是否有访问外部存储空间权限
                    if (flag4 != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                            Toast.makeText(this, R.string.permission_request_denied, Toast.LENGTH_LONG).show();
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        } else {
                            // 申请授权。
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        }
                    } else {
                        startUploadImage();
                    }
                    break;

                default:
                    break;
            }
        }
    }


    private void startPostAppeal() {
        reason = etInputReason.getText().toString();

        if (EmptyUtils.isEmpty(reason) || EmptyUtils.isEmpty(imageIds)) {
            ToastUtils.showLong(context, R.string.toast_enter_reason);
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("appealGrounds", reason);
        params.put("orderId", orderId);
        params.put("photoIds", getIdsString(imageIds));

        presenter.submitAppeal(params);
    }

    /**
     * 将 image Id 组合成用逗号分隔的字符串。
     */
    private String getIdsString(List<Integer> imageIds) {
        StringBuilder sb = new StringBuilder();

        for (Integer i : imageIds) {
            sb.append(i);
            sb.append(",");
        }

        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    private void startUploadImage() {
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

            if (EmptyUtils.isEmpty(paths)) {
                return;
            }

            String imagePath = paths.get(0);
            presenter.uploadImage(new File(imagePath));
        }
    }

    @Override
    public void uploadImageSuccess(ImageBean imageBean) {
        imageIds.add(imageBean.getImageId());

        if (tvAddImg01.getVisibility() == View.VISIBLE) {
            tvAddImg01.setVisibility(View.GONE);
            GlideUtils.loadImage(context, ivShowImg01, imageBean.getSmallImagePath());

        } else if (tvAddImg02.getVisibility() == View.VISIBLE) {
            tvAddImg02.setVisibility(View.GONE);
            GlideUtils.loadImage(context, ivShowImg02, imageBean.getSmallImagePath());

        } else if (tvAddImg03.getVisibility() == View.VISIBLE) {
            tvAddImg03.setVisibility(View.GONE);
            GlideUtils.loadImage(context, ivShowImg03, imageBean.getSmallImagePath());
        }
    }

    @Override
    public void uploadImageFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

    @Override
    public void submitAppealSuccess() {
        ToastUtils.showLong(App.getContext(), R.string.toast_submit_appeal_suc);

        Bundle bundle = new Bundle();
        bundle.putInt(AppealingActivity.EXTRA_ORDER_ID, orderId);
        quickStartActivity(AppealingActivity.class, bundle);

        ActivitiesManager.getInstance().finishActivity(SubmitAppealActivity.class);
        ActivitiesManager.getInstance().finishActivity(PaidActivity.class);
    }

    @Override
    public void submitAppealFailed(int code, String msg) {
        ToastUtils.showLong(context, msg);
    }

}
