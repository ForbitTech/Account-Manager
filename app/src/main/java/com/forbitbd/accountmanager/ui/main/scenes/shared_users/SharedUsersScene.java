package com.forbitbd.accountmanager.ui.main.scenes.shared_users;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedUsersScene extends Fragment implements SharedUserAdapter.SharedUserListener{

    private Communicator communicator;
    private SharedUserAdapter adapter;

    

    public SharedUsersScene() {
        // Required empty public constructor
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = (Communicator) getActivity();
        this.adapter = new SharedUserAdapter(this,R.layout.item_shared_user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_users_scene, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        communicator.setTitle(communicator.getUpdateProject().getName().concat(" Shared Users"));

        requestSharedUsers();
    }

    private void requestSharedUsers() {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getSharedUsers(communicator.getUpdateProject().get_id())
                .enqueue(new Callback<List<SharedProject>>() {
                    @Override
                    public void onResponse(Call<List<SharedProject>> call, Response<List<SharedProject>> response) {
                        if(response.isSuccessful()){
                            adapter.addAll(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SharedProject>> call, Throwable t) {
                        communicator.showToast("Server Error");
                    }
                });
    }

    @Override
    public void onDeleteClick(SharedProject sharedProject) {
        DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do You really want to delete this Users ?");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteSharedProject(sharedProject);
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show(getChildFragmentManager(),"DELETE");
    }

    private void deleteSharedProject(SharedProject sharedProject) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.deleteSharedProject(sharedProject.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            adapter.remove(sharedProject);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        communicator.showToast("Server Error");
                    }
                });
    }
}