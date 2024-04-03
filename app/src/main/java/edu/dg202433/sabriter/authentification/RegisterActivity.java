package edu.dg202433.sabriter.authentification;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.dg202433.android_projet.R;

/**
 * RegisterActivity permet à un nouvel utilisateur de s'inscrire à l'application en créant un compte à l'aide de Firebase Authentication.
 * L'utilisateur peut saisir son adresse e-mail et son mot de passe pour créer un nouveau compte.
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    /**
     * Instance de FirebaseAuth pour gérer l'authentification.
     */
    private FirebaseAuth mAuth;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les éléments de l'interface utilisateur et configure le bouton d'inscription.
     *
     * @param savedInstanceState Données permettant de reconstruire l'état de l'activité si elle est recréée.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        // Configuration du bouton d'inscription
        Button registerButton = findViewById(R.id.searchButton);
        registerButton.setOnClickListener(v -> {
            register();
        });
    }

    /**
     * Méthode appelée au démarrage de l'activité.
     * Vérifie si un utilisateur est déjà connecté. Si oui, redirige vers LoginActivity.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setContentView(R.layout.activity_login);
        }
    }

    /**
     * Méthode pour gérer le processus d'inscription d'un nouvel utilisateur.
     * Récupère l'adresse e-mail et le mot de passe saisis par l'utilisateur,
     * puis tente de créer un nouveau compte avec Firebase Authentication.
     */
    private void register() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * Met à jour l'interface utilisateur en fonction de l'utilisateur connecté.
     * Si l'utilisateur est connecté avec succès, redirige vers LoginActivity.
     *
     * @param currentUser Utilisateur actuellement connecté.
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            setContentView(R.layout.activity_login);
        }
    }
}

