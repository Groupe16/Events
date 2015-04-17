package com.localisation.events.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Invitation {
    private int id;
    private Event event;
    private String message;
    private User sender, receiver;
    private Date date;
    private Time time;

    public boolean accept(){
        //TODO if valid date
        this.receiver.getFutureEvents().add(this.event);
        return this.receiver.getInvitations().removeElement(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
