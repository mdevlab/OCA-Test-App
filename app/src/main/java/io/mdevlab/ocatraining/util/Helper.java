package io.mdevlab.ocatraining.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class Helper {

    private static final int FIRST_NOTIFICATION_HOUR = 17;
    private static final int FIRST_NOTIFICATION_MINUTE = 0;
    private static final int FIRST_NOTIFICATION_SECOND = 0;
    private static final int DAY = 24;
    private static final int HOUR = 60;
    private static final int MINUTE = 60;
    private static final int SECOND = 1000;

    static final String TAG = Helper.class.getSimpleName();


    /**
     * @param strings Set of string objects
     * @return The parameter 'strings' as a set of integers
     */
    public static int[] stringsToIntegers(Set<String> strings) {
        if (strings == null)
            return null;
        try {
            int[] integers = new int[strings.size()];
            int index = 0;
            for (String s : strings) {
                integers[index++] = Integer.parseInt(s);
            }
            return integers;
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


    /**
     * @param days Number of days to be converted to milliseconds
     * @return Number of days converted to milliseconds
     */
    public static long daysToMs(int days) {
        return (long) (days * DAY * HOUR * MINUTE * SECOND);
    }


    /**
     * @param frequency Value of notifications frequency in days
     * @return Converted value of the frequency in milliseconds
     */
    public static long frequencyInMillis(int frequency) {
        return frequency * DAY * HOUR * MINUTE * SECOND;
    }


    /**
     * @return Number of milliseconds until the time the first notification
     * is supposed to be sent to the user
     */
    public static long timeUntilFirstNotification() {
        long oneDayInMillis = DAY * HOUR * MINUTE * SECOND;
        long notificationTime = getNotificationTime();
        return oneDayInMillis + notificationTime;
    }


    /**
     * @return Number of milliseconds until the hour of the first notification
     * (Value may be negative or positive depending on the current hour of the day)
     */
    private static long getNotificationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, FIRST_NOTIFICATION_HOUR);
        calendar.set(Calendar.MINUTE, FIRST_NOTIFICATION_MINUTE);
        calendar.set(Calendar.SECOND, FIRST_NOTIFICATION_SECOND);
        return calendar.getTimeInMillis() - System.currentTimeMillis();
    }


    public static String fromAssetsFileToString(Context context, String filename) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }

            reader.close();
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
