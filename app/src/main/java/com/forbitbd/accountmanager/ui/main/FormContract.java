package com.forbitbd.accountmanager.ui.main;

public interface FormContract {

    interface Presenter{
    }

    interface View{
        void clearPreError();
        void showValidationError(String message,int fieldId);
    }
}
