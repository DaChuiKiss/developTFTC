package oysd.com.trade_app.modules.mycenter.presenter;

import java.util.Map;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.DataObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.EmptyBean;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.modules.mycenter.bean.VipCardPriceBean;
import oysd.com.trade_app.modules.mycenter.bean.VipLevelConfigBean;
import oysd.com.trade_app.modules.mycenter.contract.MemberPurchaseContract;
import oysd.com.trade_app.modules.mycenter.http.MycenterApi;
import oysd.com.trade_app.util.Utils;

/**
 * Created by Liam on 2018/9/20.
 */
public class MemberPurchasePresenter implements MemberPurchaseContract.Presenter {

    private MemberPurchaseContract.View view;

    public MemberPurchasePresenter(MemberPurchaseContract.View view) {
        this.view = view;
    }

    @Override
    public void getVipLevelConfigs() {
        RetrofitHelper.createApi(MycenterApi.class)
                .getVipLevelConfigs()
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<VipLevelConfigBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<VipLevelConfigBean> response) {
                        view.getVipLevelConfigsSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getVipLevelConfigsFailed(code, msg);
                    }
                });
    }

    @Override
    public void getVipCardPrices(int levelId, int cardId) {
        RetrofitHelper.createApi(MycenterApi.class)
                .getVipCardPrices(levelId, cardId)
                .compose(RxSchedulers.ioMain())
                .subscribe(new BaseObserver<ResponseList<VipCardPriceBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<VipCardPriceBean> response) {
                        view.getVipCardPricesSuccess(response.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getVipCardPricesFailed(code, msg);
                    }
                });
    }

    @Override
    public void userPurchaseVip(Map<String, Object> params) {
        RetrofitHelper.createApi(MycenterApi.class)
                .userPurchaseVip(Utils.mapToBody(params))
                .compose(RxSchedulers.ioMain())
                .subscribe(new DataObserver<EmptyBean>(App.getContext()) {
                    @Override
                    public void onSuccess(EmptyBean response) {
                        view.userPurchaseVipSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.userPurchaseVipFailed(code, msg);
                    }
                });
    }

    @Override
    public void attachView(MemberPurchaseContract.View view) {
    }

    @Override
    public void detachView() {
    }

}
