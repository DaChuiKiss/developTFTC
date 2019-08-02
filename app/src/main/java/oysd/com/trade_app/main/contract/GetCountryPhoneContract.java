package oysd.com.trade_app.main.contract;


import java.util.Map;

import oysd.com.trade_app.base.BasePresenter;
import oysd.com.trade_app.base.BaseView;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.bean.CountryPhoneBean;

/**
 * 主要功能，实现了获取国家手机号的接口类
 */
public interface GetCountryPhoneContract {

    interface Presenter extends BasePresenter<GetCountryPhoneContract.View> {
        void getCountryPhoneAll();

    }

    interface View extends BaseView<GetCountryPhoneContract.Presenter> {

        void getCountryPhoneAllSuccess(ResponseList<CountryPhoneBean> countryPhoneBeanList);
        void getCountryPhoneAllFail(int code,String msg);
    }

}
