package oysd.com.trade_app.widget;

import oysd.com.trade_app.base.BaseFragment;
import oysd.com.trade_app.base.LazyLoadBaseFragment;

/**
 * Created by yx on 16/4/3.
 */
public class TabItem {

    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int labelResId;

    public String count;

    public Class<? extends LazyLoadBaseFragment> tagFragmentClz;

    public TabItem(int imageResId, int labelResId) {
        this.imageResId = imageResId;
        this.labelResId = labelResId;
    }

    public TabItem(int imageResId, int labelResId, Class<? extends LazyLoadBaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.labelResId = labelResId;
        this.tagFragmentClz = tagFragmentClz;
    }

}
