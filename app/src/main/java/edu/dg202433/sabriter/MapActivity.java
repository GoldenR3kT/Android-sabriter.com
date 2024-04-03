package edu.dg202433.sabriter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.squareup.picasso.Picasso;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.authentification.ProfileActivity;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

/**
 * La classe MapActivity est responsable de l'affichage d'une carte OpenStreetMap avec des marqueurs représentant les maisons disponibles.
 * Elle utilise la bibliothèque osmdroid pour afficher la carte et gérer les marqueurs.
 */
public class MapActivity extends AppCompatActivity implements LocationListener, PostExecuteActivity<House> {

    private MapView map; // Vue de la carte
    private LocationManager locationManager; // Gestionnaire de localisation
    private static final int REQUEST_LOCATION_PERMISSION = 1; // Code de demande de permission de localisation

    private static final List<House> HOUSE_LIST = new ArrayList<>(); // Liste des maisons disponibles

    ArrayList<OverlayItem> items = new ArrayList<>(); // Liste des marqueurs pour les maisons

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise la carte, demande la permission de localisation et récupère les données des maisons disponibles.
     *
     * @param savedInstanceState données de l'état de l'activité sauvegardées lors de la rotation de l'écran, etc.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map);

        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        TextView title = findViewById(R.id.title);
        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        ImageButton profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Méthode appelée lorsque l'activité passe en pause.
     */
    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * Méthode appelée lorsque l'activité reprend.
     */
    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * Méthode appelée lorsque la localisation change.
     *
     * @param location Nouvelle localisation.
     */
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        setMapStartPoint(startPoint);
        locationManager.removeUpdates(this);
    }

    /**
     * Méthode pour définir le point de départ de la carte.
     *
     * @param startPoint Point de départ de la carte.
     */
    private void setMapStartPoint(GeoPoint startPoint) {
        map.getController().setZoom(9.5);
        map.getController().setCenter(startPoint);
    }

    /**
     * Méthode appelée lorsqu'une demande de permission est accordée ou refusée.
     *
     * @param requestCode  Code de la demande de permission.
     * @param permissions  Permissions demandées.
     * @param grantResults Résultats de la demande de permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
            } else {
                GeoPoint startPoint = new GeoPoint(48.8583, 2.2944); // Position par défaut (Paris)
                setMapStartPoint(startPoint);
            }
        }
    }

    /**
     * Méthode appelée lorsque la récupération des données des maisons est terminée.
     * Initialise les marqueurs sur la carte pour chaque maison.
     *
     * @param itemList Liste des maisons récupérées.
     */
    @Override
    public void onPostExecute(List<House> itemList) {
        HOUSE_LIST.addAll(itemList);

        for (House house : HOUSE_LIST) {
            items.add(new OverlayItem(house.getNom(), "", new GeoPoint(house.getLatitude(), house.getLongitude())));
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),
                items, new ItemizedOverlayWithFocus.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                AlertDialog.Builder alertDialogBuilder;
                alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);

                alertDialogBuilder.setTitle(item.getTitle());
                alertDialogBuilder.setIcon(R.drawable.home);

                House selectedHouse = HOUSE_LIST.get(index);

                ImageView imageView = new ImageView(MapActivity.this);
                Picasso.get().load(selectedHouse.getCompleteImageLinks()[0]).into(imageView);

                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(700);
                imageView.setMaxHeight(550);

                alertDialogBuilder.setView(imageView);

                alertDialogBuilder
                        .setMessage(selectedHouse.getDescription())
                        .setCancelable(true)
                        .setNeutralButton("Voir l'offre", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MapActivity.this, HouseActivity.class);
                                intent.putExtra("selectedHouse", selectedHouse);
                                startActivity(intent);
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
    }
}

