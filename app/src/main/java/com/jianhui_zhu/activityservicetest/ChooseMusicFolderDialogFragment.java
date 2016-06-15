package com.jianhui_zhu.activityservicetest;

import android.app.DialogFragment;
import android.app.Fragment;
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
    ChangeFolderInterface activityInterface;
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
        folderPathListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        folderPathListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        folderPathListRecyclerView.setAdapter(new FolderPathAdapter(directory.getAbsolutePath(),this,getActivity()));
        currentFolderfilesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        currentFolderfilesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        currentFolderfilesRecyclerView.setAdapter(new FolderAdapter(directory,this));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = ((FolderAdapter)currentFolderfilesRecyclerView.getAdapter()).getTargetFilePath();
                activityInterface.changeFolderTo(path);
                onDismiss(getDialog());
            }
        });
    }

    @Override
    public void changeFolderTo(String path) {
        File file = new File(path);

        ((FolderAdapter)currentFolderfilesRecyclerView.getAdapter()).changeFileList(file);
        ((FolderPathAdapter)folderPathListRecyclerView.getAdapter()).changePathName(path,getActivity());
    }

    public static DialogFragment newInstance(ChangeFolderInterface interfaceActivity) {

        DialogFragment fragment = new ChooseMusicFolderDialogFragment();
        ((ChooseMusicFolderDialogFragment)fragment).activityInterface = interfaceActivity;
        return fragment;
    }
}
