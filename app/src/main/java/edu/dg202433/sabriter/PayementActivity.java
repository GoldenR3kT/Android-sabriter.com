package edu.dg202433.sabriter;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import edu.dg202433.android_projet.R;

/**
 * La classe PayementActivity est responsable de l'affichage de l'écran de paiement.
 * Elle montre une barre de progression pendant une courte période, puis termine l'activité.
 */
public class PayementActivity extends AppCompatActivity {

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise l'interface utilisateur et affiche une barre de progression pendant une courte période.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);

        // Récupération de la barre de progression
        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);

        // Utilisation d'un gestionnaire pour masquer la barre de progression après un délai de 4 secondes
        new Handler().postDelayed(() -> {
            loadingProgressBar.setVisibility(View.INVISIBLE); // Masquer la barre de progression
            finish(); // Fermer l'activité
        }, 4000); // Délai en millisecondes (4 secondes)
    }
}

