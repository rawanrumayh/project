package com.example.coupons.globals;

import android.app.Application;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;

public class BaseClass extends Application {
    public static boolean location_saved = false;
    public static double my_lat = 0.0;
    public static double my_lng = 0.0;
    public static final String TAG = "MYSMTH";
    public static List<Geofence> geofences= new ArrayList<>();
    public static int triggeredChallenge = -1;
    public static String address;


    @Override
    public void onCreate() {
        super.onCreate();
    }

}
