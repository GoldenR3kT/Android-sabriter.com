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

/**
 * La classe ProfileActivity est responsable de l'affichage du profil de l'utilisateur connecté.
 * Elle affiche l'adresse e-mail de l'utilisateur avec des étoiles cachant le mot de passe,
 * et permet à l'utilisateur de se déconnecter de l'application.
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Méthode appelée lors de la création de l'activité AccountActivity.
     * Initialise les éléments de l'interface utilisateur, configure le bouton de déconnexion
     * et affiche l'adresse e-mail de l'utilisateur connecté avec des étoiles cachant le mot de passe.
     *
     * @param savedInstanceState Données permettant de reconstruire l'état de l'activité si elle est recréée.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Configuration du bouton de déconnexion
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Affichage de l'adresse e-mail de l'utilisateur connecté avec des étoiles cachant le mot de passe
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        password.setText("*******");

        // Configuration du titre pour rediriger vers MainActivity lorsqu'il est cliqué
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
