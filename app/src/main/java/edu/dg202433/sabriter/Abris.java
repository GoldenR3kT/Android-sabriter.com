package edu.dg202433.sabriter;

import java.util.Arrays;

public class Abris {
    private int id;
    private String nom;
    private float prix;
    private String adresse;
    private int nombre_de_pièces;
    private int nombre_de_chambres;
    private float superficie;
    private String[] images;
    private String description;

    @Override
    public String toString() {
        return "Abris{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", adresse='" + adresse + '\'' +
                ", nombre_de_pièces=" + nombre_de_pièces +
                ", nombre_de_chambres=" + nombre_de_chambres +
                ", superficie=" + superficie +
                ", images=" + Arrays.toString(images) +
                ", description='" + description + '\'' +
                '}';
    }
}
