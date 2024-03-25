package edu.dg202433.sabriter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class House implements HouseInterface {
    private int id;
    private String name;
    private float value;
    private String address;
    private int nbRooms;
    private int nbBedrooms;
    private float surface;
    private String[] picture;

    private String description;

    @JsonCreator
    public House(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.value = in.readFloat();
        this.picture = in.createStringArray();
        this.description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public float getValue() {
        return value;
    }

    public String[] getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setValue(float value) {
        this.value = value;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Shelter{name=" + name + ", address=" + address  + ", value=" + value + ", picture=" + picture + ", description=" + description + "}";
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeFloat(value);
        dest.writeStringArray(picture);
        dest.writeString(description);

    }

    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }
        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    public static Parcelable.Creator<House> getCreator() {
        return CREATOR;
    }

}
