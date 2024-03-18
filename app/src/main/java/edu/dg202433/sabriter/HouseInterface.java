package edu.dg202433.sabriter;

import android.content.Context;
import android.os.Parcelable;

public interface HouseInterface extends Parcelable {
    String getName();
    String getAddress();
    float getValue();
    int[] getPicture();
    String getDescription();
    void setName(String name);
    void setAddress(String address);
    void setContext(Context context);
    void setValue(float value);
    void setPicture(int[] picture);
    void setDescription(String description);
}
