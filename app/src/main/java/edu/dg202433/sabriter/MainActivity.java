package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.LoginActivity;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<House> {

    private FirebaseAuth mAuth;
    private static List<House> HOUSE_LIST;

    private static List<House> HOUSE_LIST_FILTERED;

    CheckBox checkBox;
    CheckBox checkBox2;
    CheckBox checkBox3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
        }
        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));


        ImageButton buttonGPS = findViewById(R.id.mapButton);
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


        checkBox = findViewById(R.id.checkAppartment);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterList();
            } else {
                resetFilter();
            }
        });


        checkBox2 = findViewById(R.id.checkHouse);
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterList();
            } else {
                resetFilter();
            }
        });

        checkBox3 = findViewById(R.id.checkAbris);
        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterList();
            } else {
                resetFilter();
            }
        });

        EditText budget = findViewById(R.id.locationBudgetEditText);
        budget.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                HOUSE_LIST_FILTERED.removeIf(house -> house.getPrix() > Integer.parseInt(budget.getText().toString()));
            }
        });



        searchButton.setOnClickListener(v -> {
            setContentView(R.layout.activity_search);
            TextView titleButton = findViewById(R.id.title);
            titleButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(this, MainActivity.class);

                startActivity(intent);
            });
            ListView listview2 = findViewById(R.id.listView);
            HouseAdapter adapter2 = new HouseAdapter(HOUSE_LIST_FILTERED, this);
            listview2.setAdapter(adapter2);



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
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tri, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

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




        });
    }



    @Override
    public void onPostExecute(List<House> itemList) {
        HOUSE_LIST = itemList;
        HOUSE_LIST_FILTERED = itemList;
        ListView listview = findViewById(R.id.listView);

        HouseAdapter adapter = new HouseAdapter(HOUSE_LIST, this);
        listview.setAdapter(adapter);

    }

    private void filterList() {
        List<House> originalList = new ArrayList<>(HOUSE_LIST);

        HOUSE_LIST_FILTERED.clear();

        for (House house : originalList) {
            if ((checkBox.isChecked() && house.getType().equals("Appartement")) ||
                    (checkBox2.isChecked() && house.getType().equals("Maison")) ||
                    (checkBox3.isChecked() && house.getType().equals("Abris"))) {
                HOUSE_LIST_FILTERED.add(house);
            }
        }

        if (!checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
            HOUSE_LIST_FILTERED.addAll(originalList);
        }
    }



    private void resetFilter() {
        HOUSE_LIST_FILTERED.clear();
        HOUSE_LIST_FILTERED.addAll(HOUSE_LIST);
    }


}