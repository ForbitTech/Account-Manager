package com.forbitbd.accountmanager.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.listener.ProjectClickListener;
import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.model.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {

    private Context context;
    private List<Project> projectList;
    private ProjectClickListener listener;

    public ProjectAdapter(Context context, List<Project> projectList, ProjectClickListener listener) {
        this.context = context;
        this.projectList = projectList;
        this.listener = listener;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent,false);
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ProjectHolder holder, int position) {
        Project project = projectList.get(position);
        holder.name.setText(project.getName());
        holder.location.setText(project.getLocation());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder {

        TextView name, location;

        public ProjectHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.business_name);
            location = itemView.findViewById(R.id.business_location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ItemCLick(projectList.get(getAdapterPosition()));
                }
            });
        }
    }
}
