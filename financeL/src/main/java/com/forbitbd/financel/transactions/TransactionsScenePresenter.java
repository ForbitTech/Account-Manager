package com.forbitbd.financel.transactions;

public class TransactionsScenePresenter implements TransactionsSceneContract.Presenter {

    private TransactionsSceneContract.View mView;

    public TransactionsScenePresenter(TransactionsSceneContract.View mView) {
        this.mView = mView;
    }
}
