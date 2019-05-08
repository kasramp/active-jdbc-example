package com.madadipouya.example.activejdbc.activejdbcexample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

@Many2Many(other = Playlist.class, join = "playlist_song", sourceFKName = "song_id", targetFKName = "playlist_id")
@Table("songs")
public class Song extends Model {

    private static final String TITLE_FIELD = "title";

    private static final String ARTIST_FIELD = "artist";

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
