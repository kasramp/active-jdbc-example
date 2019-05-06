package com.madadipouya.example.activejdbc.activejdbcexample.controller.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
}
