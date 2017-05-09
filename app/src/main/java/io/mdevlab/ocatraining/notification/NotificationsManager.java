package io.mdevlab.ocatraining.notification;

import java.util.Set;

import io.mdevlab.ocatraining.util.Helper;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class NotificationsManager {


    private static boolean notificationEnabled = true;


    /**
     * Method that handles turning on/off notifications
     *
     * @param notificationsAreEnabled Whether the user turned on/off notifications
     * @param frequency               Frequency of the notifications
     * @param chapters                Chapters to choose questions from in the notifications
     */
    public static void handleNotifications(boolean notificationsAreEnabled, int frequency, Set<String> chapters) {
        if (!notificationsAreEnabled) {
            turnOffNotifications();
            return;
        }
        turnOnNotifications(frequency, chapters);
    }


    private static void turnOffNotifications() {
        notificationEnabled = false;
        JobScheduler.cancelScheduledJobs();
    }


    public static void turnOnNotifications(int frequency, Set<String> chapters) {
        notificationEnabled = true;

        scheduleFirstNotification(chapters);
        schedulePeriodicJobsScheduler();
        schedulePeriodicNotifications(frequency, chapters);
    }


    private static void scheduleFirstNotification(Set<String> chapters) {
        JobScheduler.scheduleJob(Helper.timeUntilFirstNotification(), false, JobScheduler.FIRST_NOTIFICATION_TAG);
    }


    private static void schedulePeriodicJobsScheduler() {
        JobScheduler.scheduleJob(Helper.timeUntilFirstNotification(), false, JobScheduler.PERIODIC_NOTIFICATIONS_SCHEDULER_TAG);
    }


    public static void schedulePeriodicNotifications(int frequency, Set<String> chapters) {
        JobScheduler.scheduleJob(Helper.frequencyInMillis(frequency), true, JobScheduler.PERIODIC_NOTIFICATION_TAG);
    }


    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }


    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }
}
