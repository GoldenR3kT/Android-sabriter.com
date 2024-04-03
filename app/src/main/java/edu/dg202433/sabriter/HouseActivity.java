package edu.dg202433.sabriter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

/**
 * La classe HouseActivity est responsable de l'affichage des détails d'une maison sélectionnée,
 * ainsi que de la gestion des interactions avec l'utilisateur, telles que le changement d'images
 * et l'achat de la maison.
 */
public class HouseActivity extends AppCompatActivity implements PostExecuteActivity<House> {

    /**
     * Indice de l'image actuellement affichée.
     */
    private int currentImageIndex = 0;

    /**
     * Instance de la maison sélectionnée.
     */
    private  House house;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les éléments d'interface utilisateur et récupère les données de la maison sélectionnée.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        // Initialisation des éléments de l'interface utilisateur
        TextView title = findViewById(R.id.title);
        ImageButton gps = findViewById(R.id.mapButton);
        ImageButton profile = findViewById(R.id.profileButton);
        TextView house_title = findViewById(R.id.item_title);
        TextView house_description = findViewById(R.id.item_desc);
        TextView house_price = findViewById(R.id.item_price);
        TextView house_chambers = findViewById(R.id.item_chambers);
        TextView house_pieces = findViewById(R.id.item_pieces);
        TextView house_space = findViewById(R.id.item_space);

        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        gps.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        // Récupération des données de la maison via une requête HTTP asynchrone
        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));

        // Récupération des données de la maison sélectionnée
        house = getIntent().getParcelableExtra("selectedHouse");

        // Affichage des détails de la maison dans l'interface utilisateur
        house_title.setText(house.getNom());
        house_description.setText(house.getDescription());
        house_price.setText(house.getPrix() + "€");
        house_chambers.setText(house.getNombre_de_chambres() + " chambres");
        house_pieces.setText(house.getNombre_de_pieces() + " pièces");
        house_space.setText(house.getSuperficie() + " m²");

        // Gestion des boutons pour afficher les images précédentes et suivantes
        Button slideLeftButton = findViewById(R.id.slide_left);
        Button slideRightButton = findViewById(R.id.slide_right);

        // Configuration des écouteurs de clic pour les boutons
        slideLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousImage();
            }
        });

        slideRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextImage();
            }
        });

        // Affichage de la première image de la maison avec Picasso
        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image);
        Picasso.get().load(imageUrl).into(imageView);

        Button buyButton = findViewById(R.id.buy_button);

        // Gestion du bouton d'achat
        boolean isItemPurchased = getIntent().getBooleanExtra("isItemPurchased", false);
        if (isItemPurchased) {
            buyButton.setText("ACHETÉ");
            buyButton.setEnabled(false);
        } else {
            buyButton.setText("ACHETER");
        }

        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PayementActivity.class);
            startActivity(intent);
            buyButton.setText("ACHETÉ");
            buyButton.setEnabled(false);
        });
    }

    // Méthode pour afficher les images précédentes
    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % house.getCompleteImageLinks().length;
        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image);
        Picasso.get().load(imageUrl).into(imageView);
    }

    // Méthode pour afficher les images suivantes
    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + house.getCompleteImageLinks().length) % house.getCompleteImageLinks().length;
        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image); // Remplacez R.id.imageView par l'ID réel de votre ImageView
        Picasso.get().load(imageUrl).into(imageView);
    }

    /**
     * Méthode appelée lorsque la requête HTTP asynchrone est terminée.
     * Traite les données de la liste de maisons récupérées.
     *
     * @param itemList liste des maisons récupérées.
     */
    @Override
    public void onPostExecute(List<House> itemList) {
        itemList.forEach( house -> {

        });
    }
}
