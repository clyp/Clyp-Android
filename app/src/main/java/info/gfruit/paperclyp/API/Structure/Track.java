package info.gfruit.paperclyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

public class Track {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("CommentsEnabled")
    @Expose
    private Boolean commentsEnabled;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("User")
    @Expose
    public User user;
    @SerializedName("AudioFileId")
    @Expose
    private String audioFileId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Duration")
    @Expose
    private Double duration;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("ArtworkPictureUrl")
    @Expose
    private String artworkPictureUrl;
    @SerializedName("Mp3Url")
    @Expose
    private String mp3Url;
    @SerializedName("SecureMp3Url")
    @Expose
    private String secureMp3Url;
    @SerializedName("OggUrl")
    @Expose
    private String oggUrl;
    @SerializedName("SecureOggUrl")
    @Expose
    private String secureOggUrl;
    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCommentsEnabled() {
        return commentsEnabled;
    }

    public void setCommentsEnabled(Boolean commentsEnabled) {
        this.commentsEnabled = commentsEnabled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAudioFileId() {
        return audioFileId;
    }

    public void setAudioFileId(String audioFileId) {
        this.audioFileId = audioFileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtworkPictureUrl() {
        return artworkPictureUrl;
    }

    public void setArtworkPictureUrl(String artworkPictureUrl) {
        this.artworkPictureUrl = artworkPictureUrl;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getSecureMp3Url() {
        return secureMp3Url;
    }

    public void setSecureMp3Url(String secureMp3Url) {
        this.secureMp3Url = secureMp3Url;
    }

    public String getOggUrl() {
        return oggUrl;
    }

    public void setOggUrl(String oggUrl) {
        this.oggUrl = oggUrl;
    }

    public String getSecureOggUrl() {
        return secureOggUrl;
    }

    public void setSecureOggUrl(String secureOggUrl) {
        this.secureOggUrl = secureOggUrl;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        // not all tracks will have a user attached (uploaded without an account) so we check first
        if(this.getUser() != null) {
            String name = "";
            // just in case there's no first name, we check for it
            if(this.getUser().getFirstName() != null) {
                name += this.getUser().getFirstName() + " ";
            }

            // not all users have last names so we check for a last name
            if(this.getUser().getLastName() != null) {
                name += this.getUser().getLastName();
            }

            // lastly we set the track artist name
            return name;
        } else {
            return null;
        }
    }

    public String getDiscographyUrl() {
        // not all tracks are uploaded with discography (artwork) so we compensate with their profile picture
        if (this.getArtworkPictureUrl() == null) {
            // check if there's an attached user, and if not we use the default profile image
            if (this.getUser() == null) {
                // TODO use local copy of placeholder image
                return "https://d2cjvbryygm0lr.cloudfront.net/default-profile-picture-2.png";
            } else {
                return this.getUser().getProfilePictureUrl();
            }
        } else {
            return this.getArtworkPictureUrl();
        }
    }
}