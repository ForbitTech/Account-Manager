package com.forbitbd.accountmanager.ui.main.scenes.create_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.models.Project;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SceneCreateProject extends Fragment implements CreateProjectContract.View, View.OnClickListener {

    private Communicator mCommunicator;

    private MaterialButton btnBack,btnCreate;

    private CreateProjectPresenter mPresenter;

    private TextInputLayout tiBusinessName,tiBusinessDesc,tiBusinessAddress;
    private TextInputEditText etBusinessName,etBusinessDesc,etBusinessAddress;
    private TextView tvTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCommunicator = (Communicator) getActivity();
        mPresenter = new CreateProjectPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mCommunicator.getUpdateProject()!=null){
            mCommunicator.setTitle(getString(R.string.update_project));
        }else{
            mCommunicator.setTitle(getString(R.string.create_new_business));
        }

    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.title);

        tiBusinessName = view.findViewById(R.id.ti_business_name);
        tiBusinessDesc = view.findViewById(R.id.ti_business_desc);
        tiBusinessAddress = view.findViewById(R.id.ti_business_address);

        etBusinessName = view.findViewById(R.id.et_business_name);
        etBusinessDesc = view.findViewById(R.id.et_business_desc);
        etBusinessAddress = view.findViewById(R.id.et_business_address);

        btnBack = view.findViewById(R.id.btn_back);
        btnCreate = view.findViewById(R.id.btn_create);

        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        if(mCommunicator.getUpdateProject()!=null){
            mPresenter.bind(mCommunicator.getUpdateProject());
        }


    }

    @Override
    public void clearPreError() {
        tiBusinessName.setErrorEnabled(false);
        tiBusinessAddress.setErrorEnabled(false);
        tiBusinessDesc.setErrorEnabled(false);
    }

    @Override
    public void showValidationError(String message, int fieldId) {
        if (fieldId==1){
            etBusinessName.requestFocus();
            tiBusinessName.setError(message);
        }
    }


    @Override
    public void onClick(View v) {
        if(v==btnBack){
            mCommunicator.showHomeScene();
        }else if(v==btnCreate){
            mCommunicator.hideKeyboard();
            Project project = mPresenter.getProjectFromUserData();
            boolean valid = mPresenter.validate(project);
            if(!valid){
                return;
            }
            project.setUser(mCommunicator.getUser().get_id());

            if(project.get_id()!=null){
                mPresenter.updateProject(project);
            }else {
                mPresenter.saveProject(project);
            }

        }
    }

    @Override
    public Project getProjectFromUserData() {
        Project project = null;

        if(mCommunicator.getUpdateProject()!=null){
            project = mCommunicator.getUpdateProject();
        }else {
            project = new Project();
        }
        String name = etBusinessName.getText().toString().trim();
        String desc = etBusinessDesc.getText().toString().trim();
        String address = etBusinessAddress.getText().toString().trim();;
        project.setName(name);
        project.setDescription(desc);
        project.setLocation(address);
        return project;
    }

    @Override
    public void saveDone(Project project) {
        mCommunicator.addProjectTotheList(project);
    }

    @Override
    public void bind(Project project) {
        etBusinessName.setText(project.getName());
        etBusinessDesc.setText(project.getDescription());
        etBusinessAddress.setText(project.getLocation());
        tvTitle.setText(getString(R.string.update_project));
        btnCreate.setText(getString(R.string.update));
    }

    @Override
    public void updateProjectsInActivity(Project project) {
        mCommunicator.updateProjectInList(project);
    }
}
