package com.madadipouya.example.activejdbc.activejdbcexample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("songs")
public class Song extends Model {

    // Should be able to retrieve U from S

    private static final String TITLE_FIELD = "title";

    private static final String ARTIST_FIELD = "artist";

    private static final String USER_ID_FIELD = "user_id";


    static {
        validatePresenceOf(TITLE_FIELD, ARTIST_FIELD);
    }

    public Song() {

    }

    public Song(String title, String artist) {
        set(TITLE_FIELD, title, ARTIST_FIELD, artist);
    }

    public Song merge(String title, String artist) {
        return set(TITLE_FIELD, title, ARTIST_FIELD, artist);
    }
}
