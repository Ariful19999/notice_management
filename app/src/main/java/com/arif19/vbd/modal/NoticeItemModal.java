package com.arif19.vbd.modal;

import java.util.List;

public class NoticeItemModal {
    private String reporterName;
    private String postText;


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    private int postId;


    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    private String postDate;
    private List<String> imageUrls;
    private String videoUrl; // Add a field for holding video URL
    private String avatarImage; // Add a field for holding video URL

    public boolean isVideoActivity() {
        return isVideoActivity;
    }

    public void setVideoActivity(boolean videoActivity) {
        isVideoActivity = videoActivity;
    }

    private boolean isVideoActivity=false;

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }



    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
