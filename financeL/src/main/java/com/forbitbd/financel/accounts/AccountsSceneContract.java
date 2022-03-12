package com.forbitbd.financel.accounts;

import com.forbitbd.financel.models.Account;

public interface AccountsSceneContract {

    interface Presenter{
        void deleteAccountFromDatabase(Account account);
    }

    interface View{
        void removeAccountFromAdapter(Account account);
    }
}
