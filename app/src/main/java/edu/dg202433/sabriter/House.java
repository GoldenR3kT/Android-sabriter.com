package edu.dg202433.sabriter;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class House implements HouseInterface {
    private int id;
    private String nom;
    private int prix;
    private float value;
    private String adresse;
    private int nombre_de_pièces;
    private int nombre_de_chambres;
    private float superficie;
    private String[] images;

    private String description;
    private float latitude;
    private float longitude;

    public House() {
        super();
    }

    @JsonIgnore
    public House(Parcel in) {
        this.nom = in.readString();
        this.adresse = in.readString();
        this.nombre_de_pièces = in.readInt();
        this.nombre_de_chambres = in.readInt();
        this.superficie = in.readFloat();
        this.value = in.readFloat();
        this.images = in.createStringArray();
        this.description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getPrix() {
        return prix;
    }

    public float getValue() {
        return value;
    }

    public String[] getImages() {
        return images;
    }

    public int  getFirstImage() {
        return 0;
    }
    public String getDescription() {
        return description;
    }


    public int getNombre_de_chambres() {
        return nombre_de_chambres;
    }

    public float getSuperficie() {
        return superficie;
    }

    public int getNombre_de_pièces() {
        return nombre_de_pièces;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.nom = nom;
    }

    public void setAddress(String adresse) {
        this.adresse = adresse;
    }


    public void setValue(float value) {
        this.value = value;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeInt(nombre_de_pièces);
        dest.writeInt(nombre_de_chambres);
        dest.writeFloat(superficie);
        dest.writeFloat(value);
        dest.writeStringArray(images);
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
