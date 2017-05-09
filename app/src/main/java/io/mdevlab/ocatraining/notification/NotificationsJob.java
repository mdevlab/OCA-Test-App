package io.mdevlab.ocatraining.notification;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsJob extends Job {


    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        if (params == null)
            return null;

        switch (params.getTag()) {
            case JobScheduler.FIRST_NOTIFICATION_TAG:
                showNotification();
                break;
            case JobScheduler.PERIODIC_NOTIFICATIONS_SCHEDULER_TAG:
                break;
            case JobScheduler.PERIODIC_NOTIFICATION_TAG:
                showNotification();
                break;
        }

        return null;
    }


    private void showNotification() {
        new NotificationBuilder().sendNotification(getContext());
    }


    private void schedulePeriodicNotifications() {
        NotificationsManager.
    }
}
