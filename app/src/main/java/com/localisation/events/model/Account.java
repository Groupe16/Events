package com.localisation.events.model;

import java.sql.Time;

/**
 * Created by Zalila on 2015-04-18.
 */
public class Account {
    private String login;
    private String email;
    private String phone;
    private String password;
    private User user;
    private String device;
    private Time lastConnection;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Time getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Time lastConnection) {
        this.lastConnection = lastConnection;
    }
}
