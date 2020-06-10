package cn.lxbest.wb2020.checkonline_tv.Tool;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.lxbest.wb2020.checkonline_tv.R;


/**
 * 通用方法类
 * */
public class Funcs {



    //将byte数组装换成json
    public static JSONObject bytetojson(byte[] response){
        if(response==null)return null;
        try {
            return new JSONObject(new String(response));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  void showtoast(Context context, String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }

    /**判断一个key是否在jsonobject中存在且不为null*/
    public static boolean jsonItemValid(JSONObject jo, String key) {
        boolean v = false;
        try {
            v = jo.has(key) && !isNull(jo.get(key));
        }catch (Exception e) {}
        return v;
    }

    public static boolean isNull(Object o) {
        return o == null || o.toString().toLowerCase().equals("null")||o.toString().length()<=0;
    }


    //将我们服务器和请求连接起来
    public static String servUrl(String url){
        return combineUrl(Const.server, url);
    }

    //将参数，请求，服务器串起来
    public static String servUrlWQ(String url, String query) {
        String u = combineUrl(Const.server, url);
        u += (query == null || query.trim().length() == 0) ? "" : ((query.trim().indexOf('?') == 0 ? "" : "?") + query.trim());
        return u;
    }

    //将七牛服务器和id连接起来
    public static String qnUrl(String qnid) {
        return combineUrl(Const.qnserver, qnid);
    }



    //将服务器和请求连接起来
    public static String combineUrl(String part1, String part2) {
        return (part1 != null ? trim(part1, "/") : "") + (part2 != null ? ("/" + trim(part2, "/")) : "");
    }

    public static String trim(String s1, String s2) {
        int i1 = 0, i2 = s1.length();
        while (i1 < i2 && s1.substring(i1, i1 + 1).equals(s2)) i1++;
        while (i2 > 0 && s1.substring(i2 - 1, i2).equals(s2)) i2--;

        return s1.substring(i1, i2);
    }

    //回调接口
    public interface CallbackInterface {
        void onCallback(Object obj);
    }

    //double的四舍五入两位
    public static Double getTwoDouble(Double f){
        BigDecimal b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return  f1;
    }


    public static final String DateFormatGmtFull = "EEE, dd MMM yyyy HH:mm:ss 'GMT'"; //
    public static final String DateFormatGmtSimple1 = "yyyy-MM-dd";
    public static final String DateFormatGmtSimple2 = "yyyy/MM/dd";
    public static final String DateFormatGmtSimple3 = "yyyy/MM";
    public static final String DateFormatGmtSimple4 = "MM/dd";
    public static final String DateFormatGmtDT1 = "yyyy/MM/dd HH:mm";
    public static final String DateFormatGmtDT2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DateFormatGmtT1 = "HH:mm";
    public static final String DateFormatDouble = "HH.mm";

    public static Date parseGmtString2Date(String str, String format) {
        Date d = null;
        try {
            d = (new SimpleDateFormat(format, Locale.US)).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            d = null;
        }
        return d;
    }

    public static String date2FormatString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    //Date分别以millis、day、week、month为基准单位来 计算时间戳

    /**
     * 注意：该函数是从周一开始算新的一周，而非从周日开始
     * <p>
     * 因为1970-01-01 08:00:00是对应周4，所以需要补偿。补偿后1970-01-01 那一周是第0周
     */
    public static int calcWeekAdamStamp(Date date) {
        return calcWeekAdamStamp(date.getTime());
    }

    public static int calcWeekAdamStamp(long date) {
        return (int) ((date + (3 * 24 + 8) * Const.TS_1_HOUR) / Const.TS_1_WEEK);
    }

    /**
     * 获取某个月份第一天0时的stamp时间戳
     */
    public static long calcMonthAdamStamp(long date) {
        Date d = new Date(date);
        int y = d.getYear(), m = d.getMonth();
        d = new Date(y, m, 1, 0, 0, 0);
        return d.getTime() / 1000;
    }

    public static long calcMonthAdamStamp(Date date) {
        return calcMonthAdamStamp(date.getTime());
    }


    public static long calcDayAdamStamp(Date date) {
        return calcDayAdamStamp(date.getTime());
    }

    public static long calcDayAdamStamp(long date) {
        return (long) ((date + 8 * Const.TS_1_HOUR) / Const.TS_1_DAY);
    }

    public static long calcSecAdamStamp(Date date) {
        return calcSecAdamStamp(date.getTime());
    }

    public static long calcSecAdamStamp(long date) {
        return date / 1000;
    }


    //时间戳反转为时间Date
    public static Date long2Date(long t) {
        return new Date(t);
    }

    public static Date longMillis2Date(long t) {
        return long2Date(t);
    }

    public static Date longSec2Date(long s) {
        return longMillis2Date(s * 1000);
    }

    //以day为基准单位的timestamp时间戳解析
    public static Date longDay2Date(long d) {
        return new Date(d * Const.TS_1_DAY - 8 * Const.TS_1_HOUR);
    }

    //以week为基准单位的timestamp时间戳解析
    public static Date intWeek2Date(int w) {
        return new Date(w * Const.TS_1_WEEK - (3 * 24 + 8) * Const.TS_1_HOUR);
    }


    //计算从现在到参数date的时间差，根据时间间隔大小，按 "DD天"或"MM小时"或"MM分" 格式来返回
    public static String timeDiffNow2Str(Date date) {
        return timeDiff2(date, new Date());
    }

    public static String timeDiff2(Date date1, Date date2) {
        long ii = Math.abs(date1.getTime() - date2.getTime());
        long ii2 = ii / (1000 * 60 * 60 * 24); //days
        String uu = "天";
        if (ii2 < 1) {
            ii2 = ii / (1000 * 60 * 60);
            uu = "时";
            if (ii2 < 1) {
                ii2 = ii / (1000 * 60);
                uu = "分";
            }
        }

        return String.valueOf(ii2 + 1) + uu;
    }



}
