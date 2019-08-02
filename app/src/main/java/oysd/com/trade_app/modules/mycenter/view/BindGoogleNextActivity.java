package oysd.com.trade_app.modules.mycenter.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import oysd.com.trade_app.R;
import oysd.com.trade_app.base.BaseToolActivity;

public class BindGoogleNextActivity extends BaseToolActivity {

    @BindView(R.id.et_mycenter_google_pwd1)
    EditText et_mycenter_google_pwd1;

    @BindView(R.id.et_mycenter_google_pwd2)
    EditText et_mycenter_google_pwd2;

    @BindView(R.id.et_mycenter_google_pwd3)
    EditText et_mycenter_google_pwd3;

    @BindView(R.id.et_mycenter_google_pwd4)
    EditText et_mycenter_google_pwd4;

    @BindView(R.id.et_mycenter_google_pwd5)
    EditText et_mycenter_google_pwd5;

    @BindView(R.id.et_mycenter_google_pwd6)
    EditText et_mycenter_google_pwd6;

    List<EditText> edList;
    int index=0;

    @Override
    protected int setView() {
        return R.layout.activity_bind_google_next;
    }



    @Override
    protected void initView() {
        super.initView();
        setTitleText(R.string.bindGoogle);
        Timer timer=new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                InputMethodManager inputManager=(InputMethodManager)et_mycenter_google_pwd1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_mycenter_google_pwd1,0);
            }

        },200);
    }

    @Override
    protected void initData() {
        super.initData();
        edList =new ArrayList<EditText>();
        edList.add(et_mycenter_google_pwd1);
        edList.add(et_mycenter_google_pwd2);
        edList.add(et_mycenter_google_pwd3);
        edList.add(et_mycenter_google_pwd4);
        edList.add(et_mycenter_google_pwd5);
        edList.add(et_mycenter_google_pwd6);
        et_mycenter_google_pwd1.addTextChangedListener(new EditChangedListener());
        et_mycenter_google_pwd2.addTextChangedListener(new EditChangedListener());
        et_mycenter_google_pwd3.addTextChangedListener(new EditChangedListener());
        et_mycenter_google_pwd4.addTextChangedListener(new EditChangedListener());
        et_mycenter_google_pwd5.addTextChangedListener(new EditChangedListener());
        et_mycenter_google_pwd6.addTextChangedListener(new EditChangedListener());


    }



    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.length()==1){
                index++;
                if(index<6) {
                    edList.get(index).requestFocus();
                    edList.get(index).setSelection(edList.get(index).getText().length());//若editText
                }else{
                    index=5;
                }
            }else if(s.length()==0){
                index--;
                Log.d("index",index+"");
                if(index>=0) {
                    edList.get(index).requestFocus();
                    edList.get(index).setSelection(edList.get(index).getText().length());//若editText
                }else{
                    index=0;
                }

            }
            }
        }
}
