package it.clyp.clyp.API.Structure;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadsResponse {

    @SerializedName("Data")
    @Expose
    private List<User> data = null;
    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("Paging")
    @Expose
    private Paging paging;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}