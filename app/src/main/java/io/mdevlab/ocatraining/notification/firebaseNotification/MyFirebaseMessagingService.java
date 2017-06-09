package io.mdevlab.ocatraining.notification.firebaseNotification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;

import io.mdevlab.ocatraining.notification.NotificationBuilder;

/**
 * Created by husaynhakeem on 6/8/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Notification notification = remoteMessage.getNotification();

        if (notification != null)
            new NotificationBuilder().sendNotification(getApplicationContext(),
                    notification.getTitle(),
                    notification.getBody());
    }
}
