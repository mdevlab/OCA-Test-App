package io.mdevlab.ocatraining.util;

import android.util.Log;

import java.util.Set;

/**
 * Created by husaynhakeem on 5/5/17.
 */

public class Helper {

    private static final int FIRST_NOTIFICATION_TIME = 5;
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


    public static long frequencyInMillis(int frequency) {
        return frequency * DAY * HOUR * MINUTE * SECOND;
    }


    public static long timeUntilFirstNotification() {
        return MINUTE * SECOND;
    }


    private static boolean currentTimeHasPassedFirstNotificationTime() {
        return false;
    }
}
