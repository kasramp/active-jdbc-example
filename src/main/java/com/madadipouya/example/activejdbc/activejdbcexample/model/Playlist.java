package com.madadipouya.example.activejdbc.activejdbcexample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("playlists")
public class Playlist extends Model {

    // Should be able to pull S lists and U from it
    private static final String PLAYLIST_NAME_FIELD = "name";
}
