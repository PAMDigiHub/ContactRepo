package ca.jhoffman.contactsrepo.model;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by jhoffman on 16-09-08.
 */
public class Contact {
    private int id;
    private String firstname;
    private String lastname;
    private String email;

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String getEmail() {
        return email;
    }

    public Contact(int id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Contact(String firstname, String lastname, String email) {
        this(-1, firstname, lastname, email);
    }

    public void update(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
