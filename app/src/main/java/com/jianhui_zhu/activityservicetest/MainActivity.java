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
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ChangeFolderInterface{
    private MusicService.Binder binder;
    private ChangeFolderInterface folderInterface;
    private String path;
    private boolean firstClick = true;
    @OnClick({R.id.backward,R.id.forward,R.id.get_path_dialog})
    public void click(View v){
        switch (v.getId()){
            case R.id.backward:
                binder.changeCurrentSong(false);
                break;

            case R.id.forward:
                binder.changeCurrentSong(true);
                break;

            case R.id.get_path_dialog:
                ChooseMusicFolderDialogFragment.newInstance(folderInterface).show(getFragmentManager(),ChooseMusicFolderDialogFragment.class.getName());
                break;
        }
    }
    @Bind(R.id.play)
    Button play;
    @Bind(R.id.pause)
    Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folderInterface = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(firstClick){
                        if(path!=null){
                            Intent intent = new Intent(MainActivity.this,MusicService.class);
                            intent.putExtra(MusicService.MUSIC_FOLDER_TAG,path);
                            bindService(intent,sc, Context.BIND_AUTO_CREATE);
                            startService(intent);
                        }else{
                            ChooseMusicFolderDialogFragment.newInstance(folderInterface).show(getFragmentManager(),ChooseMusicFolderDialogFragment.class.getName());
                        }
                    }else{
                        if(binder!=null){
                            binder.play();
                        }
                    }

                }
            });
        }
        if (pause != null) {
            pause.setOnClickListener(new View.OnClickListener() {
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
            binder.play();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void changeFolderTo(String path) {
        this.path = path;
    }
}
