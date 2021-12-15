package com.example.coupons.owner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.coupons.Database;
import com.example.coupons.map.MapsFragment;
import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;
import com.example.coupons.ownerSettings;
import com.example.coupons.userSettings;
import com.example.coupons.user.UserHome;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OwnerHome extends AppCompatActivity {


    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FusedLocationProviderClient client;
    boolean onUpdate = false;
    Fragment fragment;
    Button logout;


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerhome);


        Database dbHelper = new Database(OwnerHome.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeQuestion, Database.colChallengeAnswer, Database.colChallengeCoupon, Database.colChallengeCouponPercentage},
                "Owner=?", new String[]{dbHelper.getCurrentUser()}, null, null, null); //selection


        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex(Database.colChallengeQuestion));
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex(Database.colChallengeAnswer));
                @SuppressLint("Range") String coupon = cursor.getString(cursor.getColumnIndex(Database.colChallengeCoupon));
                @SuppressLint("Range") String percentage = cursor.getString(cursor.getColumnIndex(Database.colChallengeCouponPercentage));
                AddView(question, answer, coupon, percentage);
            }
        }
        cursor.close();


        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addFloating);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnerHome.this, AddChallenge.class));
            }
        });


        logout= (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(OwnerHome.this, UserHome.class);
                // currentContext.startActivity(activityChangeIntent);
                OwnerHome.this.startActivity(activityChangeIntent);
            }
        });

        updateGPS();
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
            BaseClass.location_saved= true;
            BaseClass.my_lat= lat;
            BaseClass.my_lng= longi;
            fragment= new MapsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.map_holder, fragment).commit();
            Toast.makeText(OwnerHome.this, "MyLastLocation coordinat :" + latLng, Toast.LENGTH_LONG).show();
        } else {
            setMyLastLocation();
        }
    }

    private void setMyLastLocation() {
        Log.d("TAG", "setMyLastLocation: excecute, and get last location");
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
                    BaseClass.my_lat= lat;
                    BaseClass.my_lng= longi;
                    fragment= new MapsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.map_holder, fragment).commit();
                    Log.d("TAG", "MyLastLocation coordinat :" + latLng);
                    Toast.makeText(OwnerHome.this, "MyLastLocation coordinat :" + latLng, Toast.LENGTH_SHORT).show();
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.f));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    updateGPS();
                else
                    Toast.makeText(this, "this app was not granted permission", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateGPS() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            zoomMyCuurentLocation();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }


    private void AddView(String question, String answer, String coupon, String percentage) {
        LinearLayout Challengeslayout = (LinearLayout) findViewById(R.id.ChallengesLayout);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.rightMargin=10; layout.leftMargin=10; layout.topMargin=10; layout.bottomMargin=20;
        Challengeslayout.setOrientation(LinearLayout.HORIZONTAL);




        TextView questionTV = new TextView(this);
        questionTV.setText("Question");
        questionTV.setPadding(10,10,10,10);
        questionTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView questionV = new TextView(this);
        questionV.setText(question);
        questionV.setPadding(10,60,10,10);

        TextView answerTV = new TextView(this);
        answerTV.setText("Answer");
        answerTV.setPadding(10,160,10,10);
        answerTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView answerV = new TextView(this);
        answerV.setText(answer);
        answerV.setPadding(10,210,10,10);

        TextView couponTV = new TextView(this);
        couponTV.setText("Coupon");
        couponTV.setPadding(10,300,10,10);
        couponTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView couponV = new TextView(this);
        couponV.setText(coupon);
        couponV.setPadding(10,360,10,10);

        TextView PercTV = new TextView(this);
        PercTV.setText("Percentage");
        PercTV.setPadding(10,430,10,10);
        PercTV.setTypeface(Typeface.DEFAULT_BOLD);

        TextView percV = new TextView(this);
        percV.setText(percentage+"%");
        percV.setPadding(300,430,10,10);


        CardView card = new CardView(this);
        card.setCardBackgroundColor(getResources().getColor(R.color.BackgroundCard));
        card.setRadius(10);
        card.setLayoutParams(layout);
        card.setCardElevation(20);
        card.setContentPadding(20,30,20,30);
        card.addView(questionTV);
        card.addView(questionV);
        card.addView(answerTV);
        card.addView(answerV);
        card.addView(couponTV);
        card.addView(couponV);
        card.addView(PercTV);
        card.addView(percV);
        Challengeslayout.addView(card);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.owner_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.oSettings:
                Intent i = new Intent(getApplicationContext(), ownerSettings.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
