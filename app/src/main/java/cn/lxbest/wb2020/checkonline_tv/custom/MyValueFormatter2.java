package cn.lxbest.wb2020.checkonline_tv.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MyValueFormatter2 extends ValueFormatter
{

    private DecimalFormat mFormat = null;
    private String suffix;

    public MyValueFormatter2(String suffix) {
        mFormat = new DecimalFormat("#");
        this.suffix = suffix;
    }


    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis){
        if (axis instanceof XAxis) {
            return mFormat.format(value) + this.suffix;
        } else if (value > 0) {
            return mFormat.format(value) + suffix;
        } else {
            return mFormat.format(value);
        }
    }
}
