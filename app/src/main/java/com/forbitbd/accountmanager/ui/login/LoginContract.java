package com.forbitbd.accountmanager.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface LoginContract {

    interface Presenter{
        void startAutentication(GoogleSignInResult result);
        void loadFinalLayout();
        void initView();
    }

    interface View{
        void startMainActivity();
        void loadFinalLayout();
        void initView();
    }
}
