package com.forbitbd.accountmanager.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public interface LoginContract {

    interface Presenter{
        void startAutentication(GoogleSignInResult result);
    }

    interface View{
        void startMainActivity();
    }
}
