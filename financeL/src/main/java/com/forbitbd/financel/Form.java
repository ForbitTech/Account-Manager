package com.forbitbd.financel;

public interface Form {

    void clearPreError();
    void showValidationError(String message,int fieldId);
}
