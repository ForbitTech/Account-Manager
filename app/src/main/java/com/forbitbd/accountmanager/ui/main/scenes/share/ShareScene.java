package com.forbitbd.accountmanager.ui.main.scenes.share;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.User;
import com.google.android.material.button.MaterialButton;


public class ShareScene extends Fragment {
    
    private Communicator communicator;

    private TextView tvStatus;

    private MaterialButton btnShare;
    private CheckBox ckRead,ckWrite,ckUpdate,ckDelete;

   

    public ShareScene() {
        // Required empty public constructor
    }

    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.communicator = (Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_scene, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvStatus = view.findViewById(R.id.tv_status);
        ckRead = view.findViewById(R.id.ck_read);
        ckWrite = view.findViewById(R.id.ck_write);
        ckUpdate = view.findViewById(R.id.ck_update);
        ckDelete = view.findViewById(R.id.ck_delete);
        btnShare = view.findViewById(R.id.btn_share);
        updateUI(communicator.getSearchedUser());


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedProject sharedProject = new SharedProject(communicator.getUpdateProject());
                sharedProject.setUser(communicator.getSearchedUser());
                sharedProject.getFinance().setRead(ckRead.isChecked());
                sharedProject.getFinance().setWrite(ckWrite.isChecked());
                sharedProject.getFinance().setUpdate(ckUpdate.isChecked());
                sharedProject.getFinance().setDelete(ckDelete.isChecked());

                communicator.shareProject(sharedProject);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        communicator.setTitle(communicator.getUpdateProject().getName().concat(" Share Permission"));
    }

    private void updateUI(User user){
        tvStatus.setText("Share Project to "+user.getName()+" with Permissions:");
    }
}