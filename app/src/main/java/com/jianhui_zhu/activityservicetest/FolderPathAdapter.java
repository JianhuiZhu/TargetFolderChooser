package com.jianhui_zhu.activityservicetest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianhuizhu on 2016-06-11.
 */
public class FolderPathAdapter extends RecyclerView.Adapter<FolderPathAdapter.FolderPathItem> {
    private List<String> pathName = new ArrayList<>();
    private String prefix;
    private ChangeFolderInterface folderInterface;
    public FolderPathAdapter(String path,ChangeFolderInterface changeFolderInterface,Context context){
        if(pathName!=null){
            changePathName(path,context);
        }
        folderInterface = changeFolderInterface;
    }
    public void changePathName(String path, Context context){
        if(path!=null) {
            pathName.clear();
            String[] separatedPathList = path.split("/");
            this.pathName.addAll(Arrays.asList(separatedPathList).subList(2,separatedPathList.length));
            prefix = "/"+separatedPathList[0]+"/"+separatedPathList[1]+"/";
            notifyDataSetChanged();
        }else{
            Toast.makeText(context,"The path is invalid",Toast.LENGTH_LONG).show();
        }

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
                        int start = position+1;
                        int end = pathName.size()-position-1;
                        if(start>=end){
                            notifyItemChanged(start);
                        }else {
                            notifyItemRangeChanged(start,end);
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(prefix);
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
