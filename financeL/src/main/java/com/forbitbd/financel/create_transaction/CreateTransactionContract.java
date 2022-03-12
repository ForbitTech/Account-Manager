package com.forbitbd.financel.create_transaction;

import com.forbitbd.financel.Form;
import com.forbitbd.financel.models.TransactionResponse;

public interface CreateTransactionContract {

    interface Presenter{
        void openCalendar();
        TransactionResponse getTransactionFromFormData();
        boolean validate(TransactionResponse transaction);
        void updateUI(TransactionResponse transaction);
        void updateTransaction(TransactionResponse transaction,byte[] bytes);
        void saveTransaction(TransactionResponse transaction,byte[] bytes);
        void showDeleteDialog();
        void deleteTransaction(TransactionResponse transactionResponse);

    }

    interface View extends Form{
        void openCalendar();
        void showErrorToast(String message);
        TransactionResponse getTransactionFromFormData();
        void saveComplete(TransactionResponse transaction);
        void updateUI(TransactionResponse transaction);
        void updateTransactionInDashboard(TransactionResponse transaction);
        void showDeleteDialog();
        void deleteFromDashboard();
    }
}
