package edu.dg202433.sabriter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.dg202433.android_projet.R;

public class HouseActivity extends AppCompatActivity implements PostExecuteActivity<House>{

    private LinearLayout linearLayout;
    private final int[] images = {R.drawable.tente_test1, R.drawable.tente_test2, R.drawable.tente_test3};
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_activity);


        TextView title = findViewById(R.id.title);
        Button gps = findViewById(R.id.mapButton);

        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        gps.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));


        linearLayout = findViewById(R.id.linear_layout1);

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

        linearLayout.setBackgroundResource(images[currentImageIndex]);
    }

    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        linearLayout.setBackgroundResource(images[currentImageIndex]);

    }

    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
        linearLayout.setBackgroundResource(images[currentImageIndex]);

    }

    @Override
    public void onPostExecute(List<House> itemList) {
        itemList.forEach( house -> {

        });
    }
}
