package cn.lxbest.wb2020.checkonline_tv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.lxbest.wb2020.checkonline_tv.Modle.Space;
import cn.lxbest.wb2020.checkonline_tv.Tool.Const;
import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;
import cn.lxbest.wb2020.checkonline_tv.custom.MyValueFormatter;
import cz.msebera.android.httpclient.Header;

public class Home_Activity extends Activity implements OnChartValueSelectedListener {

    private PieChart piechart;
    private BarChart barchart;
    ListView listView;
    List<Space.Company> list=new ArrayList();
    CheckAdapter adapter=new CheckAdapter();

    //标题
    TextView text_title;

    //饼图数据集
    ArrayList<PieEntry> entries = new ArrayList<>();
    //柱状图数据集
    ArrayList<BarEntry> values = new ArrayList<>();


    Handler handler=new Handler();

    //人数增加倍数
    private int bs=1;

    //有关PieChart的设置
    void initPieChart(){

        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setDragDecelerationFrictionCoef(0.95f);

        piechart.setDrawCenterText(true);
        piechart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        piechart.setCenterText("公司分布情况 ");
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


        ValueFormatter xAxisFormatter = new MyValueFormatter("时");


        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisLineWidth(1f);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setAxisMinimum(8.00f);


        ValueFormatter custom = new MyValueFormatter("人");


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

    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        piechart=findViewById(R.id.piechart1);
        barchart=findViewById(R.id.barchart1);
        listView=findViewById(R.id.check_ListView);
        text_title=findViewById(R.id.title);
        //标题
        text_title.setText(Const.Company);

        initPieChart();
        initBarChart();

        piechart.setOnChartValueSelectedListener(this);

        getData();

       runnable=new Runnable() {
            @Override
            public void run() {
                //指定时间前往专利概况页面
                Intent intent=new Intent(Home_Activity.this,ZhuanLiGK_Activity.class);
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

    //得到 公司排名 列表
    private void getGSPM(){
        list=new ArrayList<>();
        String url=Funcs.servUrl(Const.Key_Resp_Path.gspm);

        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                parseGSPMData(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Funcs.showtoast(Home_Activity.this,"公司排名获取失败");
            }
        });
    }

    private void parseGSPMData(JSONObject jsonObject){
        try {
            int code =jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                //获取公司排名成功
                //再解析签到列表数据
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    list.add(new Space.Company(data.getJSONObject(i)));
                }
                listView.setAdapter(adapter);

            }else{
                Funcs.showtoast(Home_Activity.this,"公司排名获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //得到饼图数据(人员分布)
    private void getFBData(){
        entries=new ArrayList<>();
        String url=Funcs.servUrl(Const.Key_Resp_Path.ryfb);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                parseFBData(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Funcs.showtoast(Home_Activity.this,"人员分布获取失败");
            }
        });
    }

    private void parseFBData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    JSONObject js=data.getJSONObject(i);
                    int people=0;
                    String name=null;
                    int count=0;
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Company.people)) count=js.getInt(Const.Field_Table_Company.people);
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Company.alive)) people=js.getInt(Const.Field_Table_Company.alive);
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Company.name)) name=js.getString(Const.Field_Table_Company.name);
                    if(people==0){
                        entries.add(new PieEntry(1,name+" "+1+"人"));
                    } else if(people*bs<=count){
                    entries.add(new PieEntry(people*bs,name+" "+people*bs+"人"));
                    }else{
                        entries.add(new PieEntry(count,name+" "+count+"人"));
                    }
                }
                addDataToPie();

            }else{
                Funcs.showtoast(Home_Activity.this,"人员分布获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**获得人员变动数据*/
    private void getBDData(){
        values=new ArrayList<>();
        String url=Funcs.servUrl(Const.Key_Resp_Path.rybd);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                parseBDData(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Funcs.showtoast(Home_Activity.this,"人员变动获取失败");
            }
        });
    }

    private void parseBDData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){

                    JSONObject js=data.getJSONObject(i);
                    double time=0;
                    int sum=0;
                    int c=0;//孵化器注册总人数人数
                    int hour=0;//小时
                    int minute=0;//分钟
                    if(Funcs.jsonItemValid(js,"c"))c=js.getInt("c");
                    if(Funcs.jsonItemValid(js,"h"))hour=js.getInt("h");
                    if(Funcs.jsonItemValid(js,"m"))minute=js.getInt("m");
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Space.time)) time=js.getDouble(Const.Field_Table_Space.time);
                    if(Funcs.jsonItemValid(js,Const.Field_Table_Space.sum)) sum=js.getInt(Const.Field_Table_Space.sum);
                    String s=hour+"."+minute;
                    Double d=Double.parseDouble(s);
                    if(sum==0&&time<=d){
                        values.add(new BarEntry((float) time,1));
                    } else if(sum*bs<=c){
                    values.add(new BarEntry((float) time,sum*bs));
                    }else{
                        values.add(new BarEntry((float) time,c));
                    }
                }

                addDataToBar();

            }else{
                Funcs.showtoast(Home_Activity.this,"人员变动获取失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //设置为单例activity时 意图调用不会重新加载而是调用接口
    @Override
    protected void onNewIntent(Intent intent) {
         getData();
    }

    void getData(){
        getGSPM();
        getFBData();
        getBDData();
    }

    //将数据放入chart中
    void addDataToPie(){
        PieDataSet dataSet = new PieDataSet(entries, "颜色");
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

    void addDataToBar(){

            BarDataSet set1 = new BarDataSet(values, null);
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);//在柱子上显示value
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.25f);//设置柱子宽度
            barchart.setData(data);

            barchart.invalidate();
    }




    class Container{
        TextView text_name,text_people,text_check;
    }

    class CheckAdapter extends BaseAdapter{
        Container container;
        @Override
        public int getCount() {
            if(list.size()<=5){
                return list.size();
            }else{
                return 5;
            }
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view==null){
                container=new Container();
                view= LayoutInflater.from(getBaseContext()).inflate(R.layout.check_item,null);
                container.text_name=view.findViewById(R.id.name_TextView);
                container.text_people=view.findViewById(R.id.people_TextView);
                container.text_check=view.findViewById(R.id.check_TextView);
                view.setTag(container);
            }else container= (Container) view.getTag();

            Space.Company data=list.get(position);

            container.text_name.setText(data.name);
            container.text_people.setText("共 "+data.people+" 人");
            if(data.check==0){
                container.text_check.setText("签到 "+1+" 人");
            }else if(data.check*bs<=data.people){
                container.text_check.setText("签到 "+data.check*bs+" 人");
            }else{
                container.text_check.setText("签到 "+data.people+" 人");
            }
            return view;
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_7:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    //前往全屏视频
                    Intent intent=new Intent(this,FullScreenVideo_Activity.class);
                    startActivity(intent);
                }
                break;
            case KeyEvent.KEYCODE_8:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    //立马刷新数据
                    bs=1;
                    getData();
                }
                break;
            case KeyEvent.KEYCODE_5:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                   //改变人数显示
                    bs=2;
                    getData();
                }
                break;
            case KeyEvent.KEYCODE_2:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Intent intent=new Intent(this,ZhuanLi_Activity.class);
                    startActivity(intent);
                }
                break;
            case KeyEvent.KEYCODE_9:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Intent intent=new Intent(this,ZhuanLiGK_Activity.class);
                    startActivity(intent);
                }
                break;

        }
        return super.onKeyDown(keyCode, event);
    }
}
