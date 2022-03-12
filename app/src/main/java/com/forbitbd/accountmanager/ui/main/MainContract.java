package com.forbitbd.accountmanager.ui.main;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.User;

import java.util.List;

public interface MainContract {

    interface Presenter{
        void getCurrentUser(String email);
        void getUserProjects(User user);
        void deleteProject(Project project);
        void shareProject(SharedProject sharedProject);
        void deleteSharedProject(SharedProject sharedProject);
        void updateUser(User user,byte[] bytes);
    }

    interface View{
        void saveUserAndShowHomeFragment(User user);
        void startHomeScene(List<Project> projectList);
        void deleteProjectFromList(Project project);
        void showToast(String message);
        void sharedProjectDone();
        void updateComplete(User user);
    }
}
