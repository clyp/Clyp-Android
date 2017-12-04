package info.gfruit.paperclyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

public class User {

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("EmailAddressVerified")
    @Expose
    private Boolean emailAddressVerified;
    @SerializedName("EmailMarketingEnabled")
    @Expose
    private Boolean emailMarketingEnabled;
    @SerializedName("ProfilePictureUrl")
    @Expose
    private String profilePictureUrl;
    @SerializedName("PublicProfileVisible")
    @Expose
    private Boolean publicProfileVisible;
    @SerializedName("PublicProfileUrl")
    @Expose
    private String publicProfileUrl;
    @SerializedName("SubscriptionState")
    @Expose
    private String subscriptionState;
    @SerializedName("ContentAdministrator")
    @Expose
    private Boolean contentAdministrator;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("DefaultAudioFileStatus")
    @Expose
    private String defaultAudioFileStatus;
    @SerializedName("Biography")
    @Expose
    private String biography;
    @SerializedName("NotificationsSummary")
    @Expose
    private NotificationsSummary notificationsSummary;
    @SerializedName("OutgoingUserRelationship")
    @Expose
    private OutgoingUserRelationship outgoingUserRelationship;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getEmailAddressVerified() {
        return emailAddressVerified;
    }

    public void setEmailAddressVerified(Boolean emailAddressVerified) {
        this.emailAddressVerified = emailAddressVerified;
    }

    public Boolean getEmailMarketingEnabled() {
        return emailMarketingEnabled;
    }

    public void setEmailMarketingEnabled(Boolean emailMarketingEnabled) {
        this.emailMarketingEnabled = emailMarketingEnabled;
    }

    public String getProfilePictureUrl() {
        return (profilePictureUrl != null) ? profilePictureUrl : "https://d2cjvbryygm0lr.cloudfront.net/default-profile-picture-2.png";
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Boolean getPublicProfileVisible() {
        return publicProfileVisible;
    }

    public void setPublicProfileVisible(Boolean publicProfileVisible) {
        this.publicProfileVisible = publicProfileVisible;
    }

    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    public void setPublicProfileUrl(String publicProfileUrl) {
        this.publicProfileUrl = publicProfileUrl;
    }

    public String getSubscriptionState() {
        return subscriptionState;
    }

    public void setSubscriptionState(String subscriptionState) {
        this.subscriptionState = subscriptionState;
    }

    public Boolean getContentAdministrator() {
        return contentAdministrator;
    }

    public void setContentAdministrator(Boolean contentAdministrator) {
        this.contentAdministrator = contentAdministrator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDefaultAudioFileStatus() {
        return defaultAudioFileStatus;
    }

    public void setDefaultAudioFileStatus(String defaultAudioFileStatus) {
        this.defaultAudioFileStatus = defaultAudioFileStatus;
    }

    public NotificationsSummary getNotificationsSummary() {
        return notificationsSummary;
    }

    public void setNotificationsSummary(NotificationsSummary notificationsSummary) {
        this.notificationsSummary = notificationsSummary;
    }

    public OutgoingUserRelationship getOutgoingUserRelationship() {
        return outgoingUserRelationship;
    }

    public void setOutgoingUserRelationship(OutgoingUserRelationship outgoingUserRelationship) {
        this.outgoingUserRelationship = outgoingUserRelationship;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}