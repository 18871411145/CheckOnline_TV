package cn.lxbest.wb2020.checkonline_tv.Tool;


import android.graphics.Color;

public class Const {

//    public static final String server = "http://192.168.31.83:8080/checkonline_service";
    public static final String server = "http://39.100.102.110:8080/checkonline_service";
    public static final String qnserver = "http://qnyeyimg.lxbest.cn";
    public static final String contentType="application/json";

    public static final long seconds=1000;//一秒钟
    public static final long minute=1000*60;//一秒钟
    public static final long hour=1000*60*60;//一秒钟

    //公司名称
    public static String Company="贝壳瑞晟";

    public static class Env {
        /**
         * 开发环境+客户端假数据
         */
        public static final int DEV_TD = 1;
        /**
         * 开发环境+服务器ok
         */
        public static final int DEV_OK = 2;
        /**
         * 生产环境
         */
        public static final int PROD = 3;

    }


    public final static long TS_HOUR_SEC = 60 * 60;
    public final static long TS_HOUR_MILLIS = 60 * 60 * 1000;


    public final static long TS_1_MIN = 60 * 1000;
    public final static long TS_1_HOUR = 60 * 60 * 1000;
    public final static long TS_1_DAY = 24 * 60 * 60 * 1000;
    public final static long TS_1_WEEK = 7 * 24 * 60 * 60 * 1000;

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(0,199,140),Color.rgb(153,51,250),Color.rgb(255,128,0),
            Color.rgb(34,139,34),Color.rgb(255,99,71)
    };


    public static class Key_Resp {

        public static String Code = "code";
        public static String Data = "data";


    }
    /**请求url*/
    public static class Key_Resp_Path{

       //公司排名
        public static String gspm="gspm";
        //整个孵化器人员分布
        public static String ryfb="ryfb";
        //整个孵化器时间线上人员变动情况
        public static String rybd="rybd";

        //播放视频
        public static String full_video="full_video";

        //得到专利列表及专利详情
        public static String zhuanli="zhuanli";

        //获取专利概况
        public static String zhuanligk="zhuanligk";
    }


    public static class Field_Table_Company{
        public static String name="name";//公司名称
        public static String people="people";//公司人数
        public static String check="check";//签到人数
        public static String alive="alive";//在场人数
    }

    public static class Field_Table_Space{
        public static String time="time";
        public static String sum="sum";
    }


    //视频
    public static class Field_Table_Video {
        public static String Video_Qnid="video_qnid";
    }

    //专利
    public static class Field_zhuangli_Table{
        public static String zname="zname";//名称
        public static String zlh="zlh";//专利号

        public static String zlfl="zlfl";//专利分类

        public static String fbsj="fbsj";//发布时间
        public static String flzt="flzt";//法律状态
        public static String sxrq="sxrq";//专利生效日期
        public static String jsrq="jsrq";//结束日期

        public static String zlxq="zlxq";//专利详情

        public static String qnid1="qnid1";//第1张图id
        public static String qnid2="qnid2";//第2张图id
        public static String qnid3="qnid3";//第3张图id

        public static String zls="zls";//专利数
    }

    //时间

    public static class Field_Table_Time{
        public static String Month="month";//月
    }

}
