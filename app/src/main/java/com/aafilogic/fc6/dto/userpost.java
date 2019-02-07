package com.aafilogic.fc6.dto;

public class userpost {
    private String userimage;
    private String userPostText;
    private String postimage;

    public userpost(String userimage, String userPostText, String postimage) {
        this.userimage = userimage;
        this.userPostText = userPostText;
        this.postimage = postimage;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUserPostText() {
        return userPostText;
    }

    public void setUserPostText(String userPostText) {
        this.userPostText = userPostText;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }
}
