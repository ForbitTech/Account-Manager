package com.forbitbd.accountmanager.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.SignUpActivity;
import com.forbitbd.accountmanager.ui.main.MainActivity;
import com.forbitbd.accountmanager.utils.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private ImageView btngoogle;
    private TextView btnsignup;

    private boolean start_layout_loaded = true;
    private LoginPresenter mPresenter;
    private ConstraintLayout mConstraintLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_start);
        mPresenter = new LoginPresenter(this);
        mPresenter.loadFinalLayout();
        mConstraintLayout = findViewById(R.id.constraintLayout);


    }

    private void swapLayout(int layoutId){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this,layoutId);
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.login_transition);
        TransitionManager.beginDelayedTransition(mConstraintLayout,transition);
        constraintSet.applyTo(mConstraintLayout);
        start_layout_loaded = !start_layout_loaded;

        mPresenter.initView();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.google){
            googleSignin();
        }else if (id == R.id.signup){
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    }

    private void googleSignin() {
        Intent signInIntent = getGoogleApiClient().getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("HHHH","CALL "+resultCode);

        if(requestCode==RC_SIGN_IN && resultCode==RESULT_OK){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mPresenter.startAutentication(result);
        }else {

        }
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loadFinalLayout() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(start_layout_loaded){
                                swapLayout(R.layout.activity_login);
                            }else {
                                swapLayout(R.layout.login_start);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void initView() {
        btngoogle = findViewById(R.id.google);
        btnsignup = findViewById(R.id.signup);
        btngoogle.setOnClickListener(this);
        btnsignup.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}