package oysd.com.trade_app.modules.mycenter.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.App;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.common.InfoProvider;
import oysd.com.trade_app.main.LoginActivity;
import oysd.com.trade_app.main.bean.ImageBean;
import oysd.com.trade_app.modules.mycenter.contract.CertSeniorContract;
import oysd.com.trade_app.modules.mycenter.presenter.CertSeniorPresenter;
import oysd.com.trade_app.modules.mycenter.util.Glide4Engine;
import oysd.com.trade_app.modules.mycenter.util.GifSizeFilter;
import oysd.com.trade_app.util.BitmapUtils;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;

public class CertificationStep2 extends BaseToolActivity implements CertSeniorContract.View {


    @BindView(R.id.certImage1)
    ImageView certImage1;

    @BindView(R.id.certImage2)
    ImageView certImage2;

    @BindView(R.id.certImage3)
    ImageView certImage3;


    @BindView(R.id.certImage4)
    ImageView certImage4;


    @BindView(R.id.submit_id)
    Button submit_id;

    @BindView(R.id.ll_image1)
    LinearLayout ll_image1;

    @BindView(R.id.ll_image2)
    LinearLayout ll_image2;

    @BindView(R.id.ll_image3)
    LinearLayout ll_image3;


    @BindView(R.id.ll_image4)
    LinearLayout ll_image4;

    private static final int REQUEST_CODE_CHOOSE_IMGE1 = 23;
    private static final int REQUEST_CODE_CHOOSE_IMGE2 = 24;
    private static final int REQUEST_CODE_CHOOSE_IMGE3 = 25;
    private static final int REQUEST_CODE_CHOOSE_IMGE4 = 26;


    private int identityFrontImgId, identityBackImgId, identityInHandImgId, identityOtherImgId;

    public String dataBitmap;
    int index = -1;

    CertSeniorContract.Presenter presenter = new CertSeniorPresenter(this);

    @Override
    protected int setView() {
        return R.layout.activity_certification_step2;
    }


    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.advanced_audit);
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        submit_id.setOnClickListener(this);
        certImage1.setOnClickListener(this);
        certImage2.setOnClickListener(this);
        certImage3.setOnClickListener(this);
        certImage4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!Utils.isFastClick()) {
            switch (v.getId()) {
                case R.id.submit_id:
                    requestCertSenior();
                    break;
                case R.id.certImage1:
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
                        Matisse.from(CertificationStep2.this)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "oysd.com.trade_app.fileprovider"))
                                .maxSelectable(1)
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(
                                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                // for glide-V3
                                //                                            .imageEngine(new GlideEngine())
                                // for glide-V4
                                .imageEngine(new Glide4Engine())
                                .setOnSelectedListener(new OnSelectedListener() {
                                    @Override
                                    public void onSelected(
                                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                                    }
                                })
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE_IMGE1);
                    }

                    break;
                case R.id.certImage2:
                    int flag2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //权限判断是否有访问外部存储空间权限
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
                        Matisse.from(CertificationStep2.this)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "oysd.com.trade_app.fileprovider"))
                                .maxSelectable(1)
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(
                                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                // for glide-V3
                                //                                            .imageEngine(new GlideEngine())
                                // for glide-V4
                                .imageEngine(new Glide4Engine())
                                .setOnSelectedListener(new OnSelectedListener() {
                                    @Override
                                    public void onSelected(
                                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                                    }
                                })
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE_IMGE2);
                    }
                    break;
                case R.id.certImage3:
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
                        Matisse.from(CertificationStep2.this)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "oysd.com.trade_app.fileprovider"))
                                .maxSelectable(1)
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(
                                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                // for glide-V3
                                //                                            .imageEngine(new GlideEngine())
                                // for glide-V4
                                .imageEngine(new Glide4Engine())
                                .setOnSelectedListener(new OnSelectedListener() {
                                    @Override
                                    public void onSelected(
                                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                                    }
                                })
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE_IMGE3);
                    }
                    break;

                case R.id.certImage4:
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
                        Matisse.from(CertificationStep2.this)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "oysd.com.trade_app.fileprovider"))
                                .maxSelectable(1)
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(
                                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                // for glide-V3
                                //                                            .imageEngine(new GlideEngine())
                                // for glide-V4
                                .imageEngine(new Glide4Engine())
                                .setOnSelectedListener(new OnSelectedListener() {
                                    @Override
                                    public void onSelected(
                                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                                    }
                                })
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .setOnCheckedListener(new OnCheckedListener() {
                                    @Override
                                    public void onCheck(boolean isChecked) {
                                        // DO SOMETHING IMMEDIATELY HERE
                                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                    }
                                })
                                .forResult(REQUEST_CODE_CHOOSE_IMGE4);
                    }
                    break;
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();

    }

    public void requestCertSenior() {
        Map<String, Object> params = new HashMap<>();
        params.put("identityFrontImgId", identityFrontImgId);
        params.put("identityBackImgId", identityBackImgId);
        params.put("identityInHandImgId", identityInHandImgId);
        params.put("identityOtherImgId", identityOtherImgId);
        presenter.setCertSenior(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图片压缩到1M以内URI
        if (data != null) {
            Bitmap bm = BitmapUtils.getimage(Matisse.obtainPathResult(data).get(0));
            File file = compressImage(bm);
//            bm=fileToBitmap(file);
//            im_id.setImageBitmap(bm);
            if (requestCode == REQUEST_CODE_CHOOSE_IMGE1 && resultCode == RESULT_OK) {
                if (file != null) {
                    index = 1;
                    presenter.upImage(file);
                    dataBitmap = Matisse.obtainPathResult(data).get(0);
                }

            } else if (requestCode == REQUEST_CODE_CHOOSE_IMGE2 && resultCode == RESULT_OK) {
                if (file != null) {
                    index = 2;
                    presenter.upImage(file);
                    dataBitmap = Matisse.obtainPathResult(data).get(0);
                }
            } else if (requestCode == REQUEST_CODE_CHOOSE_IMGE3 && resultCode == RESULT_OK) {
                if (file != null) {
                    index = 3;
                    presenter.upImage(file);
                    dataBitmap = Matisse.obtainPathResult(data).get(0);
                }
            } else if (requestCode == REQUEST_CODE_CHOOSE_IMGE4 && resultCode == RESULT_OK) {
                if (file != null) {
                    index = 4;
                    presenter.upImage(file);
                    dataBitmap = Matisse.obtainPathResult(data).get(0);
                }
            }
        }
    }

//    public Bitmap fileToBitmap(File file) {
//        Uri uri = Uri.fromFile(file);
//        try {
//            if (uri !=null)
//            {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                return bitmap;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//            Log.e(this.getClass().getName(), e.toString());
//        }
//        return null;
//    }


    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1000) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    @Override
    protected void clickBack() {
        super.clickBack();
        setResult(RESULT_OK);
        if (!InfoProvider.getInstance().getLogin()) {
            quickStartActivity(LoginActivity.class);
        } else {
            this.finish();
        }
    }

    @Override
    public void certSeniorSuccess() {
        ActivitiesManager.getInstance().finishActivity(CertificationStep2.class);
        quickStartActivity(CertificationStep3.class);
    }

    @Override
    public void certSeniorFailed() {

    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public void upImageSuccess(ImageBean img) {
        ToastUtils.showShort(App.getContext(), getResources().getString(R.string.message16));
        if (certImage1 == null) return;
        if (index == 1) {
            Bitmap bm = BitmapUtils.getimage(dataBitmap);
            Drawable drawable = new BitmapDrawable(bm);
            ll_image1.setBackground(drawable);
            identityFrontImgId = img.getImageId();
        } else if (index == 2) {
            Bitmap bm = BitmapUtils.getimage(dataBitmap);
            Drawable drawable = new BitmapDrawable(bm);
            ll_image2.setBackground(drawable);
            identityBackImgId = img.getImageId();
        } else if (index == 3) {
            Bitmap bm = BitmapUtils.getimage(dataBitmap);
            Drawable drawable = new BitmapDrawable(bm);
            ll_image3.setBackground(drawable);
            identityInHandImgId = img.getImageId();
        } else if (index == 4) {
            Bitmap bm = BitmapUtils.getimage(dataBitmap);
            Drawable drawable = new BitmapDrawable(bm);
            ll_image4.setBackground(drawable);
            identityOtherImgId = img.getImageId();
        }

    }

    @Override
    public void upImageFail(int code, String msg) {

        ToastUtils.showLong(this, msg);
    }

}