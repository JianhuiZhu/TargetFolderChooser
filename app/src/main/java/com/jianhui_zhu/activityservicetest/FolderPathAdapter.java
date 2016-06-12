package com.jianhui_zhu.activityservicetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianhuizhu on 2016-06-11.
 */
public class FolderPathAdapter extends RecyclerView.Adapter<FolderPathAdapter.FolderPathItem> {
    private List<String> pathName = new ArrayList<>();
    private ChangeFolderInterface folderInterface;
    public FolderPathAdapter(List<String> pathName,ChangeFolderInterface changeFolderInterface){
        if(pathName!=null){
            this.pathName.addAll(pathName);
        }
        folderInterface = changeFolderInterface;
    }
    public void changePathName(List<String> newPathName){
        pathName.clear();
        if(newPathName!=null){
            pathName.addAll(newPathName);
        }
        notifyDataSetChanged();
    }
    @Override
    public FolderPathItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_folder_path,parent,false);
        return new FolderPathItem(view);
    }

    @Override
    public void onBindViewHolder(FolderPathItem holder, int position) {
        String name = pathName.get(position);
        holder.folderNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return pathName.size();
    }

    public class FolderPathItem extends RecyclerView.ViewHolder {
        @Bind(R.id.folder_container_linearlayout)
        LinearLayout container;
        @Bind(R.id.folder_name_textview)
        TextView folderNameTextView;
        public FolderPathItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if(position!=pathName.size()-1){
                        for (int i = position+1; i < pathName.size(); i++) {
                            pathName.remove(i);
                        }
                        notifyItemRangeChanged(position+1,pathName.size()-position-1);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < pathName.size(); i++) {
                            sb.append(pathName.get(i)).append("/");
                        }
                        folderInterface.changeFolderTo(sb.toString());
                    }
                }
            });
        }
    }
}
