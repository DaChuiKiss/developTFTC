package com.github.tifezh.kchartlib.chart.formatter;

import com.github.tifezh.kchartlib.chart.base.IValueFormatter;

import java.util.Locale;

/**
 * 对较大数据进行格式化
 * Created by tifezh on 2017/12/13.
 */

public class BigValueFormatter implements IValueFormatter{

    //必须是排好序的
    private int[] values={1000,1000000};
    private String[] units={"k","m"};

    @Override
    public String format(double value) {
        String unit="";
        int i=values.length-1;
        while (i>=0)
        {
            if(value>values[i]) {
                value /= values[i];
                unit = units[i];
                break;
            }
            i--;
        }
        String s =String.format(Locale.getDefault(),"%.8f", value);
        if(s.indexOf(".") > 0){
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s+unit;
    }
}
