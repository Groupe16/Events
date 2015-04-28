package com.localisation.events.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zalila on 2015-04-17.
 */
public class Theme  implements Parcelable{
    private int id;
    private String name;
    private String group;

    public Theme() {
    }

    public Theme(int id, String name, String group) {
        this.id = id;
        this.name = name;
        this.group = group;
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
        dest.writeString(group);
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Theme> CREATOR = new Parcelable.Creator<Theme>()
    {
        @Override
        public Theme createFromParcel(Parcel source)
        {
            return new Theme(source);
        }

        @Override
        public Theme[] newArray(int size)
        {
            return new Theme[size];
        }
    };

    //Constructeur avec Parcel
    public Theme(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.group = in.readString();
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
