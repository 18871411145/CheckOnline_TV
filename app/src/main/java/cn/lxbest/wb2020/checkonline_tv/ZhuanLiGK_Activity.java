package cn.lxbest.wb2020.checkonline_tv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.lxbest.wb2020.checkonline_tv.Tool.Const;
import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;
import cn.lxbest.wb2020.checkonline_tv.custom.MyValueFormatter;
import cn.lxbest.wb2020.checkonline_tv.custom.MyValueFormatter2;
import cz.msebera.android.httpclient.Header;

public class ZhuanLiGK_Activity extends Activity {

    private PieChart piechart;
    private PieChart pieChart2;
    private BarChart barchart;

    //标题
    TextView text_title;

    //专利种类数据集
    ArrayList<PieEntry> pie_entries = new ArrayList<>();
    //法律状态数据集
    ArrayList<PieEntry> pie2_entries = new ArrayList<>();
    //每月专利数数据集
    ArrayList<BarEntry> bar_entries = new ArrayList<>();

    int bs=1;

    //有关PieChart的设置
    void initPieChart(){

        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setDragDecelerationFrictionCoef(0.95f);

        piechart.setDrawCenterText(true);
        piechart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        piechart.setCenterText("专利种类分布 ");
        piechart.setCenterTextSize(20);



        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(getResources().getColor(R.color.kj_blue));
        piechart.setTransparentCircleColor(Color.WHITE);
        piechart.setTransparentCircleAlpha(110);
        piechart.setHoleRadius(45f);
        piechart.setTransparentCircleRadius(49f);
        piechart.setCenterTextColor(Color.WHITE);

        piechart.setRotationAngle(0);
        piechart.setRotationEnabled(false);
        piechart.setHighlightPerTapEnabled(true);
        piechart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = piechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        piechart.getLegend().setEnabled(false);



        piechart.setDrawEntryLabels(true);
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        piechart.setEntryLabelTextSize(10f);
    }


    void initBarChart(){

        barchart.setScaleEnabled(false);//不能缩放
        barchart.setDrawBarShadow(false);//背景阴影
        barchart.setDrawValueAboveBar(true);
        barchart.getDescription().setEnabled(false);//是否显示描述，表右下文字

        barchart.setMaxVisibleValueCount(60);//最多60个bar

        barchart.setDrawGridBackground(false);


        ValueFormatter xAxisFormatter = new MyValueFormatter2("月");


        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisLineWidth(1f);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setAxisMinimum(1f);

        ValueFormatter custom = new MyValueFormatter2("项");


        YAxis leftAxis = barchart.getAxisLeft();
        leftAxis.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        leftAxis.setDrawGridLines(false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(0);


        YAxis rightAxis = barchart.getAxisRight();
        rightAxis.setEnabled(false);


        Legend l = barchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        //取消左上角图表
        l.setEnabled(false);

    }

    void initPie2Chart(){

        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setDragDecelerationFrictionCoef(0.95f);

        pieChart2.setDrawCenterText(true);
        pieChart2.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart2.setCenterText("法律状态分布");
        pieChart2.setCenterTextSize(20);



        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setHoleColor(getResources().getColor(R.color.kj_blue));
        pieChart2.setTransparentCircleColor(Color.WHITE);
        pieChart2.setTransparentCircleAlpha(110);
        pieChart2.setHoleRadius(45f);
        pieChart2.setTransparentCircleRadius(49f);
        pieChart2.setCenterTextColor(Color.WHITE);

        pieChart2.setRotationAngle(0);
        pieChart2.setRotationEnabled(false);
        pieChart2.setHighlightPerTapEnabled(true);
        pieChart2.animateY(1400, Easing.EaseInOutQuad);

        Legend l = pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart2.getLegend().setEnabled(false);



        pieChart2.setDrawEntryLabels(true);
        pieChart2.setEntryLabelColor(Color.WHITE);
        pieChart2.setEntryLabelTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart2.setEntryLabelTextSize(10f);
    }


    Handler handler=new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanligk_activity);
        piechart=findViewById(R.id.piechart1);
        pieChart2=findViewById(R.id.piechart2);
        barchart=findViewById(R.id.barchart1);
        text_title=findViewById(R.id.title);
        //标题
        text_title.setText(Const.Company);

        initPieChart();
        initBarChart();
        initPie2Chart();

        getData();
        runnable=new Runnable() {
            @Override
            public void run() {
                //过指定时间返回主页
                Intent intent=new Intent(ZhuanLiGK_Activity.this,Home_Activity.class);
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable,1000*60*5);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    //获取展示数据
    private void getData(){
        getZLFLData();
        getFLZTData();
        getBarData();
    }

    //得到专利种类分类饼图数据
    void getZLFLData(){
        pie_entries=new ArrayList<>();
        String url=Funcs.servUrlWQ(Const.Key_Resp_Path.zhuanligk,"type="+1);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                if(jsonObject!=null){
                    parseZLFLData(jsonObject);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(App.env==Const.Env.DEV_TD){

                }else{
                    Funcs.showtoast(ZhuanLiGK_Activity.this,"专利种类分类获取失败");
                }
            }
        });

    }

    void parseZLFLData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    JSONObject js=data.getJSONObject(i);
                    String zlfl=null;
                    int zls=0;
                    if(Funcs.jsonItemValid(js,Const.Field_zhuangli_Table.zlfl)) zlfl=js.getString(Const.Field_zhuangli_Table.zlfl);
                    if(Funcs.jsonItemValid(js,Const.Field_zhuangli_Table.zls)) zls=js.getInt(Const.Field_zhuangli_Table.zls);
                    pie_entries.add(new PieEntry(zls,zlfl+" "+zls));

                }
                addDataToPie();

            }else{
                Funcs.showtoast(this,"专利种类分布获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //得到法律状态分类饼图数据
    void getFLZTData(){
        pie2_entries=new ArrayList<>();
        String url=Funcs.servUrlWQ(Const.Key_Resp_Path.zhuanligk,"type="+2);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                if(jsonObject!=null){
                    parseFLZTData(jsonObject);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(App.env==Const.Env.DEV_TD){

                }else{
                    Funcs.showtoast(ZhuanLiGK_Activity.this,"专利种类分类获取失败");
                }
            }
        });
    }

    void parseFLZTData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    JSONObject js=data.getJSONObject(i);
                    String zlfl=null;
                    int zls=0;
                    if(Funcs.jsonItemValid(js,Const.Field_zhuangli_Table.flzt)) zlfl=js.getString(Const.Field_zhuangli_Table.flzt);
                    if(Funcs.jsonItemValid(js,Const.Field_zhuangli_Table.zls)) zls=js.getInt(Const.Field_zhuangli_Table.zls);
                    pie2_entries.add(new PieEntry(zls,zlfl+" "+zls));

                }
                addDataToPie2();

            }else{
                Funcs.showtoast(this,"专利种类分布获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //得到今年各月专利持有情况柱状图数据
    void getBarData(){
        bar_entries=new ArrayList<>();
        String url=Funcs.servUrlWQ(Const.Key_Resp_Path.zhuanligk,"type="+3);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                if(jsonObject!=null){
                    parseZLZSData(jsonObject);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(App.env==Const.Env.DEV_TD){

                }else{
                    Funcs.showtoast(ZhuanLiGK_Activity.this,"专利种类分类获取失败");
                }
            }
        });
    }

    void parseZLZSData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    JSONObject js=data.getJSONObject(i);
                    int month=0;
                    int zls=0;
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Time.Month)) month=js.getInt(Const.Field_Table_Time.Month);
                    if(Funcs.jsonItemValid(js,Const.Field_zhuangli_Table.zls)) zls=js.getInt(Const.Field_zhuangli_Table.zls);
                    bar_entries.add(new BarEntry(month,zls));

                }
                addDataToBar();

            }else{
                Funcs.showtoast(this,"专利种类分布获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void addDataToPie(){
        PieDataSet dataSet = new PieDataSet(pie_entries, null);
        dataSet.setValueTextSize(10);//同样有效果 piedata设置了会掩盖其效果
        dataSet.setSliceSpace(3f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : Const.COLORFUL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        //对百分比文本设置
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(piechart));//百分比显示
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf"));
        piechart.setData(data);


        piechart.invalidate();
    }

    void addDataToPie2(){
        PieDataSet dataSet = new PieDataSet(pie2_entries, "");
        dataSet.setValueTextSize(10);//同样有效果 piedata设置了会掩盖其效果
        dataSet.setSliceSpace(3f);
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : Const.COLORFUL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        //对百分比文本设置
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart2));//百分比显示
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf"));
        pieChart2.setData(data);


        pieChart2.invalidate();
    }

    void addDataToBar(){

        BarDataSet set1 = new BarDataSet(bar_entries, null);
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(false);//在柱子上显示value
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.25f);//设置柱子宽度
        barchart.setData(data);

        barchart.invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_4:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Intent intent=new Intent(this,Home_Activity.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
