package com.forbitbd.accountmanager.ui.main.scenes.search_users;

import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserScenePresenter implements SearchUserSceneContract.Presenter {

    private SearchUserSceneContract.View mView;

    public SearchUserScenePresenter(SearchUserSceneContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadUsers(String txt) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getQueryUsers(txt)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void filter(String txt) {
        mView.filter(txt);
    }
}
