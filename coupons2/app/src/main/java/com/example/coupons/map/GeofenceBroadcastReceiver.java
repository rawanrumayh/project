package com.example.coupons.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent event= GeofencingEvent.fromIntent(intent);
        if (event.hasError()){
            Log.d("GEOBROAD", "error receive");
            return;
        }

        List<Geofence> geofenceList= event.getTriggeringGeofences();
        for(Geofence geofence: geofenceList){
            Log.d("GEOBROAD", "Challenge ID: "+geofence.getRequestId());
        }
        int transType= event.getGeofenceTransition();
        switch (transType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.d("enteeer","Geofence Triggered");
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Log.d("Dweeellll","Geofence Triggered222");
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Log.d("exiitttt","Geofence Triggered33333");
        }

    }
}
