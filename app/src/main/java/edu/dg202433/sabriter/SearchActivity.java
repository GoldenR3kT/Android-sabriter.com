package edu.dg202433.sabriter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;

public class SearchActivity extends AppCompatActivity {

    private static List<House> HOUSE_LIST_FILTERED;
    private static List<House> modifiedHouses = new ArrayList<>();

    private HouseAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView titleButton = findViewById(R.id.title);

        Parcelable[] parcelables = getIntent().getParcelableArrayExtra("houses");
        House[] houses = Arrays.copyOf(parcelables, parcelables.length, House[].class);
        HOUSE_LIST_FILTERED = new ArrayList<>(Arrays.asList(houses));

        titleButton.setOnClickListener(v1 -> {
            Intent intentSearch = new Intent();
            intentSearch.putExtra("modifiedHouses", HOUSE_LIST_FILTERED.stream().toArray(House[]::new));
            finish();
        });

        ListView listview2 = findViewById(R.id.listView);
        adapter = new HouseAdapter(HOUSE_LIST_FILTERED, this, modifiedHouses);
        listview2.setAdapter(adapter);



        ImageButton buttonGPS2 = findViewById(R.id.mapButton);

        buttonGPS2.setOnClickListener(v2 -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton2 = findViewById(R.id.profileButton);
        profileButton2.setOnClickListener(v3 -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.tri, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Prix croissant")) {
                    Collections.sort(HOUSE_LIST_FILTERED, (house1, house2) -> Integer.compare(house1.getPrix(), house2.getPrix()));
                    adapter2.notifyDataSetChanged();
                } else if (selectedItem.equals("Prix décroissant")) {
                    Collections.sort(HOUSE_LIST_FILTERED, (house1, house2) -> Integer.compare(house2.getPrix(), house1.getPrix()));
                    adapter2.notifyDataSetChanged();
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    private void updateMainList() {
        for (House modifiedHouse : modifiedHouses) {
            int index = HOUSE_LIST_FILTERED.indexOf(modifiedHouse);
            if (index != -1) {
                HOUSE_LIST_FILTERED.set(index, modifiedHouse);
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateMainList();
    }

}
