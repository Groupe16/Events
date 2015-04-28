package com.localisation.events.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Invitation  implements Parcelable{
    private int id;
    private Event event;
    private String message;
    private User sender, receiver;
    private Date date;

    public Invitation() {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeParcelable(event, flags);
        dest.writeString(message);
        //dest.writeParcelable(sender, flags);
        //dest.writeParcelable(receiver, flags);
        dest.writeString(String.valueOf(date));
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Invitation> CREATOR = new Parcelable.Creator<Invitation>()
    {
        @Override
        public Invitation createFromParcel(Parcel source)
        {
            return new Invitation(source);
        }

        @Override
        public Invitation[] newArray(int size)
        {
            return new Invitation[size];
        }
    };

    //Constructeur avec Parcel
    public Invitation(Parcel in) {
        this.id = in.readInt();
        this.event = in.readParcelable(Event.class.getClassLoader());
        this.message = in.readString();
        //this.sender = in.readParcelable(User.class.getClassLoader());
        //this.receiver = in.readParcelable(User.class.getClassLoader());
        this.date = Date.valueOf(in.readString());
    }

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
}
