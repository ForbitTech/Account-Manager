package com.forbitbd.accountmanager.ui.main.scenes.share_business;

import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.SharedProject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareBusinessPresenter implements ShareBusinessContract.Presenter {

    private ShareBusinessContract.View mView;

    public ShareBusinessPresenter(ShareBusinessContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getSharedProjects(String userId) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getSharedProjects(userId)
                .enqueue(new Callback<List<SharedProject>>() {
                    @Override
                    public void onResponse(Call<List<SharedProject>> call, Response<List<SharedProject>> response) {
                        if(response.isSuccessful()){
                            mView.addSharedProjects(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SharedProject>> call, Throwable t) {

                    }
                });
    }
}
