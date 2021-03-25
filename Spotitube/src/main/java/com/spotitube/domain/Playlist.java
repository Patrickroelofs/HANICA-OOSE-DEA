package com.spotitube.domain;


public class Playlist {
    private int id;
    private String name;
    private boolean owner;
    private int duration;

    public Playlist(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Playlist(String name) {
        this.name = name;
    }

    public Playlist() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
