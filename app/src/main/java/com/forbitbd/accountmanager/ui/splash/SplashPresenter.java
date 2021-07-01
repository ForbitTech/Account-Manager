package com.forbitbd.accountmanager.ui.splash;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashPresenter implements SplashContract.Presenter{

    private SplashContract.View mView;
    private FirebaseUser mCurrentUser;

    public SplashPresenter(SplashContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void checkdealer() {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mCurrentUser == null) {
            mView.Startlogin();
        } else {
            mView.StartMain();
        }
    }
}
