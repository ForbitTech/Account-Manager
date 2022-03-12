package com.forbitbd.accountmanager.ui.main;


import android.util.Log;

import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.SharedProjectRequest;
import com.forbitbd.androidutils.models.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;


    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getCurrentUser(String email) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getUser(email)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            mView.saveUserAndShowHomeFragment(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    @Override
    public void getUserProjects(User user) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getUserProjects(user.get_id())
                .enqueue(new Callback<List<Project>>() {
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        mView.startHomeScene(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void deleteProject(Project project) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.deleteProject(project.get_id())
                .enqueue(new Callback<Project>() {
                    @Override
                    public void onResponse(Call<Project> call, Response<Project> response) {
                        if(response.isSuccessful()){
                            mView.deleteProjectFromList(project);
                        }
                    }

                    @Override
                    public void onFailure(Call<Project> call, Throwable t) {

                    }
                });
    }

    @Override
    public void shareProject(SharedProject sharedProject) {
        SharedProjectRequest request = new SharedProjectRequest(sharedProject);
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.saveSharedProject(request)
                .enqueue(new Callback<SharedProjectRequest>() {
                    @Override
                    public void onResponse(Call<SharedProjectRequest> call, Response<SharedProjectRequest> response) {
                        if(response.isSuccessful()){
                            mView.sharedProjectDone();
                        }else {
                            mView.showToast("Already Shared");
                        }
                    }
                    @Override
                    public void onFailure(Call<SharedProjectRequest> call, Throwable t) {
                        mView.showToast("Failed to Shared Project");
                    }
                });
    }

    @Override
    public void deleteSharedProject(SharedProject sharedProject) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.deleteSharedProject(sharedProject.get_id()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateUser(User user, byte[] bytes) {
        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            // Create MultipartBody.Part using file request-body,file name and part name
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }


        // Create a request body with file and image media type



        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user.getName());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), user.getContact());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), user.getAddress());
        RequestBody organization_name = RequestBody.create(MediaType.parse("text/plain"), user.getOrganization_name());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", phone);
        map.put("organization_name", organization_name);
        map.put("address", address);

        ApiClient cmClient = ServiceGenerator.createService(ApiClient.class);
        cmClient.updateProfile(user.get_id(),part,map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==201){
                    User updatedUser = response.body();
                    mView.updateComplete(updatedUser);
                }else{
                    mView.showToast("Failed to Update User");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("HHHH","JJJJJJ "+t.getMessage());
            }
        });
    }
}
