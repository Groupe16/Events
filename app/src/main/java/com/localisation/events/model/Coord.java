package com.localisation.events.model;

/**
 * Created by Zalila on 2015-03-29.
 */
public class Coord {
    private double longitude, latitude, altitude;

    public static Coord getCoord(){
        Coord coord = new Coord();
        //TODO recupérer les coordonnées
        return coord;
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
}
