package oysd.com.trade_app.main.view;

/**
 * Created by ouyangshengduo on 2016/9/12.
 */
public interface UserView {

    void regiestSuccess(byte[] data);
    void showProgressDialog();
    void hideProgressDialog();
    void showError(String msg);
    void loginSuccess(byte[] data);

}
