package com.localisation.events.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.sql.Date;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Event  implements Parcelable{
    private int id;
    private String name;
    private String description;
    private boolean visibility;
    private Date startDate = new Date(19710101), endDate = new Date(19710101);
    private Theme theme;
    private Vector<User> organizers = new Vector<>();
    private Vector<User> participants = new Vector<>();
    private String place_name;
    private String address;
    private Coord coord;

    public Event() {
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(String.valueOf(visibility));
        dest.writeString(String.valueOf(startDate));
        dest.writeString(String.valueOf(endDate));
        dest.writeParcelable(theme, flags);
        dest.writeParcelableArray(organizers.toArray(new User[organizers.size()]), flags);
        dest.writeParcelableArray(participants.toArray(new User[participants.size()]), flags);
        dest.writeString(place_name);
        dest.writeString(address);
        dest.writeParcelable(coord, flags);
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>()
    {
        @Override
        public Event createFromParcel(Parcel source)
        {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size)
        {
            return new Event[size];
        }
    };

    //Constructeur avec Parcel
    public Event(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.visibility = Boolean.valueOf(in.readString());
        String startDate = in.readString();
        if(startDate != null)
        this.startDate = Date.valueOf(startDate);
        String endDate = in.readString();
        if(endDate != null)
            this.endDate = Date.valueOf(endDate);;
        User[] organizers = in.createTypedArray(User.CREATOR);
        this.organizers = new Vector<>();
        for (int i = 0; i < organizers.length; i++){
            this.organizers.addElement(organizers[i]);
        }
        User[] participants = in.createTypedArray(User.CREATOR);
        this.participants = new Vector<>();
        for (int i = 0; i < participants.length; i++){
            this.participants.addElement(participants[i]);
        }
        this.place_name = in.readString();
        this.address = in.readString();
        this.coord = in.readParcelable(Coord.class.getClassLoader());
    }

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

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getPlaceName() {
        return place_name;
    }

    public void setPlaceName(String name) {
        this.place_name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
