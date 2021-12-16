package com.example.coupons.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.coupons.NotificationHelper;
import com.example.coupons.globals.BaseClass;
import com.example.coupons.owner.OwnerHome;
import com.example.coupons.user.UserHome;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper nHelper = new NotificationHelper(context);

        GeofencingEvent event= GeofencingEvent.fromIntent(intent);
        if (event.hasError()){
            Log.d("GEOBROAD", "error receive");
            return;
        }

        int geofenceid=-1;

        List<Geofence> geofenceList= event.getTriggeringGeofences();
        for(Geofence geofence: geofenceList){
            geofenceid= Integer.parseInt(geofence.getRequestId());
            Log.d("GEOBROAD", "Challenge ID: "+geofence.getRequestId());
        }
        int transType= event.getGeofenceTransition();
        switch (transType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                BaseClass.triggeredChallenge= geofenceid;
                Log.d("ENTER","Geofence Triggered");
                nHelper.sendHighPriorityNotification("Around U!", "Play and Earn your Coupon!", UserHome.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                BaseClass.triggeredChallenge= geofenceid;
                Log.d("DWELL","Geofence Triggered222");
//                nHelper.sendHighPriorityNotification("Around you!", "Play and Earn your Coupon!", UserHome.class);
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                BaseClass.triggeredChallenge=  -1;
                Log.d("EXIT","Geofence Triggered33333");
        }

    }
}
