package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.Firebase;


import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.LoginActivity;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<House> {

    private static List<House> HOUSE_LIST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));


        Button buttonGPS = findViewById(R.id.mapButton);
        Button searchButton = findViewById(R.id.searchButton);


        buttonGPS.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });


        searchButton.setOnClickListener(v -> {
            setContentView(R.layout.activity_search);
            TextView titleButton = findViewById(R.id.title);
            titleButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(this, MainActivity.class);

                startActivity(intent);
            });
            ListView listview2 = findViewById(R.id.listView);
            HouseAdapter adapter2 = new HouseAdapter(HOUSE_LIST, this);
            listview2.setAdapter(adapter2);

            Button buttonGPS2 = findViewById(R.id.mapButton);

            buttonGPS2.setOnClickListener(v2 -> {
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            });

        });
    }


    @Override
    public void onPostExecute(List<House> itemList) {
        for (House house : itemList) {
            System.out.println(house.getNom());
        }
        HOUSE_LIST = itemList;
        ListView listview = findViewById(R.id.listView);

        HouseAdapter adapter = new HouseAdapter(itemList, this);
        listview.setAdapter(adapter);

    }
}