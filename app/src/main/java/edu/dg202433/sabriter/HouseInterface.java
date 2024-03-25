package edu.dg202433.sabriter;

import android.content.Context;
import android.os.Parcelable;

public interface HouseInterface extends Parcelable {
    String getName();
    String getAddress();
    float getValue();
    String[] getPicture();
    String getDescription();
    void setName(String name);
    void setAddress(String address);
    void setValue(float value);
    void setPicture(String[] picture);
    void setDescription(String description);
}
