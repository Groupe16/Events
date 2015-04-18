package com.localisation.events.model;

import android.media.Image;

import java.sql.Date;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-29.
 */
public class User {
    private int id;
    private String firstName, lastName;
    private String city;
    private Date bDate;
    private Vector<Theme> interest;
    private Vector<Event> organizedEvents, futureEvents, pastEvents;
    private Vector<Invitation> invitations;
    private Vector<User> friends;
    private Vector<FriendRequest> friendRequests;
    private Image avatar;


    public boolean addTheme(Theme theme){
        return this.interest.add(theme);
    }

    public boolean createEvent(Event event){
        event.addOrganizer(this);
        return this.organizedEvents.add(event);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public Vector<Theme> getInterest() {
        return interest;
    }

    public void setInterest(Vector<Theme> interest) {
        this.interest = interest;
    }

    public Vector<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(Vector<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public Vector<Event> getFutureEvents() {
        return futureEvents;
    }

    public void setFutureEvents(Vector<Event> futureEvents) {
        this.futureEvents = futureEvents;
    }

    public Vector<Event> getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(Vector<Event> pastEvents) {
        this.pastEvents = pastEvents;
    }

    public Vector<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Vector<Invitation> invitations) {
        this.invitations = invitations;
    }

    public Vector<User> getFriends() {
        return friends;
    }

    public void setFriends(Vector<User> friends) {
        this.friends = friends;
    }

    public Vector<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Vector<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}
