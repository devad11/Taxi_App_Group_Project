package com.taxiproject.group6.taxiapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.taxiproject.group6.taxiapp.android.Manifest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.taxiproject.group6.taxiapp.R;
//import com.taxiproject.group6.taxiapp.classes.LocationHelper;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //    private final int REQUEST_LOCATION = 1;
//    private LocationManager  locationManager;
//    private LocationHelper locationHelper;
    private boolean permissionsGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;


    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getUsersPermission();
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

//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (permissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void init(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(MapsActivity.this);
    }

    public void getDeviceLocation() {
        Log.d(TAG,"getDeviceLocation: getting devices current location.");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(permissionsGranted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            Log.d(TAG, "getDeviceLocation: location found");
                            Location currentLocation =  (Location) task.getResult();
                            LatLng currentCoordinates =
                                    new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                            moveCamera(currentCoordinates, DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG, "getDeviceLocation: location NOT found");
                            Toast.makeText(MapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException se){
            Log.d(TAG, "getDeviceLocation: SecurityException: "  +  se.getMessage());
        }
    }

    private void moveCamera(LatLng latLng,  float zoom){
        Log.d(TAG,"moveCamera: changing map position to:  lat: " + latLng.latitude + " lng:  " +  latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void getUsersPermission() {
        Log.d(TAG, "getUserPermissions: called");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getUserPermissions: failed");
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 ,0,  locationListener);
            Log.d(TAG, "getUserPermissions: granted");
            permissionsGranted = true;
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        permissionsGranted = false;
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0){
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        permissionsGranted = false;
                        Log.d(TAG, "onRequestPermissionsResult: failed");
                        return;
                    }
                    Log.d(TAG, "onRequestPermissionsResult: granted");
                    permissionsGranted = true;
                    init();
                }
            }else{
                Toast.makeText(MapsActivity.this, "Need location permissions ON", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
