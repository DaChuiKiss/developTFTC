package oysd.com.trade_app.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.modules.mycenter.view.CertificationStep1;

/**
 * OptionsPicker
 * Created by Liam on 2018/8/20
 */
public final class PickerUtils {

    private PickerUtils() {
        // Prevents from being instantiated.
    }

    /**
     * 返回一个单个选项的 PickerView 。
     *
     * @param context  Context
     * @param listener OnOptionSelectListener
     * @param <T>      T
     * @return OptionPickerView
     */
    public static <T> OptionsPickerView<T> getOneOptionPicker(@NonNull Context context,
                                                              @NonNull OnOptionsSelectListener listener) {
        return getOneOptionPicker(context, null, listener);
    }

    /**
     * 返回一个单个选项的 PickerView 。
     *
     * @param context  Context
     * @param list     List
     * @param listener OnOptionSelectListener
     * @param <T>      T
     * @return OptionPickerView
     */
    public static <T> OptionsPickerView<T> getOneOptionPicker(@NonNull Context context,
                                                              @Nullable List<T> list,
                                                              @NonNull OnOptionsSelectListener listener) {
        // build config.
        OptionsPickerView<T> pickerView = new OptionsPickerBuilder(context, listener)
                .setCancelColor(ContextCompat.getColor(context, R.color.text_grey))
                .build();
        if (EmptyUtils.isNotEmpty(list)) pickerView.setPicker(list);
        return pickerView;
    }

    /**
     * 展示一个单个选项的 PickerView 。
     *
     * @param context  Context
     * @param list     List
     * @param listener OnOptionSelectListener
     * @param <T>      T
     */
    public static <T> void showOneOptionPicker(@NonNull Context context,
                                               @NonNull List<T> list,
                                               @NonNull OnOptionsSelectListener listener) {
        getOneOptionPicker(context, list, listener).show();
    }

}
