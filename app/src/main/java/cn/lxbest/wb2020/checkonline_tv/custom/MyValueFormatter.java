package cn.lxbest.wb2020.checkonline_tv.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;

public class MyValueFormatter extends ValueFormatter
{

    private DecimalFormat mFormat = null;
    private String suffix;

    public MyValueFormatter(String suffix) {
        mFormat = new DecimalFormat("#");
        this.suffix = suffix;
    }


    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        if (axis instanceof XAxis) {
//            return mFormat.format(value) + this.suffix;
            String a1 = String.valueOf(value);
            List<String> l1 = Arrays.asList(a1.split("\\."));
            return l1.get(0) + ":" + (l1.size()>1 ? (l1.get(1).length()<2?"0"+l1.get(1):l1.get(1)) : "00");
        } else if (value > 0) {
            return mFormat.format(value) + suffix;
        } else {
            return mFormat.format(value);
        }
    }
}
