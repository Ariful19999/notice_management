package com.arif19.vbd.recycleview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.arif19.vbd.R;
import com.arif19.vbd.modal.AllCommentModal;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllCommentAdapter extends RecyclerView.Adapter<AllCommentAdapter.ViewHolder> {

    private List<AllCommentModal> AllCommentModal; // Replace AllCommentModal with your actual data model
    private Context context;

    public AllCommentAdapter(Context context, List<AllCommentModal> allCommentModal) {
        this.context = context;
        this.AllCommentModal = allCommentModal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCommentModal currentPost = AllCommentModal.get(position);

        String avatarImageUrl = currentPost.getCommentorProfile();
        Glide.with(context)
                .load(avatarImageUrl)
                .centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.commentAvatar);

        holder.comentorName.setText(currentPost.getCommentorName());
        holder.comment.setText(currentPost.getComment());


    }

    @Override
    public int getItemCount() {
        return AllCommentModal.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView comentorName;
        TextView comment;
        CircleImageView commentAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAvatar = itemView.findViewById(R.id.commentAvatar);
            comentorName = itemView.findViewById(R.id.comentorName);
            comment = itemView.findViewById(R.id.comment);

        }
    }

}
