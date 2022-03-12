package com.forbitbd.accountmanager.ui.main.scenes.create_project;

import com.forbitbd.accountmanager.ui.main.FormContract;
import com.forbitbd.androidutils.models.Project;

public interface CreateProjectContract {

    interface Presenter extends FormContract.Presenter {
        boolean validate(Project project);
        Project getProjectFromUserData();
        void saveProject(Project project);
        void updateProject(Project project);
        void bind(Project project);
    }

    interface View extends FormContract.View{
        Project getProjectFromUserData();
        void saveDone(Project project);
        void bind(Project project);
        void updateProjectsInActivity(Project project);
    }


}
