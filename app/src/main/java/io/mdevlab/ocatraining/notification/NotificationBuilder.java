package io.mdevlab.ocatraining.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.activity.MainActivity;

/**
 * Created by husaynhakeem on 5/9/17.
 */

public class NotificationBuilder {

    final String TAG = NotificationBuilder.class.getSimpleName();
    private final int NOTIFICATION_ID = 001;
    private final String NOTIFICATION_TITLE = "Reviewing time !";


    /**
     * @param context
     * @param notificationBody
     */
    public void sendNotification(@Nullable Context context, String notificationBody) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builtNotification(context, notificationBody));
        } catch (NullPointerException e) {
            Log.e(TAG, (e.getMessage() != null) ? e.getMessage() : context.getString(R.string.notification_build_error));
        }
    }


    /**
     * @param context
     * @param notificationBody
     * @return Build notification object
     */
    private Notification builtNotification(Context context, String notificationBody) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(notificationBody)
                .setContentIntent(intentOpenedByNotification(context))
                .setAutoCancel(true)
                .build();
    }


    /**
     * @param context
     * @return Pending intent to the intent that will be opened after clicking on
     * the notification
     */
    private PendingIntent intentOpenedByNotification(Context context) {

        // Intent of activity user will be redirected to
        Intent resultIntent = new Intent(context, MainActivity.class);

        // Pending intent to open previously defined intent
        return PendingIntent.getActivity(context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
