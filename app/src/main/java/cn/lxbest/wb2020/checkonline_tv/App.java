package cn.lxbest.wb2020.checkonline_tv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;

import cn.lxbest.wb2020.checkonline_tv.Tool.Const;

public class App extends Application {
   public static AsyncHttpClient http;

    public static int env= Const.Env.DEV_TD;

    public static int screenWidth, screenHeight;

    public App() {
        http=new AsyncHttpClient();
        http.addHeader("dataType", "json");
        http.addHeader("User-Agent", "jpark");

        http.setResponseTimeout(1000*60);


    }


    @Override
    public void onCreate() {
        super.onCreate();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public static Activity getActivity(Context context){
        while(!(context instanceof  Activity) && context instanceof ContextWrapper){
            context = ((ContextWrapper)context).getBaseContext();
        }
        return (Activity)context;
    }

    public static ViewGroup getRootView(Activity act) {
        return act.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static void showLoadingMask(ViewGroup parent) {
        if (parent==null) return;
        ViewGroup v = (ViewGroup) App.getActivity(parent.getContext()).getLayoutInflater().inflate(R.layout.view_loading_mask, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, screenHeight));
//        v.setTop(0); v.setLeft(0);
        parent.addView(v);
    }

    public static void showLoadingMask(Activity act) {
        if (act==null) return;
        ViewGroup p = getRootView(act);
        showLoadingMask(p);
    }


    public static void hideLoadingMask(ViewGroup parent) {
        if (parent==null) return;
        parent.removeView(parent.findViewById(R.id.cl_loading_mask));
    }

    public static void hideLoadingMask(Activity act) {
        if (act==null) return;
        ViewGroup p = getRootView(act);
        hideLoadingMask(p);
    }
}
