package com.forbitbd.accountmanager.ui.main.scenes.business_dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;

public class SceneBusinessDashboard extends Fragment {

    private Communicator mCommunicator;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCommunicator = (Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_dashboard, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
    }
}
