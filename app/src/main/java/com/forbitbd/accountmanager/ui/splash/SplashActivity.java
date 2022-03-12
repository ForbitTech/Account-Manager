package com.forbitbd.accountmanager.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.login.LoginActivity;
import com.forbitbd.accountmanager.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashPresenter mPresenter;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable,delay);
                mPresenter.checkdealer();
            }
        },delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void Startlogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void StartMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}