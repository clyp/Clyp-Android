package info.gfruit.paperclyp.API.Structure;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponse {

    @SerializedName("Comment")
    @Expose
    private Comment comment;
    @SerializedName("Replies")
    @Expose
    private List<Object> replies = null;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Object> getReplies() {
        return replies;
    }

    public void setReplies(List<Object> replies) {
        this.replies = replies;
    }

}
