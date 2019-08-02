package oysd.com.trade_app.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import oysd.com.trade_app.util.EmptyUtils;

/**
 * @author houmingkuan
 * @time 2018/7/31
 * @desc Base adapter for RecyclerView or ListView.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected Context context;
    protected int layoutId;
    protected List<T> dataSet;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    /**
     * Callback for item click action.
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * Callback for item long click action.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public BaseAdapter(@NonNull Context context, @LayoutRes int layoutId) {
        this(context, layoutId, null);
    }

    public BaseAdapter(@NonNull Context context, @LayoutRes int layoutId,
                       @Nullable List<T> dataSet) {
        this.context = context;
        this.layoutId = layoutId;
        this.dataSet = dataSet == null ? new ArrayList<>() : dataSet;
    }

    /**
     * Sets item click listener for this adapter.
     *
     * @param onItemClickListener OnItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Sets item long click listener for this adapter.
     *
     * @param onItemLongClickListener OnItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    @NonNull
    public List<T> getDataSet() {
        return dataSet;
    }

    /**
     * Sets new data set.
     */
    public void setDataSet(@Nullable List<T> dataSet) {
        if (dataSet == null) return;
        this.dataSet = dataSet;
    }

    /**
     * Adds data set into this.
     */
    public void addDataSet(@Nullable List<T> dataSet) {
        if (EmptyUtils.isEmpty(dataSet)) return;
        this.dataSet.addAll(dataSet);
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseHolder.getHolder(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        bindListeners(holder, position);
        onBind(holder, dataSet.get(position), position);
    }

    // Binds listeners to this holder.
    private void bindListeners(final BaseHolder holder, final int position) {
        final View view = holder.getItemView();

        if (onItemClickListener != null) {
            view.setOnClickListener(v -> onItemClickListener.onItemClick(view, position));
        }

        if (onItemLongClickListener != null) {
            view.setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClick(view, position);
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    /**
     * SubAdapter 需要实现的数据绑定。
     */
    protected abstract void onBind(BaseHolder holder, T t, int position);

}
