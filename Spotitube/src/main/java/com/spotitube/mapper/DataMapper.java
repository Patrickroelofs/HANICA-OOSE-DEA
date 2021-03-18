package com.spotitube.mapper;

import com.spotitube.controller.dto.*;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TrackDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.domain.Track;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    @Inject
    PlaylistDAO playlistDAO;

    @Inject
    TrackDAO trackDAO;

    public PlaylistsDTO mapPlaylistDTO(String token) {
        List<Playlist> playlists = playlistDAO.getAllPlaylists(token);
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

    public TracksDTO mapTracksDTO(int forPlaylist, boolean toggler) {
        List<Track> tracks = trackDAO.getAllTracks(forPlaylist, toggler);
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
}
