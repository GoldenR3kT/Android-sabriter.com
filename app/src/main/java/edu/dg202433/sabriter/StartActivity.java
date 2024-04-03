package edu.dg202433.sabriter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.dg202433.android_projet.R;

/**
 * La classe StartActivity est responsable de l'affichage de l'écran de démarrage de l'application.
 * Elle anime le logo de la maison et le titre de l'application lors du démarrage de l'application.
 * Après l'animation, elle redirige l'utilisateur vers l'écran de connexion.
 */
public class StartActivity extends AppCompatActivity {

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise l'animation du logo de la maison et du titre de l'application, puis redirige l'utilisateur vers l'écran de connexion.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Animation de rotation du logo de la maison
        ObjectAnimator rotateAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.rotation);
        ImageView house = findViewById(R.id.house);
        rotateAnimation.setTarget(house);

        // Animation de glissement vers le haut du titre de l'application
        ObjectAnimator slideUpAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.slide_up);
        TextView title = findViewById(R.id.title);
        slideUpAnimation.setTarget(title);

        // Définir un écouteur pour l'animation de rotation
        rotateAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Démarrer l'animation de glissement vers le haut après l'animation de rotation
                slideUpAnimation.start();
                // Rediriger vers l'écran de connexion après l'animation de glissement
                slideUpAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(StartActivity.this, edu.dg202433.sabriter.authentification.LoginActivity.class);
                        startActivity(intent);
                        finish(); // Fermer cette activité pour éviter de revenir en arrière avec le bouton "Retour"
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        // Démarrer l'animation de rotation du logo de la maison
        rotateAnimation.start();
    }
}
