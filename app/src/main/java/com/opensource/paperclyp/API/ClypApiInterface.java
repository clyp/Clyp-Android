package com.opensource.paperclyp.API;

import java.util.List;

import com.opensource.paperclyp.API.Response.AuthResponse;
import com.opensource.paperclyp.API.Response.CategoryResponse;
import com.opensource.paperclyp.API.Response.NotificationResponse;
import com.opensource.paperclyp.API.Response.UploadsResponse;
import com.opensource.paperclyp.API.Structure.Notification;
import com.opensource.paperclyp.API.Structure.Playlist;
import com.opensource.paperclyp.API.Structure.Track;
import com.opensource.paperclyp.API.Structure.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by lite20 on 7/16/2017.
 */

public interface ClypApiInterface {
    @Headers({"Authorization: Basic MjkzMTE5Og==","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<AuthResponse> getAuthToken(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password
    );

    @Headers({"Authorization: Basic MjkzMTE5Og==","Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<AuthResponse> refreshToken(
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
    );

    @GET("v2/me/uploads")
    Call<UploadsResponse> getUploads(
            @Header("Authorization") String auth_token
    );

    @GET("/notifications")
    Call<NotificationResponse> getNotifications(
            @Header("Authorization") String auth_token
    );

    @GET("/notifications")
    Call<NotificationResponse> getSetofNotifications(
            @Header("Authorization") String auth_token
    );

    @GET("/notifications/{noteid}/acknowledgement")
    Call<List<Notification>> acknowledgeNotification(
            @Header("Authorization") String auth_token,
            @Path("noteid") String note_id
    );

    @GET("me")
    Call<User> getSelf(
            @Header("Authorization") String auth_token
    );

    @GET("featuredlist/featured")
    Call<List<Track>> getFeaturedTracks();

    @GET("featuredlist/popular")
    Call<List<Track>> getPopularTracks();

    @GET("featuredlist/recent")
    Call<List<Track>> getRecentTracks();

    @GET("categorylist")
    Call<List<CategoryResponse>> getCategoryList();

    @GET("categorylist/{category}")
    Call<List<Track>> getCategoryTrackList(@Path("category") String category);

    @GET()
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @GET("{playlistId}/playlist")
    Call<Playlist> getPlaylist(@Path("playlistId") String playlistId);

    @GET("{trackId")
    Call<Track> getTrackMetadata(@Path("trackId") String trackId);

    @GET("user/{userId}")
    Call<User> getUserMetaData(@Path("userId") String userId);
}
