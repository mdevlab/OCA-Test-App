package io.mdevlab.ocatraining.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.mdevlab.ocatraining.R;

/**
 * Created by husaynhakeem on 5/10/17.
 */

public class UtilSharedPreferences {

    private final static boolean DEFAULT_NOTIFICATIONS_STATUS = true;
    private final static int DEFAULT_NOTIFICATIONS_FREQUENCY = 2;
    private final static Set<String> DEFAULT_NOTIFICATIONS_CHAPTERS = new HashSet<>(Arrays.asList(new String[]{"0"}));

    private static UtilSharedPreferences instance;
    private static SharedPreferences preferences;
    private static Context context;


    private UtilSharedPreferences() {
        if (context != null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static UtilSharedPreferences with(Context newContext) {
        context = newContext;
        if (instance == null)
            instance = new UtilSharedPreferences();
        return instance;
    }


    public boolean notificationsAreEnabled() {
        return preferences.getBoolean(
                context.getString(R.string.notifications_toggle_key),
                DEFAULT_NOTIFICATIONS_STATUS
        );
    }


    public int getNotificationsFrequency() {
        try {
            return Integer.parseInt(preferences.getString(
                    context.getString(R.string.notifications_frequency_key),
                    String.valueOf(DEFAULT_NOTIFICATIONS_FREQUENCY)
            ));
        } catch (NumberFormatException e) {
            return DEFAULT_NOTIFICATIONS_FREQUENCY;
        }
    }


    public Set<String> getNotificationsChapters() {
        return preferences.getStringSet(
                context.getString(R.string.notifications_chapters_key),
                DEFAULT_NOTIFICATIONS_CHAPTERS
        );
    }
}
