package oysd.com.trade_app.modules.trade.adapter;

import com.github.tifezh.kchartlib.chart.BaseKChartAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oysd.com.trade_app.modules.trade.bean.KLineEntity;
import oysd.com.trade_app.util.LoadingImgDialog;


/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */

public class KChartAdapter extends BaseKChartAdapter {

    public List<KLineEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<KLineEntity> datas) {
        this.datas = datas;
    }

    private List<KLineEntity> datas = new ArrayList<>();

    public KChartAdapter() {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public Date getDate(int position) {
        try {
            String s = datas.get(position).Date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = format.parse(s);
            return date;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向头部添加数据
     */
    public void addHeaderData(List<KLineEntity> data) {
        if (data != null && !data.isEmpty()) {
            datas.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 向尾部添加数据
     */
    public void addFooterData(List<KLineEntity> data,boolean isObject,LoadingImgDialog loadingColorDialog) {

        if (data != null && !data.isEmpty()) {
            if(!isObject)
            {
                datas.clear();
                datas.addAll(data);
            }else{
              List<KLineEntity> tempDatas = new ArrayList<>();
                tempDatas.addAll(datas);
                tempDatas.remove(tempDatas.size()-1);
                datas.clear();
                datas.addAll(tempDatas);
                datas.addAll(data);
            }
            notifyDataSetChanged();
        }
        if(loadingColorDialog!=null&&loadingColorDialog.isShowing()){
            loadingColorDialog.dismiss();
        }
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLineEntity data) {
        datas.set(position, data);
        notifyDataSetChanged();
    }

}
