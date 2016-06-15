package com.jianhui_zhu.activityservicetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianhuizhu on 2016-06-11.
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderItem> {
    public String getTargetFilePath() {
        return targetFilePath;
    }
    public void changeFileList(File files){
        File[] tempFiles = files.listFiles();
        this.files.clear();
        if(tempFiles!=null) {
            for (File file :
                    tempFiles) {
                if(file.isDirectory()){
                    this.files.add(file);
                }
                String mineType = URLConnection.guessContentTypeFromName(file.getName());
                if(mineType!=null&&mineType.startsWith("audio")){
                    this.files.add(file);
                }
            }
        }
        notifyDataSetChanged();
    }
    private String targetFilePath;
    private List<File> files = new ArrayList<>();
    private ChangeFolderInterface folderInterface;
    public FolderAdapter(File file, ChangeFolderInterface changeFolderInterface){
        if(files!=null){
            changeFileList(file);
        }
        folderInterface = changeFolderInterface;
    }
    @Override
    public FolderAdapter.FolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_folder_file,parent,false);
        return new FolderItem(view);
    }

    @Override
    public void onBindViewHolder(FolderAdapter.FolderItem holder, int position) {
        File file = files.get(position);
        holder.folderNameTextView.setText(file.getName());
        if(file.isDirectory()){
            holder.folderIcImageView.setImageResource(R.drawable.ic_folder_black_24dp);
        }else{
            String mineType = URLConnection.guessContentTypeFromName(file.getName());
                holder.folderIcImageView.setImageResource(R.drawable.ic_audio_file_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FolderItem extends RecyclerView.ViewHolder {
        @Bind(R.id.file_area)
        RelativeLayout container;
        @Bind(R.id.folder_ic)
        ImageView folderIcImageView;
        @Bind(R.id.folder_name_textview)
        TextView folderNameTextView;
        public FolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    targetFilePath = files.get(getLayoutPosition()).getAbsolutePath();
                    folderInterface.changeFolderTo(targetFilePath);
                }
            });
        }
    }
}
