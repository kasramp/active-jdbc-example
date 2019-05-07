package com.madadipouya.example.activejdbc.activejdbcexample.controller;

import com.google.common.collect.ImmutableMap;
import com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions.PlaylistNotFoundException;
import com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions.UserNotFoundException;
import com.madadipouya.example.activejdbc.activejdbcexample.model.Playlist;
import com.madadipouya.example.activejdbc.activejdbcexample.model.Song;
import com.madadipouya.example.activejdbc.activejdbcexample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/v1/playlists")
@RestController
public class PlaylistController {

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Map<String, Object>> getPlaylists(@PathVariable int userId) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            return user.getAll(Playlist.class).toMaps();
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @GetMapping(value = "/{userId}/details")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Map<?, Object>> getPlaylistWithSongs(@PathVariable int userId) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            return user.getAll(Playlist.class).stream()
                    .map(playlist ->
                            ImmutableMap.builder()
                                    .putAll(playlist.toMap())
                                    .put("songs", playlist.getAll(Song.class).toMaps())
                                    .build())
                    .collect(Collectors.toList());
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}")
    public Map<String, Object> createPlaylist(@PathVariable int userId, @Valid @RequestBody PlaylistDto playlistDto) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            List<Song> songs = user.getAll(Song.class)
                    .stream().filter(song -> playlistDto.songIds.contains(song.getId()))
                    .collect(Collectors.toList());
            Playlist playlist = new Playlist(playlistDto.name);
            user.add(playlist);
            songs.forEach(playlist::add);
            return playlist.toMap();
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{playlistId}")
    public List<Map<?, Object>> updatePlaylist(@PathVariable int playlistId, @Valid @RequestBody PlaylistDto playlistDto) throws PlaylistNotFoundException {
        Playlist playlist = Playlist.findById(playlistId);
        if (playlist != null) {
            User user = User.findById(playlist.get("user_id"));
            List<Song> songs = user.getAll(Song.class)
                    .stream().filter(song -> playlistDto.songIds.contains(song.getId()))
                    .collect(Collectors.toList());
            playlist.merge(playlistDto.name);
            if (playlist.saveIt()) {
                songs.forEach(playlist::remove);
                songs.forEach(playlist::add);
            }
            return user.getAll(Playlist.class).stream().map(p -> ImmutableMap.builder()
                    .putAll(p.toMap()).put("songs", p.getAll(Song.class).toMaps())
                    .build()).collect(Collectors.toList());
        }
        throw new PlaylistNotFoundException(String.format("There is no playlist associated with id: %s", playlistId));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{playlistId}")
    public void deletePlaylist(@PathVariable int playlistId) throws PlaylistNotFoundException {
        Playlist playlist = Playlist.findById(playlistId);
        if (playlist != null) {
            playlist.deleteCascadeShallow();
        } else {
            throw new PlaylistNotFoundException(String.format("There is no playlist associated with id: %s", playlistId));
        }
    }

    public static class PlaylistDto {
        @NotBlank
        private String name;

        @NotNull
        private List<Integer> songIds;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getSongIds() {
            return songIds;
        }

        public void setSongIds(List<Integer> songIds) {
            this.songIds = songIds;
        }
    }
}
