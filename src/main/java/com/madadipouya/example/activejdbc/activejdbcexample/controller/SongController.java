package com.madadipouya.example.activejdbc.activejdbcexample.controller;

import com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions.SongNotFoundException;
import com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions.UserNotFoundException;
import com.madadipouya.example.activejdbc.activejdbcexample.model.Song;
import com.madadipouya.example.activejdbc.activejdbcexample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RequestMapping("/v1/songs")
@RestController
public class SongController {

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Map<String, Object>> getSongs(@PathVariable int userId) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            return user.getAll(Song.class).toMaps();
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}")
    public Map<String, Object> createSong(@PathVariable int userId, @Valid @RequestBody SongDto songDto) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            Song song = new Song(songDto.title, songDto.artist);
            user.add(song);
            return song.toMap();
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{songId}")
    public Map<String, Object> updateSong(@PathVariable int songId, @Valid @RequestBody SongDto songDto) throws SongNotFoundException {
        Song song = Song.findById(songId);
        if (song != null) {
            song.merge(songDto.title, songDto.artist);
            if (song.saveIt()) {
                return song.toMap();
            }
            return null;
        }
        throw new SongNotFoundException(String.format("There is no song associated with id: %s", songId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{songId}")
    public void deleteSong(@PathVariable int songId) throws SongNotFoundException {
        Song song = Song.findById(songId);
        if (song != null) {
            song.deleteCascadeShallow();
        } else {
            throw new SongNotFoundException(String.format("There is no song associated with id: %s", songId));
        }
    }

    public static class SongDto {

        @NotBlank
        private String title;

        @NotBlank
        private String artist;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
    }
}
