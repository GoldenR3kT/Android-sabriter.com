package edu.dg202433.sabriter.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * La classe House représente une maison avec ses différentes caractéristiques telles que l'ID, le nom, le type, le prix, etc.
 * Implémente l'interface Parcelable pour permettre le passage d'instances entre les composants Android via les Intent.
 */
public class House implements HouseInterface, Parcelable{
    private final int id;
    private String nom;
    private final String type;
    private final int prix;
    private float note;
    private List<Float> listNotes;
    private String adresse;
    private final int nombre_de_pieces;
    private final int nombre_de_chambres;
    private final float superficie;
    private final String[] images;
    private String description;
    private final float latitude;
    private final float longitude;
    private final String localisation;

    private boolean hasVoted;
    @JsonIgnore
    private String[] completeImageLinks;

    @JsonCreator
    public House(@JsonProperty("id") int id,
                 @JsonProperty("nom") String nom,
                 @JsonProperty("type") String type,
                 @JsonProperty("prix") int prix,
                 @JsonProperty("note") float note,
                 @JsonProperty("adresse") String adresse,
                 @JsonProperty("nombre_de_pieces") int nombre_de_pieces,
                 @JsonProperty("nombre_de_chambres") int nombre_de_chambres,
                 @JsonProperty("superficie") float superficie,
                 @JsonProperty("images") String[] images,
                 @JsonProperty("description") String description,
                 @JsonProperty("latitude") float latitude,
                 @JsonProperty("longitude") float longitude,
                 @JsonProperty("localisation") String localisation
    ) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.note = note;
        this.adresse = adresse;
        this.nombre_de_pieces = nombre_de_pieces;
        this.nombre_de_chambres = nombre_de_chambres;
        this.superficie = superficie;
        this.images = images;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localisation = localisation;
        listNotes = new ArrayList<>();
        listNotes.add(note);
        hasVoted = false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void initializeCompleteImageLinks() {
        if (images != null) {
            completeImageLinks = new String[images.length];
            for (int i = 0; i < images.length; i++) {
                completeImageLinks[i] = "https://github.com/GoldenR3kT/abri_data/blob/main/images/" + images[i] +"?raw=true";
            }
        }
    }

    public String[] getCompleteImageLinks() {
        if (completeImageLinks == null) {
            initializeCompleteImageLinks();
        }
        return completeImageLinks;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public int getPrix() {
        return prix;
    }

    public float getNote() {
        return note;
    }

    public String[] getImages() {
        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                images[i] = "https://github.com/GoldenR3kT/abri_data/blob/main/images/" + images[i] +"?raw=true";
            }
        }
        return images;
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

    public int getNombre_de_pieces() {
        return nombre_de_pieces;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getLocalisation() { return localisation; }

    public float getMoyenneNote() {
        if (listNotes.isEmpty()) {
            return 0.0f;
        }
        float sum = 0.0f;
        for (Float rating : listNotes) {
            sum += rating;
        }
        float moyenne = sum / listNotes.size();
        return Float.parseFloat(String.format("%.1f", moyenne));    }

    public void setNoteFromRatingBarChange(float newNote) {
        listNotes.add(newNote);
        this.note = getMoyenneNote();
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(type);
        dest.writeInt(prix);
        dest.writeFloat(note);
        dest.writeString(adresse);
        dest.writeInt(nombre_de_pieces);
        dest.writeInt(nombre_de_chambres);
        dest.writeFloat(superficie);
        dest.writeStringArray(images);
        dest.writeString(description);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeString(localisation);
    }

    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            int id = in.readInt();
            String nom = in.readString();
            String type = in.readString();
            int prix = in.readInt();
            float note = in.readFloat();
            String adresse = in.readString();
            int nombre_de_pieces = in.readInt();
            int nombre_de_chambres = in.readInt();
            float superficie = in.readFloat();
            String[] images = in.createStringArray();
            String description = in.readString();
            float latitude = in.readFloat();
            float longitude = in.readFloat();
            String localisation = in.readString();
            return new House(id, nom, type, prix, note, adresse, nombre_de_pieces, nombre_de_chambres, superficie, images, description, latitude, longitude, localisation);
        }
        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        House other = (House) obj;
        return id == other.id;
    }

}
