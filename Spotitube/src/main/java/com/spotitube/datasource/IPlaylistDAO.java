package com.spotitube.datasource;

import com.spotitube.controller.dto.PlaylistDTO;

public interface IPlaylistDAO {

    void addPlaylist(String playlistName, String username, String token);
}
