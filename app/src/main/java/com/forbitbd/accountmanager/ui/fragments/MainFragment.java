package com.forbitbd.accountmanager.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.listener.ProjectClickListener;
import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.adapter.ProjectAdapter;
import com.forbitbd.accountmanager.model.Project;
import com.forbitbd.accountmanager.ui.dashboard.DashboardActivity;
import com.forbitbd.accountmanager.utils.Constant;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private ArrayList<Project> projectList;
    private ExtendedFloatingActionButton tbncreate;

    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        projectList = new ArrayList<>();
        projectList.add(new Project("Daily Shopping", "Salimuddin Market, Shah Ali Bagh, Mirpur-1"));
        projectList.add(new Project("Swapno Super Shop", "Shah Ali Bagh, Mirpur-1"));
        adapter = new ProjectAdapter(getContext(), projectList, new ProjectClickListener() {
            @Override
            public void ItemCLick(Project project) {
                Intent intent = new Intent(getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        tbncreate = view.findViewById(R.id.create_business);
        tbncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateBusinessFragment dialogFragment = new CreateBusinessFragment();
                dialogFragment.show(getChildFragmentManager(), "GHGJHGJHGHJ");
            }
        });
        return view;
    }
}