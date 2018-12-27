package com.taxiproject.group6.taxiapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.MapLocationHelper;
import com.taxiproject.group6.taxiapp.classes.PickerDialogObject;
import com.taxiproject.group6.taxiapp.classes.PlaceAutocompleteAdapter;
import com.taxiproject.group6.taxiapp.classes.PlaceInfo;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    //    private final int REQUEST_LOCATION = 1;
//    private LocationManager  locationManager;
//    private LocationHelper locationHelper;
    private boolean permissionsGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private MapLocationHelper mapLocationHelper;
    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17;
    private static final LatLngBounds LAT_LNG_BOUNDS =
            new LatLngBounds(new LatLng(51.384959, -10.269509), new LatLng(52.452138, -7.84153));

    private AutoCompleteTextView inputSearchEditText;
    private ImageView gpsImage;
    private Button personalDetailsButton, pickUpAddressButton, destinationAddressButton;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient googleApiClient;
    private GeoDataClient geoDataClient;
    private PlaceInfo placeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        destinationAddressButton = findViewById(R.id.destinationAddressButton);
        pickUpAddressButton = findViewById(R.id.pickUpAddressButton);
        personalDetailsButton = findViewById(R.id.personalDetailsButton);
        inputSearchEditText = findViewById(R.id.inputSearchEditText);
        gpsImage = findViewById(R.id.gpsImage);
        getUsersPermission();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "initializing map");
        Toast.makeText(this, "Map is  ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        mapLocationHelper = new MapLocationHelper(mMap);
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (permissionsGranted) {
//            getDeviceLocation();

            mapLocationHelper.getDeviceLocation(this, permissionsGranted);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    private void init() {
        Log.d(TAG, "init: Initialising");

//        geoDataClient = Places.getGeoDataClient(this, null);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        inputSearchEditText.setOnItemClickListener(autoCompleteClickListener);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(
                this, googleApiClient, LAT_LNG_BOUNDS, null);

        inputSearchEditText.setAdapter(placeAutocompleteAdapter);
        inputSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                String searchString = inputSearchEditText.getText().toString();
                mapLocationHelper.geoLocate(this, searchString);
            }
            return false;
        });
        gpsImage.setOnClickListener(v -> {
            Log.d(TAG, "gpsImage: clicked gps image");
            mapLocationHelper.getDeviceLocation(this, permissionsGranted);
        });
        hideSoftKeyBoard();

        pickUpAddressButton.setOnClickListener(v -> {
            PickerDialogObject customDialog = new PickerDialogObject(MapsActivity.this);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialog.show();
        });

        destinationAddressButton.setOnClickListener(v -> {
            PickerDialogObject customDialog = new PickerDialogObject(MapsActivity.this);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialog.show();
        });

        personalDetailsButton.setOnClickListener(v -> {
            Intent i = new Intent(MapsActivity.this, UserDetailsActivity.class);
            startActivity(i);
        });
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(MapsActivity.this);
    }

    private void getUsersPermission() {
        Log.d(TAG, "getUserPermissions: called");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getUserPermissions: failed");
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 ,0,  locationListener);
            Log.d(TAG, "getUserPermissions: granted");
            permissionsGranted = true;
            initMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        permissionsGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        permissionsGranted = false;
                        Log.d(TAG, "onRequestPermissionsResult: failed");
                        return;
                    }
                    Log.d(TAG, "onRequestPermissionsResult: granted");
                    permissionsGranted = true;
                    initMap();
                }
            } else {
                Toast.makeText(MapsActivity.this, "Need location permissions ON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void hideSoftKeyBoard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
            ----------------------  google places api autocomplete suggestions -----------------------
     */

    private AdapterView.OnItemClickListener autoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction ITEM = placeAutocompleteAdapter.getItem(position);
            final String PLACE_ID = Objects.requireNonNull(ITEM).getPlaceId();

            PendingResult<PlaceBuffer> placeRes = Places.GeoDataApi.getPlaceById(googleApiClient, PLACE_ID);
            placeRes.setResultCallback(updatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> updatePlaceDetailsCallback = places -> {
        if(!places.getStatus().isSuccess()){
            Log.d(TAG, "onResult: Place query not successfully completed " + places.getStatus().toString());
            places.release();
            return;
        }
        final Place place = places.get(0);

        placeInfo = new PlaceInfo();

        try {
            placeInfo.setName(place.getName().toString());
            placeInfo.setLatLng(place.getLatLng());
            placeInfo.setRating(place.getRating());
            placeInfo.setId(place.getId());
            placeInfo.setAddress(place.getAddress().toString());
            placeInfo.setPhoneNo(place.getPhoneNumber().toString());
            placeInfo.setWebsiteUri(place.getWebsiteUri());
        }catch (NullPointerException npe){
            Log.d(TAG, "PlaceInfo: null param");
        }
        Log.d(TAG, "onResult: place details: " + placeInfo.toString());

        mapLocationHelper.moveCamera(placeInfo.getLatLng(), DEFAULT_ZOOM, placeInfo.getName());

        places.release();
    };
}
