package edu.dg202433.sabriter.classes;

import android.os.Parcelable;

public interface HouseInterface extends Parcelable {

    int getId();
    String getNom();
    String getType();
    int getPrix();
    String[] getImages();
    String getDescription();
    int getNombre_de_pieces();
    int getNombre_de_chambres();
    float getSuperficie();
    float getNote();
    String getLocalisation();
    float getLatitude();
    float getLongitude();
}

