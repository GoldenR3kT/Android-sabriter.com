package edu.dg202433.sabriter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.classes.House;

public class PayementActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);


        new Handler().postDelayed(() -> {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            finish();
        }, 4000);
    }
}
