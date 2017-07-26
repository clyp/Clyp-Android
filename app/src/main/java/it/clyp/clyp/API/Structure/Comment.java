package it.clyp.clyp.API.Structure;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("CommentId")
    @Expose
    private String commentId;
    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("IsEdited")
    @Expose
    private Boolean isEdited;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

}