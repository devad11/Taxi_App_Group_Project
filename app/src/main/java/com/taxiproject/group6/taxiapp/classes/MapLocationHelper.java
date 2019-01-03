package com.taxiproject.group6.taxiapp.classes;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLocationHelper  {

    private static final String TAG = "MapLocationHelper";
    private static final float DEFAULT_ZOOM = 15;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker pickUpMarker, destinationMarker;
    private LatLng pickUpLatLng, destinationLatLng;

    public MapLocationHelper(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void geoLocate(Activity activity, String searchString, PlaceInfo placeInfo) {
        Log.d(TAG, "geoLocate: Locating");
//        String searchString = inputSearchEditText.getText().toString();
        Geocoder geocoder = new Geocoder(activity);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch(IOException ioe){
            Log.d(TAG, "geoLocate: IOException: " + ioe.getMessage());
        }
        if(list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "Found address: " + address.toString());
//            Toast.makeText(activity, "Address: " + address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, placeInfo);
        }
    }

    public void getDeviceLocation(Activity activity, boolean permissionsGranted) {
        Log.d(TAG,"getDeviceLocation: getting devices current location.");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        try{
            if(permissionsGranted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        Log.d(TAG, "getDeviceLocation: location found");
                        Location currentLocation =  (Location) task.getResult();
                        LatLng currentCoordinates =
                                new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                        PlaceInfo placeInfo = new PlaceInfo();
                        placeInfo.setName("My Location");
                        moveCamera(currentCoordinates, DEFAULT_ZOOM, placeInfo);
                    }else{
                        Log.d(TAG, "getDeviceLocation: location NOT found");
                        Toast.makeText(activity, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch(SecurityException se){
            Log.d(TAG, "getDeviceLocation: SecurityException: "  +  se.getMessage());
        }
    }

    public void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG,"moveCamera: changing map position to:  lat: " + latLng.latitude + " lng:  " +  latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!placeInfo.getName().equalsIgnoreCase("my location")) {
            if(placeInfo.getPosition().equalsIgnoreCase("pickup")) {
                if(pickUpMarker != null) {
                    pickUpMarker.setPosition(latLng);
                    pickUpLatLng = latLng;
                }else {
                    MarkerOptions marker = new MarkerOptions()
                            .position(latLng)
                            .title(placeInfo.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    pickUpMarker = mMap.addMarker(marker);
                }
//                getRouterToMarker(pickUpLatLng);
            }else if(placeInfo.getPosition().equalsIgnoreCase("destination")){
                if(destinationMarker != null) {
                    destinationMarker.setPosition(latLng);
                    destinationLatLng = latLng;
                }
                else {
                    MarkerOptions marker = new MarkerOptions()
                            .position(latLng)
                            .title(placeInfo.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    destinationMarker = mMap.addMarker(marker);
                }

            }else if(placeInfo.getPosition().equalsIgnoreCase("null")){
                Log.d(TAG, "placeInfo position == null");
            }
        }
    }

    public LatLng getPickUpLatLng() {
        return pickUpLatLng;
    }

    public void setPickUpLatLng(LatLng pickUpLatLng) {
        this.pickUpLatLng = pickUpLatLng;
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(LatLng destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }
}
