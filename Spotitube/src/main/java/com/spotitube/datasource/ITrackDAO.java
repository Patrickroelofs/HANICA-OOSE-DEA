package com.spotitube.datasource;

import com.spotitube.domain.Track;

import java.util.List;

public interface ITrackDAO {
    List<Track> getAllTracks(int forPlaylist, boolean toggler);

    void deleteTrack(int playlistId, int trackId);

    void addTrackToPlaylist(int playlistId, int id, boolean offlineAvailable);
}
