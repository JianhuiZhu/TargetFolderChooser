package com.jianhui_zhu.activityservicetest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianhuizhu on 2016-06-11.
 */
public class ChooseMusicFolderDialogFragment extends DialogFragment implements ChangeFolderInterface{
    @Bind(R.id.folder_path_list_recyclerview)
    RecyclerView folderPathListRecyclerView;
    @Bind(R.id.current_folder_fils_recyclerview)
    RecyclerView currentFolderfilesRecyclerView;
    @Bind(R.id.cancel_button)
    Button cancelButton;
    @Bind(R.id.ok_button)
    Button okButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_choose_folder_dialog_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void changeFolderTo(String path) {

    }
}
