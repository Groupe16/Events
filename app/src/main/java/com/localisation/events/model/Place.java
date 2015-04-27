package com.localisation.events.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zalila on 2015-04-17.
 */
public class Place implements Parcelable{
    private int id;
    private String name;
    private Coord coord;
    private String address;

    public Place() {
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
        dest.writeParcelable(coord, flags);
        dest.writeString(address);
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>()
    {
        @Override
        public Place createFromParcel(Parcel source)
        {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size)
        {
            return new Place[size];
        }
    };

    //Constructeur avec Parcel
    public Place(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.coord = in.readParcelable(Coord.class.getClassLoader());
        this.address = in.readString();
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
}
