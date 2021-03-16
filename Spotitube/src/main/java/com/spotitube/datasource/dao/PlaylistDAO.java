package com.spotitube.datasource.dao;

import com.spotitube.controller.dto.PlaylistDTO;
import com.spotitube.controller.dto.PlaylistsDTO;
import com.spotitube.datasource.IPlaylistDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {

  @Resource(name = "jdbc/spotitube")
  DataSource dataSource;
}
