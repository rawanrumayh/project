package com.example.coupons.map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofenceHelper extends ContextWrapper {
    static final String TAG = "GeofenceHelper";
    PendingIntent pendingIntent;


    public GeofenceHelper(Context base) {
        super(base);
    }

    public GeofencingRequest getGeofencenigRequest(Geofence geofence) {
        return new GeofencingRequest.Builder().addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).build();
    }

    public Geofence getGeofence(String id, LatLng latLng, float radius, int transitionTypes) {
        return new Geofence.Builder().setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setRequestId(id).setTransitionTypes(transitionTypes).setExpirationDuration(Geofence.NEVER_EXPIRE).setLoiteringDelay(20).build();
    }

    public PendingIntent getPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


}
