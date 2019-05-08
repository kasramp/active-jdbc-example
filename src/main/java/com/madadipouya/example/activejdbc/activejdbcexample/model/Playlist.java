package com.madadipouya.example.activejdbc.activejdbcexample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("playlists")
public class Playlist extends Model {

    private static final String PLAYLIST_NAME_FIELD = "name";

    static {
        validatePresenceOf(PLAYLIST_NAME_FIELD);
    }

    public Playlist() {

    }

    public Playlist(String name) {
        set(PLAYLIST_NAME_FIELD, name);
    }

    public Playlist merge(String name) {
        return set(PLAYLIST_NAME_FIELD, name);
    }
}
