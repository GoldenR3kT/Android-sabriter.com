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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import edu.dg202433.android_projet.R;
import edu.dg202433.sabriter.classes.House;
import edu.dg202433.sabriter.request.HttpAsyncGet;
import edu.dg202433.sabriter.request.PostExecuteActivity;

public class MapActivity extends AppCompatActivity implements LocationListener, PostExecuteActivity<House> {

    private MapView map;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private static final List<House> HOUSE_LIST = new ArrayList<>(); //the complete list

    ArrayList<OverlayItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext() ,
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.map_activity);

        String url = "https://raw.githubusercontent.com/GoldenR3kT/abri_data/main/data.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, House.class, this, new ProgressDialog(this));

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
        OverlayItem home = new OverlayItem("La Casa", "Villa", new GeoPoint(37.7749,-122.4194));
        items.add(home);
        items.add(new OverlayItem("Eiffel Tower", "Tour Eiffel", new GeoPoint(47.8583, 2.3944)));



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
    public void onLocationChanged(Location location) {
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

    @Override
    public void onPostExecute(List<House> itemList) {
        System.out.println("ICIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("itemList = " + itemList);
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
                imageView.setImageResource(R.drawable.tente_test1);


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
