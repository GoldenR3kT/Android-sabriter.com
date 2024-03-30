package edu.dg202433.sabriter.classes;

import android.content.Context;
import android.os.Parcelable;

public interface HouseInterface extends Parcelable {

    int getId();
    String getNom();

    String getAdresse();
    int getPrix();
    float getValue();
    String[] getImages();
    String getDescription();

    int getNombre_de_pieces();

    int getNombre_de_chambres();

    float getSuperficie();

    int getFirstImage();


    void setName(String name);
    void setAddress(String address);
    void setValue(float value);
    void setDescription(String description);
}
