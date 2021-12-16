package com.example.coupons.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coupons.Database;
import com.example.coupons.NotificationHelper;
import com.example.coupons.UserMapsActivity;
import com.example.coupons.controls.challengeAdapter;
import com.example.coupons.map.GeofenceHelper;
import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;
import com.example.coupons.model.challenge_model;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private static final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 170;
    private String address = "";
    GeofencingClient client;
    final int GEOFENCE_RADIUS = 100;
    GeofenceHelper helper;
    Database dbHelper;
    NotificationHelper nHelper;
    FusedLocationProviderClient client2;
    LinearLayout Challengeslayout;



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
        nHelper = new NotificationHelper(this);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        //Action bar title
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.actionbar_title);
        Title.setText("Home");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title

//        logout = (Button) findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//                Intent activityChangeIntent = new Intent(UserHome.this, OwnerHome.class);
//                // currentContext.startActivity(activityChangeIntent);
//                UserHome.this.startActivity(activityChangeIntent);
//            }
//        });

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


        client = LocationServices.getGeofencingClient(this);
        helper = new GeofenceHelper(this);


        if (Build.VERSION.SDK_INT >= 29) {
            //We need background permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getAllGeofences();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    //We show a dialog and ask for permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }
            }
        }

        Challengeslayout = (LinearLayout) findViewById(R.id.ChallengesLayout);


        dbHelper = new Database(UserHome.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeID},
                null, null, null, null, null); //selection

        // intent id
        if (cursor != null) {
            Challengeslayout.removeAllViews();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Database.colChallengeID));
                if (dbHelper.getChallengeLng(id) == BaseClass.my_lng && dbHelper.getChallengeLat(id) == BaseClass.my_lat) {
                    if (!BaseClass.played.contains(id)){
                        nHelper.sendHighPriorityNotification("Around U!", "Play and Earn your Coupon!", UserHome.class);
                    AddView(id);}
                }
            }
        }
        cursor.close();
        db.close();

        updateGPS();
        refresh(180000);
    }


    public void content3() {
        Challengeslayout.removeAllViews();

        Challengeslayout = (LinearLayout) findViewById(R.id.ChallengesLayout);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeID},
                null, null, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(Database.colChallengeID));
                if (dbHelper.getChallengeLng(id) == BaseClass.my_lng && dbHelper.getChallengeLat(id) == BaseClass.my_lat) {
                    if (!BaseClass.played.contains(id)){
                    nHelper.sendHighPriorityNotification("Around U!", "Play and Earn your Coupon!", UserHome.class);
                    AddView(id);}
                }
            }
        }
        cursor.close();
        db.close();

        refresh(180000);
    }

    private void refresh(int ms) {
        final Handler hld = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateGPS();
                content3();
            }
        };

        hld.postDelayed(runnable, ms);
    }

    private void zoomMyCuurentLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            double lat = location.getLatitude();
            double longi = location.getLongitude();
            LatLng latLng = new LatLng(lat, longi);
            BaseClass.location_saved = true;
            BaseClass.my_lat = lat;
            BaseClass.my_lng = longi;
        } else {
            setMyLastLocation();
        }
    }

    private void setMyLastLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double lat = location.getLatitude();
                    double longi = location.getLongitude();
                    LatLng latLng = new LatLng(lat, longi);
                    BaseClass.my_lat = lat;
                    BaseClass.my_lng = longi;
                }
            }
        });
    }


    private void updateGPS() {
        client2 = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            zoomMyCuurentLocation();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();

                } else
                    Toast.makeText(this, "this app was not granted permission", Toast.LENGTH_SHORT).show();
            case BACKGROUND_LOCATION_ACCESS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAllGeofences();
                } else
                    Toast.makeText(this, "this app was not granted permission1111", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
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

    @SuppressLint("MissingPermission")
    private void addGeofence(String id, LatLng latLng, float radius) {
        Geofence geofence = helper.getGeofence(id, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest request = helper.getGeofencenigRequest(geofence);
        PendingIntent pendingIntent = helper.getPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
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
//                    Log.d("client.addGeofences", e.toString());
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.uSettings:
                Intent i = new Intent(getApplicationContext(), userSettings.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void AddView(String id) {
        final String cId = id;
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(380, 560);
        layout.rightMargin = 10;
        layout.leftMargin = 10;
        layout.topMargin = 10;
        layout.bottomMargin = 20;
        layout.gravity = Gravity.CENTER_HORIZONTAL;
        Challengeslayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout llayout = new LinearLayout(this);
        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llayout.setOrientation(LinearLayout.HORIZONTAL);
        layparams.gravity = Gravity.CENTER;
        llayout.setLayoutParams(layparams);


        ImageView img = new ImageView(this);
        img.setImageDrawable(getResources().getDrawable(R.drawable.question2));
        LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 380);
        imgparams.setMargins(20, 20, 20, 20);
        imgparams.gravity = Gravity.CENTER;
        img.setLayoutParams(imgparams);

        LinearLayout llayout2 = new LinearLayout(this);
        LinearLayout.LayoutParams layparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llayout2.setOrientation(LinearLayout.VERTICAL);
        llayout2.setPadding(0, 360, 0, 0);
        llayout2.setLayoutParams(layparams2);

        Button playBtn = new Button(this);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 90);
        btnparams.gravity = Gravity.BOTTOM;
        playBtn.setText("PLAY");
        playBtn.setBackgroundColor(getResources().getColor(R.color.white));
        playBtn.setTextColor(getResources().getColor(R.color.purpText));
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHome.this, Challenge.class);
                i.putExtra("id", cId);
                UserHome.this.startActivity(i);
            }
        });


        CardView card = new CardView(this);
        card.setCardBackgroundColor(getResources().getColor(R.color.white));
        card.setRadius(10);
        card.setLayoutParams(layout);
        card.setCardElevation(20);
        card.setContentPadding(20, 30, 20, 30);
        llayout.addView(img);
        card.addView(llayout);
        llayout2.addView(playBtn);
        card.addView(llayout2);
        Challengeslayout.addView(card);

    }


}
