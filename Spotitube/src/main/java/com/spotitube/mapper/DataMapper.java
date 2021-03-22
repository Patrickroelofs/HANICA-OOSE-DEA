package com.spotitube.mapper;

import com.spotitube.service.dto.*;
import com.spotitube.datasource.IPlaylistDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.domain.Track;

import javax.inject.Inject;
import java.util.ArrayList;

public class DataMapper {

    IPlaylistDAO playlistDAO;
    ITrackDAO trackDAO;

    public PlaylistsDTO mapPlaylistToPlaylistsDTO(String token) {
        ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists(token);
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int length = 0;
        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = playlist.getId();
            playlistDTO.name = playlist.getName();
            playlistDTO.owner = playlist.isOwner();
            playlistsDTO.playlists.add(playlistDTO);
            length += playlist.getDuration();
        }

        playlistsDTO.length = length;
        return playlistsDTO;
    }

    public TracksDTO mapTracksToTracksDTO(int forPlaylist, boolean toggler) {
        ArrayList<Track> tracks;

        if(toggler) {
            tracks = trackDAO.getAllTracks(forPlaylist);
        } else {
            tracks = trackDAO.getAllTracksNotInPlaylist(forPlaylist);
        }

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for (Track track : tracks) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = track.getId();
            trackDTO.title = track.getTitle();
            trackDTO.performer = track.getPerformer();
            trackDTO.duration = track.getDuration();
            trackDTO.album = track.getAlbum();
            trackDTO.playcount = track.getPlaycount();
            trackDTO.publicationDate = track.getPublicationDate();
            trackDTO.description = track.getDescription();
            trackDTO.offlineAvailable = track.getOfflineAvailable();

            tracksDTO.tracks.add(trackDTO);
        }
        return tracksDTO;
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO PlaylistDAO) {
        this.playlistDAO = PlaylistDAO;
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
