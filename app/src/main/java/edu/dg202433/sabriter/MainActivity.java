package edu.dg202433.sabriter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.LoginActivity;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

/**
 * La classe MainActivity est l'activité principale de l'application. Elle gère l'interface utilisateur,
 * l'authentification des utilisateurs, la récupération des données sur les maisons depuis un serveur distant,
 * et la filtration de ces données en fonction des critères spécifiés par l'utilisateur.
 */
public class MainActivity extends AppCompatActivity implements PostExecuteActivity<House> {

    /**
     * Instance de FirebaseAuth pour gérer l'authentification des utilisateurs.
     */
    private FirebaseAuth mAuth;

    /**
     * Liste des maisons récupérées depuis le serveur distant.
     */
    private static List<House> HOUSE_LIST;

    /**
     * Liste filtrée des maisons en fonction des critères spécifiés par l'utilisateur.
     */
    private static List<House> HOUSE_LIST_FILTERED;

    /**
     * Champ de sélection pour les appartements.
     */
    private RadioButton checkBox;

    /**
     * Champ de sélection pour les maisons.
     */
    private RadioButton checkBox2;

    /**
     * Champ de sélection pour les abris.
     */
    private RadioButton checkBox3;

    /**
     * Champ de saisie pour le budget.
     */
    private EditText budgetEditText;

    /**
     * Champ de saisie pour la localisation.
     */
    private EditText locationEditText;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les éléments de l'interface utilisateur, vérifie l'authentification de l'utilisateur
     * et récupère les données sur les maisons depuis le serveur distant.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        // Vérification de l'authentification de l'utilisateur
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
        }

        // Récupération des données sur les maisons depuis le serveur distant
        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));

        // Initialisation des éléments de l'interface utilisateur et définition des actions des boutons
        initUI();
    }

    /**
     * Méthode appelée après la récupération des données sur les maisons depuis le serveur.
     * Traite les données et les stocke dans la liste des maisons.
     *
     * @param itemList liste des maisons récupérées depuis le serveur.
     */
    @Override
    public void onPostExecute(List<House> itemList) {
        HOUSE_LIST = itemList;
        HOUSE_LIST_FILTERED = itemList;
    }

    /**
     * Méthode pour initialiser les éléments de l'interface utilisateur et définir les actions des boutons.
     */
    private void initUI() {
        // Initialisation des éléments de l'interface utilisateur
        ImageButton buttonGPS = findViewById(R.id.mapButton);
        ImageButton profileButton = findViewById(R.id.profileButton);
        Button searchButton = findViewById(R.id.searchButton);
        checkBox = findViewById(R.id.radioAppartment);
        checkBox2 = findViewById(R.id.radioHouse);
        checkBox3 = findViewById(R.id.radioAbris);
        budgetEditText = findViewById(R.id.locationBudgetEditText);
        locationEditText = findViewById(R.id.locationEditText);

        // Définition des actions des boutons
        buttonGPS.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        profileButton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        searchButton.setOnClickListener(v -> {
            filterList();
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("houses", HOUSE_LIST_FILTERED.toArray(new House[HOUSE_LIST_FILTERED.size()]));
            startActivity(intent);
        });
    }

    /**
     * Méthode pour filtrer la liste des maisons en fonction des critères spécifiés par l'utilisateur.
     */
    private void filterList() {
        List<House> filteredList = new ArrayList<>();

        // Filtrage des maisons en fonction des critères spécifiés
        for (House house : HOUSE_LIST) {
            boolean isHouseValid = true;

            if (checkBox.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Appartement");
            }
            if (checkBox2.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Maison");
            }
            if (checkBox3.isChecked()) {
                isHouseValid = isHouseValid && house.getType().equals("Abris");
            }

            if (!budgetEditText.getText().toString().isEmpty()) {
                int budgetValue = Integer.parseInt(budgetEditText.getText().toString());
                isHouseValid = isHouseValid && house.getPrix() <= budgetValue;
            }

            if (!locationEditText.getText().toString().isEmpty()) {
                String locationValue = locationEditText.getText().toString().toLowerCase();
                isHouseValid = isHouseValid && house.getLocalisation().toLowerCase().contains(locationValue);
            }

            if (isHouseValid) {
                filteredList.add(house);
            }
        }

        HOUSE_LIST_FILTERED = filteredList;

        // Si aucun résultat correspondant n'est trouvé, afficher toutes les maisons
        if (HOUSE_LIST_FILTERED.isEmpty()) {
            HOUSE_LIST_FILTERED = new ArrayList<>(HOUSE_LIST);
        }
    }
}
