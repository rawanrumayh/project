package com.example.coupons.owner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.coupons.Database;
import com.example.coupons.R;
import com.example.coupons.globals.BaseClass;
import com.example.coupons.logIn;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class coffeeOwnerSignUp extends AppCompatActivity {
    EditText coffeeName, userName, password, repassword;
    Button signUp;
    Database DB;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_owner_sign_up);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Action bar title
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.actionbar_title);
        Title.setText("Sign Up");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title


        //Input
        coffeeName = (EditText) findViewById(R.id.coffeeName);
        userName = (EditText) findViewById(R.id.coffeeUsername);
        password = (EditText) findViewById(R.id.coffeePassword);
        repassword = (EditText) findViewById(R.id.coffeePassword2);
        signUp = (Button) findViewById(R.id.COsignup);
        DB = new Database(this);

        //Listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = coffeeName.getText().toString();
                String cUsername = userName.getText().toString();

                String pass = password.getText().toString();
                String rePass = repassword.getText().toString();

                if (cName.isEmpty() || cUsername.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
                    Toast.makeText(coffeeOwnerSignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(rePass)) {
                        Boolean checkUser = DB.checkUsername(cUsername);

                        if (checkUser == false) {
                            updateGPS();
                            Boolean insert = DB.register(cUsername, pass, cName, "owner");

                            if (insert == true) {
                                Toast.makeText(coffeeOwnerSignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), OwnerHome.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(coffeeOwnerSignUp.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(coffeeOwnerSignUp.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(coffeeOwnerSignUp.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
        } else
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(coffeeOwnerSignUp.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String address = addresses.get(0).getAddressLine(0);
                            BaseClass.my_lng = location.getLongitude();
                            BaseClass.my_lat = location.getLatitude();
                            BaseClass.address = address;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        double lat = location.getLatitude();
                        double longi = location.getLongitude();
                        LatLng latLng = new LatLng(lat, longi);
                        BaseClass.my_lat = lat;
                        BaseClass.my_lng = longi;
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


}