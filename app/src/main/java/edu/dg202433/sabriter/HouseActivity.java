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

public class HouseActivity extends AppCompatActivity implements PostExecuteActivity<House> {


    private int currentImageIndex = 0;
    private  House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);


        TextView title = findViewById(R.id.title);
        ImageButton gps = findViewById(R.id.mapButton);
        ImageButton profile = findViewById(R.id.profileButton);

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

        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));

        house = getIntent().getParcelableExtra("selectedHouse");


        TextView house_title = findViewById(R.id.item_title);
        TextView house_description = findViewById(R.id.item_desc);
        TextView house_price = findViewById(R.id.item_price);
        TextView house_chambers = findViewById(R.id.item_chambers);
        TextView house_pieces = findViewById(R.id.item_pieces);
        TextView house_space = findViewById(R.id.item_space);

        house_title.setText(house.getNom());
        house_description.setText(house.getDescription());
        house_price.setText(house.getPrix() + "€");
        house_chambers.setText(house.getNombre_de_chambres() + " chambres");
        house_pieces.setText(house.getNombre_de_pieces() + " pièces");
        house_space.setText(house.getSuperficie() + " m²");


        Button slideLeftButton = findViewById(R.id.slide_left);
        Button slideRightButton = findViewById(R.id.slide_right);

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


        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image); // Remplacez R.id.imageView par l'ID réel de votre ImageView
        Picasso.get().load(imageUrl).into(imageView);

        Button buyButton = findViewById(R.id.buy_button);


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

    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % house.getCompleteImageLinks().length;
        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image);
        Picasso.get().load(imageUrl).into(imageView);


    }

    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + house.getCompleteImageLinks().length) % house.getCompleteImageLinks().length;
        String imageUrl = house.getCompleteImageLinks()[currentImageIndex];
        ImageView imageView = findViewById(R.id.item_image); // Remplacez R.id.imageView par l'ID réel de votre ImageView
        Picasso.get().load(imageUrl).into(imageView);

    }

    @Override
    public void onPostExecute(List<House> itemList) {
        itemList.forEach( house -> {

        });
    }
}
