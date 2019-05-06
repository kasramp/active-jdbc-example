package com.madadipouya.example.activejdbc.activejdbcexample.controller;

import com.google.common.collect.ImmutableMap;
import com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions.UserNotFoundException;
import com.madadipouya.example.activejdbc.activejdbcexample.model.Playlist;
import com.madadipouya.example.activejdbc.activejdbcexample.model.User;
import com.madadipouya.example.activejdbc.activejdbcexample.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/v1/users")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Map<String, Object>> getUsers() {
        return User.findAll().toMaps();
    }

    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Map<?, ?>> getUsersWithDetails() {
        return User.findAll().stream().map(user -> {
            return ImmutableMap.builder()
                    .putAll(user.toMap())
                    .put("songs", user.getAll(Song.class).toMaps())
                    .put("playlists", user.getAll(Playlist.class).toMaps())
                    .build();
        }).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Map<String, Object> createUser(@Valid @RequestBody UserDto userDto) {
        User user = new User(userDto.firstName, userDto.lastName, userDto.emailAddress);
        if (user.saveIt()) {
            return user.toMap();
        }
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@PathVariable int userId) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            user.deleteCascade();
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    @PutMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) throws UserNotFoundException {
        User user = User.findById(userId);
        if (user != null) {
            user.merge(userDto.firstName, userDto.lastName, userDto.emailAddress);
            if (user.saveIt()) {
                return user.toMap();
            }
            return null;
        }
        throw new UserNotFoundException(String.format("There is no user associated with id: %s", userId));
    }

    public static class UserDto {
        @Email
        @NotBlank
        private String emailAddress;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
