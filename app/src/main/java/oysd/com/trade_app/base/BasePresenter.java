package oysd.com.trade_app.base;

/**
 * @author houmingkuan
 * @time 2018/7/31
 * @desc Mvp base presenter.
 */
public interface BasePresenter<T> {

    void attachView(T view);

    void detachView();

}
