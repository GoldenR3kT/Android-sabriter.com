package edu.dg202433.sabriter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.animation.DrawableAlphaProperty;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import edu.dg202433.android_projet.R;

public class MapActivity extends AppCompatActivity implements LocationListener {

    private MapView map;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext() ,
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.map_activity);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // Demander des mises à jour de localisation
        } else {
            // Demander la permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        /* GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        IMapController mapController = map.getController();
        mapController.setZoom(9.5);
        mapController.setCenter(startPoint); */

        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("La Casa", "Villa", new GeoPoint(48.8583, 2.2944));
        items.add(home);
        items.add(new OverlayItem("Eiffel Tower", "Tour Eiffel", new GeoPoint(47.8583, 2.3944)));


        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),
                items, new ItemizedOverlayWithFocus.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        AlertDialog.Builder alertDialogBuilder;
                        alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);

                        alertDialogBuilder.setTitle("titre");
                        alertDialogBuilder.setIcon(R.drawable.home);

                        ImageView imageView = new ImageView(MapActivity.this);
                        imageView.setImageResource(R.drawable.tente_test1);


                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setMaxWidth(700);
                        imageView.setMaxHeight(550);

                        alertDialogBuilder.setView(imageView);

                        alertDialogBuilder
                                .setMessage("message")
                                .setCancelable(true)
                                .setNeutralButton("Voir l'offre", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MapActivity.this, HouseActivity.class);
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

        TextView title = findViewById(R.id.title);
        title.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        setMapStartPoint(startPoint);
        locationManager.removeUpdates(this);
    }

    private void setMapStartPoint(GeoPoint startPoint) {
        map.getController().setZoom(9.5);
        map.getController().setCenter(startPoint);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, démarrer les mises à jour de localisation
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
            } else {
                // Permission refusée, définir la localisation par défaut
                GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
                setMapStartPoint(startPoint);
            }
        }
    }

}
