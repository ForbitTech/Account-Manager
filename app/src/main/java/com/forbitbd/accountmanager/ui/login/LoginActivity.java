package com.forbitbd.accountmanager.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.SignUpActivity;
import com.forbitbd.accountmanager.ui.main.MainActivity;
import com.forbitbd.accountmanager.utils.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;
    private static final int RC_SIGN_IN = 9001;
    private ImageView btngoogle;
    private TextView btnsignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);

        btngoogle = findViewById(R.id.google);
        btnsignup = findViewById(R.id.signup);
        btngoogle.setOnClickListener(this);
        btnsignup.setOnClickListener(this);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                mPresenter.startAutentication(result);
                break;
        }
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}