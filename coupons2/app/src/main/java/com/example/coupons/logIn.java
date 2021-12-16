package com.example.coupons;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.example.coupons.globals.BaseClass;
import com.example.coupons.owner.OwnerHome;
import com.example.coupons.user.UserHome;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class logIn extends AppCompatActivity {
    EditText username,password;
    Button Login;
    Database DB;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
        Title.setText("Login");
        String color = getString(Integer.parseInt(String.valueOf(R.color.white)));
        Title.setTextColor(Color.parseColor(color));

        getSupportActionBar().setCustomView(view,params);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title



        //Input
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.login);
        DB = new Database(this);

        //Listeners
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String UserName = username.getText().toString();
                String pass = password.getText().toString();

                if(UserName.isEmpty() || pass.isEmpty()){
                    Toast.makeText(logIn.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean login= DB.checkUsernamePassword(UserName,pass);

                    if(login == true){
                        updateGPS();
                        Toast.makeText(logIn.this,"log in successful",Toast.LENGTH_SHORT).show();

                        String type = DB.getUserType(UserName);

                        if(type.equals("owner")){
                            DB.close();
                            Intent i = new Intent(getApplicationContext(), OwnerHome.class);
                            startActivity(i);
                        }
                        else if (type.equals("user")) {
                            DB.close();
                            Intent i = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(logIn.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(logIn.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
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
            BaseClass.location_saved= true;
            BaseClass.my_lat= lat;
            BaseClass.my_lng= longi;
            try {
                Geocoder geocoder = new Geocoder(logIn.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                BaseClass.my_lng = location.getLongitude();
                BaseClass.my_lat = location.getLatitude();
                BaseClass.address = address;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            fragment= new MapsFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.map_holder, fragment).commit();
        } else {
            setMyLastLocation();
        }
    }

    private void setMyLastLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        else fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(logIn.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        BaseClass.my_lng = location.getLongitude();
                        BaseClass.my_lat = location.getLatitude();
                        BaseClass.address = address;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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