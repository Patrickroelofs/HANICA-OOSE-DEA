package com.spotitube.datasource;

import com.spotitube.domain.Track;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

public interface ITrackDAO {
    ArrayList<Track> getAllTracksNotInPlaylist(int forPlaylist) throws InternalServerErrorException;

    ArrayList<Track> getAllTracks(int forPlaylist);

    void deleteTrack(int playlistId, int trackId);

    void addTrackToPlaylist(int playlistId, int id, boolean offlineAvailable);
}
