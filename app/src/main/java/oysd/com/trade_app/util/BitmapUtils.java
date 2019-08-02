package oysd.com.trade_app.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bitmap utils.
 * Created by Liam on 2017/12/7.
 */

public final class BitmapUtils {

    private BitmapUtils() {
        // prevent from being instantiating.
    }

    /**
     * Compresses image by pixel, this will modify width/height.
     * Used to get thumbnail.
     *
     * @param context Context
     * @param imgUri  Uri of specified image
     * @param wPixel  Target pixel of width
     * @param hPixel  Target pixel of height
     * @return Bitmap
     * @throws IOException IOException
     */
    public static Bitmap getRatioBitmap(Context context, Uri imgUri, int wPixel, int hPixel)
            throws IOException {

        BitmapFactory.Options boundsOptions = new BitmapFactory.Options();
        boundsOptions.inJustDecodeBounds = true;
        // default : Bitmap.Config.ARGB_8888
        boundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        InputStream inputStream = context.getContentResolver().openInputStream(imgUri);
        BitmapFactory.decodeStream(inputStream, null, boundsOptions);
        if (inputStream != null) inputStream.close();

        int w = boundsOptions.outWidth;
        int h = boundsOptions.outHeight;
        int scale = getScale(w, h, wPixel, hPixel);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        // options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = scale;
        inputStream = context.getContentResolver().openInputStream(imgUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        if (inputStream != null) inputStream.close();

        return bitmap;
    }

    public static Bitmap getRatioBitmap(String imgPath, float wPixel, float hPixel) {

        return null;
    }

    /**
     * Gets the compression ratio of this Bitmap.
     *
     * @param wOriginal Original width
     * @param hOriginal Original height
     * @param wTarget   Target width
     * @param hTarget   Target height
     * @return ratio
     */
    private static int getScale(int wOriginal, int hOriginal, int wTarget, int hTarget) {
        int scale = 1;
        if (wTarget >= wOriginal && hTarget >= hOriginal) return scale;

        int wRatio = (wOriginal % wTarget) == 0 ? wOriginal / wTarget : wOriginal / wTarget + 1;
        int hRatio = (hOriginal % hTarget) == 0 ? hOriginal / hTarget : hOriginal / hTarget + 1;

        scale = wRatio > hRatio ? wRatio : hRatio;
        return scale;
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 400f;// 这里设置高度为800f
        float ww = 240f;// 这里设置宽度为480f

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放

        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }

        if (be <= 0)
            be = 1;

        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存 bitmap 到相册。
     *
     * @return {@code true} if success, otherwise {@code false}.
     */
    public static boolean saveBitmapToGallery(@NonNull Context context, @NonNull Bitmap bitmap) {
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "screenshot";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            // 把文件插入到系统图库
            // MediaStore.Images.Media.insertImage(context.getContentResolver(),
            //         file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
