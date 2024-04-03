package edu.dg202433.sabriter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.classes.HouseAdapter;

/**
 * La classe SearchActivity est responsable de l'affichage des résultats de recherche des maisons.
 * Elle affiche une liste de maisons filtrées selon les critères de recherche sélectionnés par l'utilisateur.
 */
public class SearchActivity extends AppCompatActivity {

    private static List<House> HOUSE_LIST_FILTERED;
    private static List<House> modifiedHouses = new ArrayList<>();

    private HouseAdapter adapter;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise l'interface utilisateur et récupère les maisons filtrées à afficher.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView titleButton = findViewById(R.id.title);

        Parcelable[] parcelables = getIntent().getParcelableArrayExtra("houses");
        House[] houses = Arrays.copyOf(parcelables, parcelables.length, House[].class);
        HOUSE_LIST_FILTERED = new ArrayList<>(Arrays.asList(houses));

        Collections.sort(HOUSE_LIST_FILTERED, (house1, house2) -> Integer.compare(house1.getPrix(), house2.getPrix()));

        titleButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        ListView listView = findViewById(R.id.listView);
        adapter = new HouseAdapter(HOUSE_LIST_FILTERED, this, modifiedHouses);
        listView.setAdapter(adapter);

        ImageButton buttonGPS = findViewById(R.id.mapButton);
        buttonGPS.setOnClickListener(v2 -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v3 -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tri, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Prix croissant")) {
                    Collections.sort(HOUSE_LIST_FILTERED, (house1, house2) -> Integer.compare(house1.getPrix(), house2.getPrix()));
                    adapter.notifyDataSetChanged();
                } else if (selectedItem.equals("Prix décroissant")) {
                    Collections.sort(HOUSE_LIST_FILTERED, (house1, house2) -> Integer.compare(house2.getPrix(), house1.getPrix()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Met à jour la liste principale des maisons avec les maisons modifiées.
     */
    private void updateMainList() {
        for (House modifiedHouse : modifiedHouses) {
            int index = HOUSE_LIST_FILTERED.indexOf(modifiedHouse);
            if (index != -1) {
                HOUSE_LIST_FILTERED.set(index, modifiedHouse);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Méthode appelée lorsque l'activité entre à nouveau en premier plan.
     * Met à jour la liste principale des maisons.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateMainList();
    }
}

