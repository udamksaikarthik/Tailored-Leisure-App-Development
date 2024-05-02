package com.project.tailoredleisureappdevelopment;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class VenuesMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Global Variables
    private final String google_places_api_key = "AIzaSyAAnchBf4s7Nr-9cU9K3jUiUGvdioi-5ig";
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap gMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private SearchView mapSearchView;

    private PlacesClient placesClient;

    private static final String TAG = "VenuesMapsActivity";

    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    private EditText editText;
    private TextView textView;

    private com.project.tailoredleisureappdevelopment.entities.Place placeObj;

    private Button venueSearchBtnId;

    /*
    The onCreate method is the start of the Layout Activity
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("VenuesMapsActivity", "Inside VenuesMapsActivity Class OnCreate Method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        placeObj = new com.project.tailoredleisureappdevelopment.entities.Place();

        editText = findViewById(R.id.search_map_id);

        venueSearchBtnId = (Button) findViewById(R.id.venue_search_button_id);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        Places.initialize(getApplicationContext(),google_places_api_key);

        if(Places.isInitialized()){
            Log.d("VenuesMapsActivity", "Initializing the Places.");
            Places.initialize(getApplicationContext(),google_places_api_key);
        }else{
            Log.d("VenuesMapsActivity", "Places are already intialized.");
        }

        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.PHONE_NUMBER, Place.Field.WEBSITE_URI, Place.Field.RATING, Place.Field.USER_RATINGS_TOTAL, Place.Field.ID);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        , fieldList).build(VenuesMapsActivity.this);

                startActivityForResult(intent, 100);
            }
        });

        venueSearchBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityVenueInfo();
            }
        });

    }

    private void openActivityVenueInfo(){
        Log.d("VenuesMapsActivity: DEBUG", "openActivityVenueInfo METHOD: called.");
        Intent intent = new Intent(VenuesMapsActivity.this, VenuesMapsActivityInfo.class);
        intent.putExtra("PLACE_OBJECT", (Serializable) placeObj);
        Log.d("VenuesMapsActivity: DEBUG", "placeObj: "+placeObj.toString());
        startActivity(intent);
    }




    private void getLastLocation() {
        Log.d("VenuesMapsActivity: DEBUG", "getLastLocation: called.");

        // Asking and checking for device location permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);

            return;
        }

        //retrives most recent cached device location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d("VenuesMapsActivity: DEBUG", "getLastLocation: onSuccess.");
                if(location!=null){
                    Log.d("VenuesMapsActivity: DEBUG", "location is not null.");
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_venues_map);
                    mapFragment.getMapAsync(VenuesMapsActivity.this);
                }else{
                    Log.d("VenuesMapsActivity: DEBUG", "location is null.");
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        gMap = googleMap;

        LatLng userLocation= new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        gMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            Double latitude = place.getLatLng().latitude;
            Double longitude = place.getLatLng().longitude;
            placeObj.setAddress(place.getAddress());
            placeObj.setName(place.getName());
            placeObj.setRating(place.getRating());
            placeObj.setWebsiteUri(place.getWebsiteUri().toString());
            placeObj.setUserRatingsTotal(place.getUserRatingsTotal());
            placeObj.setPhoneNumber(place.getPhoneNumber());
            editText.setText(place.getAddress());
            placeObj.setPlaceId(place.getId());
            LatLng LatLng = new LatLng(latitude, longitude);
            gMap.addMarker(new MarkerOptions().position(LatLng).title(place.getAddress()));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng, 10));
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
