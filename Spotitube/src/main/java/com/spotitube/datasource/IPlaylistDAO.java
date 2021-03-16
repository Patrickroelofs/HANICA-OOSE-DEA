package com.spotitube.datasource;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.domain.Playlist;

import java.util.List;

public interface IPlaylistDAO {

    void addPlaylist(String playlistName, String username, String token);

    List<Playlist> getAllPlaylists(String token);

    void deletePlaylist(int id);

    void editPlaylist(String name, int id, String token);
}
