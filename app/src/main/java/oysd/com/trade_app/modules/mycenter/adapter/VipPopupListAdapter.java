package oysd.com.trade_app.modules.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import oysd.com.trade_app.R;
import oysd.com.trade_app.util.EmptyUtils;


/**
 * Created by Liam on 2018/9/20.
 */
public class VipPopupListAdapter extends BaseAdapter {

    private Context context;
    private List<String> dataSet;
    // 默认选中的 item 项。
    private int selectedPosition;
    private int showType;
    private LayoutInflater inflater;


    public VipPopupListAdapter(@NonNull Context context) {
        this(context, null);
    }

    public VipPopupListAdapter(Context context, List<String> dataSet) {
        this(context, dataSet, 0);
    }

    public VipPopupListAdapter(@NonNull Context context, @Nullable List<String> dataSet,
                               int selectedPosition) {
        this(context, dataSet, selectedPosition, 1);
    }

    public VipPopupListAdapter(@NonNull Context context, @Nullable List<String> dataSet,
                               int selectedPosition, int showType) {
        this.context = context;
        this.dataSet = dataSet == null ? new ArrayList<>() : dataSet;
        this.selectedPosition = selectedPosition;
        this.showType = showType;
        inflater = LayoutInflater.from(context);
    }

    public void setDataSet(List<String> dataSet) {
        if (dataSet == null) return;
        this.dataSet = dataSet;
    }

    public void addDataSet(List<String> dataSet) {
        if (EmptyUtils.isEmpty(dataSet)) return;

        this.dataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    // 设置当前选中的 item position.
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vip_popup, parent, false);
            ImageView imageView = convertView.findViewById(R.id.iv_vip_popup_icon);
            TextView textView = convertView.findViewById(R.id.tv_vip_popup_text);

            viewHolder = new ViewHolder();
            viewHolder.icon = imageView;
            viewHolder.text = textView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 设置选中的 item 显示 image.
        if (position == selectedPosition) {
            viewHolder.icon.setVisibility(View.VISIBLE);

            if (showType == 1) {
                viewHolder.icon.setImageResource(R.drawable.icon_vip_choice);
                viewHolder.text.setTextColor(ContextCompat.getColor(context, R.color.text_light_yellow));
            } else if (showType == 2) {
                viewHolder.icon.setImageResource(R.drawable.icon_vip_choice_blue);
                viewHolder.text.setTextColor(ContextCompat.getColor(context, R.color.text_blue));
            }

        } else {
            viewHolder.icon.setVisibility(View.INVISIBLE);
            viewHolder.text.setTextColor(ContextCompat.getColor(context, R.color.text_normal));
        }

        viewHolder.text.setText(dataSet.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public ImageView icon;
        public TextView text;
    }

}
