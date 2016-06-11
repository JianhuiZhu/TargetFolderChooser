package com.jianhui_zhu.activityservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private MusicService.Binder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button)findViewById(R.id.play);
        Button stopButton = (Button)findViewById(R.id.pause);
        Intent intent = new Intent(MainActivity.this,MusicService.class);
        bindService(intent,sc, Context.BIND_AUTO_CREATE);
        startService(intent);

        if (startButton != null) {
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(binder!=null){
                       binder.play();
                   }
                }
            });
        }
        if (stopButton != null) {
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binder.pause();
                }
            });
        }

    }
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.Binder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
