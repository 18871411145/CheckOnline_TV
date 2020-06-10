package cn.lxbest.wb2020.checkonline_tv;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.lxbest.wb2020.checkonline_tv.Tool.Const;
import cn.lxbest.wb2020.checkonline_tv.Tool.Funcs;
import cz.msebera.android.httpclient.Header;


public class FullScreenVideo_Activity extends Activity {

    VideoView videoView;
    String video_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreen_activity);

        videoView=findViewById(R.id.fullscreen_video);

        getVideoData();

    }

    //@TODO:/var/log/xxx.mp4
    void getVideoData(){
        App.showLoadingMask(this);
        String url= Funcs.servUrl(Const.Key_Resp_Path.full_video);

        App.http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject=Funcs.bytetojson(responseBody);
                parseVideoUrl(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(App.env==Const.Env.DEV_TD){
                    video_url=Const.qnserver+"/mov_test_001.mp4";
                    App.hideLoadingMask(FullScreenVideo_Activity.this);
                    playVideo();
                }else{
                    Funcs.showtoast(FullScreenVideo_Activity.this,"获取视频失败，请检查网络连接");
                }
            }
        });

    }

    void parseVideoUrl(JSONObject jsonObject){
        App.hideLoadingMask(this);
        try {
            int code=jsonObject.getInt(Const.Key_Resp.Code);
            if(code==200){
                JSONObject data=jsonObject.getJSONObject(Const.Key_Resp.Data);
                String qnid=data.getString(Const.Field_Table_Video.Video_Qnid);
                video_url= Funcs.qnUrl(qnid);
                playVideo();
            }else{
                Funcs.showtoast(this,"获取视频地址失败");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    void playVideo(){
        videoView.setVideoURI(Uri.parse(video_url));
//        videoView.setVideoPath(video_url);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.GONE);
        videoView.start();

        //播放完成监听
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent=new Intent(FullScreenVideo_Activity.this,Home_Activity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_8:
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Intent intent=new Intent(this,Home_Activity.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
