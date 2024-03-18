package edu.dg202433.sabriter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import edu.dg202433.android_projet.R;

public class HouseActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private final int[] images = {R.drawable.tente_test1, R.drawable.tente_test2, R.drawable.tente_test3}; // Mettez vos identifiants d'images ici
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_activity);

        linearLayout = findViewById(R.id.linear_layout1); // Remplacez "imageView" par l'ID de votre ImageView

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
    }

    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        linearLayout.setBackgroundResource(images[currentImageIndex]);
    }

    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
        linearLayout.setBackgroundResource(images[currentImageIndex]);
    }
}
