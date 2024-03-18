package edu.dg202433.sabriter;

import android.content.Context;

public class Shelter {
    private String name;

    private String address;
    private Context context;
    private float value;

    private int[] picture;

    private String description;

    public Shelter(String name, String address, Context context, float value, int[] picture, String description) {
        this.name = name;
        this.address = address;
        this.context = context;
        this.value = value;
        this.picture = picture;
        this.description = description;
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

}
