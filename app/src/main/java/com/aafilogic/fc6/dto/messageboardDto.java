package com.aafilogic.fc6.dto;

public class messageboardDto {

    String messageimage;
    String messagename;
    String messagetext;


    public messageboardDto(String messageimage, String messagename, String messagetext) {
        this.messageimage = messageimage;
        this.messagename = messagename;
        this.messagetext = messagetext;
    }

    public String getMessageimage() {
        return messageimage;
    }

    public void setMessageimage(String messageimage) {
        this.messageimage = messageimage;
    }

    public String getMessagename() {
        return messagename;
    }

    public void setMessagename(String messagename) {
        this.messagename = messagename;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }
}
