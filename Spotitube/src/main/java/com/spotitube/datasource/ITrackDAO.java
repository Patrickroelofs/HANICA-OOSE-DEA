package com.spotitube.datasource;

import com.spotitube.domain.Track;

import java.util.List;

public interface ITrackDAO {
    List<Track> getAllTracks(int forPlaylist, String token);
}
