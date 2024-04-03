package edu.dg202433.sabriter.authentification;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.MainActivity;

/**
 * LoginActivity permet à l'utilisateur de se connecter à l'application en utilisant Firebase Authentication.
 * L'utilisateur peut saisir son adresse e-mail et son mot de passe pour se connecter.
 * S'il n'a pas de compte, il peut être redirigé vers l'écran d'inscription.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    /**
     * Instance de FirebaseAuth pour gérer l'authentification.
     */
    private FirebaseAuth mAuth;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les éléments de l'interface utilisateur et configure les écouteurs de clic.
     *
     * @param savedInstanceState Données permettant de reconstruire l'état de l'activité si elle est recréée.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // Configuration du bouton de connexion
        Button login = findViewById(R.id.connexion);
        login.setOnClickListener(v -> {
            login();
        });

        // Configuration du lien vers l'écran d'inscription
        TextView register = findViewById(R.id.pasDeCompte);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Méthode appelée au démarrage de l'activité.
     * Vérifie si un utilisateur est déjà connecté. Si oui, redirige vers MainActivity.
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Méthode pour gérer le processus de connexion de l'utilisateur.
     * Récupère l'adresse e-mail et le mot de passe saisis par l'utilisateur,
     * puis tente de s'authentifier avec Firebase Authentication.
     */
    public void login() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    /**
     * Met à jour l'interface utilisateur en fonction de l'utilisateur connecté.
     * Si l'utilisateur est connecté avec succès, redirige vers MainActivity.
     *
     * @param currentUser Utilisateur actuellement connecté.
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
