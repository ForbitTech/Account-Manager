package com.forbitbd.accountmanager.ui.main.scenes.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.models.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;


public class ProfileScene extends Fragment implements View.OnClickListener {

    private Communicator communicator;

    private ImageView ivImage,ivEdit;
    private TextView tvName,tvEmail;
    private TextInputLayout tiName,tiContact,tiOrganizationName,tiAddress;
    private TextInputEditText etName,etContact,etOrganizationName,etAddress;
    private MaterialButton btnCancel,btnUpdate;



    public ProfileScene() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = (Communicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_scene, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivImage = view.findViewById(R.id.image);
        ivEdit = view.findViewById(R.id.iv_edit);
        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);

        tiName = view.findViewById(R.id.ti_name);
        tiContact = view.findViewById(R.id.ti_contact);
        tiOrganizationName = view.findViewById(R.id.ti_organization_name);
        tiAddress = view.findViewById(R.id.ti_address);

        etName = view.findViewById(R.id.et_name);
        etContact = view.findViewById(R.id.et_contact);
        etOrganizationName = view.findViewById(R.id.et_organization_name);
        etAddress = view.findViewById(R.id.et_address);

        btnCancel = view.findViewById(R.id.btn_cancel);
        btnUpdate = view.findViewById(R.id.btn_update);

        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        ivEdit.setOnClickListener(this);


        bind(communicator.getUser());
    }

    private void bind(User user) {
        if(user.getImage()!=null && !user.getImage().equals("")){
            Picasso.get().load(user.getImage()).into(ivImage);
        }

        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());

        etName.setText(user.getName());
        etContact.setText(user.getContact());
        etOrganizationName.setText(user.getOrganization_name());
        etAddress.setText(user.getAddress());
    }

    @Override
    public void onResume() {
        super.onResume();
        communicator.setTitle(communicator.getUser().getName().concat(" Profile"));
        if(communicator.getUserBitmap()!=null){
            ivImage.setImageBitmap(communicator.getUserBitmap());
        }
    }

    @Override
    public void onClick(View v) {
        if(v==btnCancel){
            communicator.showHomeScene();
        }else if(v==btnUpdate){
//            communicator.updateUser()
            User user = getUserFormFormData();
            boolean valid = validate(user);

            if(!valid){
                return;
            }

            communicator.updateUserInServer(user);
        }else if(v==ivEdit){
            communicator.openCropImageActivity();
        }
    }

    private User getUserFormFormData() {
        User user = communicator.getUser();
        user.setName(etName.getText().toString());
        user.setContact(etContact.getText().toString());
        user.setOrganization_name(etOrganizationName.getText().toString());
        user.setAddress(etAddress.getText().toString());

        return user;
    }

    private void clearPreErrors(){
        tiName.setErrorEnabled(false);
        tiContact.setErrorEnabled(false);
        tiOrganizationName.setErrorEnabled(false);
        tiAddress.setErrorEnabled(false);
    }

    private void showErrorMessage(int fieldId,String message){

        switch (fieldId){
            case 1:
                etName.requestFocus();
                tiName.setError(message);
                break;

            case 2:
                etContact.requestFocus();
                tiContact.setError(message);
                break;

            case 3:
                etOrganizationName.requestFocus();
                tiOrganizationName.setError(message);
                break;

            case 4:
                etAddress.requestFocus();
                tiAddress.setError(message);
                break;
        }

    }




    private boolean validate(User user){
        clearPreErrors();

        if(user.getName()==null || user.getName().equals("")){
            showErrorMessage(1,"Please Enter Your Name");
            return false;
        }

        if(user.getContact()==null || user.getContact().equals("")){
            showErrorMessage(2,"Contact Number Empty");
            return false;
        }

        if(user.getOrganization_name()==null || user.getOrganization_name().equals("")){
            showErrorMessage(3,"Organization Name Empty");
            return false;
        }

        if(user.getAddress()==null || user.getAddress().equals("")){
            showErrorMessage(4,"Address Should not Empty");
            return false;
        }

        return true;
    }
}