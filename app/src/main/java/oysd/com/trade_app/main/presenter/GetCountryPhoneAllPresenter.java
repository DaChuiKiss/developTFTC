package oysd.com.trade_app.main.presenter;

import oysd.com.trade_app.App;
import oysd.com.trade_app.http.BaseObserver;
import oysd.com.trade_app.http.RetrofitHelper;
import oysd.com.trade_app.http.RxSchedulers;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import oysd.com.trade_app.main.contract.GetCountryPhoneContract;
import oysd.com.trade_app.main.http.MainApi;

public class GetCountryPhoneAllPresenter implements GetCountryPhoneContract.Presenter {

    private GetCountryPhoneContract.View view;

    public GetCountryPhoneAllPresenter(GetCountryPhoneContract.View view) {
        this.view = view;
    }

    @Override
    public void getCountryPhoneAll() {
        RetrofitHelper.createApi(MainApi.class)
                .getCountryPhoneAll()
                .compose(RxSchedulers.<ResponseList<CountryPhoneBean>>ioMain())
                .subscribe(new BaseObserver<ResponseList<CountryPhoneBean>>(App.getContext()) {
                    @Override
                    public void onSuccess(ResponseList<CountryPhoneBean> countryPhoneBeanList) {
                        view.getCountryPhoneAllSuccess(countryPhoneBeanList);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        view.getCountryPhoneAllFail(code,msg);
                    }
                });
    }


    @Override
    public void attachView(GetCountryPhoneContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
