package com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions;

public class SongNotFoundException extends Exception {

    public SongNotFoundException(String message) {
        super(message);
    }
}
