package com.forbitbd.accountmanager.ui.main.scenes.search_users;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.main.Communicator;
import com.forbitbd.androidutils.models.User;

import java.util.List;


public class SearchUsersScene extends Fragment implements SearchView.OnQueryTextListener,UserAdapter.UserClickListener, SearchUserSceneContract.View {


    private Communicator communicator;

    private UserAdapter adapter;
    private SearchUserScenePresenter mPresenter;



    public SearchUsersScene() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchUserScenePresenter(this);
        communicator = (Communicator) getActivity();
        adapter = new UserAdapter(this,R.layout.single_user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_users_scene, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView rvUsers = view.findViewById(R.id.user_recycler_view);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(adapter);

        SearchView mSearchView  = view.findViewById(R.id.recycler_view);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        communicator.setTitle("Search User for Share Project");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()==1){
            mPresenter.loadUsers(newText);
        }else{
            mPresenter.filter(newText);
        }
        return false;
    }

    @Override
    public void onUserClick(User user) {
        communicator.setSearchUser(user);
    }

    @Override
    public void renderAdapter(List<User> userList) {
        adapter.addAll(userList);
    }

    @Override
    public void filter(String txt) {
        adapter.getFilter().filter(txt);
    }

}