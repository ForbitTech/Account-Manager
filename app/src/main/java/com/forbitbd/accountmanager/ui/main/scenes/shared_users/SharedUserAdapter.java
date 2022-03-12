package com.forbitbd.accountmanager.ui.main.scenes.shared_users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.androidutils.models.SharedProject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class SharedUserAdapter extends RecyclerView.Adapter<SharedUserAdapter.SharedUserHolder> {


    private List<SharedProject> sharedProjectList;
    private SharedUserListener listener;
    private int layoutId;


    public SharedUserAdapter(SharedUserListener listener, int layoutId) {
        this.listener = listener;
        this.layoutId = layoutId;
        this.sharedProjectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SharedUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        return new SharedUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedUserHolder holder, int position) {
        SharedProject sharedProject = sharedProjectList.get(position);
        holder.bind(sharedProject);
    }

    @Override
    public int getItemCount() {
        return sharedProjectList.size();
    }

    public void addAll(List<SharedProject> sharedProjectList){
        this.sharedProjectList = sharedProjectList;
        notifyDataSetChanged();
    }

    public void remove(SharedProject sharedProject){
        int position = sharedProjectList.indexOf(sharedProject);
        sharedProjectList.remove(position);
        notifyItemRemoved(position);
    }

    class SharedUserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName,tvEmail;
        private ImageView ivImage;
        private ImageView btnDelete;

        public SharedUserHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            ivImage = itemView.findViewById(R.id.iv_image);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            FontDrawable delete = new FontDrawable(itemView.getContext(), R.string.fa_trash_solid, true, false);
            delete.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            btnDelete.setImageDrawable(delete);

            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onDeleteClick(sharedProjectList.get(getAdapterPosition()));
        }

        public void bind(SharedProject sharedProject){
            tvName.setText(sharedProject.getUser().getName());
            tvEmail.setText(sharedProject.getUser().getEmail());

            if(sharedProject.getUser().getImage()!=null && !sharedProject.getUser().getImage().equals("")){
                Picasso.get().load(sharedProject.getUser().getImage())
                        .into(ivImage);
            }
        }
    }


    public interface SharedUserListener{
        void onDeleteClick(SharedProject sharedProject);
    }
}
