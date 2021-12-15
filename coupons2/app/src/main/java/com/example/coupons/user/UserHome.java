package com.example.coupons.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coupons.Database;
import com.example.coupons.UserMapsActivity;
import com.example.coupons.controls.challengeAdapter;
import com.example.coupons.map.GeofenceHelper;
import com.example.coupons.map.MapsFragment;
import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;
import com.example.coupons.model.challenge_model;
import com.example.coupons.owner.OwnerHome;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class UserHome extends LocationBaseActivity {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private static final int PERMISSIONS_BACKGROUND_LOCATION = 170;
    private ProgressDialog progressDialog;
    private String address = "";
    Fragment fragment;
    Button logout;
    GeofencingClient client;
    final int GEOFENCE_RADIUS = 100;
    GeofenceHelper helper;
    RecyclerView recyclerView;
    challengeAdapter adapter;
    List<challenge_model> challengesList;
    Database dbHelper;


    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Location permission!", "Would you mind to turn GPS on?");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bnavBar);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_map:
                        startActivity(new Intent(getApplicationContext(), UserMapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        return true;

                }
                return false;
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(UserHome.this, OwnerHome.class);
                // currentContext.startActivity(activityChangeIntent);
                UserHome.this.startActivity(activityChangeIntent);
            }
        });


        client = LocationServices.getGeofencingClient(this);
        helper = new GeofenceHelper(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getAllGeofences();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSIONS_BACKGROUND_LOCATION);
            }
        }

        dbHelper = new Database(UserHome.this);

        challengesList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.challengeRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (BaseClass.triggeredChallenge != -1) {
            Log.d("challenge", "there is a close challenge");
            challengesList.add(
                    new challenge_model(
                            BaseClass.triggeredChallenge,
                            dbHelper.getChallengeQuestion("" + BaseClass.triggeredChallenge),
                            dbHelper.getChallengeAnswer("" + BaseClass.triggeredChallenge)
                    )
            );

            adapter = new challengeAdapter(this, challengesList);
            recyclerView.setAdapter(adapter);
        }
        content();

    }

    @SuppressLint("MissingPermission")
    public void content() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            double lat = location.getLatitude();
            double longi = location.getLongitude();
            LatLng latLng = new LatLng(lat, longi);
            BaseClass.location_saved= true;
            BaseClass.my_lat= lat;
            BaseClass.my_lng= longi;
//            fragment= new MapsFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.map_holder, fragment).commit();
//            Toast.makeText(UserHome.this, "MyLastLocation coordinat :" + latLng, Toast.LENGTH_LONG).show();
        }

        if (BaseClass.triggeredChallenge != -1) {
            Log.d("challenge", "there is a close challenge");
            challengesList.clear();
            challengesList.add(
                    new challenge_model(
                            BaseClass.triggeredChallenge,
                            dbHelper.getChallengeQuestion("" + BaseClass.triggeredChallenge),
                            dbHelper.getChallengeAnswer("" + BaseClass.triggeredChallenge)
                    )
            );

            adapter = new challengeAdapter(UserHome.this, challengesList);
            recyclerView.setAdapter(adapter);
        }
        refresh(1000);
    }

    private void refresh(int ms) {
        final Handler hld = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        hld.postDelayed(runnable, ms);
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    fragment = new MapsFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.map_holder2, fragment).commit();

                } else
                    Toast.makeText(this, "this app was not granted permission", Toast.LENGTH_SHORT).show();
            case PERMISSIONS_BACKGROUND_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAllGeofences();
                } else
                    Toast.makeText(this, "this app was not granted permission1111", Toast.LENGTH_LONG).show();
        }
    }


    private void initLocation() {
        getLocation();
//        Toast.makeText(this, "YESSS", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {
        StringBuilder locationString = new StringBuilder();
        locationString.append("Longitude: " + location.getLongitude());
        locationString.append("\n");
        locationString.append("Latitude: " + location.getLatitude());
//        Log.i(BaseClass.TAG, "location : " + locationString);
        BaseClass.my_lng = location.getLongitude();
        BaseClass.my_lat = location.getLatitude();

        try {
            Geocoder geocoder = new Geocoder(UserHome.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            BaseClass.my_lng = location.getLongitude();
            BaseClass.my_lat = location.getLatitude();
//            fragment = new MapsFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.map_holder2, fragment).commit();

            this.address = address;
            Toast.makeText(this, "address" + address, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        populateNearestPolls();

        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        } else {
            initLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissProgress();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onLocationFailed(int type) {
    }

    public void getAllGeofences() {
        Database dbHelper = new Database(UserHome.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeID, Database.colOwnerLng, Database.colOwnerLat},
                null, null, null, null, null); //selection


        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String lng = cursor.getString(cursor.getColumnIndex(Database.colOwnerLng));
                @SuppressLint("Range") String lat = cursor.getString(cursor.getColumnIndex(Database.colOwnerLat));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Database.colChallengeID));

                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                addGeofence(id, latLng, GEOFENCE_RADIUS);
            }
        }
        cursor.close();
    }

    private void addGeofence(String id, LatLng latLng, float radius) {
        Geofence geofence = helper.getGeofence(id, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest request = helper.getGeofencenigRequest(geofence);
        PendingIntent pendingIntent = helper.getPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        } else {
            client.addGeofences(request, pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {
//                    Toast.makeText(UserHome.this, "added", Toast.LENGTH_LONG).show();
                    if (BaseClass.geofences.add(geofence)) ;
//                        Toast.makeText(UserHome.this, "okkkkk", Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(UserHome.this, "here", Toast.LENGTH_LONG).show();
                    Log.d("client.addGeofences", e.toString());
                }
            });

        }
    }
}
