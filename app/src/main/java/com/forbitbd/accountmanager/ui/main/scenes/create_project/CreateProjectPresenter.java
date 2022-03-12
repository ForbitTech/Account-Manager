package com.forbitbd.accountmanager.ui.main.scenes.create_project;


import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProjectPresenter implements CreateProjectContract.Presenter {

    private CreateProjectContract.View mView;

    public CreateProjectPresenter(CreateProjectContract.View mView) {
        this.mView = mView;
    }

    @Override
    public boolean validate(Project project) {
        mView.clearPreError();

        if(project.getName().equals("") || project.getName()==null){
            mView.showValidationError("Business Name Empty is not Allowed",1);
            return false;
        }

        if(project.getLocation().equals("") || project.getLocation()==null){
            project.setLocation("");
        }

        if(project.getDescription().equals("") || project.getDescription()==null){
            project.setDescription("");
        }
        return true;
    }

    @Override
    public Project getProjectFromUserData() {
        return mView.getProjectFromUserData();
    }

    @Override
    public void saveProject(Project project) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.createBusiness(project)
                .enqueue(new Callback<Project>() {
                    @Override
                    public void onResponse(Call<Project> call, Response<Project> response) {
                        if(response.isSuccessful()){
                            mView.saveDone(response.body());
                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<Project> call, Throwable t) {
                    }
                });
    }

    @Override
    public void updateProject(Project project) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.updateProject(project.get_id(),project)
                .enqueue(new Callback<Project>() {
                    @Override
                    public void onResponse(Call<Project> call, Response<Project> response) {
                        if(response.isSuccessful()){
                            mView.updateProjectsInActivity(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Project> call, Throwable t) {

                    }
                });
    }

    @Override
    public void bind(Project project) {
        mView.bind(project);
    }
}
