package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
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
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterList();
            } else {
                resetFilter();
            }
        });


        checkBox2 = findViewById(R.id.radioHouse);
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterList();
            } else {
                resetFilter();
            }
        });

        checkBox3 = findViewById(R.id.radioAbris);
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
            HOUSE_LIST_FILTERED.clear();
            HOUSE_LIST_FILTERED.addAll(HOUSE_LIST);
        }
    }



    public static void resetFilter() {
        HOUSE_LIST_FILTERED.clear();
        HOUSE_LIST_FILTERED.addAll(HOUSE_LIST);
    }


}