package it.clyp.clyp.API.Response;

import java.util.List;

import it.clyp.clyp.API.ClypApi;
import it.clyp.clyp.API.Structure.Track;
import it.clyp.clyp.API.Callback.StandardCallback;

/**
 * Created by lite20 on 7/17/2017.
 */

public class CategoryResponse {
    private String title;
    private String location;

    private List<Track> tracks;

    public CategoryResponse() {

    }

    public void upgrade(ClypApi api) {
        api.getCategoryTracks(
                this.title,
                new StandardCallback() {
                    @Override
                    public void onComplete(String err, Object data) {
                        List<Track> trackList = (List<Track>) data;
                        if(err != null) {
                            // TODO alert user that something went wrong

                        } else {
                            tracks = trackList;
                        }
                    }
                }
        );

    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }
}
