package it.clyp.clyp.Util;

import java.util.ArrayList;
import java.util.List;

import it.clyp.clyp.API.Structure.Track;

/**
 * Created by lite20 on 7/19/2017.
 */

public class TrackUtil {
    public static List<String> getDiscographyUrls(List<Track> t) {
        List<String> urls = new ArrayList<String>(t.size());
        for(int i = 0; i < t.size(); i++) {
            urls.add(t.get(i).getDiscographyUrl());
        }

        return urls;
    }
}
