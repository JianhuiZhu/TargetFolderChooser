package com.jianhui_zhu.activityservicetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by jianhuizhu on 2016-06-09.
 */
public class MusicService extends Service {
    Context context;
    private MediaPlayer mediaPlayer;
    public static final String TAG = "player";
    private static final int tempMusic = R.raw.music;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"Music player onbind",Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this,"Music player onUnbind",Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {

        Toast.makeText(this,"MusicService OnCreate() ",Toast.LENGTH_SHORT).show();
        context = this;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this,"MusicService start",Toast.LENGTH_SHORT).show();
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"MusicService destroy",Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        super.onDestroy();

    }
    public class Binder extends android.os.Binder{
        public void play(){
            if(!mediaPlayer.isPlaying()){
                mediaPlayer = MediaPlayer.create(context,R.raw.music);
                mediaPlayer.start();
            }
        }
        public void pause(){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }
}
