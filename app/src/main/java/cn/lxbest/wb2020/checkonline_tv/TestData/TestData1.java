package cn.lxbest.wb2020.checkonline_tv.TestData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.lxbest.wb2020.checkonline_tv.Tool.Const;

public class TestData1 {
    public static JSONObject zhuanlis() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Const.Key_Resp.Code,200);

            List<HashMap<String,Object>> list=new ArrayList<>();

            list.add(new HashMap<String, Object>(){{
                put(Const.Field_zhuangli_Table.zname,"一种液压装置");
                put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");
            }});

            list.add(new HashMap<String, Object>(){{
                put(Const.Field_zhuangli_Table.zname,"一种液压装置");
                put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");
            }});

            list.add(new HashMap<String, Object>(){{
                put(Const.Field_zhuangli_Table.zname,"一种液压装置");
                put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");
            }});

            list.add(new HashMap<String, Object>(){{
                put(Const.Field_zhuangli_Table.zname,"一种液压装置");
                put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");
            }});

            list.add(new HashMap<String, Object>(){{
                put(Const.Field_zhuangli_Table.zname,"一种液压装置");
                put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");
            }});

            jsonObject.put(Const.Key_Resp.Data,new JSONArray(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //专利详情
    public static JSONObject ZhuanLiXQ() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Const.Key_Resp.Code,200);
            JSONObject data=new JSONObject();
            data.put(Const.Field_zhuangli_Table.zname,"一种压装置");
            data.put(Const.Field_zhuangli_Table.zlh,"1231asd1231da");
            data.put(Const.Field_zhuangli_Table.zlfl,"科技发明");
            data.put(Const.Field_zhuangli_Table.fbsj,"2020-2-23");
            data.put(Const.Field_zhuangli_Table.flzt,"已下证");
            data.put(Const.Field_zhuangli_Table.sxrq,"2020-2-23");
            data.put(Const.Field_zhuangli_Table.jsrq,"2020-2-25");
            data.put(Const.Field_zhuangli_Table.zlxq,"自己设计的一种液压装置，非常好用.....");

            jsonObject.put(Const.Key_Resp.Data,data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
