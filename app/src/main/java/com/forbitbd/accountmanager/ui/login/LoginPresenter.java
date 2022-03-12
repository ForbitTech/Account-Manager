package com.forbitbd.accountmanager.ui.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.forbitbd.accountmanager.api.ApiClient;
import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{

    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startAutentication(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            Log.d("UUUUU", "Not Success " + result.getStatus().getStatusCode());

        }
    }

    @Override
    public void loadFinalLayout() {
        mView.loadFinalLayout();
    }

    @Override
    public void initView() {
        mView.initView();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        if(user!=null){
                            registerToDatabase(user);
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void registerToDatabase(FirebaseUser user){
        User usr = new User();

        if(user.getEmail()!=null){
            usr.setEmail(user.getEmail());
        }

        if(user.getDisplayName()!=null){
            usr.setName(user.getDisplayName());
        }

        if(user.getPhoneNumber()!=null){
            usr.setContact(user.getPhoneNumber());
        }

        if(user.getPhotoUrl()!=null){
            usr.setImage(user.getPhotoUrl().toString());
        }

        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<User> call = apiClient.register(usr);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==201){
                    mView.startMainActivity();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


//        Log.d("UUUUU",user.getDisplayName());
//        Log.d("UUUUU",user.getPhoneNumber()+"");
//        Log.d("UUUUU",user.getEmail()+"");
//        Log.d("UUUUU",user.getMetadata().toString()+"");
    }
}
