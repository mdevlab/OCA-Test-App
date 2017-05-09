package io.mdevlab.ocatraining.notification;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

/**
 * Created by husaynhakeem on 5/9/17.
 */

public class JobScheduler {

    public static final String FIRST_NOTIFICATION_TAG = "first notification tag";
    public static final String PERIODIC_NOTIFICATIONS_SCHEDULER_TAG = "periodic notifications scheduler tag";
    public static final String PERIODIC_NOTIFICATION_TAG = "periodic notification tag";


    /**
     * @param when       When the job should run
     * @param isPeriodic Whether the job should repeat itself periodically
     */
    public static void scheduleJob(long when, boolean isPeriodic, String tag) {
        if (isPeriodic)
            schedulePeriodicJob(when, tag);
        else
            scheduleOneTimeJob(when, tag);
    }


    /**
     * @param period The job should run once every "period"
     * @param tag    Job tag
     */
    private static void schedulePeriodicJob(long period, String tag) {
        new JobRequest.Builder(tag)
                .setPeriodic(period)
                .build()
                .schedule();
    }


    /**
     * @param runTime The job should run only once at "runtime"
     * @param tag     Job tag
     */
    private static void scheduleOneTimeJob(long runTime, String tag) {
        new JobRequest.Builder(tag)
                .setExact(runTime)
                .build()
                .schedule();
    }


    /**
     * Cancel all scheduled jobs
     */
    public static void cancelScheduledJobs() {
        JobManager.instance().cancelAll();
    }
}
