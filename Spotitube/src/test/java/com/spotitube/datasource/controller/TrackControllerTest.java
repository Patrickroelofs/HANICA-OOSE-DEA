package com.spotitube.datasource.controller;

import com.spotitube.controller.TrackController;
import com.spotitube.datasource.ITokenDAO;
import com.spotitube.datasource.ITrackDAO;
import com.spotitube.mapper.DataMapper;
import org.junit.jupiter.api.BeforeEach;

public class TrackControllerTest {
    public String TOKEN = "111-111-111";

    TrackController trackController;
    DataMapper dataMapper;
    ITokenDAO tokenDAO;
    ITrackDAO trackDAO;

    @BeforeEach
    public void setup() {
        trackController = new TrackController();
    }
}
