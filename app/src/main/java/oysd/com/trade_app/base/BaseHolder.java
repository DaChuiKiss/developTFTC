package oysd.com.trade_app.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashSet;

/**
 * @author houmingkuan
 * @time 2018/7/31
 * @desc Base view holder of all adapter of RecyclerView.
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private View itemView;
    // All views indexed with their Ids.
    private SparseArray<View> views;

    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> childLongClickViewIds;

    public BaseHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.views = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.childLongClickViewIds = new LinkedHashSet<>();
    }

    /**
     * An easy way to get a BaseHolder.
     *
     * @param parent   Parent Layout
     * @param layoutId Layout resource Id
     * @param <T>      View type
     * @return BaseHolder instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseHolder> T getHolder(@NonNull ViewGroup parent,
                                                     @LayoutRes int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return (T) new BaseHolder(view);
    }

    /**
     * @return the itemView.
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * @return childClickViewIds.
     */
    public LinkedHashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    /**
     * @return childLongClickViewIds.
     */
    public LinkedHashSet<Integer> getChildLongClickViewIds() {
        return childLongClickViewIds;
    }

    /**
     * Gets a view corresponding to the specified ViewId.
     *
     * @return view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Sets the text of a TextView.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setText(@IdRes int viewId, @NonNull CharSequence sequence) {
        TextView textView = getView(viewId);
        textView.setText(sequence);
        return this;
    }

    /**
     * Sets the text of a TextView with a string resource Id.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setText(@IdRes int viewId, @StringRes int resId) {
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    /**
     * Sets the text color of a TextView.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return null;
    }

    /**
     * Sets the text of a Button.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setButtonText(@IdRes int viewId, @NonNull CharSequence sequence) {
        Button button = getView(viewId);
        button.setText(sequence);
        return this;
    }

    /**
     * Sets the text of a Button with a string resource Id.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setButtonText(@IdRes int viewId, @StringRes int resId) {
        Button button = getView(viewId);
        button.setText(resId);
        return this;
    }

    /**
     * Sets the image of a ImageView with a Image resource Id.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * Sets the image of a ImageView with a Bitmap object.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setImageBitmap(@IdRes int viewId, @NonNull Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Sets the image of a ImageView with a Drawable object.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setImageDrawable(@IdRes int viewId, @NonNull Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * Sets the background color of a view .
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Sets the background of a view with a Drawable resource Id.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;

    }

    /**
     * Sets the background of a view with a Drawable resource Id.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setdrawRightImg(@IdRes int viewId, @NonNull Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        TextView view = getView(viewId);
        view.setCompoundDrawables(null, null, drawable, null);
        return this;

    }


    public LinearLayout isVisable(@IdRes int viewId) {
        LinearLayout ll = getView(viewId);
        return ll;

    }

    /**
     * Sets the visibility of a view.
     *
     * @param viewId View Id
     * @param gone   true : Gone; false : Visible
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setGone(@IdRes int viewId, boolean gone) {
        View view = getView(viewId);
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
        return this;
    }

    /**
     * Sets the visibility of a view.
     *
     * @param viewId  View Id
     * @param visible true : Visible ; false : Invisible
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Sets OnClickListener of a view .
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnClickListener(@IdRes int viewId,
                                         @NonNull View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * Sets OnLongClickListener of a view.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnLongClickListener(@IdRes int viewId,
                                             @NonNull View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * Sets OnTouchListener of a view.
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnTouchListener(@IdRes int viewId,
                                         @NonNull View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * Sets OnItemClickListener of items
     * (only for AdapterView, such as ListView / Spinner and so on)
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnItemClickListener(@IdRes int viewId,
                                             @NonNull AdapterView.OnItemClickListener listener) {
        AdapterView adapterView = getView(viewId);
        adapterView.setOnItemClickListener(listener);
        return this;
    }

    /**
     * Sets OnItemLongClickListener of items
     * (only for AdapterView, such as ListView / Spinner and so on)
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnItemLongClickListener(@IdRes int viewId,
                                                 @NonNull AdapterView.OnItemLongClickListener listener) {
        AdapterView adapterView = getView(viewId);
        adapterView.setOnItemLongClickListener(listener);
        return this;
    }

    /**
     * Sets OnItemSelectedListener of items
     * (only for AdapterView, such as ListView / Spinner and so on)
     *
     * @return This BaseHolder for chain use.
     */
    public BaseHolder setOnItemSelectedListener(@IdRes int viewId,
                                                @NonNull AdapterView.OnItemSelectedListener listener) {
        AdapterView adapterView = getView(viewId);
        adapterView.setOnItemSelectedListener(listener);
        return this;
    }

}
