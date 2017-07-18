package it.clyp.clyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

public class OutgoingUserRelationship {

    @SerializedName("Status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}