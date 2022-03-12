package com.forbitbd.accountmanager.ui.main.scenes.share_business;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;

import java.util.ArrayList;
import java.util.List;

public class ShareBusinessAdapter extends RecyclerView.Adapter<ShareBusinessAdapter.ShareBusinessHolder> {

    private ShareBusinessContract.View mView;
    private List<SharedProject> sharedProjectList;

    public ShareBusinessAdapter(ShareBusinessContract.View mView) {
        this.mView = mView;
        this.sharedProjectList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ShareBusinessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_business,parent,false);
        return new ShareBusinessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareBusinessHolder holder, int position) {
        SharedProject sharedProject = sharedProjectList.get(position);
        holder.bind(sharedProject);
    }

    @Override
    public int getItemCount() {
        return sharedProjectList.size();
    }

    public void addSharedProjects(List<SharedProject> sharedProjectList){
        this.sharedProjectList = sharedProjectList;
        notifyDataSetChanged();
    }

    public void remove(SharedProject sharedProject){
        int pos = sharedProjectList.indexOf(sharedProject);
        sharedProjectList.remove(pos);
        notifyItemRemoved(pos);
    }

    class ShareBusinessHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvBusinessName,tvBusinessLocation;
        private ImageView ivDelete,ivView;

        public ShareBusinessHolder(@NonNull View itemView) {
            super(itemView);
            tvBusinessLocation = itemView.findViewById(R.id.tv_business_address);
            tvBusinessName = itemView.findViewById(R.id.tv_business_name);
            ivDelete = itemView.findViewById(R.id.delete);
            ivView = itemView.findViewById(R.id.view);

            ivDelete.setOnClickListener(this);
            ivView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view==itemView || view==ivView){
                mView.startFinanceActivity(sharedProjectList.get(getAdapterPosition()));
            }else if(view==ivDelete){
                mView.deleteSharedProject(sharedProjectList.get(getAdapterPosition()));
            }

        }

        public void bind(SharedProject sharedProject){
            Project project = sharedProject.getProject();
            if(project!=null){
                tvBusinessName.setText(project.getName());
                tvBusinessLocation.setText(project.getLocation());
            }
        }
    }

}
