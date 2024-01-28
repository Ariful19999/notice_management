package com.arif19.vbd.recycleview;

import static com.arif19.vbd.public_url.PublicUrl.rootUrl;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arif19.vbd.PostActivity;
import com.arif19.vbd.R;
import com.arif19.vbd.modal.NewsFeedItem;
import com.bumptech.glide.Glide;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private List<NewsFeedItem> newsFeedItems; // Replace NewsFeedItem with your actual data model
    private Context context;

    public NewsFeedAdapter(Context context, List<NewsFeedItem> newsFeedItems) {
        this.context = context;
        this.newsFeedItems = newsFeedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Retrieve the current NewsFeedItem from the list
        NewsFeedItem currentPost = newsFeedItems.get(position);


        // Set reporter's name

        if(currentPost.getReporterName().equals("")){
            holder.postTitle.setVisibility(View.GONE);
        }else if(currentPost.getReporterName().equals("null")){
            holder.postTitle.setText("user");
        }
        else{
            holder.postTitle.setText(currentPost.getReporterName());
        }

        // Set post text
        if(currentPost.getPostText().equals("")){
            holder.textPost.setVisibility(View.GONE);
        }else{
            if(currentPost.getPostText().equals("null")){
                holder.textPost.setVisibility(View.GONE);
            }else{
                holder.textPost.setText(currentPost.getPostText());
            }
        }

        // set post date

        holder.postDate.setText(currentPost.getPostDate());
//        if(!currentPost.getPostDate().equals("null")){
//            holder.postDate.setText(currentPost.getPostDate());
//        }else{
//            holder.postDate.setVisibility(View.GONE);
//        }


//         Check if there are images to display
//         Calculate the width based on the device width
        int deviceWidth = getDeviceWidth(); // Implement getDeviceWidth() method
        List<String> imageUrls = currentPost.getImageUrls(); // Get image URLs for the post

        if (imageUrls != null && !imageUrls.isEmpty()) {
            // Show the layout for multiple images
            holder.layoutImages.setVisibility(View.VISIBLE);

            // Clear any previous ImageViews
            holder.layoutImages.removeAllViews();

            // Create ImageViews and load images into them using Glide
            for (String imageUrl : imageUrls) {
                ImageView imageView = new ImageView(context);

                // Set the aspect ratio for the images (adjust as needed)
                float aspectRatio = 1.2f; // Example aspect ratio (width:height)

                // Calculate the height based on the aspect ratio
                int calculatedHeight = Math.round(deviceWidth / aspectRatio);

                // Set the calculated width and height to the ImageView
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(deviceWidth, calculatedHeight);
                layoutParams.setMargins(0, 0, 5, 5);
                imageView.setLayoutParams(layoutParams);

                // Load image into the ImageView using Glide
                Glide.with(context)
                        .load(imageUrl)
                        //.placeholder(R.drawable.placeholder_image) // Placeholder image
                       // .error(R.drawable.error_image) // Error image if loading fails
                        .centerCrop()
                        .into(imageView);

                // Add the ImageView to the layout
                holder.layoutImages.addView(imageView);
            }

        } else {
            // Hide the layout for multiple images if no images are present
            holder.layoutImages.setVisibility(View.GONE);
        }




        // Check if the post contains a video URL
        if (currentPost.getVideoUrl() != null && !currentPost.getVideoUrl().isEmpty()) {
            holder.videoView.setVisibility(View.VISIBLE);
            String videoPath =currentPost.getVideoUrl();
            // String videoPath = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_50mb%20-%20Copy.mp4";
            // Convert the path to a Uri to play the video
            Uri uri = Uri.parse(videoPath);
            holder.videoView.setVideoURI(uri);

            //  holder.videoView.setVideoPath(currentPost.getVideoUrl());

            holder.videoView.requestFocus();

            // Create an instance of MediaController using the provided context
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            // Set MediaController to the VideoView
            holder.videoView.setMediaController(mediaController);
            // holder.videoView.start();

        } else {
            holder.videoView.setVisibility(View.GONE);
        }

        /// set reporter image

        String avatarImageUrl = currentPost.getAvatarImage();
        Glide.with(context)
                .load(avatarImageUrl)
                .centerCrop()
                .placeholder(R.drawable.single_avatar)
                .error(R.drawable.single_avatar)
                .into(holder.avatarImage);


        if(currentPost.isVideoActivity()){
            holder.layoutImages.setVisibility(View.GONE);
            holder.textPost.setVisibility(View.GONE);
        }

        if(currentPost.isVideoActivity() && (currentPost.getVideoUrl() == null || currentPost.getVideoUrl().isEmpty())){
            holder.postCard.setVisibility(View.GONE);
            holder.postCard.setVisibility(View.INVISIBLE);
        }


        // Other bindings...

    }

    @Override
    public int getItemCount() {
        return newsFeedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView textPost;
        LinearLayout layoutImages;
        VideoView videoView;
        CircleImageView avatarImage;
        TextView postDate;
        CardView postCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.postAvatar);;
            postTitle = itemView.findViewById(R.id.postTitle);
            textPost = itemView.findViewById(R.id.textPost);
            layoutImages = itemView.findViewById(R.id.layoutImages);
            videoView = itemView.findViewById(R.id.videoView);
            postDate = itemView.findViewById(R.id.postDate);
            postCard = itemView.findViewById(R.id.postCard);
            // Other view initialization...
        }
    }
    // Method to get the device width
    private int getDeviceWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }

        return 0; // Return a default value if unable to get the device width
    }


}
