package oysd.com.trade_app.main;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import oysd.com.trade_app.ActivitiesManager;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;
import oysd.com.trade_app.http.bean.ResponseEntity;
import oysd.com.trade_app.http.bean.ResponseList;
import oysd.com.trade_app.main.Tools.CharacterParserUtil;
import oysd.com.trade_app.main.Tools.CountryComparator;
import oysd.com.trade_app.main.Tools.CountrySortAdapter;
import oysd.com.trade_app.main.Tools.CountrySortModel;
import oysd.com.trade_app.main.Tools.GetCountryNameSort;
import oysd.com.trade_app.main.Tools.SideBar;
import oysd.com.trade_app.main.bean.CountryPhoneBean;
import oysd.com.trade_app.main.contract.GetCountryPhoneContract;
import oysd.com.trade_app.main.presenter.GetCountryPhoneAllPresenter;
import oysd.com.trade_app.modules.trade.bean.SocketRecord;
import oysd.com.trade_app.util.ACache;
import oysd.com.trade_app.util.ToastUtils;
import oysd.com.trade_app.util.Utils;


/**
 * 类简要描述
 * <p/>
 * <p>
 * 类详细描述
 * </p>
 *
 * @author duanbokan
 */
public class CountryActivity extends BaseToolActivity implements GetCountryPhoneContract.View{

    String TAG = "CountryActivity";

    private List<CountrySortModel> mAllCountryList;

    @BindView(R.id.country_et_search)
    EditText country_edt_search;

    @BindView(R.id.country_lv_list)
    ListView country_lv_countryList;

//    @BindView(R.id.country_iv_cleartext)
////    ImageView country_iv_clearText;

    private CountrySortAdapter adapter;

    @BindView(R.id.country_sidebar)
     SideBar sideBar;

    @BindView(R.id.country_dialog)
    TextView dialog;

    @BindView(R.id.tv_country_back)
    TextView tv_country_back;

    private ACache mAcache;
    private CountryComparator pinyinComparator;

    private GetCountryNameSort countryChangeUtil;

    private CharacterParserUtil characterParserUtil;
    GetCountryPhoneContract.Presenter presenter =new GetCountryPhoneAllPresenter(this);

    @Override
    protected int setView() {
        return R.layout.coogame_country;
    }

    @Override
    protected  void initData(){
        setListener();
        mAcache=ACache.get(this);
        String sResult=null;
        if(Utils.isChina()) {
            sResult = mAcache.getAsString("countryListCN");
        }else{
            sResult = mAcache.getAsString("countryListEN");
        }
        if (sResult!=null){
            Gson json =new Gson();
            List<CountryPhoneBean> data =json.fromJson(sResult,new TypeToken<List<CountryPhoneBean>>() {
            }.getType());
            mAllCountryList.clear();
            for(int i=0;i<data.size();i++){
                String countryName=data.get(i).getName();
                String countryNumber ="+"+data.get(i).getPrefix();
                int phoneLength =data.get(i).getPhoneLength();
                int countryId =data.get(i).getId();
                String countryCode =data.get(i).getCountryCode();
                String countrySortKey = characterParserUtil.getSelling(countryName);
                CountrySortModel countrySortModel = new CountrySortModel(countryName, countryNumber,
                        countrySortKey,phoneLength,countryId,countryCode);
                String sortLetter = countryChangeUtil.getSortLetterBySortKey(countrySortKey);
                if (sortLetter == null) {
                    sortLetter = countryChangeUtil.getSortLetterBySortKey(countryName);
                }

                countrySortModel.sortLetters = sortLetter;
                mAllCountryList.add(countrySortModel);
            }

            Collections.sort(mAllCountryList, pinyinComparator);
            adapter.updateListView(mAllCountryList);
            Log.e(TAG, "changdu" + mAllCountryList.size());
        }else {
            requestGetCountryPhoneAll();
        }
    }

    @Override
    protected void initClicks() {
        super.initClicks();
        tv_country_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_country_back:
                ActivitiesManager.getInstance().finishActivity();
                break;
        }
    }

    public void requestGetCountryPhoneAll(){
        presenter.getCountryPhoneAll();
    }
    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        sideBar.setTextView(dialog);
        mAllCountryList = new ArrayList<CountrySortModel>();
        pinyinComparator = new CountryComparator();
        countryChangeUtil = new GetCountryNameSort();
        characterParserUtil = new CharacterParserUtil();

        // 将联系人进行排序，按照A~Z的顺序
        Collections.sort(mAllCountryList, pinyinComparator);
        adapter = new CountrySortAdapter(this, mAllCountryList);
        country_lv_countryList.setAdapter(adapter);

    }

    /****
     * 添加监听
     */
    private void setListener() {
//        country_iv_clearText.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                country_edt_search.setText("");
//                Collections.sort(mAllCountryList, pinyinComparator);
//                adapter.updateListView(mAllCountryList);
//            }
//        });

        country_edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchContent = country_edt_search.getText().toString();
//                if (searchContent.equals("")) {
//                    country_iv_clearText.setVisibility(View.INVISIBLE);
//                } else {
//                    country_iv_clearText.setVisibility(View.VISIBLE);
//                }

                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(searchContent, mAllCountryList);

                    adapter.updateListView(fileterList);
                } else {
                    adapter.updateListView(mAllCountryList);
                }
                country_lv_countryList.setSelection(0);
            }
        });

        // 右侧sideBar监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    country_lv_countryList.setSelection(position);
                }
            }
        });

        country_lv_countryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                String countryName = null;
                String countryNumber = null;
                int phoneLength;
                int countryId;
                String  countryCode;
                String searchContent = country_edt_search.getText().toString();

                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(searchContent, mAllCountryList);
                    countryName = fileterList.get(position).countryName;
                    countryNumber = fileterList.get(position).countryNumber;
                    phoneLength = fileterList.get(position).phoneLength;
                    countryId = fileterList.get(position).countryId;
                    countryCode=fileterList.get(position).countryCode;
                } else {
                    // 点击后返回
                    countryName = mAllCountryList.get(position).countryName;
                    countryNumber = mAllCountryList.get(position).countryNumber;
                    phoneLength = mAllCountryList.get(position).phoneLength;
                    countryId = mAllCountryList.get(position).countryId;
                    countryCode=mAllCountryList.get(position).countryCode;
                }

                Intent intent = new Intent();
                intent.setClass(CountryActivity.this, RegisterActivity.class);
                intent.putExtra("countryName", countryName);
                intent.putExtra("countryNumber", countryNumber);
                intent.putExtra("phoneLength", phoneLength);
                intent.putExtra("countryId", countryId);
                intent.putExtra("countryCode",countryCode);
                setResult(RESULT_OK, intent);
                Log.e(TAG, "countryName: + " + countryName + "countryNumber: " + countryNumber);
                finish();

            }
        });

    }

    /**
     * 获取国家列表
     */
    private void getCountryList(ResponseList<CountryPhoneBean> countryPhoneBeanList) {

        if(adapter==null)return;

        List<CountryPhoneBean> data =countryPhoneBeanList.getData();
        Gson gson =new Gson();
        if(Utils.isChina()) {
            mAcache.put("countryListCN",gson.toJson(data),60*60*24);
        }else{
            mAcache.put("countryListEN",gson.toJson(data),60*60*24);
        }

        for(int i=0;i<data.size();i++){
            String countryName=data.get(i).getName();
            String countryNumber ="+"+data.get(i).getPrefix();
            int phoneLength =data.get(i).getPhoneLength();
            int countryId =data.get(i).getId();
            String countryCode =data.get(i).getCountryCode();
            String countrySortKey = characterParserUtil.getSelling(countryName);
            CountrySortModel countrySortModel = new CountrySortModel(countryName, countryNumber,
                    countrySortKey,phoneLength,countryId,countryCode);
            String sortLetter = countryChangeUtil.getSortLetterBySortKey(countrySortKey);
            if (sortLetter == null) {
                sortLetter = countryChangeUtil.getSortLetterBySortKey(countryName);
            }

            countrySortModel.sortLetters = sortLetter;
            mAllCountryList.add(countrySortModel);
        }

        Collections.sort(mAllCountryList, pinyinComparator);
        adapter.updateListView(mAllCountryList);
        Log.e(TAG, "changdu" + mAllCountryList.size());
    }

    @Override
    public void getCountryPhoneAllSuccess(ResponseList<CountryPhoneBean> countryPhoneBeanList) {
        getCountryList(countryPhoneBeanList);
    }

    @Override
    public void getCountryPhoneAllFail(int code,String msg) {
        ToastUtils.showLong(CountryActivity.this,msg);
    }
}
