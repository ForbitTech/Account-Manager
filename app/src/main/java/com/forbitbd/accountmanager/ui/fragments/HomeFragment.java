package com.forbitbd.accountmanager.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forbitbd.accountmanager.R;
import com.github.clans.fab.FloatingActionButton;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton fabReport,fabDownload;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new DashboardTransactionFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initView(View view) {
        fabReport = view.findViewById(R.id.fab_report);
        fabDownload = view.findViewById(R.id.fab_download);

        fabReport.setOnClickListener(this);
        fabDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

    }
}