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
 
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
 
import com.example.coupons.MainActivity;
import com.example.coupons.AddChallenge;
import com.example.coupons.R;
 
 
public class NotificationReceiver extends BroadcastReceiver {
 
    @Override 
    public void onReceive(Context context, Intent intent) {
 
 
        sendNotification(context);
 
    }
 
    private void sendNotification(Context context) {
        Intent intent = new Intent(context, Challenge.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
 
        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("Play to win a discount!")
                        .setContentText("Click here to play and win a discount")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
 
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
 
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
 
        notificationManager.notify(200, notificationBuilder.build());
    }
} 
   