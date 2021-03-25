package com.spotitube.datasource.mapper;

import com.spotitube.domain.Track;
import com.spotitube.service.dto.PlaylistsDTO;
import com.spotitube.service.dto.TracksDTO;
import com.spotitube.datasource.dao.PlaylistDAO;
import com.spotitube.datasource.dao.TrackDAO;
import com.spotitube.datasource.dao.UserDAO;
import com.spotitube.domain.Playlist;
import com.spotitube.service.dto.DTOMapper.DTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class DTOMapperTest {
    private final String TOKEN = "111-111-111";
    private final String PLAYLIST_NAME = "Playlist1";
    private final int PLAYLIST_ID = 1;
    private final String USERNAME = "patrick";

    public int TRACK_ID = 1;
    public final String TRACK_TITLE = "Track Title";
    public final String TRACK_PERFORMER = "Performer";
    public String PLAYLIST_TITLE = "PlaylistTitle";
    public String PLAYLIST_PERFORMER = "PlaylistTitle";
    public final int PLAYLIST_DURATION = 100;
    public final String PLAYLIST_ALBUM = "PlaylistTitle";
    public int PLAYLIST_PLAYCOUNT = 5;
    public final String PLAYLIST_PUBLICATIONDATE = "PlaylistTitle";
    public final String PLAYLIST_DESCRIPTION = "PlaylistTitle";
    public final boolean PLAYLIST_OFFLINEAVAILABLE = false;


    private DTOMapper DTOMapper;


    @BeforeEach
    public void setup() {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        UserDAO userDAO = new UserDAO();
        PlaylistDAO playlistDAO = new PlaylistDAO();
        TrackDAO trackDAO = new TrackDAO();
        DTOMapper = new DTOMapper();
        PlaylistDAO mockPlaylistDAO = Mockito.spy(playlistDAO);
        TrackDAO mockTrackDAO = Mockito.spy(trackDAO);

        userDAO.setDataSource(dataSource);
        playlistDAO.setDataSource(dataSource);
        trackDAO.setDataSource(dataSource);
        DTOMapper.setPlaylistDAO(playlistDAO);
        DTOMapper.setTrackDAO(trackDAO);
    }

    @Test
    public void mapPlaylistToPlaylistsDTOTest() {
        try {
            ArrayList<Playlist> playlists = new ArrayList<>();
            Playlist playlist = new Playlist();
            playlist.setId(1);
            playlist.setDuration(PLAYLIST_DURATION);
            playlists.add(playlist);

            PlaylistsDTO playlistsResult = DTOMapper.mapPlaylistToPlaylistsDTO(playlists);

            assertEquals(1, playlistsResult.playlists.get(0).id);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void mapTracksToTracksDTOTest() {
        try {
            ArrayList<Track> tracks = new ArrayList<>();
            Track track = new Track();
            track.setId(1);
            track.setTitle(TRACK_TITLE);
            track.setPerformer(TRACK_PERFORMER);
            track.setDuration(PLAYLIST_DURATION);
            track.setAlbum(PLAYLIST_ALBUM);
            track.setPlaycount(500);
            track.setPublicationDate(PLAYLIST_PUBLICATIONDATE);
            track.setDescription(PLAYLIST_DESCRIPTION);
            track.setOfflineAvailable(PLAYLIST_OFFLINEAVAILABLE);

            tracks.add(track);

            TracksDTO tracksResult = DTOMapper.mapTracksToTracksDTO(tracks);

            assertEquals(1, tracksResult.tracks.get(0).id);
        } catch (Exception e) {
            fail(e);
        }
    }
}
