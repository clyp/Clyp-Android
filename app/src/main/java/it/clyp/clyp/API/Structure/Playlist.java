package it.clyp.clyp.API.Structure;

import java.util.List;

/**
 * Created by lite20 on 7/17/2017.
 */

public class Playlist {
    private String playlistId;
    private String featureSubmissionEligibility;

    private List<Track> audioFiles;

    private boolean modifiable;
    private boolean contentAdministrator;

    public Playlist() {

    }

    public String getPlaylistId() {
        return playlistId;
    }

    public List<Track> getAudioFiles() {
        return audioFiles;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public boolean isContentAdministrator() {
        return contentAdministrator;
    }

    public String getFeatureSubmissionEligibility() {
        return featureSubmissionEligibility;
    }
}