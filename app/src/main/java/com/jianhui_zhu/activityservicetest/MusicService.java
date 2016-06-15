package com.jianhui_zhu.activityservicetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianhuizhu on 2016-06-09.
 */
public class MusicService extends Service {
    Context context;
    private MediaPlayer mediaPlayer;
    public static final String TAG = "player";
    private int position = 0;
    public static final String MUSIC_FOLDER_TAG = "music_folder";
    private List<String> musics = new ArrayList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String path = intent.getExtras().getString(MUSIC_FOLDER_TAG);
        musics.addAll(getMusicFilePath(path));
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        context = this;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                position = (position+1)%musics.size();
                try {
                    mediaPlayer.setDataSource(musics.get(position));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });
        mediaPlayer.setLooping(true);
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this,"MusicService start",Toast.LENGTH_SHORT).show();
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
                try {
                    mediaPlayer.setDataSource(musics.get(position));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        }
        public void pause(){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
        public void changeCurrentSong(boolean direction){
            if(direction) {
                position = Math.abs(position - 1) % musics.size();
            }else{
                position = (position+1)%musics.size();
            }
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musics.get(position));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }
    private List<String> getMusicFilePath(String path){
        List<String> musics = new ArrayList<>();
        File files = new File(path);
        for (File file : files.listFiles()) {
            String mineType = URLConnection.guessContentTypeFromName(file.getName());
            if(mineType!=null&&mineType.startsWith("audio")){
                musics.add(file.getAbsolutePath());
            }
        }
        return musics;
    }
}
