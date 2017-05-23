package io.mdevlab.ocatraining.notification;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

import io.mdevlab.ocatraining.modelManager.QuestionManager;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsJob extends Job {


    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        switch (params.getTag()) {
            case JobScheduler.FIRST_NOTIFICATION_TAG:
                showNotification();
                break;
            case JobScheduler.PERIODIC_NOTIFICATIONS_SCHEDULER_TAG:
                schedulePeriodicNotifications();
                break;
            case JobScheduler.PERIODIC_NOTIFICATION_TAG:
                showNotification();
                break;
        }

        return null;
    }


    private void showNotification() {
        new NotificationBuilder().sendNotification(getContext(), notificationQuestion());
    }


    private String notificationQuestion() {
        return QuestionManager.getRandomQuestionForDisplay();
    }


    private void schedulePeriodicNotifications() {
        NotificationsManager.schedulePeriodicNotifications(getContext());
    }
}
