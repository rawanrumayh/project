package com.example.coupons.user;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.Window;

import com.example.coupons.globals.BaseClass;
import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;

import java.util.List;
import java.util.Locale;

public class LocationManager extends LocationBaseActivity {

    private ProgressDialog progressDialog;

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Location permission!", "Would you mind to turn GPS on?");
    }

    private void initLocation() {
        getLocation();
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
    public void onLocationChanged(Location location) {
        StringBuilder locationString = new StringBuilder();
        locationString.append("Longitude: " + location.getLongitude());
        locationString.append("\n");
        locationString.append("Latitude: " + location.getLatitude());

        if (location != null) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                BaseClass.my_lng = location.getLongitude();
                BaseClass.my_lat = location.getLatitude();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

}
