package com.jianhui_zhu.activityservicetest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        File directory = Environment.getExternalStorageDirectory();
        String path = directory.getAbsolutePath();
        String [] paths = path.split("/|\\\\");
        List<String> pathsList = new ArrayList<>();
        pathsList.addAll(Arrays.asList(paths));
        List<File> fileList = new ArrayList<>();
        fileList.addAll(Arrays.asList(directory.listFiles()));
        folderPathListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        folderPathListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        folderPathListRecyclerView.setAdapter(new FolderPathAdapter(pathsList,this));
        currentFolderfilesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        currentFolderfilesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        currentFolderfilesRecyclerView.setAdapter(new FolderAdapter(fileList,this));
    }

    @Override
    public void changeFolderTo(String path) {
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        File[] tempFiles = file.listFiles();
        fileList.addAll(Arrays.asList(tempFiles));
        ((FolderAdapter)currentFolderfilesRecyclerView.getAdapter()).changeFileList(fileList);
        String dir = file.getAbsolutePath();
        String [] paths = dir.split("/|\\\\");
        List<String> dirList = new ArrayList<>();
        dirList.addAll(Arrays.asList(paths));
        ((FolderPathAdapter)folderPathListRecyclerView.getAdapter()).changePathName(dirList);
    }
}
