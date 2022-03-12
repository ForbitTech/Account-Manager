package com.forbitbd.accountmanager.api;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.SharedProjectRequest;
import com.forbitbd.androidutils.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiClient {

    @GET("/civil/api/users/query/{query}")
    Call<List<User>> getQueryUsers(@Path("query") String query);

    @POST("/civil/api/users")
    Call<User> register(@Body User data);

    @GET("/civil/api/users/{email}")
    Call<User> getUser(@Path("email") String email);

    @GET("/civil/api/projects/{user_id}")
    Call<List<Project>> getUserProjects(@Path("user_id") String user_id);

    @POST("/civil/api/projects")
    Call<Project> createBusiness(@Body Project project);

    @PUT("/civil/api/projects/{project_id}")
    Call<Project> updateProject (@Path("project_id") String projectId, @Body Project project);

    @DELETE("/civil/api/projects/{project_id}")
    Call<Project> deleteProject(@Path("project_id") String projectId);

    @PUT("/civil/api/users/{user_id}")
    @Multipart
    Call<User> updateProfile(@Path("user_id") String user_id,
                             @Part MultipartBody.Part file,
                             @PartMap() Map<String, RequestBody> partMap);
    
    //jhkkdshfsdfsdjh hkldasdsjdhk



    @POST("/civil/api/sharedprojects")
    Call<SharedProjectRequest> saveSharedProject(@Body SharedProjectRequest sharedProjectRequest);

    @GET("/civil/api/sharedprojects/sharedusers/{project_id}")
    Call<List<SharedProject>> getSharedUsers(@Path("project_id") String projectId);

    @GET("/civil/api/sharedprojects/sharedprojects/{uid}")
    Call<List<SharedProject>> getSharedProjects(@Path("uid") String uid);

    @DELETE("/civil/api/sharedprojects/{id}")
    Call<Void> deleteSharedProject(@Path("id") String sharedProjectId);
}
