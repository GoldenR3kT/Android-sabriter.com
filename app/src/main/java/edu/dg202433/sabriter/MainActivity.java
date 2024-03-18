package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.dg202433.android_projet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGPS = findViewById(R.id.mapButton);
        Button searchButton = findViewById(R.id.searchButton);

        buttonGPS.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(v -> {
            setContentView(R.layout.activity_search);
        });
    }


}