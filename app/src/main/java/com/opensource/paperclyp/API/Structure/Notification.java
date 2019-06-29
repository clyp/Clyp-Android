package com.opensource.paperclyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

public class Notification {

    @SerializedName("NotificationId")
    @Expose
    private String notificationId;
    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("Acknowledged")
    @Expose
    private Boolean acknowledged;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("TargetUrl")
    @Expose
    private String targetUrl;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}