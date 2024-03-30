package edu.dg202433.sabriter.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.MainActivity;
import edu.dg202433.sabriter.MapActivity;

public class ProfileActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        password.setText("*******");

        Button buttonGPS = findViewById(R.id.mapButton);


        buttonGPS.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        TextView title = findViewById(R.id.title);
        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}
