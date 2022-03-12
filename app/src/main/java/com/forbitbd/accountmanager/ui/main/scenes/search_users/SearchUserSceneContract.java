package com.forbitbd.accountmanager.ui.main.scenes.search_users;

import com.forbitbd.androidutils.models.User;

import java.util.List;

public interface SearchUserSceneContract {

    interface Presenter{
        void loadUsers(String txt);
        void filter(String txt);
    }

    interface View{
        void renderAdapter(List<User> userList);
        void filter(String txt);
    }
}
