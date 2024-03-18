package edu.dg202433.sabriter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class House implements HouseInterface {
    private String name;

    private String address;
    private Context context;
    private float value;

    private int[] picture;

    private String description;

    public House(String name, String address, Context context, float value, int[] picture, String description) {
        this.name = name;
        this.address = address;
        this.context = context;
        this.value = value;
        this.picture = picture;
        this.description = description;
    }

    public House(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.context = (Context) in.readValue(Context.class.getClassLoader());
        this.value = in.readFloat();
        this.picture = in.createIntArray();
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

    public Context getContext() {
        return context;
    }

    public float getValue() {
        return value;
    }

    public int[] getPicture() {
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

    public void setContext(Context context) {
        this.context = context;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setPicture(int[] picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Shelter{name=" + name + ", address=" + address + ", context=" + context + ", value=" + value + ", picture=" + picture + ", description=" + description + "}";
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeValue(context);
        dest.writeFloat(value);
        dest.writeIntArray(picture);
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
