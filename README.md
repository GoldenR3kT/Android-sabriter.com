# Android fil rouge

## GPS
Ce gps se concentre sur l'affichage d'une carte OpenStreetMap avec des marqueurs de maison disponbles à la vente.

Dans le fichier 'AndroidManifest.xml' les permissions nécessaires pour accéder au GPS sont déclarées : 
####
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
####
Ces permissions permettent à l'application d'accéder aux données de localisation précises et approximatives.

Dans la classe 'MapActivity', le GPS est activé de manière suivante : 
####
locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
} else {
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
}
####
Si la permission d'accès à la localisation est accordée, la méthode 'requestLocationUpdates()' est appelée pour demander des mises à jour de localisation. Sinon, la permission est demandée à l'utilisateur.


La méthode onLocationChanged() est appelée chaque fois qu'une nouvelle localisation est disponible :
####
public void onLocationChanged(Location location) {
    double latitude = location.getLatitude();
    double longitude = location.getLongitude();
    GeoPoint startPoint = new GeoPoint(latitude, longitude);
    setMapStartPoint(startPoint);
    locationManager.removeUpdates(this);
}
####
Cette méthode récupère les coordonnées de latitude et de longitude de la localisation actuelle, puis centre la carte sur ce point.

La méthode onRequestPermissionsResult() est utilisée pour gérer la réponse de l'utilisateur à la demande de permission :
####
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_LOCATION_PERMISSION) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        } else {
            GeoPoint startPoint = new GeoPoint(48.8583, 2.2944); // Coordonnées par défaut (Paris)
            setMapStartPoint(startPoint);
        }
    }
}
####
Si la permission est accordée, les mises à jour de localisation sont démarrées. Sinon, la carte est centrée sur des coordonnées par défaut (Paris).