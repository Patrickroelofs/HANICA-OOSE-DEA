package com.spotitube.datasource;

import com.spotitube.domain.Playlist;

import java.util.ArrayList;
import java.util.List;

public interface IPlaylistDAO {

    boolean addPlaylist(String playlistName, String username);

    ArrayList<Playlist> getAllPlaylists(String token);

    boolean deletePlaylist(int id);

    boolean editPlaylist(String name, int id);
}
