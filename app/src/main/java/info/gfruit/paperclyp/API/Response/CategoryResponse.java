package info.gfruit.paperclyp.API.Response;

import java.util.List;

import info.gfruit.paperclyp.API.ClypApi;
import info.gfruit.paperclyp.API.Structure.Track;
import info.gfruit.paperclyp.API.Callback.StandardCallback;

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
