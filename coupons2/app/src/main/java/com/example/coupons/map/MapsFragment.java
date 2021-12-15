package com.example.coupons.map;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static androidx.constraintlayout.motion.widget.Debug.printStack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements LocationListener {
    GoogleMap googleMap1;
    GoogleMap mMap;
//    FusedLocationProviderClient client;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap1 = googleMap;
            googleMap.setMyLocationEnabled(true);
            MarkerOptions markerOptions = new MarkerOptions();

            LatLng latLng = new LatLng(BaseClass.my_lat, BaseClass.my_lng);
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            googleMap.clear();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            googleMap.addMarker(markerOptions);

//            CircleOptions co= new CircleOptions();
//            for (Geofence geofence: BaseClass.geofences){
//                co.center(geofence.getRequestId().);
//                googleMap.addCircle();
//            }


//            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(latLng);
//                    markerOptions.title(latLng.latitude + "" + latLng.longitude);
//                    googleMap.clear();
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                    googleMap.addMarker(markerOptions);
//                }
//            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_maps, container, false);
//        return View.inflate(getContext(), R.layout.fragment_maps, container);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {

            super.onViewCreated(view, savedInstanceState);
//        client = LocationServices.getFusedLocationProviderClient(this);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
            if (mapFragment != null) {
                mapFragment.getMapAsync(callback);
            }
        } catch (Exception e) {
            Log.e("TAG", "onCreateView", e);
            throw e;
        }
    }


//    private void initLocation(){
//        getLocation();
//    }


//    public void setCurrentLocation(MarkerOptions mo) {
////        SupportMapFragment mapFragment =
////                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
////        mapFragment.getMapAsync(new OnMapReadyCallback() {
////            @Override
////            public void onMapReady(GoogleMap googleMap) {
////                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mo.getPosition(), 10));
////                googleMap.addMarker(mo);
////            }
////        });
//        googleMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(mo.getPosition(), 10));
//        googleMap1.addMarker(mo);
//        return;
//    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
//        googleMap1.clear();
//        googleMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//        googleMap1.addMarker(markerOptions);
    }

}

