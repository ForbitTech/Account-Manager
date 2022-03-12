package com.forbitbd.accountmanager.ui.main.scenes.share_business;

import com.forbitbd.androidutils.models.SharedProject;

import java.util.List;

public interface ShareBusinessContract {

    interface Presenter{
        void getSharedProjects(String userId);
    }

    interface View{
        void addSharedProjects(List<SharedProject> sharedProjectList);
        void startFinanceActivity(SharedProject sharedProject);
        void deleteSharedProject(SharedProject sharedProject);
    }
}
