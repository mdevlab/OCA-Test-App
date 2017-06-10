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
import io.mdevlab.ocatraining.activity.RandomTestActivity;
import io.mdevlab.ocatraining.model.Question;

import static io.mdevlab.ocatraining.activity.FavoriteQuestionActivity.CURRENT_QUESTION;

/**
 * Created by husaynhakeem on 5/9/17.
 */

public class NotificationBuilder {

    final String TAG = NotificationBuilder.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;


    /**
     * @param context
     * @param questionId
     * @param notificationTitle
     * @param notificationBody
     */
    public void sendNotification(@Nullable Context context, int questionId, String notificationTitle, String notificationBody) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builtNotification(context, questionId, notificationTitle, notificationBody));
        } catch (NullPointerException e) {
            Log.e(TAG, (e.getMessage() != null) ? e.getMessage() : context.getString(R.string.notification_build_error));
        }
    }


    /**
     * @param context
     * @param notificationTitle
     * @param notificationBody
     * @return Build notification object
     */
    private Notification builtNotification(Context context, int questionId, String notificationTitle, String notificationBody) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_oca_java_prep)
                .setContentTitle(notificationTitle)
                .setContentIntent(intentOpenedByNotification(context, questionId))
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationBody))
                .build();
    }


    /**
     * @param context
     * @param questionId
     * @return Pending intent to the intent that will be opened after clicking on
     * the notification
     */
    private PendingIntent intentOpenedByNotification(Context context, int questionId) {

        if (questionId == Question.NO_QUESTION_ID) {
            return intentOpenedByFirebaseNotification(context);
        }

        return intentOpenedByQuestionNotification(context, questionId);
    }


    private PendingIntent intentOpenedByFirebaseNotification(Context context) {

        // Intent of activity user will be redirected to
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Pending intent to open previously defined intent
        return PendingIntent.getActivity(context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private PendingIntent intentOpenedByQuestionNotification(Context context, int questionId) {

        // Intent of activity user will be redirected to
        Intent resultIntent = new Intent(context, RandomTestActivity.class);
        resultIntent.putExtra(CURRENT_QUESTION, questionId);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Pending intent to open previously defined intent
        return PendingIntent.getActivity(context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
