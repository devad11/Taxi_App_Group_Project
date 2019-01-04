package com.taxiproject.group6.taxiapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
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

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.classes.DatabaseConnector;
import com.taxiproject.group6.taxiapp.classes.MapLocationHelper;
import com.taxiproject.group6.taxiapp.classes.PlaceAutocompleteAdapter;
import com.taxiproject.group6.taxiapp.classes.PlaceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private GoogleMap mMap;
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

//    private AutoCompleteTextView inputSearchEditText, pickUpEditText, destinationEditText;
    private AutoCompleteTextView pickUpEditText, destinationEditText;
    private ImageView gpsImage, personalDetailsImage;
    private Button logoutButton, bookingButton;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GoogleApiClient googleApiClient;
    private GeoDataClient geoDataClient;
    private PlaceInfo placeInfo;
    private LatLng pickUpLatLng, destinationLatLng;

    private String locLat, locLng, destLat, destLng, cost, position;

    private List<Polyline> polyLines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};

    private double distance;
    private int duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bookingButton = findViewById(R.id.bookingButton);
        logoutButton = findViewById(R.id.logoutButton);
        personalDetailsImage = findViewById(R.id.personalDetailsButton);
//        inputSearchEditText = findViewById(R.id.inputSearchEditText);
        pickUpEditText = findViewById(R.id.pickupEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        gpsImage = findViewById(R.id.gpsImage);
        getUsersPermission();

        pickUpEditText.setTag("PickUp");
        destinationEditText.setTag("Destination");

        polyLines = new ArrayList<>();
////////////////////dummy data//////////////////////////////////
        locLat = "52.239944";
        locLng = "-8.7064314";
        destLat = "59.235684";
        destLng = "-8.6064321";
        cost = "12.0";

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(i);
        });

        bookingButton.setOnClickListener(v -> {
            DatabaseConnector.sendBooking(locLat, locLng, destLat, destLng, cost);
            Intent intent = new Intent(this, PaypalActivity.class);
            startActivity(intent);
        });
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

        pickUpEditText.setOnItemClickListener(autoCompleteClickListener);
        destinationEditText.setOnItemClickListener(autoCompleteClickListener);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(
                this, googleApiClient, LAT_LNG_BOUNDS, null);

        pickUpEditText.setAdapter(placeAutocompleteAdapter);
        pickUpEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                String searchString = pickUpEditText.getText().toString();
                this.placeInfo.setPosition("PickUp");
                position = "PickUp";

                mapLocationHelper.geoLocate(this, searchString, placeInfo);
                pickUpLatLng = mapLocationHelper.getPickUpLatLng();
            }
            return false;
        });

        destinationEditText.setAdapter(placeAutocompleteAdapter);
        destinationEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                String searchString = destinationEditText.getText().toString();
                this.placeInfo.setPosition("Destination");
                position = "Destination";

                mapLocationHelper.geoLocate(this, searchString, placeInfo);
                destinationLatLng = mapLocationHelper.getDestinationLatLng();
                getRouterToMarker();
            }
            return false;
        });

        gpsImage.setOnClickListener(v -> {
            Log.d(TAG, "gpsImage: clicked gps image");
            mapLocationHelper.getDeviceLocation(this, permissionsGranted);
        });
        hideSoftKeyBoard();

        personalDetailsImage.setOnClickListener(v -> {
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
        -----------------   Routing Listener Methods -------------------------
        Draws a polyline to show the direction
     */

    private void getRouterToMarker() {
        erasePolyLines();
        if(destinationLatLng != null && pickUpLatLng != null) {
            String url = mapLocationHelper.makeURL(pickUpLatLng, destinationLatLng);

            String api ="AIzaSyBGgab4P7_F83Q5NsWG2top64dmpSyBfbc";
            Routing routing = new Routing.Builder()
                    .key(api)
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(pickUpLatLng, destinationLatLng)
                    .build();
            routing.execute();
        }
    }


    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        if(polyLines.size()>0) {
            for (Polyline poly : polyLines) {
                poly.remove();
            }
        }

        polyLines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polyLines.add(polyline);
            distance = route.get(i).getDistanceValue();
            duration = route.get(i).getDurationValue();
            Log.d(TAG, "Distance between points: " + distance + " Duration: " + duration/60);
            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ distance +": duration - "+ duration ,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled() {

    }

    // to erase the polyLines if ride is cancelled
    private void erasePolyLines(){
        if(polyLines != null) {
            for (Polyline line : polyLines) {
                line.remove();
            }
            polyLines.clear();
        }
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
        Log.d(TAG, "---------POSITION:" + position);

        position = getCurrentFocus().getTag().toString();

        try {
            placeInfo.setName(place.getName().toString());
            placeInfo.setLatLng(place.getLatLng());
            placeInfo.setRating(place.getRating());
            placeInfo.setId(place.getId());
            placeInfo.setAddress(place.getAddress().toString());
            placeInfo.setPhoneNo(place.getPhoneNumber().toString());
            placeInfo.setWebsiteUri(place.getWebsiteUri());
            placeInfo.setPosition(position);
        }catch (NullPointerException npe){
            Log.d(TAG, "PlaceInfo: null param");
        }
        Log.d(TAG, "onResult: place details: " + placeInfo.toString());



        mapLocationHelper.moveCamera(placeInfo.getLatLng(), DEFAULT_ZOOM, placeInfo);

        if(position.equalsIgnoreCase("destination"))
            destinationLatLng = placeInfo.getLatLng();
        else if(position.equalsIgnoreCase("pickup"))
            pickUpLatLng = placeInfo.getLatLng();

        getRouterToMarker();
        places.release();
    };

}
