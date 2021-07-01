package com.forbitbd.accountmanager.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.utils.BaseActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class NavDrawerFragment extends Fragment {

    private ImageView image;
    private TextView name, email;

    public NavDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (account!=null){
//            name.setText(account.getDisplayName());
//            email.setText(account.getEmail());
//            Picasso.with(getContext()).load(account.getPhotoUrl()).into(image);
//        }


        return view;
    }
}