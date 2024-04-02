package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;



import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;

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


    RadioButton checkBox;
    RadioButton checkBox2;
    RadioButton checkBox3;

    EditText budgetEditText;

    EditText locationEditText;
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


        checkBox = findViewById(R.id.radioAppartment);
        checkBox2 = findViewById(R.id.radioHouse);
        checkBox3 = findViewById(R.id.radioAbris);


        budgetEditText = findViewById(R.id.locationBudgetEditText);
        locationEditText = findViewById(R.id.locationEditText);



        searchButton.setOnClickListener(v -> {
            filterList();
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("houses", HOUSE_LIST_FILTERED.toArray(new House[HOUSE_LIST_FILTERED.size()]));
            startActivity(intent);
        });




    }



    @Override
    public void onPostExecute(List<House> itemList) {
        HOUSE_LIST = itemList;
        HOUSE_LIST_FILTERED = itemList;

    }

    private void filterList() {
        List<House> filteredList = new ArrayList<>();

        for (House house : HOUSE_LIST) {
            boolean isHouseValid = true;

            if (checkBox.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Appartement");
            }
            if (checkBox2.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Maison");
            }
            if (checkBox3.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Abris");
            }

            if (!budgetEditText.getText().toString().isEmpty()) {
                int budgetValue = Integer.parseInt(budgetEditText.getText().toString());
                isHouseValid = isHouseValid && house.getPrix() <= budgetValue;
            }

            if (!locationEditText.getText().toString().isEmpty()) {
                String locationValue = locationEditText.getText().toString().toLowerCase();
                isHouseValid = isHouseValid && house.getLocalisation().toLowerCase().contains(locationValue);
            }

            if (isHouseValid) {
                filteredList.add(house);
            }
        }

        HOUSE_LIST_FILTERED = filteredList;

        if (HOUSE_LIST_FILTERED.isEmpty()) {
            HOUSE_LIST_FILTERED = new ArrayList<>(HOUSE_LIST);
        }
    }








}