package it.clyp.clyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("Next")
    @Expose
    private String next;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}