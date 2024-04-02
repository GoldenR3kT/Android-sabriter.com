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

    void setName(String nom);
    void setAddress(String adresse);
    void setNote(float value);
    void setDescription(String description);
}
