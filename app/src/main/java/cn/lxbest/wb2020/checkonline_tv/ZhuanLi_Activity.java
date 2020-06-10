package cn.lxbest.wb2020.checkonline_tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.lxbest.wb2020.checkonline_tv.Modle.ZhuanLi;
import cn.lxbest.wb2020.checkonline_tv.TestData.TestData1;
import cn.lxbest.wb2020.checkonline_tv.Tool.Const;
import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;
import cz.msebera.android.httpclient.Header;

public class ZhuanLi_Activity  extends Activity {

    ListView listView;
    MyAdapter adapter;
    //适配器list
    List<ZhuanLi>  zhuanLis=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanli_activity);

        listView=findViewById(R.id.lv_zhuanli);
        adapter=new MyAdapter();
        listView.setAdapter(adapter);
        getData();

    }

    private void getData(){
        zhuanLis=new ArrayList<>();
        String url= Funcs.servUrl(Const.Key_Resp_Path.zhuanli);
        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                if(jsonObject!=null){
                    parseData(jsonObject);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(App.env==Const.Env.DEV_TD){
                    JSONObject jsonObject= TestData1.zhuanlis();
                    parseData(jsonObject);
                }else{
                    Funcs.showtoast(ZhuanLi_Activity.this,"连接失败");
                }
            }
        });

    }

    private void parseData(JSONObject jsonObject){
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONArray data=jsonObject.getJSONArray(Const.Key_Resp.Data);
                for(int i=0;i<data.length();i++){
                    zhuanLis.add(new ZhuanLi(data.getJSONObject(i)));
                }
                listView.setAdapter(adapter);
            }else{
                Funcs.showtoast(ZhuanLi_Activity.this,"数据获取失败失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class Container{
        ImageView iv_image;
        TextView tv_title,tv_content;
    }

    class MyAdapter extends BaseAdapter{
        Container container;

        @Override
        public int getCount() {
            return zhuanLis.size();
        }

        @Override
        public Object getItem(int position) {
            return zhuanLis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view==null){
                container=new Container();
                view= LayoutInflater.from(getBaseContext()).inflate(R.layout.zhuanli_item,null);
                container.iv_image=view.findViewById(R.id.iv_image);
                container.tv_title=view.findViewById(R.id.tv_title);
                container.tv_content=view.findViewById(R.id.tv_content);
                view.setTag(container);
            }else container= (Container) view.getTag();

            ZhuanLi data=zhuanLis.get(position);
            Picasso.with(getBaseContext()).load(Funcs.qnUrl(data.qnid1)).placeholder(R.drawable.zhuanli1).into(container.iv_image);
            container.tv_title.setText(data.zname);
            container.tv_content.setText(data.zlxq);

            return view;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_5:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Intent intent=new Intent(this,Home_Activity.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
