package it.clyp.clyp.Util;

import android.os.Handler;
import android.view.View;

import java.util.List;

import it.clyp.clyp.API.Structure.Track;

/**
 * Created by lite20 on 7/19/2017.
 */

public class PeriodicLabelSwitcher implements Runnable {
    private final Handler handler;

    private final int delay;
    private final int id;

    private final List<String> urls;

    private final List<Track> tracks;

    private final View rootView;

    private int offset = 0;

    public PeriodicLabelSwitcher(int id, View rootView, List<Track> tracks, int delay) {
        this.handler = new Handler();
        this.delay = delay;
        this.urls = TrackUtil.getDiscographyUrls(tracks);
        this.rootView = rootView;
        this.id = id;
        this.tracks = tracks;
    }

    @Override
    public void run() {
        CachedIconloader.setIcon(urls.get(offset), rootView, id, null);
        offset++;
        if(offset == urls.size()) {
            offset = 0;
        }
        // Repeat this the same runnable code block again in the number of seconds set in "this.delay"
        handler.postDelayed(this, this.delay);
    }

    public void start() {
        handler.post(this);
    }

    public Track getCurrentTrack() {
        // the value of offset is always 1 more than the current track
        // so we have to hop back a track to get the current one
        int prev = offset - 1;
        if(offset < 0) {
            offset = tracks.size() - 1;
        }

        return tracks.get(prev);
    }
}
