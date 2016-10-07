package headfirst.com.projectapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayNotifications extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 5453;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String notifID = getIntent().getExtras().getString("NotifID");


        triggerNotification(notifID);


        finish();
    }
    private void triggerNotification(final String text){   //Notification Triggering Function
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.iitlogo);
        Intent intent = new Intent(this, LoginActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginActivity.class);   // Creates a virtual stack so that when back key is pressed main activity will be displayed
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                );
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        CharSequence tickerText="Time To Look At The Courses";
        Notification notification = new Notification.Builder(this)
                .setLargeIcon(bm)
                .setSmallIcon(R.drawable.iitlogo)             // Setting Notification Icon
                .setContentTitle(getString(R.string.notification))// Setting Title of Notification
                .setAutoCancel(true)// Makes this notification automatically dismissed when the user touches it
                .setPriority(Notification.PRIORITY_MAX)           // Setting priority to Max
                .setContentIntent(pendingIntent)
                .setTicker(text)
                .setContentText(text)
                .setLights(0xFF0000FF, 100, 3000)
                .setSound(soundUri)
                .build();

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager =                        //Notification Manager
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
        // Log.d(TAG, "Notification sent successfully.");

    }
}
