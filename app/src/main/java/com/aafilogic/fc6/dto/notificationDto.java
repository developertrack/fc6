package com.aafilogic.fc6.dto;

public class notificationDto {
    private String notificationDate;
    private String notificationImage;
    private String notificationText;

    public notificationDto(String notificationDate, String notificationImage, String notificationText) {
        this.notificationDate = notificationDate;
        this.notificationImage = notificationImage;
        this.notificationText = notificationText;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
