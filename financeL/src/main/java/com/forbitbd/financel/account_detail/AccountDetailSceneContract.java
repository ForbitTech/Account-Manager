package com.forbitbd.financel.account_detail;

import com.forbitbd.financel.models.AccountInfo;

public interface AccountDetailSceneContract {

    interface Presenter{
        void renderAccountInfo(AccountInfo info);
    }

    interface View{
        void renderAccountInfo(AccountInfo info);
    }
}
