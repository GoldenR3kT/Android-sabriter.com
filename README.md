# Android fil rouge

## GPS
Ce gps se concentre sur l'affichage d'une carte OpenStreetMap avec des marqueurs de maison disponbles à la vente.

Dans le fichier 'AndroidManifest.xml' les permissions nécessaires pour accéder au GPS sont déclarées : 

```
uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" 
uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" 
```
Ces permissions permettent à l'application d'accéder aux données de localisation précises et approximatives.

Dans la classe 'MapActivity', le GPS est activé de manière suivante : 

locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

```
if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
} else {
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
}
```

Si la permission d'accès à la localisation est accordée, la méthode 'requestLocationUpdates()' est appelée pour demander des mises à jour de localisation. Sinon, la permission est demandée à l'utilisateur.


La méthode onLocationChanged() est appelée chaque fois qu'une nouvelle localisation est disponible :

```
public void onLocationChanged(Location location) {
    double latitude = location.getLatitude();
    double longitude = location.getLongitude();
    GeoPoint startPoint = new GeoPoint(latitude, longitude);
    setMapStartPoint(startPoint);
    locationManager.removeUpdates(this);
}
```
 
Cette méthode récupère les coordonnées de latitude et de longitude de la localisation actuelle, puis centre la carte sur ce point.

La méthode onRequestPermissionsResult() est utilisée pour gérer la réponse de l'utilisateur à la demande de permission :

```
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
```

Si la permission est accordée, les mises à jour de localisation sont démarrées. Sinon, la carte est centrée sur des coordonnées par défaut (Paris).

Voici la partie Firebase du README complétée :


## Firebase

Dans cette application Android, Firebase est utilisé pour l'authentification des utilisateurs. Voici comment cela est mis en œuvre :

### Authentification des utilisateurs

Firebase Auth est utilisé pour gérer l'authentification des utilisateurs avec leur adresse e-mail et leur mot de passe.

#### Activité de connexion (LoginActivity)

Lorsque l'utilisateur ouvre l'application, s'il est déjà connecté, il est redirigé vers l'écran principal (MainActivity). Sinon, il est dirigé vers l'écran de connexion où il peut saisir son adresse e-mail et son mot de passe. Lorsqu'il clique sur le bouton de connexion, la méthode `login()` est appelée. Cette méthode récupère les informations saisies par l'utilisateur et utilise `signInWithEmailAndPassword` de Firebase Auth pour tenter de se connecter. Si la connexion réussit, l'utilisateur est redirigé vers MainActivity. Sinon, une notification d'échec d'authentification est affichée.

#### Activité d'inscription (RegisterActivity)

Lorsque l'utilisateur souhaite créer un nouveau compte, il peut accéder à l'écran d'inscription où il doit saisir son adresse e-mail et choisir un mot de passe. Lorsqu'il clique sur le bouton d'inscription, la méthode `register()` est appelée. Cette méthode utilise `createUserWithEmailAndPassword` de Firebase Auth pour créer un nouveau compte avec les informations fournies par l'utilisateur. Si l'inscription réussit, l'utilisateur est redirigé vers l'écran de connexion. Sinon, une notification d'échec d'inscription est affichée.

#### Activité de profil (ProfileActivity)

L'utilisateur peut également accéder à son profil où il peut voir son adresse e-mail et se déconnecter. Lorsqu'il clique sur le bouton de déconnexion, la méthode `signOut()` de Firebase Auth est utilisée pour déconnecter l'utilisateur et le rediriger vers l'écran de connexion.

```
FirebaseAuth.getInstance().signOut();
Intent intent = new Intent(this, LoginActivity.class);
startActivity(intent);
```

Ces fonctionnalités d'authentification permettent à l'application de gérer les utilisateurs de manière sécurisée et de leur offrir une expérience personnalisée.

