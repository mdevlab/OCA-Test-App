package io.mdevlab.ocatraining.notification;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {

        if (tag.equals(JobScheduler.FIRST_NOTIFICATION_TAG) ||
                tag.equals(JobScheduler.PERIODIC_NOTIFICATIONS_SCHEDULER_TAG) ||
                tag.equals(JobScheduler.PERIODIC_NOTIFICATION_TAG))
            return new NotificationsJob();

        return null;
    }
}
