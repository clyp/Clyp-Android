package info.gfruit.paperclyp.API.Structure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated with: http://www.jsonschema2pojo.org/
 * By lite20
 */

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