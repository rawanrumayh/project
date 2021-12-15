package com.example.coupons.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.coupons.globals.BaseClass;
import com.example.coupons.notifications.NotificationHelper;
import com.example.coupons.user.Challenge;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
//import com.example.polly.activities.mypolls;
import com.example.coupons.user.Challenge;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//        import com.example.polly.R;
            import com.example.coupons.R;
//        import com.example.polly.activities.MainActivity;
//        import com.example.polly.activities.polldetails;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
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
            //notificationHelper.sendHighPriorityNotification(challange.class);
        }
        int transType= event.getGeofenceTransition();
        switch (transType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                BaseClass.triggeredChallenge= geofenceid;
                Log.d("ENTER","Geofence Triggered");
                notificationHelper.sendHighPriorityNotification(Challenge.class,geofenceid );
//                sendNotification(context);

                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                BaseClass.triggeredChallenge= geofenceid;
                Log.d("DWELL","Geofence Triggered222");
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                BaseClass.triggeredChallenge=  -1;
                Log.d("EXIT","Geofence Triggered33333");
        }

    }


//
//    private void sendNotification(Context context) {
//        Intent intent = new Intent(context, Challenge.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = context.getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(context, channelId)
//                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                        .setContentTitle("Play to win a discount!")
//                        .setContentText("Click here to play.")
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Notifications",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(200, notificationBuilder.build());
//    }

}



