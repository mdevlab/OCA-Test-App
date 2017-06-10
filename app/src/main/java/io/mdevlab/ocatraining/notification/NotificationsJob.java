package io.mdevlab.ocatraining.notification;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;

import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;

import static io.mdevlab.ocatraining.modelManager.QuestionManager.buildQuestionForDisplay;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsJob extends Job {


    private static final String NOTIFICATION_TITLE = "Reviewing time !";


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

        Question question = notificationQuestion();

        if (question != null)
            new NotificationBuilder().sendNotification(getContext(),
                    question.getId(),
                    NOTIFICATION_TITLE,
                    buildQuestionForDisplay(question));
    }


    private Question notificationQuestion() {
        return QuestionManager.getRandomQuestionForDisplay();
    }


    private void schedulePeriodicNotifications() {
        NotificationsManager.schedulePeriodicNotifications(getContext());
    }
}
