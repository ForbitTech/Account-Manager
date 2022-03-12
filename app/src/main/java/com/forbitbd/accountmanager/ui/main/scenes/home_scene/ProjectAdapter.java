package com.forbitbd.accountmanager.ui.main.scenes.home_scene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.androidutils.models.Project;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {

    private List<Project> projectList;
    private ProjectClickListener listener;
    public ProjectAdapter(ProjectClickListener listener){
        this.listener = listener;
        this.projectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Project project = projectList.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }


    public void add(Project project){
        projectList.add(project);
        int position = projectList.indexOf(project);
        notifyItemInserted(position);
    }

    public void addAll(List<Project> projectList){
        this.projectList = projectList;
        notifyDataSetChanged();
    }


    class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvBusinessName,tvBusinessAddress;
        private ImageView ivDownArrow;
        private LinearLayout mHideLayout,mShowContainer;
        ImageView ivEdit,ivShare,ivUser,ivDelete;

        public ProjectHolder(@NonNull View itemView) {
            super(itemView);
            tvBusinessName = itemView.findViewById(R.id.tv_business_name);
            tvBusinessAddress = itemView.findViewById(R.id.tv_business_address);
            ivDownArrow = itemView.findViewById(R.id.down_arrow);
            mHideLayout = itemView.findViewById(R.id.hide_container);
            mShowContainer = itemView.findViewById(R.id.show_container);


            ivEdit = itemView.findViewById(R.id.edit);
            ivShare = itemView.findViewById(R.id.share);
            ivUser = itemView.findViewById(R.id.user);
            ivDelete = itemView.findViewById(R.id.delete);

            FontDrawable edit = new FontDrawable(itemView.getContext(), R.string.fa_edit_solid, true, false);
            FontDrawable delete = new FontDrawable(itemView.getContext(), R.string.fa_trash_solid, true, false);
            FontDrawable share = new FontDrawable(itemView.getContext(), R.string.fa_share_solid, true, false);
            FontDrawable users = new FontDrawable(itemView.getContext(), R.string.fa_users_solid, true, false);
            edit.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            delete.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            share.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            users.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
            ivEdit.setImageDrawable(edit);
            ivShare.setImageDrawable(share);
            ivUser.setImageDrawable(users);
            ivDelete.setImageDrawable(delete);

            ivDelete.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
            ivShare.setOnClickListener(this);
            ivUser.setOnClickListener(this);
            ivDownArrow.setOnClickListener(this);
            mShowContainer.setOnClickListener(this);
        }

        public void bind(Project project){
            tvBusinessName.setText(project.getName());
            tvBusinessAddress.setText(project.getLocation());
        }

        @Override
        public void onClick(View v) {
            if(v==ivDownArrow){
                if(ivDownArrow.getRotation()==0){
                    mShowContainer.animate()
                            .setInterpolator(new OvershootInterpolator())
                            .translationX(-mShowContainer.getWidth())
                            .setDuration(700)
                            .start();
                    ivDownArrow.animate().rotation(90f);
                }else{
                    mShowContainer.animate()
                            .setInterpolator(new OvershootInterpolator())
                            .translationX(0)
                            .setDuration(700)
                            .start();
                    ivDownArrow.animate().rotation(0f);
                }

            }else if(v==mShowContainer){
                listener.onProjectClick(projectList.get(getAdapterPosition()));
            }else if(v==ivEdit){
                listener.onEditClick(projectList.get(getAdapterPosition()));
            }else if(v==ivDelete){
                listener.onDeleteClick(projectList.get(getAdapterPosition()));
            }else if(v==ivShare){
                listener.onShareClick(projectList.get(getAdapterPosition()));
            }else if(v==ivUser){
                listener.onSharedUsersClick(projectList.get(getAdapterPosition()));
            }

        }
    }




    public interface ProjectClickListener{
        void onProjectClick(Project project);
        void onEditClick(Project project);
        void onDeleteClick(Project project);
        void onShareClick(Project project);
        void onSharedUsersClick(Project project);
    }

}
