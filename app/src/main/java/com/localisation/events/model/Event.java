package com.localisation.events.model;

import android.media.Image;

import java.sql.Date;
import java.sql.Time;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Event {
    private int id;
    private String name;
    private String description;
    private boolean visibility;
    private Place place;
    private Date startDate, endDate;
    private Time startTime, endTime;
    private Theme theme;
    private Vector<User> organizers;
    private Vector<User> participants;
    private Image image;

    public boolean sendInvitation(Vector<User> users, Invitation invitation){
        invitation.setEvent(this);
        return participants.addAll(users);
    }

    public boolean addOrganizer(User user) {
        return this.organizers.add(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Vector<User> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(Vector<User> organizers) {
        this.organizers = organizers;
    }

    public Vector<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Vector<User> participants) {
        this.participants = participants;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
