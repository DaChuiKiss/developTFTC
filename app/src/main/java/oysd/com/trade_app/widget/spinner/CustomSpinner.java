package oysd.com.trade_app.widget.spinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liam on 2018/8/15
 */
public class CustomSpinner<T> extends AppCompatSpinner {

    private Context context;
    private List<T> dataSet;

    // a instance for settings.
    private CustomSpinner customSpinner;
    private ArrayAdapter<T> adapter;

    public CustomSpinner(@NonNull Context context) {
        this(context, null);
    }
    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, null);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs,
                         @Nullable List<T> dataSet) {
        super(context, attrs);
        this.context = context;
        this.dataSet = dataSet == null ? new ArrayList<>() : dataSet;

        customSpinner = this;

        setCustomSpinner();
    }

    private void setCustomSpinner() {
        // customSpinner.setBackgroundColor(0x0);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dataSet);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customSpinner.setAdapter(adapter);
    }

    public void setDataSet(List<T> dataSet) {
        this.dataSet = dataSet;
        if (adapter != null) {
            adapter.addAll(dataSet);
            adapter.notifyDataSetChanged();
        }
    }


}
