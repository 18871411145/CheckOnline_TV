package cn.lxbest.wb2020.checkonline_tv.Modle;

import org.json.JSONObject;

import cn.lxbest.wb2020.checkonline_tv.Tool.Const;
import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;

/**办公地点*/
public class Space {

    public int sum;//总人数

    /**公司模型*/
    public static class Company implements Comparable<Company> {

        public String name;//公司名字
        public int people;//公司人数
        public int check;//出勤人数

        public Company(JSONObject jsonObject){
            try{
                if(Funcs.jsonItemValid(jsonObject,Const.Field_Table_Company.name)) name=jsonObject.getString(Const.Field_Table_Company.name);
                if(Funcs.jsonItemValid(jsonObject,Const.Field_Table_Company.check)) check=jsonObject.getInt(Const.Field_Table_Company.check);
                if(Funcs.jsonItemValid(jsonObject,Const.Field_Table_Company.people)) people=jsonObject.getInt(Const.Field_Table_Company.people);
            }catch (Exception e){

            }

        }

        @Override
        public int compareTo(Company o) {
            return o.check-this.check;
        }
    }
}
