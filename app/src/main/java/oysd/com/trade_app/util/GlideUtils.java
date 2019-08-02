package oysd.com.trade_app.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * OtcUtils method for Glide.
 * Created by Liam on 2018/8/23.
 */
public final class GlideUtils {

    private GlideUtils() {
        // Prevents from being instantiated.
    }

    /**
     * 最基本的 load image.
     */
    public static void loadImage(@NonNull Context context, @NonNull ImageView imageView,
                                 String path) {
        Glide.with(context).load(path)
                .into(imageView);
    }

    /**
     * 加载图片：带占位图和 error 图。
     */
    public static void loadImage(@NonNull Context context, @NonNull ImageView imageView,
                                 String path, int placeHolderImage, int errorImage) {
        Glide.with(context).load(path)
                .apply(RequestOptions.placeholderOf(placeHolderImage).error(errorImage))
                .into(imageView);
    }

    /**
     * 加载图片：指定大小.
     */
    public static void loadImageSize(@NonNull Context context, @NonNull ImageView imageView,
                                     String path, int width, int height) {
        Glide.with(context).load(path)
                .apply(RequestOptions.overrideOf(width, height))
                .into(imageView);
    }

    /**
     * 加载图片：指定大小，带占位图和 error 图。
     */
    public static void loadImageSize(@NonNull Context context, @NonNull ImageView imageView,
                                     String path, int width, int height,
                                     int placeHolderImage, int errorImage) {
        Glide.with(context).load(path)
                .apply(RequestOptions.overrideOf(width, height)
                        .placeholder(placeHolderImage).error(errorImage))
                .into(imageView);
    }

    /**
     * 加载图片：跳过内存缓存。
     */
    public static void loadImageCache(@NonNull Context context, @NonNull ImageView imageView,
                                      String path) {
        loadImageCache(context, imageView, path, true);
    }

    /**
     * 加载图片：是否缓存内存。
     */
    public static void loadImageCache(@NonNull Context context, @NonNull ImageView imageView,
                                      String path, boolean cacheMemory) {
        Glide.with(context).load(path)
                .apply(RequestOptions.skipMemoryCacheOf(cacheMemory))
                .into(imageView);
    }


}
