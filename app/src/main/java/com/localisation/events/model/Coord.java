package com.localisation.events.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Coord  implements Parcelable{
    private int id;
    private double longitude, latitude, altitude;

    public Coord() {
    }

    public Coord(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public static Coord getCoord(){
        Coord coord = new Coord();
        //TODO recupérer les coordonnées
        return coord;
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
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(altitude);
    }


    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>()
    {
        @Override
        public Coord createFromParcel(Parcel source)
        {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size)
        {
            return new Coord[size];
        }
    };

    //Constructeur avec Parcel
    public Coord(Parcel in) {
        this.id = in.readInt();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.altitude = in.readDouble();
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latutide) {
        this.latitude = latutide;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
