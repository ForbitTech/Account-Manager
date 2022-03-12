package com.forbitbd.accountmanager.ui.main.scenes.share_business;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.Constant;

import java.util.List;


public class ShareBusiness extends Fragment implements ShareBusinessContract.View {

    private ShareBusinessPresenter mPresenter;

    private RecyclerView mRecyclerView;

    private ShareBusinessAdapter adapter;
    private Communicator communicator;

  

    public ShareBusiness() {
        // Required empty public constructor
    }

   
   

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = (Communicator) getActivity();
        mPresenter = new ShareBusinessPresenter(this);
        adapter = new ShareBusinessAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_business, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        mPresenter.getSharedProjects(communicator.getUser().get_id());
    }

    @Override
    public void addSharedProjects(List<SharedProject> sharedProjectList) {
        adapter.addSharedProjects(sharedProjectList);
    }

    @Override
    public void startFinanceActivity(SharedProject sharedProject) {
        communicator.startFinanceActivity(sharedProject);
    }

    @Override
    public void deleteSharedProject(SharedProject sharedProject) {
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do You really want to delete this Project ?");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                communicator.deleteSharedProject(sharedProject);
                deleteDialog.dismiss();
                adapter.remove(sharedProject);
            }
        });
        deleteDialog.show(getChildFragmentManager(),"DELETE");

    }

    @Override
    public void onResume() {
        super.onResume();
        communicator.setTitle(getString(R.string.shared_projects));
    }
}