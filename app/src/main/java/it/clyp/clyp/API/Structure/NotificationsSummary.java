package it.clyp.clyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

public class NotificationsSummary {

    @SerializedName("Count")
    @Expose
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}