package com.spotitube.datasource;

import com.spotitube.domain.Track;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;

public interface ITrackDAO {
    ArrayList<Track> getAllTracksNotInPlaylist(int forPlaylist) throws InternalServerErrorException;

    ArrayList<Track> getAllTracks(int forPlaylist);

    boolean deleteTrack(int playlistId, int trackId);

    boolean addTrackToPlaylist(int playlistId, int id, boolean offlineAvailable);
}
