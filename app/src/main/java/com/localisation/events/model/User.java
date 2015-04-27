package com.localisation.events.model;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-29.
 */
public class User  implements Parcelable{
    private int id;
    private String firstName, lastName;
    private String city;
    private Date bDate;
    private Vector<Theme> interest;
    private Vector<Event> organizedEvents, futureEvents, pastEvents;
    private Vector<Invitation> invitations;
    private String login;
    private String password;
    private String phone;
    private String device;
    private String email;
    private Timestamp lastConnection;
    private Coord coord;

    public User() {
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(city);
        if(bDate == null)
        {
            bDate = new Date(19710101);
        }
        dest.writeString(String.valueOf(bDate));
        if(interest == null)
        {
            interest = new Vector<Theme>();
        }
        dest.writeParcelableArray(interest.toArray(new Theme[interest.size()]), flags);
        if(organizedEvents == null)
        {
            organizedEvents = new Vector<Event>();
        }
        dest.writeParcelableArray(organizedEvents.toArray(new Event[organizedEvents.size()]), flags);
        if(futureEvents == null)
        {
            futureEvents = new Vector<Event>();
        }
        dest.writeParcelableArray(futureEvents.toArray(new Event[futureEvents.size()]), flags);
        if(pastEvents == null)
        {
            pastEvents = new Vector<Event>();
        }
        dest.writeParcelableArray(pastEvents.toArray(new Event[pastEvents.size()]), flags);
        if(invitations == null)
        {
            invitations = new Vector<Invitation>();
        }
        dest.writeParcelableArray(invitations.toArray(new Invitation[invitations.size()]), flags);
        dest.writeString(login);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(device);
        if(lastConnection == null)
        {
            lastConnection = new Timestamp(71,01,01,00,00,00,00);
        }
        dest.writeString(String.valueOf(lastConnection));
        dest.writeParcelable(coord, flags);
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel source)
        {
            return new User(source);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    //Constructeur avec Parcel
    public User(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.city = in.readString();
        this.bDate = Date.valueOf(in.readString());
        Theme[] interests = in.createTypedArray(Theme.CREATOR);
        this.interest = new Vector<>();
        for (int i = 0; i < interests.length; i++){
            this.interest.addElement(interests[i]);
        }
        Event[] organizedEvents = in.createTypedArray(Event.CREATOR);
        this.organizedEvents = new Vector<>();
        for (int i = 0; i < organizedEvents.length; i++){
            this.organizedEvents.addElement(organizedEvents[i]);
        }
        Event[] futureEvents = in.createTypedArray(Event.CREATOR);
        this.futureEvents = new Vector<>();
        for (int i = 0; i < futureEvents.length; i++){
            this.futureEvents.addElement(futureEvents[i]);
        }
        Event[] pastEvents = in.createTypedArray(Event.CREATOR);
        this.pastEvents = new Vector<>();
        for (int i = 0; i < pastEvents.length; i++){
            this.pastEvents.addElement(pastEvents[i]);
        }
        Invitation[] invitations = in.createTypedArray(Invitation.CREATOR);
        this.invitations = new Vector<>();
        for (int i = 0; i < invitations.length; i++){
            this.invitations.addElement(invitations[i]);
        }
        this.login = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.device = in.readString();
        this.lastConnection = Timestamp.valueOf(in.readString());
        this.coord = in.readParcelable(Coord.class.getClassLoader());
    }

    public boolean addTheme(Theme theme) {
        return this.interest.add(theme);
    }

    public boolean createEvent(Event event) {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Timestamp lastConnection) { this.lastConnection = lastConnection; }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}