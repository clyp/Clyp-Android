package it.clyp.clyp.API.Response;

/**
 * Created by lite20 on 7/26/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.clyp.clyp.API.Structure.Notification;

public class NotificationResponse {

    @SerializedName("Data")
    @Expose
    private List<Notification> data = null;

    public List<Notification> getData() {
        return data;
    }
}