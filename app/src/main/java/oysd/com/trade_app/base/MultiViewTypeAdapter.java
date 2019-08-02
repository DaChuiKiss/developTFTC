package oysd.com.trade_app.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author houmingkuan
 * @time 2018/9/5
 * @desc Multi ViewType adapter.
 * Supports a fixed header and empty view.
 */
public abstract class MultiViewTypeAdapter<T> extends BaseAdapter<T> {


    public MultiViewTypeAdapter(@NonNull Context context, int layoutId) {
        super(context, layoutId);
    }

    public MultiViewTypeAdapter(@NonNull Context context, int layoutId, @Nullable List<T> dataSet) {
        super(context, layoutId, dataSet);
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }


}
