package com.forbitbd.accountmanager.ui.main;

import android.graphics.Bitmap;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.models.User;

import java.util.List;

public interface Communicator {
    void startFinanceActivity(Project project);
    void startFinanceActivity(SharedProject sharedProject);

    User getUser();

    void showCreateProjectScene(Project project);
    void showHomeScene();
    void showShareScene();
    void showProfileScene();
    void showShareBusinessScene();
    void hideKeyboard();
    void showSearchUserScene(Project project);
    void showSharedUsersScene(Project project);
    void addProjectTotheList(Project project);
    void updateProjectInList(Project project);
    void deleteProject(Project project);
    void setSearchUser(User user);
    void shareProject(SharedProject sharedProject);
    void deleteSharedProject(SharedProject sharedProject);
    User getSearchedUser();
    void setTitle(String title);
    List<Project> getProjects();
    Project getUpdateProject();
    void showToast(String message);
    Bitmap getUserBitmap();
    void openCropImageActivity();
    void updateUserInServer(User user);
}
