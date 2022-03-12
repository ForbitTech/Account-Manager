package com.forbitbd.accountmanager.ui.main.scenes.search_users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> implements Filterable {

    private UserClickListener listener;
    private int layoutId;
    private List<User> originalList;
    private List<User> userList;

    public UserAdapter(UserClickListener listener, int layoutId) {
        this.listener = listener;
        this.layoutId = layoutId;
        this.userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    public void addAll(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        if(originalList==null){
            originalList = userList;
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<User> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<User> tmpList = new ArrayList<>();
                    for (User x: originalList){
                        if(x.getEmail().toLowerCase().startsWith(charString)){
                            tmpList.add(x);
                        }
                    }

                    filteredList = tmpList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivImage;
        TextView tvName,tvContact;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(com.forbitbd.androidutils.R.id.image);
            tvName = itemView.findViewById(com.forbitbd.androidutils.R.id.name);
            tvContact = itemView.findViewById(com.forbitbd.androidutils.R.id.contact);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onUserClick(userList.get(getAdapterPosition()));
        }

        public void bind(User user){
            if(user.getImage()!=null && !user.getImage().equals("")){
                Picasso.get()
                        .load((user.getImage()))
                        .into(ivImage);
            }


            tvName.setText(user.getName());
            tvContact.setText(user.getContact());
        }
    }


    public interface UserClickListener{
        void onUserClick(User user);
    }
}
