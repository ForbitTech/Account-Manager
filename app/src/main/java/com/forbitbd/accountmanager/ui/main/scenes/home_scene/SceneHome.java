package com.forbitbd.accountmanager.ui.main.scenes.home_scene;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.google.android.material.button.MaterialButton;

public class SceneHome extends Fragment implements ProjectAdapter.ProjectClickListener {



    private MaterialButton mCreateBusiness;
    private ProjectAdapter adapter;

    private Communicator mCommunicator;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommunicator = (Communicator) getActivity();
        adapter = new ProjectAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mCreateBusiness = view.findViewById(R.id.create_business);
        mCreateBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("HHHHH","Button Clicked "+mCommunicator.getUser().getName());
                mCommunicator.showCreateProjectScene(null);
            }
        });

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onProjectClick(Project project) {
        mCommunicator.startFinanceActivity(project);
    }

    @Override
    public void onEditClick(Project project) {
        mCommunicator.showCreateProjectScene(project);
    }

    @Override
    public void onDeleteClick(Project project) {
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do You really want to delete this Project ?");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                mCommunicator.deleteProject(project);
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show(getChildFragmentManager(),"DELETE");
    }

    @Override
    public void onShareClick(Project project) {
        mCommunicator.showSearchUserScene(project);
    }

    @Override
    public void onSharedUsersClick(Project project) {
        mCommunicator.showSharedUsersScene(project);
    }


    @Override
    public void onResume() {
        super.onResume();
        mCommunicator.setTitle(getString(R.string.project_list));
        adapter.addAll(mCommunicator.getProjects());
    }
}
