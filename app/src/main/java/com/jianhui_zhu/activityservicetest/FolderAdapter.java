package com.jianhui_zhu.activityservicetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
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

    private String targetFilePath;
    private List<File> files = new ArrayList<>();
    private ChangeFolderInterface folderInterface;
    RadioGroup radioGroup;
    public FolderAdapter(List<File>files, ChangeFolderInterface changeFolderInterface){
        if(files!=null){
            this.files.addAll(files);
        }
        folderInterface = changeFolderInterface;
    }
    public void changeFileList(List<File> newFiles){
        files.clear();
        if(newFiles!=null){
            files.addAll(newFiles);
        }
        notifyDataSetChanged();
    }
    @Override
    public FolderAdapter.FolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_folder_file,parent,false);
        radioGroup = new RadioGroup(parent.getContext());
        return new FolderItem(view);
    }

    @Override
    public void onBindViewHolder(FolderAdapter.FolderItem holder, int position) {
        File file = files.get(position);
        holder.folderNameTextView.setText(file.getName());
        if(file.isDirectory()){
            radioGroup.addView(holder.folderSelectRadioButton);
        }else{
            String mineType = URLConnection.guessContentTypeFromName(file.getName());
            if(mineType.startsWith("audio")){
                holder.folderIcImageView.setImageResource(R.drawable.ic_audio_file_24dp);
            }else{
                holder.folderIcImageView.setImageResource(R.drawable.ic_file_24dp);
            }
            holder.folderSelectRadioButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class FolderItem extends RecyclerView.ViewHolder {
        @Bind(R.id.folder_ic)
        ImageView folderIcImageView;
        @Bind(R.id.folder_name_textview)
        TextView folderNameTextView;
        @Bind(R.id.folder_select_radiobutton)
        RadioButton folderSelectRadioButton;
        public FolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            folderSelectRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        int position = getAdapterPosition();
                        targetFilePath = files.get(position).getAbsolutePath();
                    }
                }
            });
        }
    }
}
