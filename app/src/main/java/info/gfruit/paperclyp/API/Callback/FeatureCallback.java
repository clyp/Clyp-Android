package info.gfruit.paperclyp.API.Callback;

import java.util.List;

import info.gfruit.paperclyp.API.Structure.Track;

/**
 * Created by lite20 on 7/17/2017.
 */

public interface FeatureCallback {

    void onComplete(String error, List<Track> body, int item);
}
