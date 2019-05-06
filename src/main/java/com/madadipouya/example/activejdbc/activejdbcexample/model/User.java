package com.madadipouya.example.activejdbc.activejdbcexample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("users")
public class User extends Model {

    // Should be able to retrieve list of S belong to U

    private static final String FIRST_NAME_FIELD = "first_name";

    private static final String LAST_NAME_FIELD = "last_name";

    private static final String EMAIL_ADDRESS_FIELD = "email_address";

    static {
        validatePresenceOf(FIRST_NAME_FIELD, LAST_NAME_FIELD, EMAIL_ADDRESS_FIELD);
        validateEmailOf(EMAIL_ADDRESS_FIELD);

    }

    public User() {

    }

    public User(String firstName, String lastName, String emailAddress) {
        set(FIRST_NAME_FIELD, firstName, LAST_NAME_FIELD, lastName, EMAIL_ADDRESS_FIELD, emailAddress);
    }

    public User merge(String firstName, String lastName, String emailAddress) {
        return set(FIRST_NAME_FIELD, firstName, LAST_NAME_FIELD, lastName, EMAIL_ADDRESS_FIELD, emailAddress);
    }
}
